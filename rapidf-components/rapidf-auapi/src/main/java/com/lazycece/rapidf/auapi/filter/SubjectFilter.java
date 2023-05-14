/*
 *    Copyright 2023 lazycece<lazycece@gmail.com>
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

package com.lazycece.rapidf.auapi.filter;

import com.lazycece.au.api.token.Subject;
import com.lazycece.au.api.token.SubjectContext;
import com.lazycece.au.context.RequestContext;
import com.lazycece.au.filter.AuFilter;
import com.lazycece.au.http.HttpServletRequestWrapper;
import com.lazycece.au.log.AuLogger;
import com.lazycece.au.log.AuLoggerFactory;
import com.lazycece.rapidf.auapi.extension.UserSubject;

/**
 * @author lazycece
 * @date 2023/5/9
 */
public class SubjectFilter implements AuFilter {

    private final AuLogger log = AuLoggerFactory.getLogger(this.getClass());

    @Override
    public String name() {
        return "au-subject-filter";
    }

    @Override
    public boolean preHandle() throws Exception {
        log.debug("Subject filter process begin");
        Subject subject = SubjectContext.getContext();
        if (subject instanceof UserSubject) {
            UserSubject userSubject = (UserSubject) subject;
            HttpServletRequestWrapper requestWrapper = (HttpServletRequestWrapper) RequestContext.getCurrentContext().getRequest();
            // TODO: 2023/5/15  lazycece

        }

        log.debug("Subject filter process end");
        return true;
    }

    @Override
    public void postHandle() throws Exception {
    }
}
