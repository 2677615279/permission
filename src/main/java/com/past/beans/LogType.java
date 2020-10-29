package com.past.beans;

/**
 * 记录操作类型的接口
 */
public interface LogType {

    int TYPE_DEPT = 1;//记录部门的操作

    int TYPE_USER = 2;//记录用户的操作

    int TYPE_ACL_MODULE = 3;//记录权限模块的操作

    int TYPE_ACL = 4;//记录权限点的操作

    int TYPE_ROLE = 5;//记录角色的操作

    int TYPE_ROLE_ACL = 6;//记录角色权限关联关系的操作

    int TYPE_ROLE_USER = 7;//记录角色用户关联关系的操作

}
