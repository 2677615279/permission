package com.past.dto;

import com.past.model.SysAcl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 对权限点对象的属性做了扩展，用来做权限点树形 层级适配
 */
@Getter
@Setter
public class AclDto extends SysAcl {

    private boolean checked = false;// 是否要默认选中

    private boolean hasAcl = false;// 是否有权限操作

    /**
     * 适配，传入权限点对象，copy成AclDto
     * @param sysAcl
     * @return
     */
    public static AclDto adapt(SysAcl sysAcl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(sysAcl, dto);
        return dto;
    }

    @Override
    public String toString() {
        return "AclDto{" +
                "checked=" + checked +
                ", hasAcl=" + hasAcl +
                '}';
    }


}
