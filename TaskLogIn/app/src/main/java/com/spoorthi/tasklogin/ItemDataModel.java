package com.spoorthi.tasklogin;

/**
 * Created by Spoorthi on 1/12/2018.
 */

public class ItemDataModel
{
    private String head;
    private String lat;
    private String lang;
    private String image;

    public ItemDataModel(String head, String lat, String lang, String image) {
        this.head = head;
        this.lat = lat;
        this.lang = lang;
        this.image = image;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
