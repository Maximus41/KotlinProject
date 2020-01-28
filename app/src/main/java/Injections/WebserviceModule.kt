package Injections

import Repository.DB.LocalCache
import Repository.DB.PictureDAO
import Repository.DB.PictureDatabase
import Repository.PictureRepo
import Repository.Services.GalleryService
import Util.BASE_URL
import Util.CONNECTION_TIME_OUT
import android.content.Context
import androidx.room.RoomDatabase
import com.kotlin.testapp.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class WebserviceModule(context: Context) {

    private var app = context

    @Singleton
    @Provides
    fun getRetrofitInstance(client : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun getOkHttpClientInstance(loggingInterceptor : HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECTION_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun getHttpLoggingInterceptor() : HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        return interceptor
    }

    @Singleton
    @Provides
    fun getGalleryService(retrofit : Retrofit) : GalleryService {
        return retrofit.create(GalleryService :: class.java)
    }

    @Singleton
    @Provides
    fun getPictureRepo(cache : LocalCache, service : GalleryService) : PictureRepo {
        return PictureRepo(cache, service)
    }

    @Singleton
    @Provides
    fun getPictureDao() : PictureDAO {
        return PictureDatabase.getDatabase(app).getPictureDao()
    }

    @Singleton
    @Provides
    fun getLocalCache(dao : PictureDAO) : LocalCache {
        return LocalCache(dao)
    }
}