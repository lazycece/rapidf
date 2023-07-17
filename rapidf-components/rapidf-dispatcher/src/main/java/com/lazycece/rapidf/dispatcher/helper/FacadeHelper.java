/*
 *    Copyright 2023 lazycece<lazycece@gmail.com>
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

package com.lazycece.rapidf.dispatcher.helper;

import com.lazycece.rapidf.dispatcher.constants.DispatcherConstants;
import com.lazycece.rapidf.dispatcher.core.facade.*;
import com.lazycece.rapidf.dispatcher.exception.DispatchException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lazycece
 * @date 2023/7/15
 */
public class FacadeHelper {

    private static final List<Class<?>> FACADE_SPEC_INTERFACES;

    static {
        FACADE_SPEC_INTERFACES = new ArrayList<>();
        FACADE_SPEC_INTERFACES.add(Facade.class);
        FACADE_SPEC_INTERFACES.add(CommandFacade.class);
        FACADE_SPEC_INTERFACES.add(QueryFacade.class);
    }

    public static boolean isExpectedFacade(Class<?> clazz) {
        return Arrays.stream(clazz.getInterfaces())
                .filter(ifs -> !FACADE_SPEC_INTERFACES.contains(ifs))
                .anyMatch(ifs -> Arrays.stream(ifs.getInterfaces())
                        .anyMatch(FACADE_SPEC_INTERFACES::contains));
    }

    public static Map<String, FacadeRegistration.FacadeAction> getFacadeActionMap(Class<?> clazz) {
        Class<?> facadeIfs = Arrays.stream(clazz.getInterfaces())
                .filter(ifs -> !FACADE_SPEC_INTERFACES.contains(ifs))
                .filter(ifs -> Arrays.stream(ifs.getInterfaces())
                        .anyMatch(FACADE_SPEC_INTERFACES::contains))
                .findFirst()
                .orElseThrow(() -> new DispatchException("There is no facade interface."));

        return Arrays.stream(facadeIfs.getMethods()).map(method -> {
            Class<?>[] ptc = method.getParameterTypes();
            if (ptc.length != DispatcherConstants.FACADE_ACTION_PARAMETER_LEN) {
                throw new DispatchException("The parameter length of the facade service action is wrong.");
            }
            Class<?> requestClass = ptc[0];

            FacadeRegistration.FacadeAction facadeAction = new FacadeRegistration.FacadeAction();
            facadeAction.setAction(method.getName());
            facadeAction.setMethod(method);
            facadeAction.setRequestClass(requestClass);
            return facadeAction;
        }).collect(Collectors.toMap(FacadeRegistration.FacadeAction::getAction, o -> o));
    }
}
