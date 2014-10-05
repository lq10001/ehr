package  com.ly.base.service;

import com.ly.base.vo.Worktype;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import java.util.List;


@IocBean(fields = { "dao" })
public class WorktypeService extends IdEntityService<Worktype> {
}


