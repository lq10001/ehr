package com.ly.base.action;

import com.alibaba.fastjson.JSON;
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
import java.util.LinkedHashMap;
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
    public void index(){
    }

    @At
    @Ok("json")
    public Map jobList(HttpServletRequest request,
                         @Param("..")Page p,
                         @Param("..")Job job){
        List<Job> list_obj = jobService.query(null, p);

        Map map = new LinkedHashMap();
        map.put("total",jobService.count());
        map.put("data",list_obj);
        return map;
    }

    @At
    @Ok("json")
    public void save(@Param("data") String data ){
        Object rtnObject;
        System.out.println(data);
        String s1 = data.replace("_state","webstate");
        List<Job> jobList = JSON.parseArray(s1, Job.class);

        for(Job job : jobList)
        {
            if (job.getWebstate().equals("added")){
                job.setId(null);
                jobService.dao().insert(job);
            }else if(job.getWebstate().equals("modified")) {
                jobService.dao().updateIgnoreNull(job);
            }else{
                jobService.delete(job.getId());
            }
        }
    }

}
