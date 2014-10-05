package com.ly.employee.action;

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

import com.ly.employee.vo.Exitinfo;
import com.ly.employee.service.ExitinfoService;


@IocBean
@At("/exitinfo")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ExitinfoAction {

	private static final Log log = Logs.getLog(ExitinfoAction.class);
	
	@Inject
	private ExitinfoService exitinfoService;

    @At("/")
    @Ok("beetl:/WEB-INF/employee/exitinfo_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Exitinfo exitinfo,
                      HttpServletRequest request){
        Cnd c = new ParseObj(exitinfo).getCnd();
        List<Exitinfo> list_m = exitinfoService.query(c, p);
        p.setRecordCount(exitinfoService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("exitinfo", exitinfo);
    }

    @At
    @Ok("beetl:/WEB-INF/employee/exitinfo.html")
    public void add(@Param("employeeid")Long employeeid,
                      HttpServletRequest request){
        request.setAttribute("employeeid", employeeid);
        request.setAttribute("exitinfo", null);
    }


    @At
    @Ok("beetl:/WEB-INF/employee/exitinfo.html")
    public void edit(@Param("id")Long id,
                     HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("exitinfo", null);
        }else{
            request.setAttribute("exitinfo", exitinfoService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Exitinfo exitinfo){
        Object rtnObject;
        if (exitinfo.getId() == null || exitinfo.getId() == 0) {
            rtnObject = exitinfoService.dao().insert(exitinfo);
        }else{
            rtnObject = exitinfoService.dao().updateIgnoreNull(exitinfo);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "exitinfo", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  exitinfoService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "exitinfo", "");
    }

}
