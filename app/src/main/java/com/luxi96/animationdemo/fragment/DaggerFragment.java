package com.luxi96.animationdemo.fragment;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luxi96.animationdemo.R;
import com.luxi96.animationdemo.util.LogUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaggerFragment extends BaseFragment {

    Retrofit retrofit;
    OkHttpClient client;
    Gson gson;
    Picasso picasso;

    @Override
    public int getResId() {
        return R.layout.fragment_dagger;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        File cacheFile = new File(getContext().getCacheDir(),"HttpCache");
        cacheFile.mkdirs();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.d(message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Cache cache = new Cache(cacheFile,10 * 1000 * 1000);

        client = new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        // todo 自定义 httpdownloader
        picasso = new Picasso.Builder(getContext())
                .downloader()
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    void populateUsers(){

    }

}
