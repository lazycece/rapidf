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

package com.lazycece.rapidf.utils;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author lazycece
 * @date 2021/12/5
 */
public class TokenLimiter {

    private static final TokenLimiter tokenLimiter = new TokenLimiter();
    private final ArrayBlockingQueue<String> queue;
    private int limit = 10;
    private int interval = 2;

    private TokenLimiter() {
        queue = new ArrayBlockingQueue<>(limit);
        queue.addAll(Arrays.asList("key", "key", "key", "key", "key", "key", "key", "key", "key", "key"));
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            addToken();
            System.out.println(" add token");
        }, 5, interval, TimeUnit.SECONDS);
    }

    private void addToken() {
        queue.offer("key");
    }

    public boolean getToken() {
        return queue.poll() != null;
    }

}
