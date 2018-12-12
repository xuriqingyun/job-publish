package zhouxu.site.jobpublish.dao.ipml;

import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zhouxu.site.jobpublish.dao.InfluxMetricDao;
import zhouxu.site.jobpublish.pojo.InfluxMetric;
import zhouxu.site.jobpublish.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:influx 库接口实现
 * User: zhouxu
 * Date: 2018-12-11 16:47
 */
@Repository
public class InfluxMetricDaoImpl implements InfluxMetricDao {

    @Autowired
    private InfluxDB influxDB;

    @Override
    public boolean add(String database, InfluxMetric influxMetric) {
        Point point = null;
        Point.Builder measurement = Point.measurement(influxMetric.getMetricName());
        if(influxMetric.getTime()!=null){
            measurement.time(influxMetric.getTime(), TimeUnit.MILLISECONDS);
        }
        measurement.tag(influxMetric.getTags())
                .fields(influxMetric.getFields());
        point = measurement.build();
        influxDB.write(database,"",point);
        return true;
    }

    @Override
    public boolean add(String database, String retentionPolicy, InfluxMetric influxMetric) {
        Point point = null;
        Point.Builder measurement = Point.measurement(influxMetric.getMetricName());
        if(influxMetric.getTime()!=null){
            measurement.time(influxMetric.getTime(), TimeUnit.MILLISECONDS);
        }
        measurement.tag(influxMetric.getTags())
                .fields(influxMetric.getFields());
        point = measurement.build();
        influxDB.write(database,retentionPolicy,point);
        return true;
    }

    @Override
    public boolean batchAdd(String database, List<InfluxMetric> influxMetrics) {
        Point[] points = new Point[influxMetrics.size()];
        buildPoints(influxMetrics, points);
        BatchPoints batchPoints = BatchPoints.database(database).points(points).build();
        influxDB.write(batchPoints);
        return true;
    }

    @Override
    public boolean batchAdd(String database, String retentionPolicy, List<InfluxMetric> influxMetrics) {
        Point[] points = new Point[influxMetrics.size()];
        buildPoints(influxMetrics, points);
        BatchPoints batchPoints = BatchPoints.database(database).points(points).retentionPolicy(retentionPolicy).build();
        influxDB.write(batchPoints);
        return true;
    }

    /**
     * @Author zhouxu
     * @Description //创建Points
     * @Date 2018/12/11 17:26
     * @Param [influxMetrics, points]
     * @return void
     * @throws
     **/
    private void buildPoints(List<InfluxMetric> influxMetrics, Point[] points) {
        for(int i=0;i<influxMetrics.size();i++){
            InfluxMetric influxMetric= influxMetrics.get(i);
            Point point = null;
            Point.Builder measurement = Point.measurement(influxMetric.getMetricName());
            if(influxMetric.getTime()!=null){
                measurement.time(influxMetric.getTime(), TimeUnit.MILLISECONDS);
            }
            measurement.tag(influxMetric.getTags())
                    .fields(influxMetric.getFields());
            point = measurement.build();
            points[i]=point;
        }
    }

    @Override
    public List<InfluxMetric> list(String database, String influxSQL){
        List<InfluxMetric> influxMetrics = new ArrayList<>();
        QueryResult query = influxDB.query(new Query(influxSQL, database));
        if(query!=null&&query.getResults()!=null){
            List<QueryResult.Result> results = query.getResults();
            if(results!=null){
                for(QueryResult.Result result: results){
                    List<QueryResult.Series> series = result.getSeries();
                    if(series!=null){
                        for(QueryResult.Series series1: series){
                            List<String> columns = series1.getColumns();
                            String metricName = series1.getName();
                            List<List<Object>> values = series1.getValues();
                            if(values!=null&&columns!=null){
//                                for(int i=0;i<columns.size();i++){
//                                    String key = columns.get(i);
                                    for(List<Object> valueList:values){
                                        if(valueList.size()==columns.size()){
                                            InfluxMetric influxMetric = new InfluxMetric(metricName);
                                            influxMetrics.add(influxMetric);
                                            for (int i=0;i<valueList.size();i++){
                                                String key = columns.get(i);
                                                if(containField(database,metricName,key)){
                                                    influxMetric.addField(key,valueList.get(i));
                                                }else if(containTag(database,metricName,key)){
                                                    influxMetric.addTag(key,valueList.get(i).toString());
                                                }else if(key.equals("time")){
                                                    influxMetric.setTime(DateUtils.getUtcSecends(valueList.get(i).toString()));
                                                }
                                            }
                                        }
                                    }
//                                }
                            }
                        }
                    }
                }
            }
        }
        return influxMetrics;
    }

    @Override
    public boolean createRetention(String policyName, String database,String duration,boolean isDefault) {
        String defaultStr="";
        if(isDefault){
            defaultStr="DEFAULT";
        }
        String sql = "CREATE RETENTION POLICY \"" + policyName + "\" "
                + "ON \""+database+"\" DURATION "+duration+" REPLICATION 1 "+ defaultStr;
        Query query = new Query(sql, database);
        influxDB.query(query);
        return true;
    }

    @Override
    public boolean createDatabase(String database) {
        influxDB.createDatabase(database);
        return false;
    }

    /**
     * @Author zhouxu
     * @Description //检测是否包含该fieldKey
     * @Date 2018/12/12 9:44
     * @Param [database, metricName, tagKey]
     * @return boolean
     * @throws
     **/
    private boolean containField(String database, String metricName,String fieldKey){
        List<String> filedNames = getFieldNames(database, metricName);
        for(String field:filedNames){
            if(field.equals(fieldKey)){
                return true;
            }
        }
        return false;
    }


    @Override
    public List<String> getFieldNames(String database, String metricName) {
        String influxSQL ="SHOW FIELD  KEYS FROM \""+metricName+"\"";
        List<String> keys = new ArrayList<>();
        QueryResult query = influxDB.query(new Query(influxSQL, database));
        if(query!=null&&query.getResults()!=null){
            List<QueryResult.Result> results = query.getResults();
            if(results!=null){
                for(QueryResult.Result result: results){
                    List<QueryResult.Series> series = result.getSeries();
                    if(series!=null){
                        for(QueryResult.Series series1: series){
                            List<List<Object>> values = series1.getValues();
                            if(values!=null){
                                for(List<Object> valueList:values){
                                    if(valueList!=null&&valueList.size()>0){
                                        keys.add(valueList.get(0).toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return keys;
    }

    /**
     * @Author zhouxu
     * @Description //检测是否包含该tagKey
     * @Date 2018/12/12 9:44
     * @Param [database, metricName, tagKey]
     * @return boolean
     * @throws
     **/
    private boolean containTag(String database, String metricName,String tagKey){
        List<String> tagNames = getTagNames(database, metricName);
        for(String tag:tagNames){
            if(tag.equals(tagKey)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getTagNames(String database, String metricName) {
        String influxSQL ="SHOW TAG KEYS FROM \""+metricName+"\"";
        List<String> keys = new ArrayList<>();
        QueryResult query = influxDB.query(new Query(influxSQL, database));
        if(query!=null&&query.getResults()!=null){
            List<QueryResult.Result> results = query.getResults();
            if(results!=null){
                for(QueryResult.Result result: results){
                    List<QueryResult.Series> series = result.getSeries();
                    if(series!=null){
                        for(QueryResult.Series series1: series){
                            List<List<Object>> values = series1.getValues();
                            if(values!=null){
                                for(List<Object> valueList:values){
                                    if(valueList!=null){
                                        for(Object value: valueList){
                                            keys.add(value.toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return keys;
    }

    @Override
    public boolean delete(String database, String metricName) {
        String sql="drop measurement \""+metricName+"\"";
        Query query = new Query(sql,database);
        influxDB.query(query);
        return  true;
    }
}
