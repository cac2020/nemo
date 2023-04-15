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

import java.util.UUID;

/**
 * 字符工具类
 * 注意：工具类一般都是final修饰 不可继承
 * @Author: cac2020
 */
public final class StringUtils {
    /**空格*/
    public static final String SPACE = " ";
    /**空串*/
    public static final String EMPTY = "";
    /**换行符*/
    public static final String LF = "\n";
    /**回车符*/
    public static final String CR = "\r";
    /**未匹配索引号*/
    public static final int INDEX_NOT_FOUND = -1;
    /**填充常量可以扩展到的最大大小*/
    private static final int PAD_LIMIT = 8192;
    /**
     * 无参构造器
     */
    private StringUtils(){}

    /**
     * 字符序列是否为空串
     * CharSequence是String的接口
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs){
        return cs == null || cs.length() == 0;
    }
    public boolean isNotEmpty(CharSequence cs){
        return !isEmpty(cs);
    }

    /**
     * 字符序列是否为全空字符
     * @param cs
     * @return
     */
    public static boolean isBlank(CharSequence cs){
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0){
            for (int i = 0; i < strLen; i ++){
                //校验字符序列中每一个字符不是空格
                if (!Character.isWhitespace(cs.charAt(i))){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isNotBlank(CharSequence cs){
        return !isBlank(cs);
    }

    /**
     * 去掉两边的空格
     * @param str
     * @return
     */
    public static String trim(String str){
        return str == null ? null : str.trim();
    }

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
