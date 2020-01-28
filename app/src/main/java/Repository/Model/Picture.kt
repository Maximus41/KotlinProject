package Repository.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pictures_table")
data class Picture (

    @PrimaryKey val pic_id : Int,
    var avatar : String,
    var title : String,
    var desc : String,
    var uploader : String,
    var date : String,
    var loc : String
)