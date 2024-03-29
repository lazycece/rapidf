/*
 *    Copyright 2021 lazycece<lazycece@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.lazycece.rapidf.validation.constraints;

import com.lazycece.rapidf.validation.validator.EnumValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * To validate enum's value is right or not .
 * <p>
 * Example one, if enum like:
 * <pre>{@code
 * public enum Sex {
 *
 *   MALE, FEMALE, OTHER
 *
 * }
 * }</pre>
 * can use the annotation as {@code @Enum(clazz = Sex.class)}, and param sex = 0;
 * Or use the annotation as {@code @Enum(clazz = Sex.class, method = "name")},
 * and param sex = "MALE";
 * <p>
 * Example two, if enum like:
 * <pre>{@code
 * public enum Role {
 *
 * ADMIN(1, "ADMIN"),
 * TEST(2, "TEST"),
 * DEVELOP(3, "DEVELOP");
 *
 * private int value;
 * private String desc;
 *
 * Role(int value, String desc) {
 * this.value = value;
 * this.desc = desc;
 * }
 *
 * public int getValue() {
 * return value;
 * }
 *
 * public void setValue(int value) {
 * this.value = value;
 * }
 *
 * public String getDesc() {
 * return desc;
 * }
 *
 * public void setDesc(String desc) {
 * this.desc = desc;
 * }
 * }
 * }</pre>
 * can use the annotation as <code>@Enum(clazz = Role.class, method = "getValue")</code>,
 * and param role = 1; Or use the annotation as <code>@Enum(clazz = Role.class,
 * method = "getDesc")</code>,and param role = "ADMIN";
 *
 * <p>
 *
 * @author lazycece
 * @date 2021/10/24
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Enum.List.class)
@Constraint(validatedBy = {EnumValidator.class})
public @interface Enum {

    String message() default "{*.validation.constraint.Enum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * The enum's class-type
     *
     * @return Class
     */
    Class<?> clazz();

    /**
     * The method's name ,which used to validate the enum's value
     *
     * @return method's name
     */
    String method() default "name";

    /**
     * Defines several {@link Enum} annotations on the same element.
     *
     * @see Enum
     */
    @Documented
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @interface List {
        Enum[] value();
    }
}
