package com.plateno.booking.internal.common.util;

/**
 * Created by jicexosl on 2015/3/4.
 */
public class GEOUtil {

	//计算两点距离，单位为km
    public static double countDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378.137;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
    
    //百度转谷歌（高德地图与谷歌地图坐标一样）
    public static double[] bd2gcj(double lat,double lng ) {
    	double m = rad(3000.0);
        double x = lng - 0.0065, y =lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * m);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * m);
        double[] locs = new double[2];
        locs[1] = z * Math.cos(theta);//lng
        locs[0] = z * Math.sin(theta);//lat
        return locs;
    }

}
