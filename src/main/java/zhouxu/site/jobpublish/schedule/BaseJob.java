package zhouxu.site.jobpublish.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import zhouxu.site.jobpublish.utils.ObjectUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:基础任务类定义统一任务实现接口
 * User: zhouxu
 * Date: 2018-12-07 9:30
 */
public abstract class BaseJob implements Job {

    //任务名称
    private String jobName;

    //所属组
    private String groupName;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @Author zhouxu
     * @Description //任务逻辑实现接口
     * @Date 2018/12/7 9:33
     * @Param [jobExecutionContext]
     * @return void
     * @throws
     **/
    public abstract void excuteJob(JobExecutionContext jobExecutionContext) throws JobExecutionException;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //注入jobName/groupName
        this.setJobName(jobExecutionContext.getJobDetail().getKey().getName());
        this.setGroupName(jobExecutionContext.getJobDetail().getKey().getGroup());
        //自动注入其他属性
        ObjectUtils.map2Object(this,jobExecutionContext.getMergedJobDataMap());
        excuteJob(jobExecutionContext);
    }
}
