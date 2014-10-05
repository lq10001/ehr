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

import com.ly.employee.vo.Department;
import com.ly.employee.service.DepartmentService;


@IocBean
@At("/department")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class DepartmentAction {

	private static final Log log = Logs.getLog(DepartmentAction.class);
	
	@Inject
	private DepartmentService departmentService;

    @At("/")
    @Ok("beetl:/WEB-INF/employee/department_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Department department,
                      HttpServletRequest request){
        Cnd c = new ParseObj(department).getCnd();
        List<Department> list_m = departmentService.query(c, p);
        p.setRecordCount(departmentService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("department", department);
    }

    @At
    @Ok("beetl:/WEB-INF/employee/department.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("department", null);
        }else{
            request.setAttribute("department", departmentService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Department department){
        Object rtnObject;
        if (department.getId() == null || department.getId() == 0) {
            rtnObject = departmentService.dao().insert(department);
        }else{
            rtnObject = departmentService.dao().updateIgnoreNull(department);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "department", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  departmentService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "department", "");
    }

}
