package com.xws.bootpro.ScheduledTasks;
 
/**
 *@Author：lm
 *@Date：2022/11/29 17:08
 *@Description 定时任务状态枚举类
*/
public enum ScheduledStatus {
 
    ENABLE(0, "禁用"),
    DISABLE(1, "启用");
 
    private int code;
    private String name;
 
    ScheduledStatus(int code, String name){
        this.code = code;
        this.name = name;
    }
 
    public static ScheduledStatus getByCode(int code){
        for (ScheduledStatus st : values()) {
            if(code == st.getCode())
                return st;
        }
        return null;
    }
 
    public int getCode() {
        return code;
    }
 
    public String getName() {
        return name;
    }
 
}