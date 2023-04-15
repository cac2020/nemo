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
package com.wjy.nemo.common.logging;

/**
 * 日志接口
 * 通过自定义接口Logger继承slf4j接口  那么其他子项目只需要引用这一个common即可 不再需要依赖slf4j
 *
 * @author cac2020
 */
public interface Logger extends org.slf4j.Logger {
}
