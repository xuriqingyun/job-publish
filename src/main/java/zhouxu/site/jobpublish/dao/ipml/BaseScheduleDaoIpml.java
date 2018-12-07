package zhouxu.site.jobpublish.dao.ipml;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import zhouxu.site.jobpublish.dao.BaseScheduleDao;
import zhouxu.site.jobpublish.exception.BizException;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhouxu
 * Date: 2018-12-06 16:03
 */
@Repository
public class BaseScheduleDaoIpml implements BaseScheduleDao {

    private Logger logger = LoggerFactory.getLogger(BaseScheduleDaoIpml.class);

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    /**
     * @Author zhouxu
     * @Description //map转换为JobDataMap
     * @Date 2018/12/6 22:37
     * @Param [map]
     * @return org.quartz.JobDataMap
     * @throws
     **/
    private JobDataMap parseToJobDataMap(Map<String,Object> map){
        JobDataMap jobDataMap = new JobDataMap();
        if(map==null){
            return jobDataMap;
        }
        for(String key: map.keySet()){
            jobDataMap.put(key,map.get(key));
        }
        return jobDataMap;
    }

    @Override
    public void addOnceJob(Class<? extends Job> clazz, String jobName, String groupName, Map<String, Object> params) {
        try {
            // 启动调度器
            scheduler.start();
        } catch (SchedulerException ex) {
//            logger.error("创建立即执行任务失败，调度器启动失败。" + ex.getMessage());
            ex.printStackTrace();
            throw new BizException("创建立即执行任务失败，调度器启动失败");
        }
        JobDetail jobDetail = null;
        try {
            //构建job信息
            jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, groupName).build();
        } catch (Exception ex) {
//            logger.error("创建立即执行任务失败，构建job信息异常。" + ex.getMessage());
            ex.printStackTrace();
            throw new BizException("创建立即执行任务失败，构建job信息异常");
        }
        //按新的cronExpression表达式构建一个新的trigger
        SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger().usingJobData(parseToJobDataMap(params)).withIdentity(jobName, groupName).startNow().build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
//            logger.error("创建立即执行任务失败。" + e.getMessage());
            e.printStackTrace();
            throw new BizException("创建立即执行任务失败");
        }
    }

    @Override
    public void addCronJob(Class<? extends Job> clazz, String jobName, String groupName,String cronExpression, Map<String, Object> params) {
        try {
            // 启动调度器
            scheduler.start();
        } catch (SchedulerException ex) {
//            logger.error("创建定时任务失败，调度器启动失败。" + ex.getMessage());
            ex.printStackTrace();
            throw new BizException("创建定时任务失败，调度器启动失败");
        }
        JobDetail jobDetail = null;
        try {
            //构建job信息
            jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, groupName).build();
        } catch (Exception ex) {
//            logger.error("创建定时任务失败，构建job信息异常。" + ex.getMessage());
            ex.printStackTrace();
            throw new BizException("创建定时任务失败，构建job信息异常");
        }
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().usingJobData(parseToJobDataMap(params)).withIdentity(jobName, groupName)
                .withSchedule(scheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
//            logger.error("创建定时任务失败。" + e.getMessage());
            e.printStackTrace();
            throw new BizException("创建定时任务失败");
        }
    }

    @Override
    public void resumeJob(String jobName, String groupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, groupName));
            logger.info("继续任务成功");
        } catch (Exception ex) {
//            logger.error("重启任务异常，" + ex.getMessage());
            ex.printStackTrace();
            throw new BizException("继续任务异常");
        }
    }

    @Override
    public void pauseJob(String jobName, String groupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, groupName));
            logger.info("暂停任务成功");
        } catch (Exception ex) {
//            logger.error("暂停任务异常，" + ex.getMessage());
            ex.printStackTrace();
            throw new BizException("暂停任务异常");
        }
    }

    @Override
    public void updateCronJob(String jobName, String groupName, String cronExpression,Map<String, Object> params) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).usingJobData(parseToJobDataMap(params)).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
//            logger.error("更新定时任务失败" + e);
            e.printStackTrace();
            throw new BizException("更新定时任务失败");
        }
    }

    @Override
    public void deleteJob(String jobName, String groupName) throws Exception {
        try {
            scheduler.deleteJob(JobKey.jobKey(jobName, groupName));
            logger.info("删除任务成功");
        } catch (Exception ex) {
//            logger.error("删除任务异常，" + ex.getMessage());
            ex.printStackTrace();
            throw new BizException("删除任务异常");
        }
    }

    @Override
    public List<JobDetail> queryJobs(Map<String, Object> map) {
        return null;
    }
}
