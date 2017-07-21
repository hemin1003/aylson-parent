package com.aylson.dc.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.DictionaryDao;
import com.aylson.dc.sys.po.Dictionary;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.utils.StringUtil;


/**
 * 字典信息服务类接口实现
 */
@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary,DictionarySearch> implements DictionaryService {
	
	@Autowired
	private DictionaryDao dictionaryDao;

	@Override
	protected BaseDao<Dictionary,DictionarySearch> getBaseDao() {
		return dictionaryDao;
	}
	
	@Override
	public Result valid(DictionaryVo dictionaryVo) {
		Result result = new Result(true,"信息无误！");
		if(StringUtil.isEmpty(dictionaryVo.getDicName())){
			result.setError(ResultCode.CODE_STATE_4006, "字典名称，字典值，类型，所属组不能为空");
		}else if(StringUtil.isEmpty(dictionaryVo.getDicValue())){
			result.setError(ResultCode.CODE_STATE_4006, "字典名称，字典值，类型，所属组不能为空");
		}else if(StringUtil.isEmpty(dictionaryVo.getDicType())){
			result.setError(ResultCode.CODE_STATE_4006, "字典名称，字典值，类型，所属组不能为空");
		}else if(StringUtil.isEmpty(dictionaryVo.getDicGroup())){
			result.setError(ResultCode.CODE_STATE_4006, "字典名称，字典值，类型，所属组不能为空");
		}
		return result;
	}

}
