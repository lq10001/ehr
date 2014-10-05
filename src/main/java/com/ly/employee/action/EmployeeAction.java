package com.ly.employee.action;

import com.ly.base.service.*;
import com.ly.comm.Dwz;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import com.ly.employee.service.DepartmentService;
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

import com.ly.employee.vo.Employee;
import com.ly.employee.service.EmployeeService;


@IocBean
@At("/employee")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class EmployeeAction {

	private static final Log log = Logs.getLog(EmployeeAction.class);
	
	@Inject
	private EmployeeService employeeService;

    @Inject
    private BloodService bloodService;

    @Inject
    private EducationService educationService;

    @Inject
    private EntrystateService entrystateService;

    @Inject
    private GenderService genderService;

    @Inject
    private JobService jobService;

    @Inject
    private LevelService levelService;

    @Inject
    private MaritalService maritalService;


    @Inject
    private ProcreateService procreateService;

    @Inject
    private ProductlineService productlineService;

    @Inject
    private ProductteamService productteamService;

    @Inject
    private WorktypeService worktypeService;

    @Inject
    private PartyService partyService;

    @Inject
    private NativetypeService nativetypeService;

    @Inject
    private IctypeService ictypeService;

    @Inject
    private DepartmentService departmentService;



    @At("/")
    @Ok("beetl:/WEB-INF/employee/employee_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Employee employee,
                      HttpServletRequest request){
        Cnd c = new ParseObj(employee).getCnd();
        List<Employee> list_m = employeeService.query(c, p);
        p.setRecordCount(employeeService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("employee", employee);
    }

    @At
    @Ok("beetl:/WEB-INF/employee/employee.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("employee", null);
        }else{
            request.setAttribute("employee", employeeService.fetch(id));
        }

        request.setAttribute("list_blood", bloodService.query(null,null));
        request.setAttribute("list_education", educationService.query(null,null));
        request.setAttribute("list_entrystate", entrystateService.query(null, null));
        request.setAttribute("list_gender", genderService.query(null,null));
        request.setAttribute("list_job", jobService.query(null,null));
        request.setAttribute("list_level", levelService.query(null,null));
        request.setAttribute("list_marital", maritalService.query(null,null));
        request.setAttribute("list_procreate", procreateService.query(null,null));
        request.setAttribute("list_productline", productlineService.query(null,null));
        request.setAttribute("list_productteam", productteamService.query(null,null));
        request.setAttribute("list_worktype", worktypeService.query(null,null));

        request.setAttribute("list_party", partyService.query(null,null));
        request.setAttribute("list_nativetype", nativetypeService.query(null,null));
        request.setAttribute("list_ictype", ictypeService.query(null,null));
        request.setAttribute("list_department", departmentService.query(null,null));


    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Employee employee){
        Object rtnObject;
        if (employee.getId() == null || employee.getId() == 0) {
            rtnObject = employeeService.dao().insert(employee);
        }else{
            rtnObject = employeeService.dao().updateIgnoreNull(employee);
        }




        return Dwz.rtnMap((rtnObject == null) ? false : true, "employee", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  employeeService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "employee", "");
    }

}
