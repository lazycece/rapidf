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
import com.lazycece.rapidf.arrange.handler.AbstractTemplateHandler;
import com.lazycece.rapidf.arrange.sample.context.SampleContext;

/**
 * @author lazycece
 * @date 2024/1/20
 */
public class ArrangeHandlerSample {

    public static void main(String[] args) {
        ArrangeHandlerSample sample = new ArrangeHandlerSample();
        sample.handlerProcess();
        sample.templateHandlerProcess();
    }

    public void handlerProcess() {
        Arranger.process(new SampleContext(), new Handler<SampleContext>() {
            @Override
            public void handle(SampleContext sampleContext) {
                System.out.println("handlerProcess");
            }
        });
    }

    public void templateHandlerProcess() {
        Arranger.process(new SampleContext(), new AbstractTemplateHandler<SampleContext>() {
            @Override
            protected boolean preHandle(SampleContext sampleContext) {
                return super.preHandle(sampleContext);
            }

            @Override
            protected void doHandle(SampleContext sampleContext) {
                System.out.println("templateHandlerProcess");
            }

            @Override
            protected void postHandle(SampleContext sampleContext) {
                super.postHandle(sampleContext);
            }
        });
    }
}
