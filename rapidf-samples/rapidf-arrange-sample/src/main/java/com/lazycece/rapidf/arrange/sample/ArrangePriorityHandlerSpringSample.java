/*
 *    Copyright 2026 lazycece<lazycece@gmail.com>
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lazycece
 * @date 2026/7/28
 */
@Service
public class ArrangePriorityHandlerSpringSample {
    @Autowired
    private List<SampleHandler<SampleContext>> sampleHandlerList;

    public void handle(SampleContext context) {
        Arranger.process(context, sampleHandlerList);
    }

    interface SampleHandler<Context> extends PriorityHandler<Context> {

    }


    @Component
    public static class PriorityHandlerSample1 implements SampleHandler<SampleContext> {

        @Override
        public boolean accept(SampleContext context) {
            return SampleHandler.super.accept(context);
        }

        @Override
        public void handle(SampleContext sampleContext) {
            System.out.println("PriorityHandlerSample1");
        }

        @Override
        public int getOrder() {
            return 1;
        }
    }

    @Component
    public static class PriorityHandlerSample2 implements SampleHandler<SampleContext> {
        @Override
        public boolean accept(SampleContext context) {
            return SampleHandler.super.accept(context);
        }

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
