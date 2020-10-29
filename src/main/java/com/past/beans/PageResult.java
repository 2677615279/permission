package com.past.beans;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页返回结果类
 */
@Data
@Builder
public class PageResult<T>{

    private List<T> data = new ArrayList<>(); //分页列表

    private int total = 0; //当前参与分页的数目

}
