package me.sr1.omanyte.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import me.sr1.omanyte.base.util.LogUtil;
import me.sr1.omanyte.base.util.UnitTestBase;
import me.sr1.omanyte.enity.Book;
import me.sr1.omanyte.model.base.PagingLoader;

/**
 * 所有书籍的分页拉取器测试
 * @author SR1
 */
public class TotalBookPagingLoaderTest extends UnitTestBase {

    private static final String TAG = "TotalBookPagingLoaderTest";

    @Test
    public void loadTotalBookFirst() throws Exception {
        TotalBookPagingLoader loader = new TotalBookPagingLoader();

        Assert.assertTrue(loader.hasMore());
        Assert.assertEquals(0, loader.getTotalDataList().size());

        final CountDownLatch latch = new CountDownLatch(1);
        loader.load(new PagingLoader.OnPagingCallback<Book>() {
            @Override
            public void onSuccess(List<Book> newDataList, boolean hasMore) {
                LogUtil.i(TAG, "onSuccess: hasMore=" + hasMore);
                latch.countDown();
            }

            @Override
            public void onError(String errorMessage) {
                LogUtil.i(TAG, "onError: " + errorMessage);
                latch.countDown();
            }
        });
        latch.await();

        Assert.assertTrue(loader.hasMore());
    }
}