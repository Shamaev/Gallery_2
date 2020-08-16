package com.geekbrains.gallery_v_02.view;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geekbrains.gallery_v_02.LoaderPhoto;
import com.geekbrains.gallery_v_02.R;
import com.geekbrains.gallery_v_02.presenter.DetailPresenter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;


public class DetailFragment extends MvpAppCompatFragment implements DetailView {
    private Unbinder unbinder;
    private final String authorStart = getString(R.string.autor_detail);

    private CompositeDisposable subscriptions = new CompositeDisposable();
    private Bitmap currentBitmap;

    @InjectPresenter
    DetailPresenter detailPresenter;

    @BindView(R.id.author)
    TextView textView;

    @BindView(R.id.detailPhoto)
    ImageView imageView;

    @BindView(R.id.btn_down)
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void downloadOnClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGalleryPermission();
            }
        });
    }

    private void loaderPhoto(String url) {
        subscriptions
                .add(LoaderPhoto.downloadImage(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                bitmap -> {
                                    currentBitmap = bitmap;
                                },
                                throwable -> Log.e("", "onError", throwable)));
    }

    private void getGlide(String url) {
        Glide
                .with(this)
                .load(url)
                .into(imageView);
    }

    private void refactorAuthor(String author) {
        textView.setText(authorStart  + author);
    }

    private void saveToGallery() throws IOException {
        subscriptions.add(Single.fromCallable(() -> {
            MediaStore.Images.Media.insertImage(
                    getActivity().getContentResolver(),
                    currentBitmap,
                    String.valueOf(System.currentTimeMillis()),
                    "");

            return new Object();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bitmap -> {
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                    R.string.download, Toast.LENGTH_SHORT);
                            toast.show();
                        },
                        throwable -> Log.e("", "saveToGallery", throwable)
                ));
    }

    private void requestGalleryPermission() {
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        try {
                            saveToGallery();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(
                            com.karumi.dexter.listener.PermissionRequest permissionRequest,
                            PermissionToken permissionToken) {

                    }
                })
                .check();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getData(String url, String user) {
        getGlide(url);
        refactorAuthor(user);
        loaderPhoto(url);
        downloadOnClick();
    }
}
