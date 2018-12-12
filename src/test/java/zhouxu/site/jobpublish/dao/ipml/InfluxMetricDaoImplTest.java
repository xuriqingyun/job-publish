package zhouxu.site.jobpublish.dao.ipml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zhouxu.site.jobpublish.dao.InfluxMetricDao;
import zhouxu.site.jobpublish.pojo.InfluxMetric;
import zhouxu.site.jobpublish.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhouxu
 * Date: 2018-12-11 16:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InfluxMetricDaoImplTest {

    @Autowired
    private InfluxMetricDao influxMetricDao;
    @Test
    public void add() {
        InfluxMetric metric = new InfluxMetric("memory_average");
        metric.addTag("host","server-1");
        metric.addTag("core","1");
        metric.addField("value","40");
        metric.addField("tempratrue","80");
        metric.setTime(DateUtils.getSecends(new Date()));
        influxMetricDao.add("mydb",metric);
    }

    @Test
    public void getFieldNames(){
        List<String> fieldNames = influxMetricDao.getFieldNames("mydb", "memory_average");
       System.out.println("========================");
        for(String fieldName:fieldNames){
            System.out.println(fieldName);
        }
    }

    @Test
    public void list(){
        List<InfluxMetric> influxMetrics = influxMetricDao.list("mydb", "select * from memory_average");
        for(InfluxMetric influxMetric:influxMetrics){
            System.out.println(influxMetric);
        }
    }

    @Test
    public void delete(){
        influxMetricDao.delete("mydb","memory_average");
    }
}