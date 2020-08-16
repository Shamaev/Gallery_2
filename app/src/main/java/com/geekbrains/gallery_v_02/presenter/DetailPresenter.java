package com.geekbrains.gallery_v_02.presenter;


import com.geekbrains.gallery_v_02.dagger.App;
import com.geekbrains.gallery_v_02.model.Model;
import com.geekbrains.gallery_v_02.view.DetailView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {
    @Inject
    Model model;


    @Override
    protected void onFirstViewAttach() {
        App.getAppComponent().inject(this);
        getUrl();
    }

    private void getUrl() {
        String url = model.getUrl();
        String user = model.getUser();
        getViewState().getData(url, user);
    }
}
