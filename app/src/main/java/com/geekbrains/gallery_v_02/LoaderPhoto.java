package com.geekbrains.gallery_v_02;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoaderPhoto {
    private static OkHttpClient client = new OkHttpClient.Builder().build();

    public static Single<Bitmap> downloadImage(String url) {
        return Single.create(emitter -> {
            final Request request = new Request.Builder().url(url).build();
            final Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                final Bitmap bitmap = BitmapFactory.decodeStream(response.body()
                        .byteStream());
                if (bitmap != null) {
                    emitter.onSuccess(bitmap);
                } else {
                    emitter.onError(new RuntimeException("Error decoding bitmap"));
                }
            } else {
                emitter.onError(new RuntimeException("Error downloading bitmap, code = " + response.code()));
            }
        });
    }
}
