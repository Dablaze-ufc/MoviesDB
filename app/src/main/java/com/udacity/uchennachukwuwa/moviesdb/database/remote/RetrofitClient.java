package com.udacity.uchennachukwuwa.moviesdb.database.remote;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.BASE_URL;

/**
 * Created By ChukwuwaUchenna
 */

public class RetrofitClient {
    private static Retrofit mRetrofit;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit getClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

        }
        return mRetrofit;
    }
}
