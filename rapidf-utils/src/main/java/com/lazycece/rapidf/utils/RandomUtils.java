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

import java.util.Random;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class RandomUtils {

    private static final Random random = new Random();


    public static int randomInt() {
        return random.nextInt();
    }

    /**
     * @param number int
     * @return value between 0 and number
     */
    public static int randomInt(int number) {
        return random.nextInt(number);
    }

    /**
     * init a int value between min and max
     *
     * @param min min value
     * @param max max value
     * @return value
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
