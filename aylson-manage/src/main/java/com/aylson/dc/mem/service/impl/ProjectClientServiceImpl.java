package com.aylson.dc.mem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.MemClientStatus;
import com.aylson.dc.mem.dao.ProjectClientDao;
import com.aylson.dc.mem.po.ProjectClient;
import com.aylson.dc.mem.search.ProjectClientSearch;
import com.aylson.dc.mem.service.ProjectClientService;
import com.aylson.dc.mem.vo.ClientInfoVo;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.mem.vo.ProjectClientVo;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;

@Service
public class ProjectClientServiceImpl extends BaseServiceImpl<ProjectClient,ProjectClientSearch> implements ProjectClientService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectClientServiceImpl.class);
	
	@Autowired
	private ProjectClientDao projectClientDao;
	@Autowired
	private DictionaryService dictionaryService;
	
	@Override
	protected BaseDao<ProjectClient,ProjectClientSearch> getBaseDao() {
		return this.projectClientDao;
	}

	@Override
	public List<MemAccountVo> getDistributionMember() {
		return this.projectClientDao.selectDistributionMember();
	}

	@Override
	public List<ClientInfoVo> getDistributionClient() {
		return this.projectClientDao.selectDistributionClient();
	}

	@Override
	public Map<Integer, List<ClientInfoVo>> getDistributionList(
			List<MemAccountVo> memAccountVoList,
			List<ClientInfoVo> clientInfoVoList) {
		Map<Integer, List<ClientInfoVo>> result = new HashMap<Integer, List<ClientInfoVo>>();
		//如果有可以分配的会员和业主资料，才进行处理
		if(memAccountVoList != null && memAccountVoList.size() > 0 &&
				clientInfoVoList != null && clientInfoVoList.size() > 0){
			Map<String,Integer> clientCount = new HashMap<String, Integer>(); //业主资料分配计数器
			List<ProjectClientVo> existsPorjectClientVoList = this.getList(null);
			for(MemAccountVo curMem:memAccountVoList){//开始按会员等级分配业主资料
				System.out.println("===============当前会员memberId:"+curMem.getId()+"============");
				Integer memberCount = curMem.getStillCount();
				List<ClientInfoVo> memClientInfo = new ArrayList<ClientInfoVo>();//会员对应业主资料集合
				for(int i=0; i<clientInfoVoList.size(); i++){
					if(memClientInfo.size() < memberCount){
						ClientInfoVo clientInfoVo = clientInfoVoList.get(i);
						//如果不在同一区域，那么不分派这个业主的资料
						if(curMem.getProvinceId() == null || curMem.getCityId() == null || curMem.getAreaId() == null
								|| clientInfoVo.getProvinceId() == null || clientInfoVo.getCityId() == null || clientInfoVo.getAreaId() == null){continue;}
						if(curMem.getProvinceId().intValue() != clientInfoVo.getProvinceId().intValue() || 
								curMem.getCityId().intValue() != clientInfoVo.getCityId().intValue() ||
										curMem.getAreaId().intValue() != clientInfoVo.getAreaId().intValue()){continue;}
						//如果已经存在：派送过，忽略过、提交过，也不再派送
						if(this.isExists(existsPorjectClientVoList, curMem, clientInfoVo)){continue;}
						String clientId = clientInfoVo.getId()+"_"+clientInfoVo.getSourceType();
						if(clientCount.containsKey(clientId)){
							if(clientCount.get(clientId) == clientInfoVo.getStillCount()){continue;};
							clientCount.put(clientId, clientCount.get(clientId)+1);
						}else{
							clientCount.put(clientId, 1);
						}
						memClientInfo.add(clientInfoVo);
					}
				}
				result.put(curMem.getId(), memClientInfo);
			}
		}
		//打印输出，校验结果
		/*for(MemAccountVo temp1 :memAccountVoList){
			for (Map.Entry<Integer, List<ClientInfoVo>> entry : result.entrySet()) {
				if(temp1.getId() == entry.getKey()){
					List<ClientInfoVo> list = entry.getValue();
					System.out.print("会员ID为:" + entry.getKey() + "分配的业主资料为id+sourceType:");
					if(list != null && list.size() > 0){
						for(int i=0; i<list.size(); i++){
							ClientInfoVo temp = list.get(i);
							if(i == list.size()-1){
								System.out.print(temp.getId()+"_"+temp.getSourceType());
							}else{
								System.out.print(temp.getId()+"_"+temp.getSourceType()+",");
							}
						}
					}
					System.out.println(";");
					break;
				}
			}
		}*/
		return result;
	}
	
	/**
	 * 是否已经存在：派送过，提交过，忽略过
	 * @return true:存在，false:不存在
	 */
	private Boolean  isExists(List<ProjectClientVo> existsPorjectClientVoList, MemAccountVo curMem, ClientInfoVo clientInfoVo){
		Boolean  isExists =  false;
		if(existsPorjectClientVoList == null || existsPorjectClientVoList.size() < 0)return isExists;
		if(curMem == null || clientInfoVo == null || curMem.getId() == null)return isExists;
		for(ProjectClientVo projectClientVo : existsPorjectClientVoList){//循环判断，如果存在一个，返回true;
			if(projectClientVo.getMemberId() != null && curMem.getId() != null
					&& projectClientVo.getMemberId().intValue() != curMem.getId().intValue())continue;//如果会员id不一致，继续判断
			//如果会员id一样,只有会员id，业主资料id和来源类型一样才表示存在
			if(projectClientVo.getClientId() != null && clientInfoVo != null 
					&& projectClientVo.getSourceType() != null && clientInfoVo.getSourceType() != null 
					&& projectClientVo.getClientId().intValue() == clientInfoVo.getId().intValue() 
					&& projectClientVo.getSourceType().intValue() == clientInfoVo.getSourceType().intValue()){
				isExists = true;
			}
		}
		
		return isExists;
	}

	@Override
	public Result distribuClientInfo(Map<Integer, List<ClientInfoVo>> memClientInfo) {
		Result result = new Result();
		if(!memClientInfo.isEmpty()){//如果不为空
			List<ProjectClientVo> projectClientVoList = new ArrayList<ProjectClientVo>();
			Date curDate = new Date();
			for(Map.Entry<Integer, List<ClientInfoVo>> entry : memClientInfo.entrySet()) {
				List<ClientInfoVo> list = entry.getValue();
				if(list != null && list.size() > 0){
					for(int i=0; i<list.size(); i++){
						ClientInfoVo temp = list.get(i);
						ProjectClientVo projectClientVo = new ProjectClientVo();
						projectClientVo.setClientId(temp.getId());
						projectClientVo.setCreateTime(curDate);
						projectClientVo.setMemberId(entry.getKey());
						projectClientVo.setUpdateTime(curDate);
						projectClientVo.setSourceType(temp.getSourceType());
						projectClientVo.setStatus(MemClientStatus.ASSIGNED);//已分配
						projectClientVoList.add(projectClientVo);
					}
				}
			}
			//打印输出结果
			/*for(ProjectClientVo temp:projectClientVoList){
				System.out.println("会员ID" + temp.getMemberId() + ":" + temp.getClientId() + "_" + temp.getSourceType());
			}*/
			Boolean flag = true;
			if(projectClientVoList != null && projectClientVoList.size() > 0){
				flag = this.batchAdd(projectClientVoList);
			}
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}
		return result;
	}

	@Override
	public Result distribuClientInfoJob() {
		Map<Integer, List<ClientInfoVo>> distributionList = this.getDistributionList(this.getDistributionMember(), this.getDistributionClient());
		return this.distribuClientInfo(distributionList);
	}

	@Override
	public Result ignoreClientInfoJob() {
		Result result = new Result();
		Boolean flag = false;
		ProjectClientSearch projectClientSearch = new ProjectClientSearch();
		projectClientSearch.setStatus(MemClientStatus.ASSIGNED);
		String createTime = DateUtil.format(DateUtil.operDay(new Date(), -this.getIgnoreDays()), true);
		projectClientSearch.setCreateTime(createTime);
		List<ProjectClientVo> list = this.getList(projectClientSearch);
		if(list != null && list.size() > 0){
			for(ProjectClientVo temp : list){
				temp.setStatus(MemClientStatus.IGNORED);
			}
			flag =  this.batchUpdate(list);
		}else{
			flag = true;
		}
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}
	
	/**
	 * 获取业主资料忽略有效天数，默认为7天
	 * @return
	 */
	public Integer getIgnoreDays(){
		Integer ignoreDays = 7;
		DictionarySearch search = new DictionarySearch();
		search.setDicType("ignoreDays");
		List<DictionaryVo> list = this.dictionaryService.getList(search);
		if(list != null && list.size() > 0){
			String exchangeValidDay = list.get(0).getDicValue();
			if(StringUtil.isNotEmpty(exchangeValidDay)){
				try{
					ignoreDays = Integer.parseInt(exchangeValidDay);
				}catch(Exception e){
					ignoreDays = 7;
					e.printStackTrace();
				}
			}
		}
		return ignoreDays;
	};
	
	


	
}
