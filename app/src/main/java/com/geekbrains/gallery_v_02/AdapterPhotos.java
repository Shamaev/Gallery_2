package com.geekbrains.gallery_v_02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geekbrains.gallery_v_02.dagger.App;
import com.geekbrains.gallery_v_02.entity.Hit;
import com.geekbrains.gallery_v_02.model.Model;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterPhotos extends RecyclerView.Adapter<AdapterPhotos.ViewHolder> {
    private List<Hit> hits;
    private PhotoClick listener;

    @Inject
    Model model;

    public AdapterPhotos(List<Hit> hits) {
        this.hits = hits;
    }

    public void registerCallBack(PhotoClick listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemPhoto)
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        App.getAppComponent().inject(this);
        return new AdapterPhotos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaderPhotos(
                holder.itemView,
                hits.get(position).webformatURL,
                holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = hits.get(position).webformatURL;
                String user = hits.get(position).user;
                listener.photoClick(url, user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hits.size();
    }

    private void LoaderPhotos(View itemView, String url, ImageView imageView) {
        Glide
                .with(itemView)
                .load(url)
                .into(imageView);
    }
}
