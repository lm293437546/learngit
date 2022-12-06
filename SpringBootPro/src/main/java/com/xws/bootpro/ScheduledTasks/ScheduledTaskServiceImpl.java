package com.xws.bootpro.ScheduledTasks;
 
import com.xws.bootpro.dataobj.PaScheduledDo;
import com.xws.bootpro.mapper.PaScheduledMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;
 
/**
 *@Author：lm
 *@Date：2022/11/29 17:10
 *@Description 定时任务实现
*/
@Slf4j
@Service
public class ScheduledTaskServiceImpl implements ScheduledTaskService {
 
    @Value("${task.enabled}")
    private Boolean taskEnable;
 
    /**
     * 可重入锁
     */
    private ReentrantLock lock = new ReentrantLock();
    /**
     * 定时任务线程池
     */
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
 
    /**
     * 存放已经启动的任务map
     */
    private Map<String, ScheduledFuture> scheduledFutureMap = new ConcurrentHashMap<>();

    @Resource
    PaScheduledMapper paScheduledMapper;
 
    /**
     * 描述: 根据任务key 启动任务
     *
     * @param taskKey
     * @param scheduled
     * @return java.lang.Boolean
     */
    @Override
    public Boolean start(String taskKey, PaScheduledDo scheduled) {
        log.info(">>>>>> 启动任务 {} 开始 >>>>>>", taskKey);
        //添加锁放一个线程启动，防止多人启动多次
        lock.lock();
        log.info(">>>>>> 添加任务启动锁完毕");
        try {
            //校验是否已经启动
            if (this.isStart(taskKey)) {
                log.info(">>>>>> 当前任务已经启动，无需重复启动！");
                return false;
            }
            //查询配置
            if(scheduled == null)
                scheduled = this.getByTaskKey(taskKey);
            if(scheduled == null)
                return false;
            //启动任务
            this.doStartTask(scheduled);
        } finally {
            // 释放锁
            lock.unlock();
            log.info(">>>>>> 释放任务启动锁完毕");
        }
        log.info(">>>>>> 启动任务 {} 结束 >>>>>>", taskKey);
        return true;
    }
 
    /**
     * 描述: 查询定时任务配置参数
     *
     * @param taskKey
     * @return com.yihaocard.main.module.scheduled.model.Scheduled
     */
    private PaScheduledDo getByTaskKey(String taskKey) {
        ScheduledExample scheduledExample = new ScheduledExample();
        scheduledExample.createCriteria()
                .andStatusEqualTo(ScheduledStatus.DISABLE.getCode())
                .andTaskKeyEqualTo(taskKey);
        List<PaScheduledDo> scheduleds = paScheduledMapper.selectByExample(scheduledExample);
        if(scheduleds == null || scheduleds.size() < 1)
            return null;
        return scheduleds.get(0);
    }
 
    /**
     * 描述: 根据 key 停止任务
     *
     * @param taskKey
     * @return java.lang.Boolean
     */
    @Override
    public Boolean stop(String taskKey) {
        log.info(">>>>>> 进入停止任务 {}  >>>>>>", taskKey);
        //当前任务实例是否存在
        boolean taskStartFlag = scheduledFutureMap.containsKey(taskKey);
        log.info(">>>>>> 当前任务实例是否存在 {}", taskStartFlag);
        if (taskStartFlag) {
            //获取任务实例
            ScheduledFuture scheduledFuture = scheduledFutureMap.get(taskKey);
            //关闭实例
            boolean cancel = scheduledFuture.cancel(true);
            log.info("cancel:{}", cancel);
            //删除关闭的任务实例
            scheduledFutureMap.remove(taskKey);
        }
        log.info(">>>>>> 结束停止任务 {}  >>>>>>", taskKey);
        return taskStartFlag;
    }
 
    /**
     * 描述: 根据任务key 重启任务
     *
     * @param taskKey
     * @param scheduled
     * @return java.lang.Boolean
     */
    @Override
    public Boolean restart(String taskKey, PaScheduledDo scheduled) {
        log.info(">>>>>> 进入重启任务 {}  >>>>>>", taskKey);
        //先停止
        this.stop(taskKey);
        //查询配置
        if(scheduled == null)
            scheduled = this.getByTaskKey(taskKey);
        if(scheduled == null)
            return false;
        //再启动
        return this.start(taskKey,scheduled);
    }
 
    /**
     * 初始化  ==> 启动所有正常状态的任务
     */
    @Override
    public void initAllTask() {
        if(!taskEnable){
            log.info("配置文件禁用了定时任务----");
            return;
        }
        ScheduledExample scheduledExample = new ScheduledExample();
        scheduledExample.createCriteria()
                .andStatusEqualTo(ScheduledStatus.DISABLE.getCode());
        List<PaScheduledDo> scheduleds = paScheduledMapper.selectByExample(scheduledExample);
        log.info("初始化  ==> 启动所有正常状态的任务开始 ！size={}", scheduleds == null ? 0 : scheduleds.size());
        if (scheduleds == null || scheduleds.size() < 1) {
            return;
        }
        for (PaScheduledDo scheduled : scheduleds) {
            //任务 key
            String taskKey = scheduled.getTaskKey();
            //校验是否已经启动
            if (this.isStart(taskKey)) {
                // 重启任务
                this.restart(taskKey,scheduled);
            } else {
                // 启动任务
                this.doStartTask(scheduled);
            }
        }
        log.info("初始化  ==> 启动所有正常状态的任务结束 ！");
    }
 
    /**
     * 执行启动任务
     */
    private void doStartTask(PaScheduledDo scheduled) {
        if (scheduled == null)
            return;
        //任务key
        String taskKey = scheduled.getTaskKey();
        //定时表达式
        String taskCron = scheduled.getCron();
        //获取需要定时调度的接口
        ScheduledTaskJob scheduledTaskJob = (ScheduledTaskJob) SpringContext.getBean(taskKey);
        log.info(">>>>>> 任务 [ {} ] ,cron={}", scheduled.getName(), taskCron);
        ScheduledFuture scheduledFuture = threadPoolTaskScheduler.schedule(scheduledTaskJob, (TriggerContext triggerContext) -> new CronTrigger(taskCron).nextExecutionTime(triggerContext));
        //将启动的任务放入 map
        scheduledFutureMap.put(taskKey, scheduledFuture);
    }
 
    /**
     * 任务是否已经启动
     */
    private Boolean isStart(String taskKey) {
        //校验是否已经启动
        if (scheduledFutureMap.containsKey(taskKey)) {
            if (!scheduledFutureMap.get(taskKey).isCancelled()) {
                return true;
            }
        }
        return false;
    }
 
}