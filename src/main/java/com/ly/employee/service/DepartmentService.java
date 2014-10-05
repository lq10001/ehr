package  com.ly.employee.service;

import com.ly.employee.vo.Department;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import java.util.List;


@IocBean(fields = { "dao" })
public class DepartmentService extends IdEntityService<Department> {
}


