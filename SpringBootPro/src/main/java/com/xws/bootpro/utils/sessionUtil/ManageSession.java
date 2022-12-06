package com.xws.bootpro.utils.sessionUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 *@Author：lm
 *@Date：2022/11/25 21:59
 *@Description 全局session变量
*/

@Repository
@Getter
@Setter
@ToString
public class ManageSession {

    Map<String, HttpSession> manageSession=new HashMap<>();

}