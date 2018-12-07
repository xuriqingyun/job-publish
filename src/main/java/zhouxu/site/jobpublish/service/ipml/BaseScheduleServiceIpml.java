package zhouxu.site.jobpublish.service.ipml;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhouxu.site.jobpublish.dao.BaseScheduleDao;
import zhouxu.site.jobpublish.service.BaseScheduleService;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:任务调度服务实现
 * User: zhouxu
 * Date: 2018-12-06 16:24
 */
@Service
public class BaseScheduleServiceIpml implements BaseScheduleService {

    @Autowired
    private BaseScheduleDao baseScheduleDao;

    @Override
    public void addOnceJob(Class<? extends Job> clazz, String jobName, String groupName, Map<String, Object> params) {
        baseScheduleDao.addOnceJob(clazz,jobName,groupName,params);
    }

    @Override
    public void addCronJob(Class<? extends Job> clazz, String jobName, String groupName, String cronExpression, Map<String, Object> params) {
        baseScheduleDao.addCronJob(clazz,jobName,groupName,cronExpression,params);
    }

    @Override
    public void resumeJob(String jobName, String groupName) {
        baseScheduleDao.resumeJob(jobName,groupName);
    }

    @Override
    public void pauseJob(String jobName, String groupName) {
        baseScheduleDao.pauseJob(jobName,groupName);
    }

    @Override
    public void updateCronJob(String jobName, String groupName, String cronExpression, Map<String, Object> params) {
        baseScheduleDao.updateCronJob(jobName,groupName,cronExpression,params);
    }

    @Override
    public void deleteJob(String jobName, String groupName) throws Exception {
        baseScheduleDao.deleteJob(jobName,groupName);
    }

    @Override
    public List<JobDetail> queryJobs(Map<String, Object> map) {
        return baseScheduleDao.queryJobs(map);
    }
}
