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
import com.lazycece.rapidf.arrange.handler.PriorityHandler;
import com.lazycece.rapidf.arrange.sample.context.SampleContext;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lazycece
 * @date 2024/1/20
 */
public class ArrangePriorityHandlerSample {

    public static void main(String[] args) {
        SampleContext context = new SampleContext();
        Arranger.process(context, Stream.of(new PriorityHandlerSample2(), new PriorityHandlerSample1())
                .sorted(Comparator.comparingInt(PriorityHandler::getOrder)).collect(Collectors.toList()));
    }

    static class PriorityHandlerSample1 implements PriorityHandler<SampleContext> {

        @Override
        public void handle(SampleContext sampleContext) {
            System.out.println("PriorityHandlerSample1");
        }

        @Override
        public int getOrder() {
            return 1;
        }
    }

    static class PriorityHandlerSample2 implements PriorityHandler<SampleContext> {

        @Override
        public void handle(SampleContext sampleContext) {
            System.out.println("PriorityHandlerSample2");
        }

        @Override
        public int getOrder() {
            return 2;
        }
    }
}
