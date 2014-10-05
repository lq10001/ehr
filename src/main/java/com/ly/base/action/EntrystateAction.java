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

import com.ly.base.vo.Entrystate;
import com.ly.base.service.EntrystateService;


@IocBean
@At("/entrystate")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class EntrystateAction {

	private static final Log log = Logs.getLog(EntrystateAction.class);
	
	@Inject
	private EntrystateService entrystateService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/entrystate_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map entrystateList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Entrystate entrystate){
        List<Entrystate> list_obj = entrystateService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",entrystateService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Entrystate> entrystateList = JSON.parseArray(s1, Entrystate.class);

        for(Entrystate entrystate : entrystateList)
        {
            if (entrystate.getWebstate().equals("added")){
                entrystate.setId(null);
                entrystateService.dao().insert(entrystate);
            }else if(entrystate.getWebstate().equals("modified")) {
                entrystateService.dao().updateIgnoreNull(entrystate);
            }else{
                entrystateService.delete(entrystate.getId());
            }
        }
    }

}
