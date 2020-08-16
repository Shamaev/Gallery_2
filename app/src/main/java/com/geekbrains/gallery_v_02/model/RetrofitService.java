package com.geekbrains.gallery_v_02.model;

import com.geekbrains.gallery_v_02.entity.Photo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("api")
    Observable<Photo> getPhoto(@Query("key") String key);
}
