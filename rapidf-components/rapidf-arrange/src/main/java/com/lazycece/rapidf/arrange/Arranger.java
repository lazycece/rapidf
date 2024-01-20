/*
 *    Copyright 2024 lazycece<lazycece@gmail.com>
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

package com.lazycece.rapidf.arrange;

import com.lazycece.rapidf.arrange.function.Handler;
import com.lazycece.rapidf.arrange.handler.PriorityHandler;
import com.lazycece.rapidf.arrange.stream.ArrangeStream;
import com.lazycece.rapidf.arrange.template.ProcessTemplate;

import java.util.Comparator;
import java.util.List;

/**
 * @author lazycece
 * @date 2024/1/20
 */
public class Arranger {

    /**
     * process template arrange.
     *
     * @param template ${@link ProcessTemplate}
     * @param <R>      result type
     * @return result
     */
    public static <R> R process(ProcessTemplate<R> template) {
        template.checkParam();
        R result = null;
        if (template.preHandle()) {
            result = template.handle();
            template.postHandle();
        }
        return result;
    }

    /**
     * Handler process arrange.
     *
     * @param context context
     * @param handler ${@link Handler}
     * @param <C>     context type
     */
    public static <C> void process(C context, Handler<C> handler) {
        handler.handle(context);
    }

    /**
     * Priority handler process arrange.
     *
     * @param context          context
     * @param priorityHandlers ${@link PriorityHandler}
     * @param <C>              context type
     */
    public static <C> void process(C context, List<PriorityHandler<C>> priorityHandlers) {
        priorityHandlers.stream()
                .sorted(Comparator.comparingInt(PriorityHandler::getOrder))
                .filter(handler -> handler.accept(context))
                .forEach(handler -> handler.handle(context));
    }

    /**
     * Stream process arrange.
     *
     * @param context context
     * @param <C>     context type
     * @return stream
     */
    public static <C> ArrangeStream<C> stream(C context) {
        return ArrangeStream.of(context);
    }
}
