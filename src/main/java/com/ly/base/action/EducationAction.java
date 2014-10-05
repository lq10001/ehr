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

import com.ly.base.vo.Education;
import com.ly.base.service.EducationService;


@IocBean
@At("/education")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class EducationAction {

	private static final Log log = Logs.getLog(EducationAction.class);
	
	@Inject
	private EducationService educationService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/education_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map educationList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Education education){
        List<Education> list_obj = educationService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",educationService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Education> educationList = JSON.parseArray(s1, Education.class);

        for(Education education : educationList)
        {
            if (education.getWebstate().equals("added")){
                education.setId(null);
                educationService.dao().insert(education);
            }else if(education.getWebstate().equals("modified")) {
                educationService.dao().updateIgnoreNull(education);
            }else{
                educationService.delete(education.getId());
            }
        }
    }

}
