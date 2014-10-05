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
    public void index(@Param("..")Page p,
                      @Param("..")Ictype ictype,
                      HttpServletRequest request){
        Cnd c = new ParseObj(ictype).getCnd();
        List<Ictype> list_m = ictypeService.query(c, p);
        p.setRecordCount(ictypeService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("ictype", ictype);
    }

    @At
    @Ok("beetl:/WEB-INF/base/ictype.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("ictype", null);
        }else{
            request.setAttribute("ictype", ictypeService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Ictype ictype){
        Object rtnObject;
        if (ictype.getId() == null || ictype.getId() == 0) {
            rtnObject = ictypeService.dao().insert(ictype);
        }else{
            rtnObject = ictypeService.dao().updateIgnoreNull(ictype);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "ictype", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  ictypeService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "ictype", "");
    }

}
