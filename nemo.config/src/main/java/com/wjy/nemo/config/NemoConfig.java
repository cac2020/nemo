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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 全局配置类
 * @author cac2020
 */
@XmlAccessorType(XmlAccessType.FIELD) //定义xml序列化的字段类型
@XmlRootElement(name = "nemoConfig")
public class NemoConfig implements Serializable {
    //需要哪些属性就在配置文件里设置 解析配置文件时就会对应找到key放到相应的属性上，比如：
    /**应用名称*/
    @XmlElement(name = "appName", nillable = true)
    private String appName = "";
    /**应用地址*/
    @XmlElement(name = "host", nillable = true)
    private String host = "";
    /**应用端口*/
    @XmlElement(name = "port", nillable = true)
    private int port = 25001;
    /**应用基础包路径*/
    @XmlElement(name = "basePackage", nillable = true)
    private String basePackage;
    /**阻塞线程检查周期时长*/
    @XmlElement(name = "blockedThreadCheckInterval", nillable = true)
    private long blockedThreadCheckInterval;
    /**警告类异常时长*/
    @XmlElement(name = "warningExceptionTime", nillable = true)
    private long warningExceptionTime;
    /**最小并行度*/
    @XmlElement(name = "parallelismMin", nillable = true)
    private int parallelismMin = 8;
    /**最大并行度*/
    @XmlElement(name = "parallelismMax", nillable = true)
    private int parallelismMax = 128;
    /**并行度因子*/
    @XmlElement(name = "parallelismFactor", nillable = true)
    private int parallelismFactor = 16;

    public int getParallelismMin() {
        return parallelismMin;
    }

    public void setParallelismMin(int parallelismMin) {
        this.parallelismMin = parallelismMin;
    }

    public int getParallelismMax() {
        return parallelismMax;
    }

    public void setParallelismMax(int parallelismMax) {
        this.parallelismMax = parallelismMax;
    }

    public int getParallelismFactor() {
        return parallelismFactor;
    }

    public void setParallelismFactor(int parallelismFactor) {
        this.parallelismFactor = parallelismFactor;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public long getBlockedThreadCheckInterval() {
        return blockedThreadCheckInterval;
    }

    public void setBlockedThreadCheckInterval(long blockedThreadCheckInterval) {
        this.blockedThreadCheckInterval = blockedThreadCheckInterval;
    }

    public long getWarningExceptionTime() {
        return warningExceptionTime;
    }

    public void setWarningExceptionTime(long warningExceptionTime) {
        this.warningExceptionTime = warningExceptionTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NemoConfig [appName=");
        builder.append(appName);
        builder.append(", host=");
        builder.append(host);
        builder.append(", port=");
        builder.append(port);
        builder.append(", basePackage=");
        builder.append(basePackage);
        builder.append(", warningExceptionTime=");
        builder.append(warningExceptionTime);
        builder.append(", blockedThreadCheckInterval=");
        builder.append(blockedThreadCheckInterval);
        builder.append("]");
        return builder.toString();
    }
}
