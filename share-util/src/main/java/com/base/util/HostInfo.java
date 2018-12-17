package com.base.util;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 当前主机信息类。
 */
public class HostInfo {
    /**
     * 主机前缀
     */
    private static String hostPrefix;

    static {
        hostPrefix = getLocalHostIPV4() + ":" + getProcessId();
    }

    private static int getProcessId() {
        try {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            StringBuffer pid = new StringBuffer();
            for (int i = 0, l = name.length(); i < l; i++) {
                if (Character.isDigit(name.charAt(i))) {
                    pid.append(name.charAt(i));
                } else if (pid.length() > 0) {
                    break;
                }
            }

            return Integer.parseInt(pid.toString());
        } catch (Throwable e) {
            return 0;
        }
    }

    private static String getLocalHostName() {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    private static String getLocalHostIPV4() {
        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();
            List<String> candidateIps = new ArrayList<String>();
            while (en.hasMoreElements()) {
                NetworkInterface i = (NetworkInterface) en.nextElement();
                for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements(); ) {
                    InetAddress addr = (InetAddress) en2.nextElement();
                    if (!addr.isLoopbackAddress()) {
                        if (addr instanceof Inet4Address) {
                            candidateIps.add(addr.getHostAddress());
                        }
                    }
                }
            }
            if (candidateIps == null || candidateIps.isEmpty()) {
                return "Unknown";
            }
            String localHostIp = getLocalHostName();
            if (candidateIps.contains(localHostIp)) {
                return localHostIp;
            }
            return candidateIps.get(0);
        } catch (Exception e) {
            return "Unknown";
        }

    }

    public static String getHostInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append(hostPrefix);
        sb.append(":");
        sb.append(Thread.currentThread().getId());
        return sb.toString();
    }

    public static String getHostAndProcessInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append(hostPrefix);
        return sb.toString();
    }
}
