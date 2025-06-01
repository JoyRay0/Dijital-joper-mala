package com.mala.digital_joper_mala.Model;

import java.util.List;

public class Main_response {

    private String status;
    private List<Data> data;
    private String title;
    private String description;
    private String img_link;
    private String web_link;
    private String msize;


    public String getStatus() {
        return status;
    }

    public List<Data> getData() {
        return data;
    }

    public String getDescription() {
        return description;
    }

    public String getImg_link() {
        return img_link;
    }

    public String getTitle() {
        return title;
    }

    public String getWeb_link() {
        return web_link;
    }

    public String getMsize() {
        return msize;
    }
}
