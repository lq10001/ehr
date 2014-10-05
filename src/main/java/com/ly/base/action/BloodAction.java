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

import com.ly.base.vo.Blood;
import com.ly.base.service.BloodService;


@IocBean
@At("/blood")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class BloodAction {

	private static final Log log = Logs.getLog(BloodAction.class);
	
	@Inject
	private BloodService bloodService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/blood_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Blood blood,
                      HttpServletRequest request){
        Cnd c = new ParseObj(blood).getCnd();
        List<Blood> list_m = bloodService.query(c, p);
        p.setRecordCount(bloodService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("blood", blood);
    }

    @At
    @Ok("beetl:/WEB-INF/base/blood.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("blood", null);
        }else{
            request.setAttribute("blood", bloodService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Blood blood){
        Object rtnObject;
        if (blood.getId() == null || blood.getId() == 0) {
            rtnObject = bloodService.dao().insert(blood);
        }else{
            rtnObject = bloodService.dao().updateIgnoreNull(blood);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "blood", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  bloodService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "blood", "");
    }

}
