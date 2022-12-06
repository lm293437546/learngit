package com.xws.bootpro.utils.oshi;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.alibaba.fastjson.JSONObject;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
 
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 *@Author：lm
 *@Date：2022/11/30 10:45
 *@Description 通过oshi获取系统和硬件信息
*/
 
public class OshiUtils {
 
    public static JSONObject recordCpuInfo(){
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        CentralProcessor processor = OshiUtil.getProcessor();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cpuTotal",cpuInfo.getToTal());
        jsonObject.put("cpuSys",cpuInfo.getSys());
        jsonObject.put("cpuUser",cpuInfo.getUser());
        jsonObject.put("cpuWait",cpuInfo.getWait());
        jsonObject.put("cpuFree",cpuInfo.getFree());
        jsonObject.put("cpuUsed",cpuInfo.getUsed());
        jsonObject.put("cpuId",processor.getProcessorIdentifier().getProcessorID());
        jsonObject.put("serialNumber",OshiUtil.getSystem().getSerialNumber());
        return jsonObject;
    }

    /*
    * 获取序列号（S/N）,唯一
    * */
    public static String getPcSN(){
        ComputerSystem system = OshiUtil.getSystem();
        //序列号（S/N）
        String serialNumber = system.getSerialNumber();
        return serialNumber;
    }

    /*
     * 获取处理器ID(CPUID)，唯一
     * */
    public static String getPcCPUID(){
        //处理器ID(CPUID)
        String processorID = OshiUtil.getProcessor().getProcessorIdentifier().getProcessorID();
        return processorID;
    }

    /*
     * 获取通用唯一识别码(UUID)
     * */
    public static String getPcUUID(){
        ComputerSystem system = OshiUtil.getSystem();
        return system.getHardwareUUID();
    }

    /*
     * 获取PC品牌
     * */
    public static String getPcBrand(){
        ComputerSystem system = OshiUtil.getSystem();
        return system.getManufacturer();
    }

    /*
     * 获取PC系统版本号
     * */
    public static String getPcSysVersion(){
        return OshiUtil.getOs().toString();
    }

    /*
    *获取PC处理器型号
    * */
    public static String getPcProcessor(){
        CentralProcessor processor = OshiUtil.getProcessor();
        return processor.getProcessorIdentifier().getName();
    }

    /*
    * 获取内存条信息
    * */
    public static String getPcMemory(){
        GlobalMemory memory = OshiUtil.getMemory();
        List<PhysicalMemory> physicalMemoryList = memory.getPhysicalMemory();
        BigDecimal GbDw = new BigDecimal(1024 * 1024 * 1024);
        BigDecimal HzDw = new BigDecimal(1000 * 1000);
        String info = "总共" + physicalMemoryList.size() + "块内存条：";
        for (PhysicalMemory physicalMemory : physicalMemoryList) {
            info += physicalMemory + "；";
        }
        return info;
    }

    /*
    * 磁盘信息
    * */
    public static String getPcFile(){
        BigDecimal GbDw = new BigDecimal(1024 * 1024 * 1024);
        FileSystem fileSystem = OshiUtil.getOs().getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        String info = "总共分为" + fileStores.size() + "个磁盘，分别为：";
        for (OSFileStore fileStore : fileStores) {
            double getTotalSpace = new BigDecimal(fileStore.getTotalSpace()).divide(GbDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
            double getUsableSpace = new BigDecimal(fileStore.getUsableSpace()).divide(GbDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
            info += fileStore.getName() + "，总空间:" + getTotalSpace + "G，" + "可用空间:" + getUsableSpace + "，类型:" + fileStore.getType() + "，标签:" + fileStore.getLabel() + "；";
        }
        return info;
    }

    /*
    *   1、计算机系统和固件，底板
        2、操作系统和版本 / 内部版本
        3、物理（核心）和逻辑（超线程）CPU，处理器组，NUMA 节点
        4、系统和每个处理器的负载百分比和滴答计数器
        5、CPU 正常运行时间，进程和线程
        6、进程正常运行时间，CPU，内存使用率，用户 / 组，命令行
        7、已使用 / 可用的物理和虚拟内存
        8、挂载的文件系统（类型，可用空间和总空间）
        9、磁盘驱动器（型号，序列号，大小）和分区
        10、网络接口（IP，带宽输入 / 输出）
        11、电池状态（电量百分比，剩余时间，电量使用情况统计信息）
        12、连接的显示器（带有 EDID 信息）
        13、USB 设备
        14、传感器（温度，风扇速度，电压）
    * */
    public static void main(String[] args) {
        SystemInfo systemInfo = new SystemInfo();
        //获取计算机系统，固件，底版--每天执行一次，或者手动执行一次
        System.out.println(OshiUtil.getSystem().getManufacturer());
        System.out.println(OshiUtil.getSystem().getSerialNumber());
        System.out.println(OshiUtil.getSystem().getHardwareUUID());
        //操作系统和版本/内部版本
        System.out.println("====================");
        System.out.println(OshiUtil.getOs());
        //处理器型号，处理器个数，处理器核心数，处理器线程数,标识符，处理器id，供应商
        System.out.println("====================");
        CentralProcessor processor = OshiUtil.getProcessor();
        System.out.println(processor.getProcessorIdentifier().getName());
        System.out.println(processor.getPhysicalPackageCount());
        System.out.println(processor.getPhysicalProcessorCount());
        System.out.println(processor.getLogicalProcessorCount());
        System.out.println(processor.getProcessorIdentifier().getIdentifier());
        System.out.println(processor.getProcessorIdentifier().getProcessorID());
        System.out.println(processor.getProcessorIdentifier().getVendor());

        // 已使用/可用的物理和虚拟内存
        //物理内存的 容量，类型，频率，品牌
        System.out.println("====================");
        GlobalMemory memory = OshiUtil.getMemory();
        List<PhysicalMemory> physicalMemoryList = memory.getPhysicalMemory();
        BigDecimal GbDw = new BigDecimal(1024 * 1024 * 1024);
        BigDecimal HzDw = new BigDecimal(1000 * 1000);
        for (PhysicalMemory physicalMemory : physicalMemoryList) {
            System.out.println(physicalMemory.getBankLabel());
            System.out.println(physicalMemory);
            double getCapacity = new BigDecimal(physicalMemory.getCapacity()).divide(GbDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
            System.out.println(getCapacity);
            System.out.println(physicalMemory.getMemoryType());
            double getClockSpeed = new BigDecimal(physicalMemory.getClockSpeed()).divide(HzDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
            System.out.println(getClockSpeed);
            System.out.println(physicalMemory.getManufacturer());
        }
        //交换内存信息
        VirtualMemory virtualMemory = memory.getVirtualMemory();
        double getSwapTotal = new BigDecimal(virtualMemory.getSwapTotal()).divide(GbDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
        double getSwapUsed = new BigDecimal(virtualMemory.getSwapUsed()).divide(GbDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
        System.out.println(getSwapTotal);
        System.out.println(getSwapUsed);
        //获取使用率
        double getTotal = new BigDecimal(memory.getTotal()).divide(GbDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
        double getAvailable = new BigDecimal(memory.getAvailable()).divide(GbDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
        System.out.println(getTotal);
        System.out.println(getAvailable);
        //挂载的文件系统（类型，可用空间和总空间）
        System.out.println("====================");
        FileSystem fileSystem = OshiUtil.getOs().getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        // 名字，总空间，可用空间，空闲空间，mount，类型，标签，
        for (OSFileStore fileStore : fileStores) {
            System.out.println(fileStore.getName());
            double getTotalSpace = new BigDecimal(fileStore.getTotalSpace()).divide(GbDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
            System.out.println(getTotalSpace);
            double getUsableSpace = new BigDecimal(fileStore.getUsableSpace()).divide(GbDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
            System.out.println(getUsableSpace);
            double getFreeSpace = new BigDecimal(fileStore.getFreeSpace()).divide(GbDw).setScale(2, RoundingMode.HALF_UP).doubleValue();
            System.out.println(getFreeSpace);
            System.out.println(fileStore.getMount());
            System.out.println(fileStore.getType());
            System.out.println(fileStore.getLabel());
            System.out.println("----------------------------------");
        }
        System.out.println("====================");
        //网络接口（IP，带宽输入/输出）
        //名称，接收的字节数，发送的字节数，ipv4地址，ipv6地址，接受包大小，发送包大小，显示名称，别名，操作状态，mac地址，mtu，速度
        List<NetworkIF> networkIFs = OshiUtil.getNetworkIFs();
        for (NetworkIF networkIF : networkIFs) {
            System.out.println(networkIF.getName());

            System.out.println(networkIF.getBytesRecv());
            System.out.println(networkIF.getBytesSent());

            System.out.println( Arrays.toString(networkIF.getIPv4addr()) );
            System.out.println( Arrays.toString(networkIF.getIPv6addr()) );

            System.out.println(networkIF.getPacketsRecv());
            System.out.println(networkIF.getPacketsSent());

            System.out.println(networkIF.getDisplayName());
            System.out.println(networkIF.getIfAlias());
            System.out.println(networkIF.getIfOperStatus());

            System.out.println(networkIF.getMacaddr());

            System.out.println(networkIF.getMTU());

            System.out.println(networkIF.getSpeed());
            System.out.println("-----------------------------------");
        }
        System.out.println("====================");
        //电池状态（电量百分比，剩余时间，电量使用情况统计信息）
        List<PowerSource> powerSources = OshiUtil.getHardware().getPowerSources();
        //名称，安培，制造商，容量单位，当前容量，循环次数，初始容量，设备名字，制造日期，电池当前最大容量，电池使用率，电量剩余百分比，当前温度，电压
        for (PowerSource powerSource : powerSources) {

            System.out.println(powerSource.getName());
            System.out.println(powerSource.getAmperage());
            System.out.println(powerSource.getManufacturer());
            System.out.println(powerSource.getCapacityUnits());
            System.out.println(powerSource.getCurrentCapacity());
            System.out.println(powerSource.getCycleCount());
            System.out.println(powerSource.getDesignCapacity());
            System.out.println(powerSource.getDeviceName());
            System.out.println(powerSource.getManufactureDate());
            System.out.println(powerSource.getMaxCapacity());
            System.out.println(powerSource.getPowerUsageRate());
            System.out.println(powerSource.getRemainingCapacityPercent());
//            System.out.println(powerSource.getTemperature());
            System.out.println(powerSource.getVoltage());
        }
        //传感器（温度，风扇速度，电压）
        Sensors sensors = OshiUtil.getSensors();
        //处理器温度
        System.out.println(sensors.getCpuTemperature());
        //处理器电压
        System.out.println(sensors.getCpuVoltage());
        //风扇转速
        System.out.println(Arrays.toString(sensors.getFanSpeeds()));
//        System.out.println(cpuInfo.getCpuModel());
        SystemInfo si = new SystemInfo();
        System.out.println(si.getHardware().getSensors().getCpuTemperature());
    }
}