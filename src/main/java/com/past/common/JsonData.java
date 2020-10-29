package com.past.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理json数据，封装JsonData对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonData implements Serializable {

    private boolean ret;//返回结果 正常返回true，有异常时false

    private String msg;//如果有异常(ret属性值为false)，所给信息

    private Object data;//正常返回时(ret属性为true)，返回前台的数据

    public JsonData(boolean ret) {
        this.ret = ret;
    }

    /**
     * 成功后，需要通知信息到前端
     * @param msg
     * @param data
     * @return
     */
    public static JsonData success(String msg, Object data){
        JsonData jsonData = JsonData.builder().ret(true).build();
        jsonData.msg = msg;
        jsonData.data = data;
        return jsonData;
    }

    /**
     * 成功后，不需要通知信息到前端
     * @param data
     * @return
     */
    public static JsonData success(Object data){
        JsonData jsonData = JsonData.builder().ret(true).build();
        jsonData.data = data;
        return jsonData;
    }

    /**
     * 成功后，直接返回JsonData对象
     * @return
     */
    public static JsonData success(){
        return JsonData.builder().ret(true).build();
    }

    /**
     * 失败后，通知信息到前端
     * @param msg
     * @return
     */
    public static JsonData fail(String msg){
        JsonData jsonData = JsonData.builder().ret(false).build();
        jsonData.msg = msg;
        return jsonData;
    }

    /**
     * 封装json处理集
     * @return
     */
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("ret",ret);
        map.put("msg",msg);
        map.put("data",data);
        return map;
    }

}
