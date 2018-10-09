package com.vaibhav.moviedetails.network;

import com.vaibhav.moviedetails.BuildConfig;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class ApiClient {

    private static final String BASE_URL = "http://www.omdbapi.com/";

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
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("apikey", "8336b2a5").build();
                request = request.newBuilder().url(url).build();
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
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
