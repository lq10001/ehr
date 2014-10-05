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

import com.ly.base.vo.Productline;
import com.ly.base.service.ProductlineService;


@IocBean
@At("/productline")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ProductlineAction {

	private static final Log log = Logs.getLog(ProductlineAction.class);
	
	@Inject
	private ProductlineService productlineService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/productline_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map productlineList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Productline productline){
        List<Productline> list_obj = productlineService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",productlineService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Productline> productlineList = JSON.parseArray(s1, Productline.class);

        for(Productline productline : productlineList)
        {
            if (productline.getWebstate().equals("added")){
                productline.setId(null);
                productlineService.dao().insert(productline);
            }else if(productline.getWebstate().equals("modified")) {
                productlineService.dao().updateIgnoreNull(productline);
            }else{
                productlineService.delete(productline.getId());
            }
        }
    }

}
