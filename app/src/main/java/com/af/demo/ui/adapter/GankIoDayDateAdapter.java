package com.af.demo.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.af.demo.R;
import com.af.demo.api.Bean.GankIoDayDataBean;
import com.af.demo.api.Bean.GankIoDayDataMultpleItem;
import com.af.demo.ui.holder.SuperViewHolder;
import com.blankj.utilcode.util.StringUtils;

/**
 * 作者：thf on 2018/5/28 0028 14:51
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class GankIoDayDateAdapter extends BaseMultiAdapter<GankIoDayDataMultpleItem> {

	public GankIoDayDateAdapter(Context context) {
		super(context);
		addItemType(GankIoDayDataMultpleItem.TITLE, R.layout.adapter_title_gank_io_day_data);
		addItemType(GankIoDayDataMultpleItem.CONTENT, R.layout.adapter_content_gank_io_day_data);
		addItemType(GankIoDayDataMultpleItem.IMG, R.layout.adapter_image_list);
	}

	@Override
	public void onBindItemHolder(SuperViewHolder holder, int position) {
		GankIoDayDataMultpleItem multpleItem = mDataList.get(position);
		switch (multpleItem.getItemType()) {
			case GankIoDayDataMultpleItem.TITLE:
				bindTitle(holder, multpleItem);
				break;
			case GankIoDayDataMultpleItem.CONTENT:
				bindContent(holder, multpleItem);
				break;
			case GankIoDayDataMultpleItem.IMG:
				bindImage(holder, multpleItem);
				break;
			default:

				break;
		}
	}

	private void bindTitle(SuperViewHolder holder, GankIoDayDataMultpleItem item) {
		TextView tvTitle = holder.getView(R.id.tv_title);
		tvTitle.setText("●  " + ((item.getData().toString()).trim()));
	}

	private void bindContent(SuperViewHolder holder, GankIoDayDataMultpleItem item) {
		TextView tvDesc = holder.getView(R.id.tv_desc);
		TextView tvAuthor = holder.getView(R.id.tv_author);
		GankIoDayDataBean.ItemBean itemData = (GankIoDayDataBean.ItemBean) item.getData();
		tvDesc.setText(itemData.getDesc());
		if (!StringUtils.isEmpty(itemData.getWho())) {
			tvAuthor.setText("(" + itemData.getWho().trim() + ")");
		}
	}


	private void bindImage(SuperViewHolder holder, GankIoDayDataMultpleItem item) {

	}
}
