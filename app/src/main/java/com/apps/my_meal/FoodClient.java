
package com.apps.my_meal;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodClient {

    // TODO 10 Add BASE url
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    public static Retrofit getFoodClient() {
        // TODO 11 configures Retrofit
        return new Retrofit.Builder().baseUrl(BASE_URL).client(provideOkHttp()).addConverterFactory(GsonConverterFactory.create()).build() ;
    }

    private static Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private static OkHttpClient provideOkHttp() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(provideLoggingInterceptor())
                .build();
    }
}
