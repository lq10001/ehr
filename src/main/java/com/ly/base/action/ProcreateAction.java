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

import com.ly.base.vo.Procreate;
import com.ly.base.service.ProcreateService;


@IocBean
@At("/procreate")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ProcreateAction {

	private static final Log log = Logs.getLog(ProcreateAction.class);
	
	@Inject
	private ProcreateService procreateService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/procreate_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map procreateList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Procreate procreate){
        List<Procreate> list_obj = procreateService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",procreateService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Procreate> procreateList = JSON.parseArray(s1, Procreate.class);

        for(Procreate procreate : procreateList)
        {
            if (procreate.getWebstate().equals("added")){
                procreate.setId(null);
                procreateService.dao().insert(procreate);
            }else if(procreate.getWebstate().equals("modified")) {
                procreateService.dao().updateIgnoreNull(procreate);
            }else{
                procreateService.delete(procreate.getId());
            }
        }
    }

}
