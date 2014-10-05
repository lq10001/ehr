package com.ly.sys.action;

import com.alibaba.fastjson.JSON;
import com.ly.comm.Dwz;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import com.ly.sys.service.MenuService;
import com.ly.sys.vo.Menu;
import com.ly.webvo.Tree;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.PairAdaptor;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@IocBean
@At("/menu")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class MenuAction{

	private static final Log log = Logs.getLog(MenuAction.class);

	@Inject
	private Dao dao;
	
	@Inject
	private MenuService menuService;

    @At("/")
    @Ok("beetl:/WEB-INF/sys/menu_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Menu menu,
                      HttpServletRequest request){

        Cnd c = new ParseObj(menu).getCnd();
        List<Menu> list_m = menuService.query(c.asc("ordernum"), p);
        p.setRecordCount(menuService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("menu", menu);
    }

    @At
    @Ok("json")
    public Map menuList2(HttpServletRequest request,
                                @Param("..")Page p,
                                @Param("..")Menu menu){
        Cnd c = new ParseObj(menu).getCnd();
        List<Menu> list_m = menuService.query(c.asc("ordernum"), p);

        Map map = new LinkedHashMap();
        map.put("total",menuService.count(c));
        map.put("data",list_m);
        return map;
    }

    @At
    @Ok("json")
    public List<Tree> menuList(HttpServletRequest request){
        Cnd c = Cnd.where("state","=",1);
        List<Menu> list_m = menuService.query(c.asc("pname"), null);

        List<Tree> list_tree = new LinkedList<Tree>();
        for (Menu menu : list_m)
        {
            Tree tree = new Tree();
            tree.setId(menu.getName());
            tree.setText(menu.getCnname());
            if (!menu.getPname().equals("0"))
            {
                tree.setPid(menu.getPname());
            }
            list_tree.add(tree);

        }
        System.out.println(JSON.toJSONString(list_m));
        return list_tree;
    }

    @At
    @Ok("beetl:/WEB-INF/sys/menu.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("menu", null);
        }else{
            request.setAttribute("menu", menuService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Menu menu){
        Object rtnObject;
        if (menu.getId() == null || menu.getId() == 0) {
            rtnObject = menuService.dao().insert(menu);
        }else{
            rtnObject = menuService.dao().updateIgnoreNull(menu);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "menu", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  menuService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "menu", "");
    }

}
