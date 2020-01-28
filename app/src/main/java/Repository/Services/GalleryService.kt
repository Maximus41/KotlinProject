package Repository.Services

import retrofit2.http.GET
import retrofit2.http.Path
import Repository.Model.Picture
import io.reactivex.Single
import retrofit2.http.Query

interface GalleryService {

    @GET("pictures")
    fun getPictures(@Query("page") pageNo : Int, @Query("limit") limit : Int)
            : Single<ArrayList<Picture>>

    @GET("pictures")
    fun getAllPictures() : Single<ArrayList<Picture>>

    @GET("pictures/{id}")
    fun getPictureById(@Path("id") id : Int) : Single<Picture>
}