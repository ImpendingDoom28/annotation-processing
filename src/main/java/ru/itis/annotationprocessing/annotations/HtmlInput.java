package ru.itis.annotationprocessing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HtmlInput {
    String placeholder() default "Enter value...";
    String name() default "";
    String type() default "text";
}
