package  com.ly.base.service;

import com.ly.base.vo.Gender;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import java.util.List;


@IocBean(fields = { "dao" })
public class GenderService extends IdEntityService<Gender> {
}


