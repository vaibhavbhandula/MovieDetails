package com.vaibhav.moviedetails.network;

import com.vaibhav.moviedetails.BuildConfig;
import com.vaibhav.moviedetails.commons.Utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class ApiClient {

    private static final String BASE_URL = "https://api.non.sa/";

    private static Retrofit retrofit = null;

    private static OkHttpClient.Builder client = null;

    private static OkHttpClient getOkHttpClient() {
        if (client == null) {
            client = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
                client.addNetworkInterceptor(interceptor);
            }
            client.addInterceptor(chain -> {
                Request request = chain.request().newBuilder().headers(Utils.getHeaders()).build();
                return chain.proceed(request);
            });
        }
        return client.build();
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
