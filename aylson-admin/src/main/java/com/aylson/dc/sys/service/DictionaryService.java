package com.aylson.dc.sys.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.Dictionary;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.vo.DictionaryVo;


/**
 * 字典信息基础服务类
 */
public interface DictionaryService extends BaseService<Dictionary,DictionarySearch> {

	/**
	 * 校验信息有效性
	 * @param dictionaryVo
	 * @return
	 */
	public Result valid(DictionaryVo dictionaryVo);
	
	
}
