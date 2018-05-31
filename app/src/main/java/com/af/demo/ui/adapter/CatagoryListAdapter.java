package com.af.demo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.af.demo.R;
import com.af.demo.api.Bean.CategoryListBean;
import com.af.demo.ui.holder.SuperViewHolder;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

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
public class CatagoryListAdapter extends ListBaseAdapter<CategoryListBean> {
	private boolean isIinitRecyclerView = false;
	private CategoryImagesAdapter mImagesAdapter;

	public CatagoryListAdapter(Context context) {
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
		LRecyclerView lRecyclerView = (LRecyclerView) holder.getView(R.id.rv_images);
		//只让RecyclerView走一次
		if (!isIinitRecyclerView) {
			lRecyclerView.setPullRefreshEnabled(false);
			lRecyclerView.setLoadMoreEnabled(false);
			lRecyclerView.setHasFixedSize(true);
			lRecyclerView.setLayoutManager(new LinearLayoutManager(lRecyclerView.getContext(),
					RecyclerView.HORIZONTAL,
					false));
			mImagesAdapter = new CategoryImagesAdapter(lRecyclerView.getContext());
			lRecyclerView.setAdapter(new LRecyclerViewAdapter(mImagesAdapter));
		}
		if (bean.getImages().size() > 0) {
			mImagesAdapter.setDataList(bean.getImages());
			mImagesAdapter.notifyDataSetChanged();
		}
	}
}
