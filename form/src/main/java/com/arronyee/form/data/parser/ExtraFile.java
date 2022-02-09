package com.arronyee.form.data.parser;

/**
 * showtext代表图片路径，value代表图片id
 */
public class ExtraFile extends DataParser{
    public static final String TAG = "file";
    private int picNum;
    private int videoNum;
    private String imageUrl;

    {
        type = TAG;
    }


    public ExtraFile() {
    }

    public ExtraFile(String typeStr) {
        try {
            String[] infos = typeStr.split(":");
            String info = infos[1];
            picNum = Integer.parseInt(info.split("/")[0]);
            videoNum = Integer.parseInt(info.split("/")[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPicNum() {
        return picNum;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public int getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
