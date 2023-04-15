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
package com.wjy.nemo.config.parser;

/**
 * 解析配置文件的范型接口
 * 涉及三项工作：读取文件、解析文件、转换java bean
 * @author cac2020
 * @param <T>
 */
public interface ConfigParser<T> {
    /**
     * 解析配置文件
     *
     * @return T
     */
    T parse();
}
