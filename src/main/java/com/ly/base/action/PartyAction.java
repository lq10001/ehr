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

import com.ly.base.vo.Party;
import com.ly.base.service.PartyService;


@IocBean
@At("/party")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class PartyAction {

	private static final Log log = Logs.getLog(PartyAction.class);
	
	@Inject
	private PartyService partyService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/party_list.html")
    public void index(){
    }

    @At
    @Ok("json")
    public Map partyList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Party party){
        List<Party> list_obj = partyService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",partyService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Party> partyList = JSON.parseArray(s1, Party.class);

        for(Party party : partyList)
        {
            if (party.getWebstate().equals("added")){
                party.setId(null);
                partyService.dao().insert(party);
            }else if(party.getWebstate().equals("modified")) {
                partyService.dao().updateIgnoreNull(party);
            }else{
                partyService.delete(party.getId());
            }
        }
    }

}
