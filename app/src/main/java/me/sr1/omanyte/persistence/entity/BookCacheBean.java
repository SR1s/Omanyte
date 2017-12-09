package me.sr1.omanyte.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 书籍信息的缓存Bean
 * @author SR1
 */
@Entity
public class BookCacheBean {

    @PrimaryKey(autoGenerate = true)
    public int Id;

    @ColumnInfo(name = "json_data")
    public String JsonData;
}
