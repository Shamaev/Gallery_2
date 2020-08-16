package com.geekbrains.gallery_v_02.dagger;

import android.app.Application;

import com.geekbrains.gallery_v_02.model.Model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Model provideModel() {
        return new Model();
    }
}