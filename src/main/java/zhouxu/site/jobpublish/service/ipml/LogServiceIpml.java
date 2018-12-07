package zhouxu.site.jobpublish.service.ipml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import zhouxu.site.jobpublish.service.LogService;

/**
 * Created with IntelliJ IDEA.
 * Description:日志服务实现
 * User: zhouxu
 * Date: 2018-12-06 14:27
 */
//@Service
//@EnableScheduling
public class LogServiceIpml implements LogService {

    private Logger logger = LoggerFactory.getLogger(LogServiceIpml.class);

    @Scheduled(cron = "*/2 * * * * *")
    @Override
    public void cleanAgentLog() {
        logger.info("clean agent log");
    }

    @Scheduled(cron = "*/3 * * * * *")
    @Override
    public void cleanServerLog() {
        logger.info("clean server log");
    }
}
