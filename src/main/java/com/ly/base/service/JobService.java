package  com.ly.base.service;

import com.ly.base.vo.Job;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import java.util.List;


@IocBean(fields = { "dao" })
public class JobService extends IdEntityService<Job> {
}


