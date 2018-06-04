package com.af.demo.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.af.demo.R;
import com.af.demo.api.Bean.CategoryListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 作者：thf on 2018/6/4 0004 09:18
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class CategoryListAdapter extends BaseQuickAdapter<CategoryListBean, BaseViewHolder> {
	private CategoryImagesAdapter mImagesAdapter;

	public CategoryListAdapter(int layoutResId, @Nullable List<CategoryListBean> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, CategoryListBean item) {
		RecyclerView lRecyclerView = (RecyclerView) helper.getView(R.id.rv_images);
		helper.setText(R.id.tv_desc,item.getDesc());
		if (item.getImages() != null && item.getImages().size() > 0) {
			lRecyclerView.setVisibility(View.VISIBLE);
			List<String> images = item.getImages();
			mImagesAdapter = new CategoryImagesAdapter(R.layout.adapter_image_list,images);
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
