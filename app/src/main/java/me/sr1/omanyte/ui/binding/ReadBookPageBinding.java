package me.sr1.omanyte.ui.binding;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.roalab.devkit.ui.ViewBinding;

import me.sr1.omanyte.R;
import me.sr1.omanyte.base.util.LogUtil;

/**
 * 阅读书籍页面布局绑定
 * @author SR1
 */

public class ReadBookPageBinding extends ViewBinding {

    private static final String TAG = "ReadBookPageBinding";

    public WebView BookView;

    public ReadBookPageBinding(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, parent, R.layout.page_read_book);

        BookView = $(R.id.book_view);
        BookView.getSettings().setJavaScriptEnabled(true);
        BookView.setWebChromeClient(new WebChromeClient() {

        });
        BookView.setWebViewClient(new WebViewClient() {

        });
    }

    public void setUrl(String url) {
        LogUtil.i(TAG, "setUrl: " + url);
        BookView.loadUrl(url);
        BookView.loadUrl("javascript:alert('test');");
    }
}
