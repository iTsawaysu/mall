package com.sun.mall.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sun
 */
public class OptionalValueConstraintValidator implements ConstraintValidator<OptionalValue, Integer> {

    private Set<Integer> set = new HashSet<>();

    /**
     * 初始化方法
     * @param constraintAnnotation  注解中提交的值
     */
    @Override
    public void initialize(OptionalValue constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        for (int value : values) {
            set.add(value);
        }
    }

    /**
     * 判断是否校验成功
     * @param integer  需要校验的值
     */
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        // 提交的值 是否包含在 注解指定的值中
        return set.contains(integer);
    }
}
