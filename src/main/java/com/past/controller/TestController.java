package com.past.controller;

import com.past.common.ApplicationContextHelper;
import com.past.common.JsonData;
import com.past.dao.SysAclModuleMapper;
import com.past.exception.ParamException;
import com.past.model.SysAclModule;
import com.past.param.TestVo;
import com.past.util.BeanValidator;
import com.past.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 测试环境  测试校验、工具类、异常全局处理
 */
@Controller
@RequestMapping("/test")
@Slf4j  //日志处理
public class TestController {

    /**
     * 测试spring整合springmvc环境
     * @return
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){

        log.info("hello");
        return "hello , permission";

    }


    /**
     * 测试数据请求返回封装好的JsonData实体类，当成功返回(无异常)或抛出异常时，展示响应信息
     * @return
     */
    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData helloUseJsonData(){

        log.info("hello.json");
        //测试数据请求时(返回json格式)  抛出自定义异常时 响应结果封装为JsonData对象{ret: false,msg: "test exception",data: null} 其中msg为自定义异常抛出后 页面返回的提示信息
//        throw new PermissionException("test exception");

        //测试数据请求时(返回json格式)  抛出非自定义异常时，响应结果封装为JsonData对象{ret: false,msg: "System Error",data: null}
//        throw new RuntimeException("test exception");

        //测试没有异常时，成功返回响应结果封装为JsonData对象{ret: true,msg: null,data: "hello , permission"}
        return JsonData.success("hello , permission");

    }


    /**
     * 测试校验  发现违反约束的属性，会打印日志 形如：key(校验出错的字段)->value(字段出错原因)，此处没有处理参数异常，处理参数异常见下面方法validateParam
     * @param testVo
     * @return
     */
    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo testVo){

        log.info("validate");
        try {
            //  直接请求/test/validate.json 因testVo引用对象未初始化，三个属性全部为空，全部校验出错，打印3条校验日志  key(校验出错的字段)->value(字段出错原因)
            //  请求/test/validate.json后面携带参数，如/validate.json?id=1&msg=123 校验后只有strList属性为空，只会打印1条校验日志 strList->strList不允许为空
            Map<String, String> map = BeanValidator.validateObject(testVo);
            if (MapUtils.isNotEmpty(map)){ //校验后有错误信息
                map.forEach((key, value) -> {
                    log.info("{}->{}",key,value);//使用占位符，打印日志  key(校验出错的字段)->value(字段出错原因，校验注解的message属性值(没有自定义就输出默认信息))
                });
            }
        } catch (Exception e){

        }
        return JsonData.success("test validate");

    }


    /**
     * 测试校验 参数异常  可能通过自定义的check方法(当没有给testVo的三个属性赋不为空的值时)  抛出自定义的 ParamException
     * testVo对象的三个字段属性值有一个为空，校验出违反约束的字段，不会走方法最后的return JsonData.success("check validate")，会抛出自定义ParamException
     * 会由com.past.common.SpringExceptionResolver处理，见该类26-30行 页面返回形如 {ret: false,msg: "{msg=不能为空, strList=不能为空, id=id不可以为空}",data: null}
     *
     * 当校验三个属性都不为空，不抛异常 ，走方法最后的return JsonData.success("check validate")，页面返回形如{ret: true,msg: null,data: "check validate"}
     *
     * 校验过程可以在方法中为testVo的三个属性选择性赋值，然后请求/check.json
     * 也可以不在方法中为testVo的三个属性选择性赋值,而在浏览器请求的url后携带参数，形如/test/check.json?id=xxx&msg=xxx&strList=xxx
     * @param testVo
     * @return
     */
    @RequestMapping("/check.json")
    @ResponseBody
    public JsonData validateParam(TestVo testVo) throws ParamException {

        log.info("validate");

//        List<String> list = new ArrayList<>();
//        list.add("1");list.add("2");
//        testVo.setId(4);testVo.setMsg("3");testVo.setStrList(list);

        BeanValidator.check(testVo);

        //校验完毕，没有违反注解校验约束的字段时，会走下面return语句，页面显示{ret: true,msg: null,data: "check validate"}
        return JsonData.success("check validate");

    }


    @RequestMapping("/jsonMapper.json")
    @ResponseBody
    public JsonData testJsonMapper(TestVo testVo) throws ParamException {

        log.info("validate");

        SysAclModuleMapper sysAclModuleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = sysAclModuleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.objToString(module));

        BeanValidator.check(testVo);

        //校验完毕，没有违反注解校验约束的字段时，会走下面return语句，页面显示{ret: true,msg: null,data: "check validate"}
        return JsonData.success("check validate");

    }


}
