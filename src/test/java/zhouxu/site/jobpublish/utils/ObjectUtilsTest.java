package zhouxu.site.jobpublish.utils;

import org.junit.Test;
import zhouxu.site.jobpublish.schedule.ExcuteShellJob;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhouxu
 * Date: 2018-12-07 9:47
 */
public class ObjectUtilsTest {

    @Test
    public void map2Object() {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("jobName","job-1");
        map.put("groupName","DEFAULT");
        map.put("shellName","clean.sh");
        map.put("abc","cc");
        ExcuteShellJob job = new ExcuteShellJob();
        ObjectUtils.map2Object(job,map);

        System.out.println(job.getJobName()+":"+job.getGroupName()+":"+job.getShellName()+":"+job.getShellContent());

    }
}