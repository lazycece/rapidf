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

package com.lazycece.rapidf.validation.validator;

import com.lazycece.rapidf.validation.constraints.Enum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class EnumValidator implements ConstraintValidator<Enum, Object> {

    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        Object[] objects = annotation.clazz().getEnumConstants();
        try {
            Method method = annotation.clazz().getMethod(annotation.method());
            for (Object o : objects) {
                if (value.equals(method.invoke(o))) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
