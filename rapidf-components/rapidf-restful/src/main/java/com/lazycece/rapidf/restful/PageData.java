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

package com.lazycece.rapidf.restful;

import java.util.List;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class PageData<T> {
    /**
     * current page data
     */
    private List<T> data;
    /**
     * total records
     */
    private Long count;
    /**
     * current page
     */
    private Integer page;

    public PageData() {
    }

    public PageData(List<T> data, Long count) {
        this.data = data;
        this.count = count;
    }

    public PageData(List<T> data, Long count, Integer page) {
        this.data = data;
        this.count = count;
        this.page = page;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
