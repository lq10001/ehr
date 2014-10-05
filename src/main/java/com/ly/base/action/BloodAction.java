package com.ly.base.action;

import com.alibaba.fastjson.JSON;
import com.ly.comm.Dwz;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import com.ly.sys.vo.Menu;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ly.base.vo.Blood;
import com.ly.base.service.BloodService;


@IocBean
@At("/blood")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class BloodAction {

	private static final Log log = Logs.getLog(BloodAction.class);
	
	@Inject
	private BloodService bloodService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/blood_list.html")
    public void index(HttpServletRequest request){
    }

    @At
    @Ok("json")
    public Map bloodList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Blood blood){
        List<Blood> list_obj = bloodService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",bloodService.count());
        map.put("data",list_obj);
        return map;
    }


    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Blood> bloodList = JSON.parseArray(s1, Blood.class);

        for(Blood blood : bloodList)
        {
            if (blood.getWebstate().equals("added")){
                blood.setId(null);
                bloodService.dao().insert(blood);
            }else if(blood.getWebstate().equals("modified")) {
                bloodService.dao().updateIgnoreNull(blood);
            }else{
                bloodService.delete(blood.getId());
            }
        }
    }

}
