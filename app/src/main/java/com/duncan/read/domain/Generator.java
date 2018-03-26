package com.duncan.read.domain;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;



/**
 * Created by Duncan Lim on 31/7/2017.
 */

public class Generator {
    public static final String API_BASE_URL = "https://hacker-news.firebaseio.com/v0/";

    private static final HttpLoggingInterceptor.Level LOG_LEVEL = HttpLoggingInterceptor.Level.BODY;
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(LoganSquareConverterFactory.create());
    private static OkHttpClient okHttpClient = new OkHttpClient();
    public static <S> S create(Class<S> apiClass) {

        Retrofit retrofit = builder.client(okHttpClient).build();
        return retrofit.create(apiClass);

    }
}
