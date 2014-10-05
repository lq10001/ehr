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

import com.ly.base.vo.Marital;
import com.ly.base.service.MaritalService;


@IocBean
@At("/marital")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class MaritalAction {

	private static final Log log = Logs.getLog(MaritalAction.class);
	
	@Inject
	private MaritalService maritalService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/marital_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map maritalList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Marital marital){
        List<Marital> list_obj = maritalService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",maritalService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Marital> maritalList = JSON.parseArray(s1, Marital.class);

        for(Marital marital : maritalList)
        {
            if (marital.getWebstate().equals("added")){
                marital.setId(null);
                maritalService.dao().insert(marital);
            }else if(marital.getWebstate().equals("modified")) {
                maritalService.dao().updateIgnoreNull(marital);
            }else{
                maritalService.delete(marital.getId());
            }
        }
    }

}
