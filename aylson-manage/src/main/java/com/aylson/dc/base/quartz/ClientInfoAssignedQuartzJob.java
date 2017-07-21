package com.aylson.dc.base.quartz;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.utils.SpringBeanManager;
import com.aylson.dc.mem.service.ProjectClientService;
import com.aylson.utils.DateUtil;

/**
 * 业主资料分配定时任务实现
 * @author wwx
 *
 */
@Service
public class ClientInfoAssignedQuartzJob extends QuartzJobBean{

	@Autowired
	private ProjectClientService projectClientService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		if (projectClientService == null) {
			projectClientService = SpringBeanManager.getBean(ProjectClientService.class);
		}
		System.out.println("时间"+DateUtil.format(new Date())+":定时器开始................");
		Result result = this.projectClientService.distribuClientInfoJob();
		System.out.println(result.getMessage());
		System.out.println("时间"+DateUtil.format(new Date())+":定时器结束................");
	}

}
