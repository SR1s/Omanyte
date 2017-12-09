package me.sr1.omanyte.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import me.sr1.omanyte.persistence.entity.BookDetailCacheBean;

/**
 * 书籍缓存数据操作对象
 * @author SR1
 */
@Dao
public interface BookDetailCacheDao {

    @Insert
    void addBookDetailCache(BookDetailCacheBean... bookDetailCache);

    @Query("SELECT * FROM BookDetailCacheBean")
    List<BookDetailCacheBean> getAll();

    @Query("SELECT * FROM BookDetailCacheBean WHERE book_id = :bookId LIMIT 1")
    BookDetailCacheBean getBookDetail(String bookId);
}
