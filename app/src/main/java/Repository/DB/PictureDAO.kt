package Repository.DB

import Repository.Model.Picture
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface PictureDAO {
    @Query("SELECT * FROM pictures_table WHERE pic_id > :fromIndex AND pic_id <= :toIndex")
    fun loadImages(fromIndex : Int, toIndex : Int) : Single<List<Picture>>

    @Query("SELECT * FROM pictures_table")
    fun loadAllImages() : Single<List<Picture>>

    @Query("SELECT * FROM pictures_table WHERE pic_id = :id")
    fun loadImageById(id : Int) : Single<Picture>

    @Query("SELECT COUNT(*) FROM pictures_table")
    fun getRowCount() : Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveImages(pics : List<Picture>)

    @Query("DELETE FROM pictures_table")
    fun deleteTableData()
}