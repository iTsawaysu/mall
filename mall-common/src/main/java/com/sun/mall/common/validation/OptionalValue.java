package com.sun.mall.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author sun
 */
@Documented
// 关联此注解和校验器
// 此处可以指定多个不同的校验器，若需要校验 Double 类型的数据，再定义一个校验器 implements ConstraintValidator<OptionalValue, Double> 即可。
@Constraint(validatedBy = {OptionalValueConstraintValidator.class})
// 指定此注解可以标记在什么位置
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
// 指定此注解的生命周期
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalValue {
    // 首先需要满足 JSR303 的规范，即要有 message、groups、Payload 三个属性。

    // 出错时的错误提示从哪获取
    String message() default "{com.sun.mall.common.validation.OptionalValue.message}";

    // 支持分组校验功能
    Class<?>[] groups() default {};

    // 负载信息
    Class<? extends Payload>[] payload() default {};

    // 注解的配置项
    int[] values() default {};
}
