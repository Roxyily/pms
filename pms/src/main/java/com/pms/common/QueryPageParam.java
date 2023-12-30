package com.pms.common;

import lombok.Data;

import java.util.HashMap;

@Data
public class QueryPageParam {
    //默认
    private static int PAGE_SIZE=20;
    private static int PAGE_NUM=1;

    private Integer pageSize=PAGE_SIZE;
    private Integer pageNum=PAGE_NUM;

    private HashMap param = new HashMap();

}
