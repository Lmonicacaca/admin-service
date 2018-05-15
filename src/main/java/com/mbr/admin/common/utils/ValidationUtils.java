package com.mbr.admin.common.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class ValidationUtils<T> {
    /**
     * 使用hibernate的注解来进行验证
     *
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    /**
     * 功能描述: <br>
     * 〈注解验证参数〉
     *
     * @param obj
     */
    public static <T> Map<String, String> validate(T obj) {
        Map<String, StringBuilder> errorMap = new LinkedHashMap<>();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (set != null && set.size() > 0) {
            String property = null;
            for (ConstraintViolation<T> cv : set) {
                //这里循环获取错误信息，可以自定义格式
                property = cv.getPropertyPath().toString();
                if (errorMap.get(property) != null) {
                    errorMap.get(property).append("," + cv.getMessage());
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(cv.getMessage());
                    errorMap.put(property, sb);
                }
            }
        }
        return errorMap.entrySet().stream().collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue().toString()));
    }

    public static StringBuffer mapValueToString(Map<String,String> map){
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuffer.append(entry.getValue()).append(",");
        }
        return stringBuffer;
    }

    public static <T> String validateStr(T obj){
        Map<String, String> map = ValidationUtils.validate(obj);
        if(map!=null&&map.size()>0){
            String msg = ValidationUtils.mapValueToString(map).toString();
            return (msg.substring(0,msg.length()-1));
        }
        return "";
    }
}
