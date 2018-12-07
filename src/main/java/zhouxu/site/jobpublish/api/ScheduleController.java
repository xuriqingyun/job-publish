package zhouxu.site.jobpublish.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import zhouxu.site.jobpublish.schedule.ExcuteShellJob;
import zhouxu.site.jobpublish.service.BaseScheduleService;
import zhouxu.site.jobpublish.utils.RestResult;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhouxu
 * Date: 2018-12-06 16:50
 */
@RestController
public class ScheduleController {

    @Autowired
    private BaseScheduleService baseScheduleService;

    @GetMapping("/health")
    public RestResult health(String code){
        return RestResult.Success(code);
    }

    /**
     * @Author zhouxu
     * @Description //创建一次性任务
     * @Date 2018/12/6 17:01
     * @Param [jobName, groupName, params]
     * @return void
     * @throws
     **/
    @PostMapping("/addOnceJob")
    public RestResult addOnceJob(String jobName, String groupName){
        baseScheduleService.addOnceJob(ExcuteShellJob.class,jobName,groupName,null);
        return RestResult.Success(true);
    }

    /**
     * @Author zhouxu
     * @Description //创建定时任务
     * @Date 2018/12/6 17:01
     * @Param [jobName, groupName, cronExpression, params]
     * @return void
     * @throws
     **/
    @PostMapping("/addCronJob")
    public RestResult addCronJob(String jobName,String groupName,String cronExpression){
        baseScheduleService.addCronJob(ExcuteShellJob.class,jobName,groupName,cronExpression,null);
        return  RestResult.Success(true);
    }

    /**
     * @Author zhouxu
     * @Description //暂停任务
     * @Date 2018/12/7 10:23
     * @Param [jobName, groupName, cronExpression]
     * @return zhouxu.site.jobpublish.utils.RestResult
     * @throws
     **/
    @PostMapping("/pauseJob")
    public RestResult pauseJob(String jobName,String groupName){
        baseScheduleService.pauseJob(jobName,groupName);
        return  RestResult.Success(true);
    }

    /**
     * @Author zhouxu
     * @Description //暂停后继续任务
     * @Date 2018/12/7 10:24
     * @Param [jobName, groupName, cronExpression]
     * @return zhouxu.site.jobpublish.utils.RestResult
     * @throws
     **/
    @PostMapping("/resumeJob")
    public RestResult resumeJob(String jobName,String groupName){
        baseScheduleService.resumeJob(jobName,groupName);
        return  RestResult.Success(true);
    }

    /**
     * @Author zhouxu
     * @Description //删除任务
     * @Date 2018/12/7 10:24
     * @Param [jobName, groupName, cronExpression]
     * @return zhouxu.site.jobpublish.utils.RestResult
     * @throws
     **/
    @PostMapping("/deleteJob")
    public RestResult deleteJob(String jobName,String groupName) throws Exception {
        baseScheduleService.deleteJob(jobName,groupName);
        return  RestResult.Success(true);
    }

    /**
     * @Author zhouxu
     * @Description //更新任务
     * @Date 2018/12/7 10:24
     * @Param [jobName, groupName, cronExpression]
     * @return zhouxu.site.jobpublish.utils.RestResult
     * @throws
     **/
    @PostMapping("/updateCronJob")
    public RestResult updateCronJob(String jobName,String groupName, String cronExpression,Map<String,Object> params)  {
        baseScheduleService.updateCronJob(jobName,groupName,cronExpression,params);
        return  RestResult.Success(true);
    }

}
