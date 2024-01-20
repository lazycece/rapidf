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

package com.lazycece.rapidf.arrange.handler;

import com.lazycece.rapidf.arrange.function.Filter;
import com.lazycece.rapidf.arrange.function.Handler;
import org.springframework.core.PriorityOrdered;

/**
 * Define the priority handler specification.
 *
 * @author lazycece
 * @date 2024/1/20
 */
public interface PriorityHandler<Context> extends Filter<Context>, Handler<Context>, PriorityOrdered {

    /**
     * @see Filter#accept
     */
    @Override
    default boolean accept(Context context) {
        return true;
    }

    /**
     * @see PriorityOrdered#getOrder()
     */
    @Override
    default int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
