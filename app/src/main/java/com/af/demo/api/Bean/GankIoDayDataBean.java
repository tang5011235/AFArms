package com.af.demo.api.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：thf on 2018/5/28 0028 13:55
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class GankIoDayDataBean extends BaseResponse<GankIoDayDataBean> {

    private List<ItemBean> Android;
    private List<ItemBean> iOS;
    @SerializedName("休息视频")
    private List<ItemBean> video;
    @SerializedName("拓展资源")
    private List<ItemBean> tuoZhan;
    @SerializedName("瞎推荐")
    private List<ItemBean> xiaTuiJian;
    @SerializedName("福利")
    private List<ItemBean> fuLi;

    public List<ItemBean> getAndroid() {
        return Android;
    }

    public void setAndroid(List<ItemBean> Android) {
        this.Android = Android;
    }

    public List<ItemBean> getIOS() {
        return iOS;
    }

    public void setIOS(List<ItemBean> iOS) {
        this.iOS = iOS;
    }

    public List<ItemBean> getiOS() {
        return iOS;
    }

    public void setiOS(List<ItemBean> iOS) {
        this.iOS = iOS;
    }

    public List<ItemBean> getVideo() {
        return video;
    }

    public void setVideo(List<ItemBean> video) {
        this.video = video;
    }

    public List<ItemBean> getTuoZhan() {
        return tuoZhan;
    }

    public void setTuoZhan(List<ItemBean> tuoZhan) {
        this.tuoZhan = tuoZhan;
    }

    public List<ItemBean> getXiaTuiJian() {
        return xiaTuiJian;
    }

    public void setXiaTuiJian(List<ItemBean> xiaTuiJian) {
        this.xiaTuiJian = xiaTuiJian;
    }

    public List<ItemBean> getFuLi() {
        return fuLi;
    }

    public void setFuLi(List<ItemBean> fuLi) {
        this.fuLi = fuLi;
    }

    public static class ItemBean {
        /**
         * _id : 56cc6d23421aa95caa707a69
         * createdAt : 2015-08-06T07:15:52.65Z
         * desc : 类似Link Bubble的悬浮式操作设计
         * publishedAt : 2015-08-07T03:57:48.45Z
         * type : Android
         * url : https://github.com/recruit-lifestyle/FloatingView
         * used : true
         * who : mthli
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

    }

}
