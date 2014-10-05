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

import com.ly.base.vo.Productteam;
import com.ly.base.service.ProductteamService;


@IocBean
@At("/productteam")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ProductteamAction {

	private static final Log log = Logs.getLog(ProductteamAction.class);
	
	@Inject
	private ProductteamService productteamService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/productteam_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map productteamList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Productteam productteam){
        List<Productteam> list_obj = productteamService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",productteamService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Productteam> productteamList = JSON.parseArray(s1, Productteam.class);

        for(Productteam productteam : productteamList)
        {
            if (productteam.getWebstate().equals("added")){
                productteam.setId(null);
                productteamService.dao().insert(productteam);
            }else if(productteam.getWebstate().equals("modified")) {
                productteamService.dao().updateIgnoreNull(productteam);
            }else{
                productteamService.delete(productteam.getId());
            }
        }
    }

}
