package com.ly.base.action;

import com.alibaba.fastjson.JSON;
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
import java.util.LinkedHashMap;
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
    public void index(){
    }

    @At
    @Ok("json")
    public Map worktypeList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Worktype worktype){
        List<Worktype> list_obj = worktypeService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",worktypeService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Worktype> worktypeList = JSON.parseArray(s1, Worktype.class);

        for(Worktype worktype : worktypeList)
        {
            if (worktype.getWebstate().equals("added")){
                worktype.setId(null);
                worktypeService.dao().insert(worktype);
            }else if(worktype.getWebstate().equals("modified")) {
                worktypeService.dao().updateIgnoreNull(worktype);
            }else{
                worktypeService.delete(worktype.getId());
            }
        }
    }

}
