package com.aylson.dc.mem.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.MemAccountDao;
import com.aylson.dc.mem.po.MemAccount;
import com.aylson.dc.mem.search.MemAccountSearch;
import com.aylson.dc.mem.vo.MemAccountVo;

@Repository
public class MemAccountDaoImpl extends BaseDaoImpl<MemAccount,MemAccountSearch> implements MemAccountDao {

	@Override
	public List<MemAccountVo> selectRelationByWxOpenId(String wxOpenId) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectRelationByWxOpenId"), wxOpenId);
	}
	
	@Override
	public List<MemAccountVo> selectRelation(Map<String, Object> params){
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectRelation"), params);
	}

	@Override
	public Boolean insertReferralWxUser(MemAccount memAccount) {
		int rows = this.sqlSessionTemplate.insert(this.getSqlName("insertReferralWxUser"), memAccount);
		if(rows > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean deleteRelationById(Integer id) {
		int rows = this.sqlSessionTemplate.delete(this.getSqlName("deleteRelationById"), id);
		if(rows > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<String> selectWxOpenId() {
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectWxOpenId"));
	}


}
