package com.aylson.dc.mem.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aylson.core.constants.SqlId;
import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.ArticleDao;
import com.aylson.dc.mem.po.Article;
import com.aylson.dc.mem.po.ArticleRead;
import com.aylson.dc.mem.po.ArticleReply;
import com.aylson.dc.mem.search.ArticleSearch;
import com.aylson.dc.mem.vo.ArticleReadVo;
import com.aylson.dc.mem.vo.ArticleReplyVo;

@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article,ArticleSearch> implements ArticleDao {

	@Override
	public Long selectReadCount(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectReadCount"), params);

	}

	@Override
	public List<ArticleRead> selectReadInfo(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectReadInfo"), params);

	}
	
	@Override
	public List<ArticleReadVo> selectReadInfo(ArticleSearch articleSearch) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectReadInfo"), articleSearch);
	}

	@Override
	public Boolean insertReadInfo(ArticleRead articleRead) {
		int rows = this.sqlSessionTemplate.insert(this.getSqlName("insertReadInfo"), articleRead);
		if(rows > 0) return true;
		return false;
		
	}

	@Override
	public Long selectReplyCount(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectReplyCount"), params);

	}
	
	@Override
	public List<ArticleReplyVo> selectReplyInfo(ArticleSearch articleSearch) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectReplyInfo"), articleSearch);
	}


	@Override
	public List<ArticleReply> selectReplyInfo(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectReplyInfo"), params);

	}

	@Override
	public Boolean insertReplyInfo(ArticleReply articleReply) {
		int rows = this.sqlSessionTemplate.insert(this.getSqlName("insertReplyInfo"), articleReply);
		if(rows > 0) return true;
		return false;
	}

	@Override
	public Long selectReplyCount(ArticleSearch articleSearch) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectReplyCount"), articleSearch);
	}

	@Override
	public Boolean updatePraiseCount(Integer articleId) {
		int rows = sqlSessionTemplate.update(this.getSqlName("updatePraiseCount"), articleId);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateShareCount(Integer articleId) {
		int rows = sqlSessionTemplate.update(this.getSqlName("updateShareCount"), articleId);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}


}
