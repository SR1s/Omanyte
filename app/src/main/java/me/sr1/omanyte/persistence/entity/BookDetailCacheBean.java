package me.sr1.omanyte.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 书籍详情缓存Bean
 * @author SR1
 */
@Entity
public class BookDetailCacheBean {

    @PrimaryKey(autoGenerate = true)
    public int Id;

    @ColumnInfo(name = "book_id")
    public String BookId;

    @ColumnInfo(name = "json_data")
    public String JsonData;
}
