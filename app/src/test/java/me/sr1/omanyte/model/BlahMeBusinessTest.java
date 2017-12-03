package me.sr1.omanyte.model;

import android.support.v4.util.Pair;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import me.sr1.omanyte.base.util.LogUtil;
import me.sr1.omanyte.base.util.UnitTestBase;
import me.sr1.omanyte.enity.Book;
import me.sr1.omanyte.enity.Category;

/**
 * @author SR1
 */
public class BlahMeBusinessTest extends UnitTestBase {

    private static final String TAG = "BlahMeBusinessTest";

    @Test
    public void testGetCategories() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        BlahMeBusiness business = new BlahMeBusiness();
        business.loadCategories(new BusinessCallback<List<Category>, String>() {
            @Override
            public void onSuccess(List<Category> data) {
                LogUtil.i(TAG, "testGetCategories >> onSuccess: " + data);
                latch.countDown();
            }

            @Override
            public void onError(String error) {
                LogUtil.i(TAG, "testGetCategories >> onError: " + error);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void testGetBooks() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        BlahMeBusiness business = new BlahMeBusiness();
        business.loadAllBook(0, new BusinessCallback<Pair<List<Book>, Integer>, String>(){
            @Override
            public void onSuccess(Pair<List<Book>, Integer> data) {
                latch.countDown();
            }

            @Override
            public void onError(String error) {
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void testGetSpecifiedCategoryBooks() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        BlahMeBusiness business = new BlahMeBusiness();
        business.loadSpecifiedCategoryBooks("20", 0, new BusinessCallback<Pair<List<Book>, Integer>, String>() {
            @Override
            public void onSuccess(Pair<List<Book>, Integer> data) {
                latch.countDown();
            }

            @Override
            public void onError(String error) {
                latch.countDown();
            }
        });

        latch.await();
    }
}