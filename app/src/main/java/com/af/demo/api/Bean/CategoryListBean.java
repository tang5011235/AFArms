package com.af.demo.api.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：thf on 2018/5/31 0031 17:00
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class CategoryListBean extends BaseResponse<List<CategoryListBean>> {

	/**
	 * _id : 5b069c9e421aa97f0624f423
	 * createdAt : 2018-05-24T19:06:06.774Z
	 * desc : EventBus for iOS
	 * images : ["http://img.gank.io/94a4e20c-07f0-41dd-9c9a-c7a7cae0d9d5","http://img.gank.io/1ba9e253-8019-44df-97ef-3db50a27efc3"]
	 * publishedAt : 2018-05-25T10:30:37.805Z
	 * source : chrome
	 * type : iOS
	 * url : https://github.com/PanZhow/IMXEventBus
	 * used : true
	 * who : lijinshanmx
	 */

	@SerializedName("_id")
	private String id;
	@SerializedName("createdAt")
	private String createdAt;
	@SerializedName("desc")
	private String desc;
	@SerializedName("publishedAt")
	private String publishedAt;
	@SerializedName("source")
	private String source;
	@SerializedName("type")
	private String type;
	@SerializedName("url")
	private String url;
	@SerializedName("used")
	private boolean used;
	@SerializedName("who")
	private String who;
	@SerializedName("images")
	private List<String> images;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
}
