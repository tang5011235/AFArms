package com.af.demo.ui.adapter;

import android.widget.TextView;

import com.af.demo.R;
import com.af.demo.api.Bean.GankIoDayDataBean;
import com.af.demo.api.Bean.GankIoDayDataMultpleItem;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

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
public class GankIoDayDateAdapter extends BaseMultiItemQuickAdapter<GankIoDayDataMultpleItem, BaseViewHolder> {


	/**
	 * Same as QuickAdapter#QuickAdapter(Context,int) but with
	 * some initialization data.
	 *
	 * @param data A new list is created out of this one to avoid mutable list
	 */
	public GankIoDayDateAdapter(List<GankIoDayDataMultpleItem> data) {
		super(data);

		addItemType(GankIoDayDataMultpleItem.TITLE, R.layout.adapter_title_gank_io_day_data);
		addItemType(GankIoDayDataMultpleItem.CONTENT, R.layout.adapter_content_gank_io_day_data);
		addItemType(GankIoDayDataMultpleItem.IMG, R.layout.adapter_image_list);
	}

	@Override
	protected void convert(BaseViewHolder helper, GankIoDayDataMultpleItem item) {
		switch (helper.getItemViewType()) {
			case GankIoDayDataMultpleItem.TITLE:
				bindTitle(helper, item);
				break;
			case GankIoDayDataMultpleItem.CONTENT:
				bindContent(helper, item);
				break;
			default:

				break;
		}
	}

	private void bindTitle(BaseViewHolder helper, GankIoDayDataMultpleItem item) {
		TextView tvTitle = helper.getView(R.id.tv_title);
		tvTitle.setText("●  " + ((item.getData().toString()).trim()));
	}

	private void bindContent(BaseViewHolder helper, GankIoDayDataMultpleItem item) {
		TextView tvDesc = helper.getView(R.id.tv_desc);
		TextView tvAuthor = helper.getView(R.id.tv_author);
		GankIoDayDataBean.ItemBean itemData = (GankIoDayDataBean.ItemBean) item.getData();
		tvDesc.setText(itemData.getDesc());
		if (!StringUtils.isEmpty(itemData.getWho())) {
			tvAuthor.setText("(" + itemData.getWho().trim() + ")");
		}
	}
}
