package  com.ly.base.service;

import com.ly.base.vo.Productteam;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import java.util.List;


@IocBean(fields = { "dao" })
public class ProductteamService extends IdEntityService<Productteam> {
}


