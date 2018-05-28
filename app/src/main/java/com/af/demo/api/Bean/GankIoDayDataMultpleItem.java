package com.af.demo.api.Bean;

import com.af.demo.ui.adapter.MultiItemEntity;

/**
 * Created by HaoFeng on 2018/5/28 0028.
 */

public class GankIoDayDataMultpleItem implements MultiItemEntity{
    public static final int TITLE = 1;
    public static final int IMG = 2;
    public static final int CONTENT = 3;
    private int itemType;
    private int spanSize;

    public GankIoDayDataMultpleItem(int itemType) {
        this.itemType = itemType;
    }

    public GankIoDayDataMultpleItem(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
