package com.wjy.nemo.config;

import com.wjy.nemo.config.parser.JsonParser;

/**
 * json文件解析测试
 *
 * @author cac2020
 */
public class JsonConfigTest {

    public static void main(String[] args){
        JsonParser parser = new JsonParser("classpath:nemo.json");
        parser.parse();
    }

}
