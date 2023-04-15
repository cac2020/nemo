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
package com.wjy.nemo.config.entity;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * XML解析对应实体bean类
 *
 * @author cac2020
 */
@XmlAccessorType(XmlAccessType.FIELD) //定义xml序列化的字段类型
@XmlRootElement(name = "outputdatas")
public class RowCrmResult {

    /**
     * 结果里面的状态码
     */
    @XmlElement(name = "resultcode", nillable = true)
    public String resultcode;

    /**
     * 结果里面的失败原因
     */
    @XmlElement(name = "reason", nillable = true)
    public String reason;


    @XmlElementWrapper(name = "results")
    @XmlElement(name = "result", nillable = true)
    public List<Result> results;


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "result")
    public static class Result {

        @XmlAttribute(name = "set_id")
        private String setId;

        @XmlAttribute(name = "rows")
        private Integer rowNum;

        @XmlAttribute(name = "cols")
        private Integer colNum;

        @XmlElement(name = "row", nillable = true)
        private List<Row> rows;

    }

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Row {

        @XmlAttribute(name = "rownum")
        private Integer rownum;

        @XmlElement(name = "col", nillable = true)
        private List<Col> cols;

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement
    public static class Col {

        @XmlAttribute(name = "colnum")
        private String colnum;

        @XmlAttribute(name = "param_id")
        private String paramId;

        @XmlAttribute(name = "param_name")
        private String paramName;

        @XmlValue
        private String content;

    }


}

