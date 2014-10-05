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

import com.ly.base.vo.Nativetype;
import com.ly.base.service.NativetypeService;


@IocBean
@At("/nativetype")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class NativetypeAction {

	private static final Log log = Logs.getLog(NativetypeAction.class);
	
	@Inject
	private NativetypeService nativetypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/nativetype_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map nativetypeList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Nativetype nativetype){
        List<Nativetype> list_obj = nativetypeService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",nativetypeService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Nativetype> nativetypeList = JSON.parseArray(s1, Nativetype.class);

        for(Nativetype nativetype : nativetypeList)
        {
            if (nativetype.getWebstate().equals("added")){
                nativetype.setId(null);
                nativetypeService.dao().insert(nativetype);
            }else if(nativetype.getWebstate().equals("modified")) {
                nativetypeService.dao().updateIgnoreNull(nativetype);
            }else{
                nativetypeService.delete(nativetype.getId());
            }
        }
    }

}
