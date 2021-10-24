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

/**
 * 0 - 00000000 00000000 00000000 00000000 00000000 0 - 00000-00000 - 000000000000
 * |   |----------时间戳 41 bit----------------------|   |---10----|   |----12----|
 * |-------------------------------64-------------------------------------------|
 *
 * @author lazycece
 * @date 2021/10/24
 */
public class SnowFlake {
    /**
     * 起始的时间戳
     */
    private final static long START_TIME = 1480166465631L;
    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5;
    /**
     * 数据中心占用的位数
     */
    private final static long DATA_CENTER_BIT = 5;
    /**
     * 数据中心id最大值
     */
    private final static long MAX_DATA_CENTER = ~(-1L << DATA_CENTER_BIT);
    /**
     * 机器id最大值
     */
    private final static long MAX_MACHINE = ~(-1L << MACHINE_BIT);
    /**
     * 序列号最大值
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    /**
     * 数据中心标识
     */
    private long dataCenterId;
    /**
     * 机器标识
     */
    private long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastTime = -1L;

    public SnowFlake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId must between 0 and " + MAX_DATA_CENTER);
        }
        if (machineId > MAX_MACHINE || machineId < 0) {
            throw new IllegalArgumentException("machineId must between 0 and " + MAX_MACHINE);
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long currTime = System.currentTimeMillis();
        if (currTime < lastTime) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        if (currTime == lastTime) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //sequence is max value
            if (sequence == 0L) {
                currTime = getNextTimeMillis();
            }
        } else {
            sequence = 0L;
        }
        lastTime = currTime;
        return sequence //序列号部分
                | machineId << SEQUENCE_BIT //机器标识部分
                | dataCenterId << (SEQUENCE_BIT + MACHINE_BIT) //数据中心部分
                | (currTime - START_TIME) << (SEQUENCE_BIT + MACHINE_BIT + DATA_CENTER_BIT); //时间戳部分
    }

    private long getNextTimeMillis() {
        long time = System.currentTimeMillis();
        while (time <= lastTime) {
            time = System.currentTimeMillis();
        }
        return time;
    }

    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        System.out.println(snowFlake.nextId());
    }
}
