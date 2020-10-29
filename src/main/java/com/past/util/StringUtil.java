package com.past.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 将字符串，以,为分隔，组成一个存储Integer数据的集合
     * @param str
     * @return
     */
    public static List<Integer> splitToListInt(String str) {

        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        return strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
    }

}
