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
    public void index(@Param("..")Page p,
                      @Param("..")Nativetype nativetype,
                      HttpServletRequest request){
        Cnd c = new ParseObj(nativetype).getCnd();
        List<Nativetype> list_m = nativetypeService.query(c, p);
        p.setRecordCount(nativetypeService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("nativetype", nativetype);
    }

    @At
    @Ok("beetl:/WEB-INF/base/nativetype.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("nativetype", null);
        }else{
            request.setAttribute("nativetype", nativetypeService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Nativetype nativetype){
        Object rtnObject;
        if (nativetype.getId() == null || nativetype.getId() == 0) {
            rtnObject = nativetypeService.dao().insert(nativetype);
        }else{
            rtnObject = nativetypeService.dao().updateIgnoreNull(nativetype);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "nativetype", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  nativetypeService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "nativetype", "");
    }

}
