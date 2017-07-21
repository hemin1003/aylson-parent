package com.aylson.dc.busi.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.OwnerGeneralConstant;
import com.aylson.dc.base.OwnerGeneralConstant.CostType;
import com.aylson.dc.busi.dao.CostDao;
import com.aylson.dc.busi.po.Cost;
import com.aylson.dc.busi.search.CostSearch;
import com.aylson.dc.busi.service.CostService;
import com.aylson.dc.busi.vo.CostVo;


@Service
public class CostServiceImpl extends BaseServiceImpl<Cost,CostSearch> implements CostService {

	@Autowired
	private CostDao costDao;

	@Override
	protected BaseDao<Cost,CostSearch> getBaseDao() {
		return costDao;
	}

	@Override
	public Result initAppointCost(Integer sourceType, Integer sourceId) {
		Result result = new Result();
		Boolean flag = false;
		List<CostVo> list = new ArrayList<CostVo>();
		Map<Integer, String> costTypeMap = OwnerGeneralConstant.CostType.CostTypeMap;
		for (Map.Entry<Integer, String> entry : costTypeMap.entrySet()) {  
		   // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			CostVo cost = new CostVo();
			cost.setSourceId(sourceId);
			cost.setSourceType(sourceType);
			cost.setCostType(entry.getKey());
			cost.setCostName(entry.getValue());
			if( entry.getKey() == CostType.DISCOUNT || entry.getKey() == CostType.ORG_DISCOUNT){
				cost.setCostValue(100.0f);
			}else{
				cost.setCostValue(0.0f);
			}
			cost.setDesc("");
			cost.setRemark("");
			list.add(cost);
		}  
		if(!list.isEmpty()){
			flag = this.batchAdd(list);
		}
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "初始化成功");
		}else{
			result.setOK(ResultCode.CODE_STATE_4006, "初始化失败");
		}
		return result;
	}


}
