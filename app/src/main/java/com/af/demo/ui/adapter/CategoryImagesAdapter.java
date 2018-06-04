package com.af.demo.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.af.demo.R;
import com.af.lib.app.AFManager;
import com.af.lib.imageengine.imp.ImageConfigImp;
import com.af.lib.imageengine.imp.ImageLoder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

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
public class CategoryImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

	public CategoryImagesAdapter(int layoutResId, @Nullable List<String> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, String item) {
		ImageView imageView = helper.getView(R.id.image_view);
		AFManager.getService(ImageLoder.class)
				.loadImage(imageView, new ImageConfigImp.Builder()
						.setUrl(item)
						.build());
	}
}
