package Repository.DB

import Repository.Model.Picture
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Picture::class], version = 1, exportSchema = false)
abstract class PictureDatabase : RoomDatabase() {


    abstract fun getPictureDao() : PictureDAO


    companion object {
        private var INSTANCE : PictureDatabase? = null
        fun getDatabase(context : Context): PictureDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    PictureDatabase::class.java,
                    "pictures_app.db"
                ).build()
            }
                return INSTANCE as PictureDatabase
        }

    }

}