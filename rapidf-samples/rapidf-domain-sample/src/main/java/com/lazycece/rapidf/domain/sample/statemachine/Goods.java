/*
 *    Copyright 2022 lazycece<lazycece@gmail.com>
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

package com.lazycece.rapidf.domain.sample.statemachine;

import com.lazycece.rapidf.domain.anotation.DomainEntity;
import com.lazycece.rapidf.domain.model.Entity;
import com.lazycece.rapidf.domain.statemachine.State;
import com.lazycece.rapidf.domain.statemachine.StateApply;

/**
 * @author lazycece
 * @date 2022/12/27
 */
@DomainEntity
public class Goods extends Entity<String> implements StateApply {

    private String goodsId;
    private String name;
    private String price;
    private Integer inventory;
    private AuditStatus status;

    @Override
    public String getId() {
        return goodsId;
    }

    @Override
    public State<?> getState() {
        return status;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public AuditStatus getStatus() {
        return status;
    }

    public void setStatus(AuditStatus status) {
        this.status = status;
    }
}
