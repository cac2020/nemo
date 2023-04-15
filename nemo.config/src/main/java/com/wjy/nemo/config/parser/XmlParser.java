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

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.wjy.nemo.common.logging.Logger;
import com.wjy.nemo.common.logging.LoggerFactory;
import com.wjy.nemo.common.util.IOUtil;
import com.wjy.nemo.common.util.ResourceUtil;
import com.wjy.nemo.common.util.StringUtils;
import com.wjy.nemo.config.NemoConfig;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * 解析XML配置文件并转java bean
 * 依赖JAXBContext 和 第三方软件：dom4j 和 hutool工具包
 *
 * 具体参考：test.java.com.wjy.nemo.config.XmlConfigTest
 * 另附参考：Java 读取xml文件的四种方式 https://blog.csdn.net/qq_36138652/article/details/127243012
 * @author: cac2020
 */
public class XmlParser implements ConfigParser<NemoConfig> {
    private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);
    private static final String defaultConfigLocation = "nemo.xml";
    private String configLocation;
    private String before;
    private String after;

    public XmlParser(String configLocation,String before,String after){
        this.configLocation = configLocation;
        this.before = before;
        this.after = after;
    }

    @Override
    public NemoConfig parse() {
        NemoConfig config = null;
        InputStream resource = null;
        if (StringUtils.isBlank(configLocation)){
            configLocation = defaultConfigLocation;
        }
        try {
            File file = ResourceUtil.getFile(configLocation);
            if (file.exists()){
                // 创建SAXReader对象
                SAXReader reader = new SAXReader();
                // 加载xml文件
                Document dc = reader.read(file);
                String xmlStr = dc.asXML();
                String content = formatResponseContent(xmlStr,before,after);
                logger.info("before parse nemoConfig, xmlContent : {}",content);
                JAXBContext jaxbContext = JAXBContext.newInstance(NemoConfig.class);
                //xml文件解析成JavaBean对象器 javabean需要添加注解
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                config = (NemoConfig) unmarshaller.unmarshal(new ByteArrayInputStream(content.getBytes()));
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

    /**
     * 格式化响应内容 == 去除其他没用的字符，并格式化XML
     *    使用的是hutool工具包
     * @param content
     * @return
     */
    public String formatResponseContent(String content,String before,String after) {
        content = StrUtil.replace(content, "<![CDATA[", "");
        content = StrUtil.replace(content, "]]>", "");
        if (StringUtils.isNotBlank(before) && StringUtils.isNotBlank(after)){
            content = StrUtil.subBetween(content, before, after);
            //content = StrUtil.trim(content);
        }
        content= StrUtil.replace(content,"\\", "");
        content = XmlUtil.format(content);
        return content;
    }
}
