package zhouxu.site.jobpublish.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:执行shell任务
 * User: zhouxu
 * Date: 2018-12-06 16:53
 */
public class ExcuteShellJob extends BaseJob {

    private Logger logger = LoggerFactory.getLogger(ExcuteShellJob.class);

    //getter、setter可以将jobdatamap自动注入
    private String shellName;

    private String shellContent;

    public String getShellName() {
        return shellName;
    }

    public void setShellName(String shellName) {
        this.shellName = shellName;
    }

    public String getShellContent() {
        return shellContent;
    }

    public void setShellContent(String shellContent) {
        this.shellContent = shellContent;
    }

    @Override
    public void excuteJob(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        logger.info(jobName+": excute "+ this.shellName);
    }
}
