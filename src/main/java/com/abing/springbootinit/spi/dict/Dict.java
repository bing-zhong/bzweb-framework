package com.abing.springbootinit.spi.dict;

import java.lang.annotation.*;

/**
 * @Author CaptainBing
 * @Date 2024/9/2 14:43
 * @Description 指定枚举字典别名
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dict {

    String value() default "";

    /**
     * 字典别名
     * @return 别名
     */
    String alias() default "";

    /**
     * @return 字典说明, 备注
     */
    String comments() default "";

}
