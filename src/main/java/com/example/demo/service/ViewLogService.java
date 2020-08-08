package com.example.demo.service;

import com.example.demo.dto.ViewDTO;

import javax.servlet.http.HttpServletRequest;

public interface ViewLogService {

    void view(HttpServletRequest request);

    ViewDTO viewInfo(int period);
}
