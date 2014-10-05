package com.ly.base.action;

import com.alibaba.fastjson.JSON;
import com.ly.comm.Dwz;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ly.base.vo.Level;
import com.ly.base.service.LevelService;


@IocBean
@At("/level")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class LevelAction {

	private static final Log log = Logs.getLog(LevelAction.class);
	
	@Inject
	private LevelService levelService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/level_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map levelList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Level level){
        List<Level> list_obj = levelService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",levelService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Level> levelList = JSON.parseArray(s1, Level.class);

        for(Level level : levelList)
        {
            if (level.getWebstate().equals("added")){
                level.setId(null);
                levelService.dao().insert(level);
            }else if(level.getWebstate().equals("modified")) {
                levelService.dao().updateIgnoreNull(level);
            }else{
                levelService.delete(level.getId());
            }
        }
    }

}
