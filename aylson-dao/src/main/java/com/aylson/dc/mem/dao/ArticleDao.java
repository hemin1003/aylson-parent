package com.aylson.dc.mem.dao;

import java.util.List;
import java.util.Map;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.mem.po.Article;
import com.aylson.dc.mem.po.ArticleRead;
import com.aylson.dc.mem.po.ArticleReply;
import com.aylson.dc.mem.search.ArticleSearch;
import com.aylson.dc.mem.vo.ArticleReadVo;
import com.aylson.dc.mem.vo.ArticleReplyVo;

public interface ArticleDao extends BaseDao<Article,ArticleSearch> {
	
	/**
	 * 查询-读取情况总条数
	 * @param params
	 * @return
	 */
	public Long selectReadCount(Map<String, Object> params);
	
	/**
	 * 查询读取情况
	 * @param params
	 * @return
	 */
	public List<ArticleRead> selectReadInfo(Map<String, Object> params);
	
	/**
	 * 查询读取情况
	 * @param params
	 * @return
	 */
	public List<ArticleReadVo> selectReadInfo(ArticleSearch articleSearch);
	
	/**
	 * 添加读取情况
	 * @param articleRead
	 * @return
	 */
	public Boolean insertReadInfo(ArticleRead articleRead);
	
	/**
	 * 查询-回复情况情况总条数
	 * @param params
	 * @return
	 */
	public Long selectReplyCount(Map<String, Object> params);
	
	/**
	 * 查询-回复情况情况总条数
	 * @param params
	 * @return
	 */
	public Long selectReplyCount(ArticleSearch articleSearch);
	
	/**
	 * 查询回复情况
	 * @param params
	 * @return
	 */
	public List<ArticleReplyVo> selectReplyInfo(ArticleSearch articleSearch);
	/**
	 * 查询回复情况
	 * @param params
	 * @return
	 */
	public List<ArticleReply> selectReplyInfo(Map<String, Object> params);
	
	/**
	 * 添加回复情况
	 * @param articleReply
	 * @return
	 */
	public Boolean insertReplyInfo(ArticleReply articleReply);
	
	/**
	 * 更新点赞数量
	 * @param articleId
	 * @return
	 */
	public Boolean updatePraiseCount(Integer articleId);
	
	/**
	 * 更新分享数量
	 * @param articleId
	 * @return
	 */
	public Boolean updateShareCount(Integer articleId);
	
}
