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

import com.ly.base.vo.Entrystate;
import com.ly.base.service.EntrystateService;


@IocBean
@At("/entrystate")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class EntrystateAction {

	private static final Log log = Logs.getLog(EntrystateAction.class);
	
	@Inject
	private EntrystateService entrystateService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/entrystate_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Entrystate entrystate,
                      HttpServletRequest request){
        Cnd c = new ParseObj(entrystate).getCnd();
        List<Entrystate> list_m = entrystateService.query(c, p);
        p.setRecordCount(entrystateService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("entrystate", entrystate);
    }

    @At
    @Ok("beetl:/WEB-INF/base/entrystate.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("entrystate", null);
        }else{
            request.setAttribute("entrystate", entrystateService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Entrystate entrystate){
        Object rtnObject;
        if (entrystate.getId() == null || entrystate.getId() == 0) {
            rtnObject = entrystateService.dao().insert(entrystate);
        }else{
            rtnObject = entrystateService.dao().updateIgnoreNull(entrystate);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "entrystate", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  entrystateService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "entrystate", "");
    }

}
