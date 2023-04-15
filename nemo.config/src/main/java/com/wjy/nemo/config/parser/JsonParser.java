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

import com.alibaba.fastjson.JSONObject;
import com.wjy.nemo.common.logging.Logger;
import com.wjy.nemo.common.logging.LoggerFactory;
import com.wjy.nemo.common.util.IOUtil;
import com.wjy.nemo.common.util.ResourceUtil;
import com.wjy.nemo.common.util.StringUtils;
import com.wjy.nemo.config.NemoConfig;

import java.io.*;

/**
 * 解析json配置文件并转java bean
 * 依赖第三方软件：fastjson
 * @author: cac2020
 */
public class JsonParser implements ConfigParser<NemoConfig> {
    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);
    private static final String defaultConfigLocation = "nemo.json";
    private String configLocation;

    public JsonParser(String configLocation){
        this.configLocation = configLocation;
    }

    @Override
    public NemoConfig parse() {
        NemoConfig config = null;
        InputStream resource = null;
        String jsonStr = "";
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
                    FileReader fileReader = new FileReader(file);
                    Reader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
                    int ch = 0;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = reader.read()) != -1) {
                        sb.append((char) ch);
                    }
                    fileReader.close();
                    reader.close();
                    jsonStr = sb.toString();
                }
            }
            if (StringUtils.isNotBlank(jsonStr)){
                config = JSONObject.parseObject(jsonStr,NemoConfig.class);
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
