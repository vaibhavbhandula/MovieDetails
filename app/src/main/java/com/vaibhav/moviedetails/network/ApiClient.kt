package com.vaibhav.moviedetails.network

import com.vaibhav.moviedetails.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
object ApiClient {

    private const val BASE_URL = "http://www.omdbapi.com/"

    private var retrofit: Retrofit? = null

    private var client: OkHttpClient.Builder? = null

    private fun getOkHttpClient(): OkHttpClient {
        if (client == null) {
            client = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BASIC
                client?.addNetworkInterceptor(interceptor)
            }
            client?.addInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("apikey", "8336b2a5").build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
        }
        return client!!.build()
    }

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }

}
