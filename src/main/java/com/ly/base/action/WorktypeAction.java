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

import com.ly.base.vo.Worktype;
import com.ly.base.service.WorktypeService;


@IocBean
@At("/worktype")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class WorktypeAction {

	private static final Log log = Logs.getLog(WorktypeAction.class);
	
	@Inject
	private WorktypeService worktypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/worktype_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Worktype worktype,
                      HttpServletRequest request){
        Cnd c = new ParseObj(worktype).getCnd();
        List<Worktype> list_m = worktypeService.query(c, p);
        p.setRecordCount(worktypeService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("worktype", worktype);
    }

    @At
    @Ok("beetl:/WEB-INF/base/worktype.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("worktype", null);
        }else{
            request.setAttribute("worktype", worktypeService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Worktype worktype){
        Object rtnObject;
        if (worktype.getId() == null || worktype.getId() == 0) {
            rtnObject = worktypeService.dao().insert(worktype);
        }else{
            rtnObject = worktypeService.dao().updateIgnoreNull(worktype);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "worktype", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  worktypeService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "worktype", "");
    }

}
