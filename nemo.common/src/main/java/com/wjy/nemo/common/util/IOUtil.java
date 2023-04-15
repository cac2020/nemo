/**
 * Copyright © 2023 xxxx公司 (cac2020@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wjy.nemo.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 处理IO流工具类
 *
 * @author cac2020
 */
public final class IOUtil {

    public static void close(InputStream io){
        if (io != null){
            try{
                io.close();
            }catch (IOException e){

            }
        }
    }

    public static void close(Closeable close){
        if (close != null){
            try {
                close.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
