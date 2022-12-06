package com.xws.bootpro.ScheduledTasks;

import com.xws.bootpro.dataobj.PaScheduledDo;

/**
 *@Author：lm
 *@Date：2022/11/29 17:09
 *@Description 定时任务接口
*/
public interface ScheduledTaskService {
 
    /**
     * 根据任务key 启动任务
     */
    Boolean start(String taskKey, PaScheduledDo scheduled);
 
    /**
     * 根据任务key 停止任务
     */
    Boolean stop(String taskKey);
 
    /**
     * 根据任务key 重启任务
     */
    Boolean restart(String taskKey, PaScheduledDo scheduled);
 
    /**
     * 初始化  ==> 启动所有正常状态的任务
     */
    void initAllTask();
}