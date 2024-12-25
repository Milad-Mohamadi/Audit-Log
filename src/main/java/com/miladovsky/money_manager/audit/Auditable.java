package com.miladovsky.money_manager.audit;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Auditable {
    String action() default "";
    String entityType() default "";
    String detail() default "";
}