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

package com.lazycece.rapidf.restful;

import com.lazycece.rapidf.restful.exception.ExceptionFactory;
import com.lazycece.rapidf.restful.response.Status;

import java.util.Collection;
import java.util.Objects;

/**
 * @author lazycece
 * @date 2021/10/30
 */
public class Assert {

    public static void isTrue(final boolean bool, final Status status,
                              final String strFormat, final Object... args) {
        if (!bool) {
            throw ExceptionFactory.assertException(String.format(strFormat, args), status);
        }
    }

    public static void isFalse(final boolean bool, final Status status,
                               final String strFormat, final Object... args) {
        if (bool) {
            throw ExceptionFactory.assertException(String.format(strFormat, args), status);
        }
    }

    public static void notBlank(final String str, final Status status,
                                final String strFormat, final Object... args) {
        isFalse(str == null || str.trim().length() == 0, status, strFormat, args);
    }

    public static void isBlank(final String str, final Status status,
                               final String strFormat, final Object... args) {
        isTrue(str == null || str.trim().length() == 0, status, strFormat, args);
    }

    public static void notNull(final Object obj, final Status status,
                               final String strFormat, final Object... args) {
        isTrue(obj != null, status, strFormat, args);
    }

    public static void isNull(final Object obj, final Status status,
                              final String strFormat, final Object... args) {
        isTrue(obj == null, status, strFormat, args);
    }

    public static void notEmpty(final Collection<?> coll, final Status status,
                                final String strFormat, final Object... args) {
        isFalse(coll == null || coll.isEmpty(), status, strFormat, args);
    }

    public static void isEmpty(final Collection<?> coll, final Status status,
                               final String strFormat, final Object... args) {
        isTrue(coll == null || coll.isEmpty(), status, strFormat, args);
    }

    public static void equal(final Object source, final Object target, final Status status,
                             final String strFormat, final Object... args) {
        isTrue(Objects.equals(source, target), status, strFormat, args);
    }

    public static void assignableFrom(final Class<?> superclass, final Class<?> subclass,
                                      final Status status, final String strFormat,
                                      final Object... args) {
        isTrue(superclass.isAssignableFrom(subclass), status, strFormat, args);
    }

    public static void less(final long source, final long standard, final Status status,
                            final String strFormat, final Object... args) {
        isTrue(source < standard, status, strFormat, args);
    }

    public static void lessOrEqual(final long source, final long standard, final Status status,
                                   final String strFormat, final Object... args) {
        isTrue(source <= standard, status, strFormat, args);
    }

    public static void greater(final long source, final long standard, final Status status,
                               final String strFormat, final Object... args) {
        isTrue(source > standard, status, strFormat, args);
    }

    public static void greaterOrEqual(final long source, final long standard, final Status status,
                                      final String strFormat, final Object... args) {
        isTrue(source >= standard, status, strFormat, args);
    }
}
