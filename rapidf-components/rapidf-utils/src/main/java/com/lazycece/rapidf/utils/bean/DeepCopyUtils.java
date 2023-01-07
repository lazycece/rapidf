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

package com.lazycece.rapidf.utils.bean;

import java.io.*;

/**
 * Deep copy of an Object by serialize, so the Object which will be copied must be Serializable
 *
 * @author lazycece
 * @date 2019/11/09
 */
public class DeepCopyUtils {

    public static Object copy(Object object) throws NotSerializableException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            out.close();

            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            return in.readObject();
        } catch (NotSerializableException e) {
            // if (e instance of NotSerializableException) throw e
            throw e;
        } catch (IOException | ClassNotFoundException e) {
            // if (e instance of IOException and ClassNotFoundException)
            // print message and return null
            e.printStackTrace();
            return null;
        }
    }
}

