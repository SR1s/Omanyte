package me.sr1.omanyte.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import me.sr1.omanyte.OmanyteApp;
import me.sr1.omanyte.persistence.dao.BookCacheDao;
import me.sr1.omanyte.persistence.dao.BookDetailCacheDao;
import me.sr1.omanyte.persistence.entity.BookCacheBean;
import me.sr1.omanyte.persistence.entity.BookDetailCacheBean;

/**
 *
 */
@Database(entities = {BookCacheBean.class, BookDetailCacheBean.class}, version = 1)
public abstract class OmanyteDatabase extends RoomDatabase {

    private static OmanyteDatabase sInstance;

    public static synchronized OmanyteDatabase get() {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(
                    OmanyteApp.getApp(),
                    OmanyteDatabase.class,
                    "local_db"
            ).build();
        }
        return sInstance;
    }

    public abstract BookCacheDao getBookCacheDao();

    public abstract BookDetailCacheDao getBookDetailCacheDao();

}
