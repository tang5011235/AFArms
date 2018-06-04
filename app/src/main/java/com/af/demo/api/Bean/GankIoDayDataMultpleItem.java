package com.af.demo.api.Bean;


import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by HaoFeng on 2018/5/28 0028.
 */

public class GankIoDayDataMultpleItem implements MultiItemEntity {
	public static final int TITLE = 1;
	public static final int IMG = 2;
	public static final int CONTENT = 3;
	private int itemType;

	public GankIoDayDataMultpleItem(int itemType) {
		this.itemType = itemType;
	}


	@Override
	public int getItemType() {
		return itemType;
	}

	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
