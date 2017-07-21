package com.aylson.dc.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.OrgDao;
import com.aylson.dc.sys.po.Org;
import com.aylson.dc.sys.search.OrgSearch;
import com.aylson.dc.sys.vo.OrgVo;

@Repository
public class OrgDaoImpl extends BaseDaoImpl<Org,OrgSearch> implements OrgDao {

	@Override
	public Boolean batchInsertRoleOrg(List<Map<String, Object>> roleOrgList) {
		int rows = this.sqlSessionTemplate.insert(this.getSqlName("batchInsertRoleOrg"), roleOrgList);
		if(rows > 0) return true;
		return false;
	}
	
	@Override
	public Boolean deleteRoleOrgByRoleId(Integer roleId){
		int row = this.sqlSessionTemplate.delete(this.getSqlName("deleteRoleOrgByRoleId"), roleId);
		if(row>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean deleteRoleOrg(Map<String, Object> params) {
		int row = this.sqlSessionTemplate.delete(this.getSqlName("deleteRoleOrg"), params);
		if(row>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<OrgVo> getUserRoleOrg(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("getUserRoleOrg"), params);
	}
}
