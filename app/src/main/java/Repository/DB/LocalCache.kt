package Repository.DB

import Repository.Model.Picture
import Util.APPLICATION_TAG
import Util.PAGE_SIZE
import android.util.Log
import io.reactivex.Maybe
import io.reactivex.Single

class LocalCache(private val dao : PictureDAO) {

    fun fetchImages(page : Int) : Single<ArrayList<Picture>> {
        var from = (page - 1) * PAGE_SIZE
        var to = from + PAGE_SIZE
        return dao.loadImages(from, to).map { pics ->
            ArrayList(pics) }
    }

    fun fetchImageById(id : Int) : Single<Picture> {
        return dao.loadImageById(id)
    }

    fun insertImages(pics : Single<ArrayList<Picture>>) : Single<ArrayList<Picture>>{
        return pics.map { images ->
            dao.saveImages(images.toList())
            ArrayList(images)
        }
    }

    fun getRowCount() : Single<Int> {
        return dao.getRowCount()
    }
}