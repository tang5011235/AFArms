package com.af.demo.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.af.demo.R;
import com.af.demo.api.Bean.GankIoDayDataBean;
import com.af.demo.api.Bean.GankIoDayDataMultpleItem;
import com.af.demo.ui.holder.SuperViewHolder;

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
public class GankIoDayDateAdapter extends BaseMultiAdapter<GankIoDayDataBean> {

    public GankIoDayDateAdapter(Context context, List<GankIoDayDataBean> list) {
        super(context);
        addItemType(GankIoDayDataMultpleItem.TITLE, R.layout.adapter_title_gank_io_day_data);
        addItemType(GankIoDayDataMultpleItem.CONTENT, R.layout.adapter_content_gank_io_day_data);
        addItemType(GankIoDayDataMultpleItem.IMG, R.layout.adapter_image_list);
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        GankIoDayDataBean item = mDataList.get(position);
        switch (item.getItemType()) {
            case GankIoDayDataMultpleItem.TITLE:
                bindTitle(holder, item);
                break;
            case GankIoDayDataMultpleItem.CONTENT:
                bindContent(holder, item);
                break;
            case GankIoDayDataMultpleItem.IMG:
                bindImage(holder, item);
                break;
            default:

                break;
        }
    }

    private void bindTitle(SuperViewHolder holder, GankIoDayDataBean item) {
        TextView tvTitle = holder.getView(R.id.tv_title);
        tvTitle.setText();
    }

    private void bindContent(SuperViewHolder holder, GankIoDayDataBean item) {
        TextView tvDesc = holder.getView(R.id.tv_desc);
        tvDesc.setText(item.get);
    }


    private void bindImage(SuperViewHolder holder, GankIoDayDataBean item) {

    }
}
