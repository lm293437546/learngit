package com.xws.bootpro.ScheduledTasks;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
 
/**
 *@Author：lm
 *@Date：2022/11/29 17:38
 *@Description 测试类01
*/
@Slf4j
@Service
public class ScheduledTaskJob01 implements ScheduledTaskJob {
 
    @Override
    public void run() {
        // TODO 要处理的业务逻辑
        log.info("ScheduledTask => 01  run  当前线程名称 {} ", Thread.currentThread().getName());
    }
}