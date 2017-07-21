package com.aylson.dc.owner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.Design;
import com.aylson.dc.owner.search.DesignSearch;
import com.aylson.dc.owner.vo.DesignVo;

public interface DesignService extends BaseService<Design,DesignSearch> {
	
	/**
	 * 根据设计信息表id删除设计信息表信息，以及设计明细
	 * @param designId
	 * @return
	 */
	public Result delDesign(Integer designId, Integer designType);
	
	/**
	 * 提交设计大样图
	 * @param designId
	 * @return
	 */
	public Result applyDraw(Integer appointId);
	
	/**
	 * 添加设计信息表内容
	 * @param designVo
	 * @param designDetailDWVoListJson
	 * @return
	 */
	public Result addDesign(DesignVo designVo,String designDetailDWVoListJson);
	
	/**
	 * 修改设计信息表内容
	 * @param designVo
	 * @param designDetailDWVoListJson
	 * @return
	 */
	public Result editDesign(DesignVo designVo,String designDetailDWVoListJson);
	
	/**
	 * 确认大样图
	 * @param designVo
	 * @return
	 */
	public Result confirmDraw(DesignVo designVo);
	
	/**
	 * 根据设计id和设计类型查询
	 * @param designId
	 * @param designType
	 * @return
	 */
	public DesignVo getDesign(Integer designId, Integer designType);
	
	
}
