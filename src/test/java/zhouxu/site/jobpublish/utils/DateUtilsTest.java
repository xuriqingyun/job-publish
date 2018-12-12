package zhouxu.site.jobpublish.utils;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhouxu
 * Date: 2018-12-12 10:57
 */
public class DateUtilsTest {

    @Test
    public void utcTest() throws ParseException {
//        String time="2018-12-11T10:03:10.047358197Z";
//        System.out.println(time);
//        String utcFormat="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(utcFormat);
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date date = null;
//        try {
//            date = simpleDateFormat.parse(time);
//            System.out.println(date+"----------------------");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
//        System.out.println(calendar.getTime());
//        System.out.println(calendar.getTimeInMillis()+"*********");
//
//        String format="yyyy-mm-dd  HH:mm:ss";
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//         System.out.println(simpleDateFormat.format(date));

         SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
         Date t = new Date();
//         System.out.println(df1.format(t));
//         System.out.println(df1.format(df1.parse("1970-01-18T21:03:18Z")) + "***********");
//         df1.setTimeZone(TimeZone.getTimeZone("UTC"));
        t=df1.parse("2018-12-12T07:11:03Z");
        System.out.println("-----------");

         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(t));

         System.out.println(new Date().getTime());
//         System.out.println(df1.format(df1.parse("2014-08-27T18:02:59.676Z")) + "***********");
//         System.out.println("2014-08-27T18:02:59.676Z");
    }

    @Test
    public void dateTest(){
//        Date date = new Date(1544593849000L);
        Date date =new Date();
        System.out.println(date.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        System.out.println(simpleDateFormat.format(date));
    }
}
