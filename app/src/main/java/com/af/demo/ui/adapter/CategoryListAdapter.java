package com.af.demo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.af.demo.R;
import com.af.demo.api.Bean.CategoryListBean;
import com.af.demo.ui.holder.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：thf on 2018/5/31 0031 16:58
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class CategoryListAdapter extends ListBaseAdapter<CategoryListBean> {
	private boolean isIinitRecyclerView = false;
	private CategoryImagesAdapter mImagesAdapter;
	private List<String> mImages;

	public CategoryListAdapter(Context context) {
		super(context);
	}

	@Override
	public int getLayoutId() {
		return R.layout.adapter_category_list;
	}

	@Override
	public void onBindItemHolder(SuperViewHolder holder, int position) {
		CategoryListBean bean = mDataList.get(position);
		((TextView) holder.getView(R.id.tv_desc)).setText(bean.getDesc());
		RecyclerView lRecyclerView = (RecyclerView) holder.getView(R.id.rv_images);
		//只让RecyclerView走一次
		if (bean.getUrl().endsWith(".jpg")) {//这是一个福利图片
			mImages = new ArrayList<>(1);
			mImages.add(bean.getUrl());

		}


		if (bean.getImages() != null && bean.getImages().size() > 0) {
			lRecyclerView.setVisibility(View.VISIBLE);
			mImagesAdapter = new CategoryImagesAdapter(mContext);
			mImages = bean.getImages();
			mImagesAdapter.setDataList(mImages);
			lRecyclerView.setAdapter(mImagesAdapter);
			lRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
					RecyclerView.HORIZONTAL,
					false));
			lRecyclerView.setHasFixedSize(true);
			mImagesAdapter.notifyDataSetChanged();
		} else {
			lRecyclerView.setVisibility(View.GONE);
		}
	}
}
