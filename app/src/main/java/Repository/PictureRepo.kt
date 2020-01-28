package Repository

import Repository.DB.LocalCache
import Repository.Model.Picture
import Repository.Services.GalleryService
import Util.APPLICATION_TAG
import Util.PAGE_SIZE
import android.util.Log
import io.reactivex.Single

/*
Make decisions here, whether to fetch data from server or db
 */
class PictureRepo(private val cache : LocalCache, private val service : GalleryService){

    //Try to fetch data from db.If db is empty, load the db and then try again
    fun loadImages(page : Int) : Single<ArrayList<Picture>> {
        return cache.fetchImages(page).flatMap { pics  ->
            if(pics == null || pics.isEmpty())
                cache.insertImages(service.getPictures(page, PAGE_SIZE))
            else
                cache.fetchImages(page)}
    }

    fun loadImageById(id : Int) : Single<Picture> {
        return cache.fetchImageById(id)
    }

    fun getRowCountInDb() : Single<Int> {
        return cache.getRowCount()
    }
}