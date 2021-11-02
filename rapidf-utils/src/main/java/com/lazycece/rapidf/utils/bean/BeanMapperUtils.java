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

package com.lazycece.rapidf.utils.bean;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Simple bean mapper by using dozer
 *
 * @author lazycece
 */
public class BeanMapperUtils {

    private static final Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

    /**
     * simple mapper
     *
     * @param source           original object
     * @param destinationClass destination class
     * @param <T>              T
     * @return destination object
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return MAPPER.map(source, destinationClass);
    }

    /**
     * simple copy
     *
     * @param source      origin object
     * @param destination destination object
     */
    public static void map(Object source, Object destination) {
        MAPPER.map(source, destination);
    }

    /**
     * simple mapper list
     *
     * @param sourceList       origin object list
     * @param destinationClass destination class
     * @param <T>              T
     * @return destination object list
     */
    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            destinationList.add(MAPPER.map(sourceObject, destinationClass));
        }
        return destinationList;
    }
}
