package zhouxu.site.jobpublish.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhouxu
 * Date: 2018-12-12 15:21
 */
public class DateUtils {
    /**
     * @Author zhouxu
     * @Description //获取秒时间忽略毫秒
     * @Date 2018/12/12 15:24
     * @Param [millisecond]
     * @return long
     * @throws
     **/
    public static long getSecends(Long millisecond ){
        String timestamp = String.valueOf(millisecond/1000)+"000";
        return Long.valueOf(timestamp);
    }

    /**
     * @Author zhouxu
     * @Description //获取字符串时间
     * @Date 2018/12/12 16:36
     * @Param [millisecond]
     * @return java.lang.String
     * @throws
     **/
    public static String getTimeStr(Long millisecond ){
        if(millisecond==null){
            return null;
        }
        Date date = new Date(millisecond);
        String format="yyyy-MM-dd  HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * @Author zhouxu
     * @Description //获取utc时间
     * @Date 2018/12/12 16:33
     * @Param [utcStr]
     * @return long
     * @throws
     **/
    public static Long getUtcSecends(String utcStr){
        int index =utcStr.indexOf(".");
        if(index!=-1){
            utcStr = utcStr.substring(0,index)+"Z";
        }
        //influxdb默认到
        String utcFormat="yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(utcFormat);
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = simpleDateFormat.parse(utcStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return calendar.getTimeInMillis();
    }

    /**
     * @Author zhouxu
     * @Description //获取秒时间忽略毫秒
     * @Date 2018/12/12 15:24
     * @Param [date]
     * @return long
     * @throws
     **/
    public static long getSecends(Date date){
        if (null == date) {
            return 0L;
        }
        String timestamp = String.valueOf(date.getTime()/1000)+"000";
        return Long.valueOf(timestamp);
    }
}
