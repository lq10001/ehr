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

import com.ly.base.vo.Procreate;
import com.ly.base.service.ProcreateService;


@IocBean
@At("/procreate")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ProcreateAction {

	private static final Log log = Logs.getLog(ProcreateAction.class);
	
	@Inject
	private ProcreateService procreateService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/procreate_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Procreate procreate,
                      HttpServletRequest request){
        Cnd c = new ParseObj(procreate).getCnd();
        List<Procreate> list_m = procreateService.query(c, p);
        p.setRecordCount(procreateService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("procreate", procreate);
    }

    @At
    @Ok("beetl:/WEB-INF/base/procreate.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("procreate", null);
        }else{
            request.setAttribute("procreate", procreateService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Procreate procreate){
        Object rtnObject;
        if (procreate.getId() == null || procreate.getId() == 0) {
            rtnObject = procreateService.dao().insert(procreate);
        }else{
            rtnObject = procreateService.dao().updateIgnoreNull(procreate);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "procreate", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  procreateService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "procreate", "");
    }

}
