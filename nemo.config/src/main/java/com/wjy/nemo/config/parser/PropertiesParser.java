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

import com.wjy.nemo.common.logging.Logger;
import com.wjy.nemo.common.logging.LoggerFactory;
import com.wjy.nemo.common.util.IOUtil;
import com.wjy.nemo.common.util.ResourceUtil;
import com.wjy.nemo.common.util.StringUtils;
import com.wjy.nemo.config.NemoConfig;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.function.BooleanSupplier;

/**
 * 解析Properties配置文件并转java bean
 *
 * Java读取properties配置文件的8种方式汇总:http://www.qb5200.com/article/588257.html
 * @author: cac2020
 */
public class PropertiesParser implements ConfigParser<NemoConfig> {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesParser.class);
    private static final String defaultConfigLocation = "nemo.properties";
    private String configLocation;

    public PropertiesParser(String configLocation){
        this.configLocation = configLocation;
    }

    @Override
    public NemoConfig parse() {
        NemoConfig config = null;
        InputStream resource = null;
        if (StringUtils.isBlank(configLocation)){
            configLocation = defaultConfigLocation;
        }
        try {
            logger.info("parse nemoConfig, configLocation : {}", configLocation);
            //使用当前类的类加载器来读取配置文件
            resource = getClass().getResourceAsStream(configLocation);
            if (resource == null){
                resource = getClass().getResourceAsStream(File.separator + configLocation);
            }
            if (resource == null){
                File file = ResourceUtil.getFile(configLocation);
                if (file.exists()){
                    resource = new FileInputStream(file);
                }
            }
            if (resource != null){
                Properties p = new Properties();
                p.load(resource);
                config = NemoConfig.class.newInstance();
                Field[] fields = NemoConfig.class.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = p.get(field.getName());
                    //获取不到的字段自动忽略
                    if (value == null) {
                        logger.info(field.getType() + " " + field.getName() + " get configuration is null");
                        continue;
                    }
                    Class<?> type = field.getType();
                    if (type == int.class){
                        field.setInt(config, Integer.parseInt(value.toString()));
                    } else if (type == long.class){
                        field.setLong(config, Long.parseLong(value.toString()));
                    } else if (type == boolean.class){
                        field.setBoolean(config, Boolean.parseBoolean(value.toString()));
                    } else if (type == byte.class){
                        field.setByte(config, Byte.parseByte(value.toString()));
                    } else if (type == char.class){
                        char[] chars = value.toString().toCharArray();
                        field.setChar(config, Character.valueOf(chars[0]));
                    } else if (type == short.class){
                        field.setShort(config, Short.valueOf(value.toString()));
                    } else if (type == float.class){
                        field.setFloat(config, Float.parseFloat(value.toString()));
                    } else if (type == double.class){
                        field.setDouble(config, Double.parseDouble(value.toString()));
                    } else {
                        field.set(config, value);
                    }
                }
                logger.info("nemoConfig : {}", config);
                return config;
            }
        }catch (Exception e){
            logger.error("fail to parse : " + configLocation, e);
        }finally {
            IOUtil.close(resource);
        }

        config = new NemoConfig();
        return config;
    }

}
