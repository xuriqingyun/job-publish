package zhouxu.site.jobpublish.service.ipml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zhouxu.site.jobpublish.schedule.ExcuteShellJob;
import zhouxu.site.jobpublish.service.BaseScheduleService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhouxu
 * Date: 2018-12-06 22:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseScheduleServiceIpmlTest {

    @Autowired
    private BaseScheduleService baseScheduleService;

    @Test
    public void addOnceJob() {
        Map<String,Object> map = new HashMap<>();
        map.put("shellName","clean.sh");
        baseScheduleService.addOnceJob(ExcuteShellJob.class,"job-1","Defualt",map);
    }

    @Test
    public void addCronJob() {
        Map<String,Object> map = new HashMap<>();
        map.put("shellName","clean.sh");
        String cron="* * * * * ?";
        baseScheduleService.addCronJob(ExcuteShellJob.class,"job-1","Defualt",cron,map);
    }
}