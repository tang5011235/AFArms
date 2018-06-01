package com.af.demo.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.af.demo.R;
import com.af.demo.ui.holder.SuperViewHolder;
import com.af.lib.app.AFManager;
import com.af.lib.imageengine.imp.ImageConfigImp;
import com.af.lib.imageengine.imp.ImageLoder;

/**
 * 作者：thf on 2018/5/31 0031 17:25
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class CategoryImagesAdapter extends ListBaseAdapter<String> {
	public CategoryImagesAdapter(Context context) {
		super(context);
	}

	@Override
	public int getLayoutId() {
		return R.layout.adapter_image_list;
	}

	@Override
	public void onBindItemHolder(SuperViewHolder holder, int position) {
		ImageView imageView = holder.getView(R.id.image_view);
		AFManager.getService(ImageLoder.class)
				.loadImage(imageView, new ImageConfigImp.Builder()
						.setUrl(mDataList.get(position))
						.build());
	}
}
