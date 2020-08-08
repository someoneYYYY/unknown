package com.example.demo.dto;

import com.example.demo.entity.ViewLog;

import java.util.List;

public class ViewDTO {

    private int viewCount;
    private List<ViewLog> viewLogs;

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<ViewLog> getViewLogs() {
        return viewLogs;
    }

    public void setViewLogs(List<ViewLog> viewLogs) {
        this.viewLogs = viewLogs;
    }
}
