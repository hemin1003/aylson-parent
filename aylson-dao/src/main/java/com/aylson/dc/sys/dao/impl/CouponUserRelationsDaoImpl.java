package com.aylson.dc.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.CouponUserRelationsDao;
import com.aylson.dc.sys.po.CouponUserRelations;
import com.aylson.dc.sys.search.CouponUserRelationsSearch;

@Repository
public class CouponUserRelationsDaoImpl extends BaseDaoImpl<CouponUserRelations, CouponUserRelationsSearch>
		implements CouponUserRelationsDao {

}
