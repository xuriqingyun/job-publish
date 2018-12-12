package zhouxu.site.jobpublish.pojo;

import zhouxu.site.jobpublish.utils.DateUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:指标对象用于保存influxdb数据
 * User: zhouxu
 * Date: 2018-12-11 15:56
 */
public class InfluxMetric {
    //表名
    private String metricName;
    //标签
    private Map<String, String> tags;
    //记录时间搓
    private Long time;
    //准确率
    private TimeUnit precision;
    //值
    private Map<String, Object> fields;

    //point数据汇总，后续可用于对象转换
    private Map<String,Object> properties;

    public InfluxMetric(String metricName){
        this.metricName = metricName;
    }

    public String getMetricName() {
        return metricName;
    }


    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public void addTag(String tagKey,String tagValue){
        if(this.tags==null){
            this.tags = new HashMap<>();
        }
        this.tags.put(tagKey,tagValue);
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public TimeUnit getPrecision() {
        return precision;
    }

    public void setPrecision(TimeUnit precision) {
        this.precision = precision;
    }

    public void addField(String fieldKey, Object fieldValue){
        if(this.fields==null){
            this.fields = new HashMap<>();
        }
        this.fields.put(fieldKey,fieldValue);
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Map<String, Object> getProperties() {
        Map<String,Object> properties = new HashMap<>();
        properties.put("metricName",metricName);
        properties.put("time",time);
        properties.put("precision",precision);
        if(tags!=null){
            for(String tagK:tags.keySet()){
                properties.put(tagK,tags.get(tagK));
            }
        }
        if(fields!=null){
            for(String fieldK:fields.keySet()){
                properties.put(fieldK,fields.get(fieldK));
            }
        }
        return properties;
    }

    @Override
    public String toString() {
        return "InfluxMetric{" +
                "metricName='" + metricName + '\'' +
                ", tags=" + tags +
                ", time=" + DateUtils.getTimeStr(this.time) +
                ", precision=" + precision +
                ", fields=" + fields +
                ", properties=" + properties +
                '}';
    }
}
