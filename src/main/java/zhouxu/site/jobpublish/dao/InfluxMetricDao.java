package zhouxu.site.jobpublish.dao;

import zhouxu.site.jobpublish.pojo.InfluxMetric;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:influx指标crud
 * User: zhouxu
 * Date: 2018-12-11 16:12
 */
public interface InfluxMetricDao {

    /**
     * @Author zhouxu
     * @Description //在对应库中的添加指标数据
     * @Date 2018/12/11 16:43
     * @Param [database, influxMetric]
     * @return boolean
     * @throws
     **/
    boolean add(String database,InfluxMetric influxMetric);

    /**
     * @Author zhouxu
     * @Description //在对应库中的添加指标数据同时添加策略
     * @Date 2018/12/11 16:43
     * @Param [database, influxMetric]
     * @return boolean
     * @throws
     **/
    boolean add(String database,String retentionPolicy,InfluxMetric influxMetric);
    /**
     * @Author zhouxu
     * @Description //在对应库中的批量添加指标数据同时添加策略
     * @Date 2018/12/11 16:33
     * @Param [influxMetrics]
     * @return boolean
     * @throws
     **/
    boolean batchAdd(String database,List<InfluxMetric> influxMetrics);
    /**
     * @Author zhouxu
     * @Description //在对应库中的批量添加指标数据
     * @Date 2018/12/11 16:33
     * @Param [influxMetrics]
     * @return boolean
     * @throws
     **/
    boolean batchAdd(String database,String retentionPolicy,List<InfluxMetric> influxMetrics);

    /**
     * @Author zhouxu
     * @Description //查询指标
     * @Date 2018/12/11 16:33
     * @Param [influxSQL]
     * @return java.util.List<zhouxu.site.jobpublish.pojo.InfluxMetric>
     * @throws
     **/
    List<InfluxMetric> list(String database,String influxSQL);

    /**
     * @Author zhouxu
     * @Description //创建策略
     * @Date 2018/12/11 16:33
     * @Param [influxSQL]
     * @return boolean
     * @throws
     **/
    boolean createRetention(String policyName, String database,String duration,boolean isDefault);

    /**
     * @Author zhouxu
     * @Description //创建数据库
     * @Date 2018/12/11 16:46
     * @Param [database]
     * @return boolean
     * @throws
     **/
    boolean createDatabase(String database);

    /**
     * @Author zhouxu
     * @Description //获取fieldNames集合
     * @Date 2018/12/11 17:29
     * @Param [database]
     * @return java.util.List<java.lang.String>
     * @throws
     **/
    List<String> getFieldNames(String database,String metricName);

    /**
     * @Author zhouxu
     * @Description //获取TagNames集合
     * @Date 2018/12/11 17:30
     * @Param [database, metricName]
     * @return java.util.List<java.lang.String>
     * @throws
     **/
    List<String> getTagNames(String database,String metricName);

    /**
     * @Author zhouxu
     * @Description //删除表
     * @Date 2018/12/12 15:41
     * @Param [database, metricName]
     * @return boolean
     * @throws
     **/
    boolean delete(String database,String metricName);
}
