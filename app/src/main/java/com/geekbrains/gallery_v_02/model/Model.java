package com.geekbrains.gallery_v_02.model;

import com.geekbrains.gallery_v_02.entity.Photo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model {
    private String url = "";
    private String user = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    public Observable<Photo> requestServer() {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);

        //https://pixabay.com/api/?key=16713608-dff6dd4b9da45bd266bf57aab
        RetrofitService api = new Retrofit.Builder()
                .baseUrl("https://pixabay.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(RetrofitService.class);

        return api.getPhoto("16713608-dff6dd4b9da45bd266bf57aab").subscribeOn(Schedulers.io());
    }
}
