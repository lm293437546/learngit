package com.xws.bootpro.utils.ipUtil;

import cn.hutool.json.JSONObject;
import com.xws.bootpro.utils.ipUtil.ip2region.DataBlock;
import com.xws.bootpro.utils.ipUtil.ip2region.DbConfig;
import com.xws.bootpro.utils.ipUtil.ip2region.DbSearcher;
import com.xws.bootpro.utils.ipUtil.ip2region.Util;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class ReginIpUtil {

    /**
     * 获取访问用户的客户端IP
     */
    public static String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            return ip.split(",")[0];
        } else {
            return ip;
        }
    }

    /**
     * 获取本机的ip地址
     *
     * @return
     * @throws SocketException
     */
    public static String getInnetIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        Enumeration<NetworkInterface> netInterfaces;
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

    // 根据ip获取地址
    public static String getCityInfo(String ip) {
        try {
            // db
            String dbPath = ReginIpUtil.class.getResource("/ip2region.db").getPath();
            File file = new File(dbPath);
            if (file.exists() == false) {
                System.out.println("Error: Invalid ip2region.db file");
            }

            // 查询算法
            int algorithm = DbSearcher.BTREE_ALGORITHM; // B-tree
            // DbSearcher.BINARY_ALGORITHM //Binary
            // DbSearcher.MEMORY_ALGORITYM //Memory

            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbPath);

            // define the method
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }

            DataBlock dataBlock = null;
            if (Util.isIpAddress(ip) == false) {
                System.out.println("Error: Invalid ip address");
            }

            dataBlock = (DataBlock) method.invoke(searcher, ip);

            return dataBlock.getRegion();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 请求ip对应的地址经纬度
     * @return
     */
    public static String getDetailIpAddr(String  ip){
        if("127.0.0.1".equals(ip)){
            return "局域网";
        }
        String APP_KEY = "JLABZ-M6TCO-65BWN-S7PXE-TJKCQ-UJFMJ";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject forObject = restTemplate.getForObject("https://apis.map.qq.com/ws/location/v1/ip?ip="+ip+"&key="+APP_KEY,
                JSONObject.class);
        JSONObject location = forObject.getJSONObject("result").getJSONObject("location");
        Object lat = location.get("lat");
        Object lng = location.get("lng");
        String latLng=lat.toString()+","+lng.toString();
        //System.out.println("经纬度"+latLng);
        //获取经纬度对应的地址原来的不详细没有给到街道
        JSONObject forObject1 = restTemplate.getForObject("https://apis.map.qq.com/ws/geocoder/v1/?location=" + latLng + "&key=" + APP_KEY, JSONObject.class);
        //位置描述
        Object o = forObject1.getJSONObject("result").get("address");
        return  o.toString();
    }

}
