package com.past.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * 分页规范条件类
 */
public class PageQuery {

    @Getter
    @Setter
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1; //当前页码

    @Getter
    @Setter
    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10; //每页显示数量

    @Setter
    private int offset; //从第几条开始(偏移量)  limit offset,pageSize->limit 0,10  从id为1开始，每页显示10条

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }


}
