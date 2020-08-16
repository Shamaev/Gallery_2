package com.geekbrains.gallery_v_02.presenter;

import com.geekbrains.gallery_v_02.AdapterPhotos;
import com.geekbrains.gallery_v_02.PhotoClick;
import com.geekbrains.gallery_v_02.dagger.App;
import com.geekbrains.gallery_v_02.entity.Hit;
import com.geekbrains.gallery_v_02.entity.Photo;
import com.geekbrains.gallery_v_02.model.Model;
import com.geekbrains.gallery_v_02.view.HomeView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {
    @Inject
    Model model;

    private List<Hit> list;

    @Override
    protected void onFirstViewAttach() {
        App.getAppComponent().inject(this);
        getAllPhoto();
    }

    private void getAllPhoto() {
        Observable<Photo> single = model.requestServer();

        Disposable disposable = single.observeOn(AndroidSchedulers.mainThread()).subscribe(photos -> {
                list = photos.hits;
                getViewState().getHits(list);
        }, throwable -> {

        });
    }

    public void refrashInfo(String url, String user) {
        model.setUrl(url);
        model.setUser(user);
    }
}
