package com.wjy.nemo.config;

import com.wjy.nemo.config.parser.PropertiesParser;

/**
 * Properties配置文件解析测试类
 *
 * @author cac2020
 */
public class PropertiesConfigTest {

    public static void main(String[] args){
        PropertiesParser parser = new PropertiesParser("classpath:nemo.properties");
        parser.parse();
    }
}
