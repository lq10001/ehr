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

import com.ly.base.vo.Level;
import com.ly.base.service.LevelService;


@IocBean
@At("/level")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class LevelAction {

	private static final Log log = Logs.getLog(LevelAction.class);
	
	@Inject
	private LevelService levelService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/level_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Level level,
                      HttpServletRequest request){
        Cnd c = new ParseObj(level).getCnd();
        List<Level> list_m = levelService.query(c, p);
        p.setRecordCount(levelService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("level", level);
    }

    @At
    @Ok("beetl:/WEB-INF/base/level.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("level", null);
        }else{
            request.setAttribute("level", levelService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Level level){
        Object rtnObject;
        if (level.getId() == null || level.getId() == 0) {
            rtnObject = levelService.dao().insert(level);
        }else{
            rtnObject = levelService.dao().updateIgnoreNull(level);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "level", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  levelService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "level", "");
    }

}
