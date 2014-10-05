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

import com.ly.base.vo.Gender;
import com.ly.base.service.GenderService;


@IocBean
@At("/gender")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class GenderAction {

	private static final Log log = Logs.getLog(GenderAction.class);
	
	@Inject
	private GenderService genderService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/gender_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map genderList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Gender gender){
        List<Gender> list_obj = genderService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",genderService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Gender> genderList = JSON.parseArray(s1, Gender.class);

        for(Gender gender : genderList)
        {
            if (gender.getWebstate().equals("added")){
                gender.setId(null);
                genderService.dao().insert(gender);
            }else if(gender.getWebstate().equals("modified")) {
                genderService.dao().updateIgnoreNull(gender);
            }else{
                genderService.delete(gender.getId());
            }
        }
    }

}
