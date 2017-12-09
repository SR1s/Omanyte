package me.sr1.omanyte.ui.binding;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roslab.devkit.ui.ViewBinding;

import me.sr1.omanyte.R;

/**
 * 目录条目绑定
 * @author SR1
 */

public class CatalogItemBinding extends ViewBinding {

    public final TextView Title;

    public CatalogItemBinding(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, parent, R.layout.item_catalog);

        Title = $(R.id.catalog_title);
    }

    public void setTitle(String title) {
        Title.setText(title);
    }
}
