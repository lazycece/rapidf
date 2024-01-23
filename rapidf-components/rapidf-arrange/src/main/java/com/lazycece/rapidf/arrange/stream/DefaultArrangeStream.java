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
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author lazycece
 * @date 2024/1/20
 */
public final class DefaultArrangeStream<Context> implements ArrangeStream<Context> {

    private Stream<Context> stream;

    DefaultArrangeStream(Context context) {
        stream = Stream.of(context);
    }

    /**
     * @see ArrangeStream#filter
     */
    @Override
    public ArrangeStream<Context> filter(Filter<Context> filter) {
        Objects.requireNonNull(filter);
        stream = stream.filter(filter::accept);
        return this;
    }

    /**
     * @see ArrangeStream#handler
     */
    @Override
    public ArrangeStream<Context> handler(Handler<Context> handler) {
        Objects.requireNonNull(handler);
        stream = stream.filter(context -> {
            handler.handle(context);
            return true;
        });
        return this;
    }

    /**
     * @see ArrangeStream#parallel
     */
    @Override
    public ArrangeStream<Context> parallel(List<? extends Handler<Context>> handlers) {
        Objects.requireNonNull(handlers);
        stream = stream.filter(context -> {
            handlers.stream().parallel().forEach(handler -> handler.handle(context));
            return true;
        });
        return this;
    }

    /**
     * @see ArrangeStream#answer
     */
    @Override
    public <R> R answer(Answer<Context, R> answer) {
        return stream.map(answer::answer).findFirst().orElse(null);
    }

    /**
     * @see ArrangeStream#end
     */
    @Override
    public void end() {
        stream.forEach(context -> {
            // Do nothing, only for end stream.
        });
    }
}
