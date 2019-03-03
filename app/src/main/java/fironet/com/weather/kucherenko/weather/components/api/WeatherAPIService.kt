package fironet.com.weather.kucherenko.weather.components.api

import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import fironet.com.weather.kucherenko.weather.components.models.WeatherModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPIService {

    @GET(Const.CITY_PATH)
    fun search(@Query("q") query: String,
               @Query("appid") api_key: String): Flowable<WeatherModel>


    companion object Factory {
        fun create(): WeatherAPIService {
            val client = OkHttpClient.Builder()
                    .addInterceptor(logInterceptorBuilder())
                    .build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(Const.HOST)
                    .build()
            return retrofit.create(WeatherAPIService::class.java)
        }

        private fun logInterceptorBuilder(): LoggingInterceptor {
            return LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .request("RequestLog")
                    .response("ResponseLog")
                    .tag("info")
                    .addHeader("version", BuildConfig.VERSION_NAME)
                    .logger { lvl, tag, msg -> Log.w(tag, msg) }
                    .build()
        }
    }
}