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
import com.lazycece.rapidf.arrange.function.Handler;

import java.util.List;

/**
 * @author lazycece
 * @date 2024/1/20
 */
public class ArrangeStreamSample {

    public static void main(String[] args) {
        ArrangeStreamSample sample = new ArrangeStreamSample();
        sample.sequential();
        sample.parallel();
    }

    void sequential() {
        Integer result = Arranger.stream(new StreamContext(0))
                .filter(this::filterLess2)
                .handler(this::handlePlus1)
                .filter(this::filterLess2)
                .answer(this::answerVal);
        System.out.println("================================= result: " + result);

        result = Arranger.stream(new StreamContext(1))
                .filter(this::filterLess2)
                .handler(this::handlePlus1)
                .filter(this::filterLess2)
                .answer(this::answerVal);
        System.out.println("================================= result: " + result);

        Arranger.stream(new StreamContext(1))
                .filter(this::filterLess2)
                .handler(this::handlePlus1)
                .handler(this::handlePlus1)
                .handler(this::handlePlus1)
                .handler(this::handlePlus1)
                .end();
        System.out.println("================================= handle");
    }

    public void parallel() {
        Integer result = Arranger.stream(new StreamContext(0))
                .filter(this::filterLess2)
                .handler(this::handlePlus1)
                .filter(this::filterLess2)
                .parallel(List.of(new PlusA(), new PlusB(), new PlusC()))
                .answer(this::answerParallel);
        System.out.println("================================= result: " + result);

    }

    public static class StreamContext {
        public int num;
        public int a;
        public int b;
        public int c;

        public StreamContext(int num) {
            this.num = num;
        }
    }

    boolean filterLess2(StreamContext context) {
        System.out.println("filter: num<2 ----------- num=" + context.num);
        return context.num < 2;
    }

    void handlePlus1(StreamContext context) {
        System.out.println("filter: num++ ----------- num=" + context.num);
        ++context.num;
    }

    Integer answerVal(StreamContext context) {
        System.out.println("answer: return num ----------- num=" + context.num);
        return context.num;
    }

    Integer answerParallel(StreamContext context) {
        System.out.println("answer: return num ----------- num=" + context.num);
        return context.a + context.b + context.c;
    }

    public static class PlusA implements Handler<StreamContext> {

        @Override
        public void handle(StreamContext context) {
            System.out.println("PlusA ------- " + Thread.currentThread().getName());
            ++context.a;
        }
    }

    public static class PlusB implements Handler<StreamContext> {

        @Override
        public void handle(StreamContext context) {
            System.out.println("PlusB ------- " + Thread.currentThread().getName());
            ++context.b;
        }
    }

    public static class PlusC implements Handler<StreamContext> {

        @Override
        public void handle(StreamContext context) {
            System.out.println("PlusC ------- " + Thread.currentThread().getName());
            ++context.c;
        }
    }

}
