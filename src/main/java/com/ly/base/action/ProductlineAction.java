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
    public void index(@Param("..")Page p,
                      @Param("..")Productline productline,
                      HttpServletRequest request){
        Cnd c = new ParseObj(productline).getCnd();
        List<Productline> list_m = productlineService.query(c, p);
        p.setRecordCount(productlineService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("productline", productline);
    }

    @At
    @Ok("beetl:/WEB-INF/base/productline.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("productline", null);
        }else{
            request.setAttribute("productline", productlineService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Productline productline){
        Object rtnObject;
        if (productline.getId() == null || productline.getId() == 0) {
            rtnObject = productlineService.dao().insert(productline);
        }else{
            rtnObject = productlineService.dao().updateIgnoreNull(productline);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "productline", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  productlineService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "productline", "");
    }

}
