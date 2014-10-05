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

import com.ly.base.vo.Job;
import com.ly.base.service.JobService;


@IocBean
@At("/job")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class JobAction {

	private static final Log log = Logs.getLog(JobAction.class);
	
	@Inject
	private JobService jobService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/job_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Job job,
                      HttpServletRequest request){
        Cnd c = new ParseObj(job).getCnd();
        List<Job> list_m = jobService.query(c, p);
        p.setRecordCount(jobService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("job", job);
    }

    @At
    @Ok("beetl:/WEB-INF/base/job.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("job", null);
        }else{
            request.setAttribute("job", jobService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Job job){
        Object rtnObject;
        if (job.getId() == null || job.getId() == 0) {
            rtnObject = jobService.dao().insert(job);
        }else{
            rtnObject = jobService.dao().updateIgnoreNull(job);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "job", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  jobService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "job", "");
    }

}
