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
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 解析Yaml配置文件并转java bean
 * 依赖第三方软件：org.yaml.snakeyaml
 * @author: cac2020
 */
public class YamlParser implements ConfigParser<NemoConfig> {
    private static final Logger logger = LoggerFactory.getLogger(YamlParser.class);
    private static final String defaultConfigLocation = "nemo.yml";
    private String configLocation;

    public YamlParser(String configLocation){
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
            //使用
            if (resource == null){
                File file = ResourceUtil.getFile(configLocation);
                if (file.exists()){
                    resource = new FileInputStream(file);
                }
            }
            if (resource != null){
                config = new Yaml().loadAs(resource, NemoConfig.class);
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
