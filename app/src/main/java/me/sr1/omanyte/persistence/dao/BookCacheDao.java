package me.sr1.omanyte.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import me.sr1.omanyte.persistence.entity.BookCacheBean;

/**
 * 书籍缓存数据操作对象
 * @author SR1
 */
@Dao
public interface BookCacheDao {

    @Insert
    void addBookCache(BookCacheBean... bookInfoJson);

    @Query("SELECT * FROM BookCacheBean")
    List<BookCacheBean> getAll();
}
