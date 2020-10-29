package com.past.controller;

import com.past.common.JsonData;
import com.past.dto.DeptLevelDto;
import com.past.param.DeptParam;
import com.past.service.SysDeptService;
import com.past.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 系统部门后台管理
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    SysDeptService sysDeptService;

    @Autowired
    SysTreeService sysTreeService;


    /**
     * 在校验属性前提下 保存部门
     * @param deptParam
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam){

        sysDeptService.save(deptParam);
        return JsonData.success();
    }


    /**
     * 在校验属性前提下 更新部门
     * @param deptParam
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam deptParam){

        sysDeptService.update(deptParam);
        return JsonData.success();
    }


    /**
     * 获取部门层级树
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){

        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }


    /**
     * 跳转部门管理页面
     * @return
     */
    @RequestMapping("/dept.page")
    public ModelAndView page() {

        return new ModelAndView("dept");
    }


    /**
     * 根据部门id，删除该部门
     * @param id
     * @return
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData deleteDept(@RequestParam("id") int id){

        sysDeptService.delete(id);
        return JsonData.success();
    }


}
