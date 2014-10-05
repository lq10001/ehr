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
    public void index(@Param("..")Page p,
                      @Param("..")Party party,
                      HttpServletRequest request){
        Cnd c = new ParseObj(party).getCnd();
        List<Party> list_m = partyService.query(c, p);
        p.setRecordCount(partyService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("party", party);
    }

    @At
    @Ok("beetl:/WEB-INF/base/party.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("party", null);
        }else{
            request.setAttribute("party", partyService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Party party){
        Object rtnObject;
        if (party.getId() == null || party.getId() == 0) {
            rtnObject = partyService.dao().insert(party);
        }else{
            rtnObject = partyService.dao().updateIgnoreNull(party);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "party", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  partyService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "party", "");
    }

}
