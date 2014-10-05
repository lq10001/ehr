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

import com.ly.base.vo.Ictype;
import com.ly.base.service.IctypeService;


@IocBean
@At("/ictype")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class IctypeAction {

	private static final Log log = Logs.getLog(IctypeAction.class);
	
	@Inject
	private IctypeService ictypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/ictype_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map ictypeList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Ictype ictype){
        List<Ictype> list_obj = ictypeService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",ictypeService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Ictype> ictypeList = JSON.parseArray(s1, Ictype.class);

        for(Ictype ictype : ictypeList)
        {
            if (ictype.getWebstate().equals("added")){
                ictype.setId(null);
                ictypeService.dao().insert(ictype);
            }else if(ictype.getWebstate().equals("modified")) {
                ictypeService.dao().updateIgnoreNull(ictype);
            }else{
                ictypeService.delete(ictype.getId());
            }
        }
    }

}
