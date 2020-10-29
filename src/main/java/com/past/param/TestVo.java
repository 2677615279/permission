package com.past.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 测试校验工具的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestVo implements Serializable {

    @NotNull(message = "id不可以为空") //校验对象是否不为null ，无法校验长度为0的字符串
    @Max(value = 10, message = "最大值不能超过10")
    @Min(value = 0, message = "最小值至少大于等于0")
    private Integer id;

    @NotBlank //校验约束（字符串）是不是null还有被trim的长度是否大于0，只对字符串，且会去掉前后空格
    private String msg;

    @NotEmpty //校验集合数组map等 是否不为null
    private List<String> strList;

    @Override
    public String toString() {
        return "TestVo{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", strList=" + strList +
                '}';
    }

}
