package org.kunteng.ktopenapi.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;


/**
 * 实现Date类型和String类型相互转换
 * 2014年4月29日
 */
public class DateUtil {

	/**
	 * 将Date类型数据转化为字符串
	 * zhouzhihao
	 * @param date 日期类型数据
	 * @return
	 */
	public static String date2String(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	 /**
     * 返回YYYYMMDDHHMMSS字符型日期
     *
     * @return 返回字符型日期
     */
	public static String dateToString(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}
    public static String date2String(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
	 /**
     * 返回yyyymmdd字符型日期
     *
     * @return 返回字符型日期
     */
	public static String dateString(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyymmdd");
		return format.format(date);
	}
	
	/**
	 * 将字符串转化为Date类型
	 * zhouzhihao
	 * @param str 字符串 格式为 yyyy-MM-dd
	 * @return
	 * @throws Exception
	 */
	public static Date string2Date(String str) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(str);
	}

    /**
     * 返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期
     */
    public static String getDate(Date date) {
        return format(date, "yyyy/MM/dd");
    }
    /**
     * 格式化输出日期
     *
     * @param date   日期
     * @param format 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                java.text.DateFormat df = new SimpleDateFormat(format);
                result = df.format(date);
            }
        }
        catch (Exception e) {
//            e.printStackTrace();
        }
        return result;
    }

    /**
     * 日期相减
     *
     * @param date  日期
     * @param day 天数
     * @return 返回相减后的日期
     */
    public static Date diffDate(Date date, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) - ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 格式化日期
     *
     * @param dateStr 字符型日期
     * @param format  格式
     * @return 返回日期
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            java.text.DateFormat df = new SimpleDateFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
            }
            date = df.parse(dt);
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取两个日期之间的时间间隔
     * @param currentDate
     * @param dateEnd
     * @return 1秒  1分钟  1小时 1天
     */
    public static String getTwoDay(Object currentDate, Object dateEnd) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        String days="";
        try {
            Date dateS = null;
            if(currentDate instanceof String) {
                dateS = myFormatter.parse((String)currentDate);
            } else if(currentDate instanceof Date) {
                dateS = (Date)currentDate;
            } else {
                return "";
            }
            Date dateE = null;
            if(dateEnd instanceof String) {
                dateE = myFormatter.parse((String)dateEnd);
            } else if(dateEnd instanceof Date) {
                dateE = (Date)dateEnd;
            } else {
                return "";
            }
            day = (dateS.getTime() - dateE.getTime()) / (24 * 60 * 60 * 1000); //天
            if(day==0){
                day = (dateS.getTime() - dateE.getTime()) / (60 * 60 * 1000); //时
                if(day==0){
                    day = (dateS.getTime() - dateE.getTime()) / ( 60 * 1000); //分
                    if(day==0){
                        day = (dateS.getTime() - dateE.getTime()) / (1000); //秒
                        days =day+" 秒";
                    }else{
                        days =day+" 分钟";
                    }
                }else{
                    days =day+" 小时";
                }
            }else{
                days =day+" 天";
            }
        } catch (Exception e) {
            return "";
        }
        return days + "";
    }


    /**
     * 判断两个日期是不是同一个天（两个时间的间隔小于24小时）
     * @param startDate
     * @param currentDate
     * @return 1秒  1分钟  1小时 1天
     */
    public static boolean isOneDay(Object startDate, Object currentDate) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean flag = false;
        long day = 0;
        String days="";
        try {
            Date dateS = null;
            if(currentDate instanceof String) {
                dateS = myFormatter.parse((String)currentDate);
            } else if(currentDate instanceof Date) {
                dateS = (Date)currentDate;
            } else {
                return flag;
            }
            Date dateE = null;
            if(startDate instanceof String) {
                dateE = myFormatter.parse((String)startDate);
            } else if(startDate instanceof Date) {
                dateE = (Date)startDate;
            } else {
                return flag;
            }
            day = (dateS.getTime() - dateE.getTime()) / (24 * 60 * 60 * 1000); //天
            if(day==0){
               flag = true;
            }else{
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    /**
     * 返回年份
     *
     * @param date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.YEAR);
    }

    /**
     * 返回月份
     *
     * @param date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.MONTH) + 1;
    }

    /**
     * 返回日份
     *
     * @param date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.SECOND);
    }
    
    /**
     * 字符串转日期类型
     * @param date
     * @return
     */
    public static Date convert(String date) {
        Date retValue = null;
        if (StringUtils.isBlank(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            retValue = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return retValue;
    }
    public static boolean isDate(String date) {
        Date retValue = null;
        if (StringUtils.isBlank(date)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            retValue = sdf.parse(date);
            if(retValue != null){
                return  true;
            }else {
                return  false;
            }
        } catch (ParseException e) {
           return false;
        }
        //return retValue;
    }
    /**
     * 增加的天数
     * @param d
     * @param day
     * @return
     */
    public static Date addDate(Date d,long day) {
    	  Date retValue = null;
    	  long time = d.getTime(); 
    	  day = day*24*60*60*1000; 
    	  time+=day; 
    	  try {
              retValue = new Date(time);
          } catch (Exception e) {
              e.printStackTrace();
          }
    	  return retValue;
    }
 
    /**
     *  增加权限表结束日期
     * @param starteDate
     * @param endDate
     * @param endTime
     * @return
     */
    public static Date addDate(Date starteDate, Date endDate, Date endTime){
    	Date retValue = null;
    	long start = starteDate.getTime();
    	long end = endDate.getTime();
    	long endtime = endTime.getTime();
    	long res = end - start;
    	endtime+=res;
    	 try {
             retValue = new Date(endtime);
         } catch (Exception e) {
             e.printStackTrace();
         }
   	  	return retValue;
    }
    /**
     * 要增加的天数加一
     */
    public static Date adddDate(Date d){
    	Calendar calendar = new  GregorianCalendar(); 
        calendar.setTime(d); 
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
        Date date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
        return date;
    }
    /**
     * 获取下月的第一天
     * @param sDate1
     * @return
     */
    public static Date getLastDayOfMonth(Date   sDate1)   {  
        Calendar   cDay1   =   Calendar.getInstance();  
        cDay1.setTime(sDate1);  
        final   int   lastDay   =   cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
        Date   date   =   cDay1.getTime();  
        date.setDate(lastDay);  
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
        String time=formatter.format(date);
        Date udDate = null;
		try {
			udDate = formatter.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Date lastDate = adddDate(udDate);
        return  lastDate;  
	}  
    
    /**
     * 获取本月的最后一天
     * @param sDate1
     * @return
     */
    public static Date getLastDay(Date   sDate1)   {  
        Calendar   cDay1   =   Calendar.getInstance();  
        cDay1.setTime(sDate1);  
        final   int   lastDay   =   cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
        Date   date   =   cDay1.getTime();  
        date.setDate(lastDay);  
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
        String time=formatter.format(date);
        Date udDate = null;
		try {
			udDate = formatter.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        return  udDate;  
	}
    
    public static void main(String[] args) {
    	
         //Date  sDate=	addDate(convert("2015-07-01 13:20:00"),new Date(),new Date());
    	/* Date sDate=adddDate(new Date());
         SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String time=formatter.format(sDate);
         System.out.println(time); */
         int  intString = dateToString().length();
         System.out.println(intString);
         String startdate = "2015-10-30 07:11:12";
         boolean flag = DateUtil.isOneDay(startdate,new Date());
         System.out.print(flag);

	}
}
