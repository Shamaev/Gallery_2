package com.geekbrains.gallery_v_02.dagger;

import com.geekbrains.gallery_v_02.AdapterPhotos;
import com.geekbrains.gallery_v_02.presenter.DetailPresenter;
import com.geekbrains.gallery_v_02.presenter.HomePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(HomePresenter homePresenter);
    void inject(DetailPresenter detailPresenter);
    void inject(AdapterPhotos adapterPhotos);
}