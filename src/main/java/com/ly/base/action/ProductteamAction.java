package com.ly.base.action;

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
    public void index(@Param("..")Page p,
                      @Param("..")Productteam productteam,
                      HttpServletRequest request){
        Cnd c = new ParseObj(productteam).getCnd();
        List<Productteam> list_m = productteamService.query(c, p);
        p.setRecordCount(productteamService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("productteam", productteam);
    }

    @At
    @Ok("beetl:/WEB-INF/base/productteam.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("productteam", null);
        }else{
            request.setAttribute("productteam", productteamService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Productteam productteam){
        Object rtnObject;
        if (productteam.getId() == null || productteam.getId() == 0) {
            rtnObject = productteamService.dao().insert(productteam);
        }else{
            rtnObject = productteamService.dao().updateIgnoreNull(productteam);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "productteam", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  productteamService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "productteam", "");
    }

}
