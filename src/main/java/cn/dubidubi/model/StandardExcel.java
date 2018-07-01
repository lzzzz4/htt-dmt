package cn.dubidubi.model;

import java.io.Serializable;

/**
 * @author lzj
 * @Description:
 * @date 18-7-1下午2:51
 */
public class StandardExcel implements Serializable {
    private String source;
    private String updateTime;
    private String topic;
    private String unit;
    private String years;
    private String country;
    private String data;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "StandardExcel{" +
                "source='" + source + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", topic='" + topic + '\'' +
                ", unit='" + unit + '\'' +
                ", years='" + years + '\'' +
                ", country='" + country + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
