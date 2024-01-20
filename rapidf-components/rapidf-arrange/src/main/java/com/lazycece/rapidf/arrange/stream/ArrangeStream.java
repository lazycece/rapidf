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

package com.lazycece.rapidf.arrange.stream;

import com.lazycece.rapidf.arrange.function.Answer;
import com.lazycece.rapidf.arrange.function.Filter;
import com.lazycece.rapidf.arrange.function.Handler;

import java.util.List;

/**
 * Arrange stream specification.
 *
 * @author lazycece
 * @date 2024/1/20
 */
public interface ArrangeStream<C> {

    /**
     * Do filter, accept or not.
     *
     * @param filter ${@link Filter}
     * @return stream
     */
    ArrangeStream<C> filter(Filter<C> filter);

    /**
     * To handle something.
     *
     * @param handler ${@link Handler}
     * @return stream
     */
    ArrangeStream<C> handler(Handler<C> handler);

    /**
     * To handle something with  parallel.
     *
     * @param handlers ${@link Handler}
     * @return stream
     */
    ArrangeStream<C> parallel(List<Handler<C>> handlers);

    /**
     * To answer a result.
     *
     * @param answer ${@link Answer}
     * @param <R>    result type
     * @return result
     */
    <R> R answer(Answer<C, R> answer);

    /**
     * To end stream.
     */
    void end();

    /**
     * Build arrange stream.
     *
     * @param context stream context
     * @param <C>     context type
     * @return stream
     */
    static <C> ArrangeStream<C> of(C context) {
        return new DefaultArrangeStream<>(context);
    }
}
