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
package com.wjy.nemo.config;

import com.wjy.nemo.config.parser.XmlParser;
import org.dom4j.DocumentException;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * 测试解析XML转bean
 * 使用Dom4J
 * @author cac2020
 */
public class XmlConfigTest {
    public static void main(String[] args) throws JAXBException, IOException, DocumentException {
        /*XmlParser parser = new XmlParser("classpath:RowCrmResult.xml","<osb:recvData xmlns:osb=\"mboss-esb/osb\">","</osb:recvData>");
        parser.parse();*/

        XmlParser parser = new XmlParser("classpath:nemo.xml","","");
        parser.parse();
    }

}
