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

package com.lazycece.rapidf.arrange.sample;

import com.lazycece.rapidf.arrange.Arranger;
import com.lazycece.rapidf.arrange.template.ProcessTemplate;

import java.util.Random;

/**
 * @author lazycece
 * @date 2024/1/20
 */
public class ArrangeProcessTemplateSample {

    public static void main(String[] args) {
        System.out.println(random(10));
    }

    public static int random(int bound) {
        return Arranger.process(new ProcessTemplate<>() {
            @Override
            public void checkParam() {
                if (bound <= 5) {
                    throw new RuntimeException("error");
                }
            }

            @Override
            public Integer handle() {
                return new Random().nextInt(bound);
            }
        });
    }
}
