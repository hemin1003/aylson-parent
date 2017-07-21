package com.aylson.dc.mem.service;

import java.util.List;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.Article;
import com.aylson.dc.mem.po.ArticleRead;
import com.aylson.dc.mem.po.ArticleReply;
import com.aylson.dc.mem.search.ArticleSearch;
import com.aylson.dc.mem.vo.ArticleReadVo;
import com.aylson.dc.mem.vo.ArticleReplyVo;
import com.aylson.dc.mem.vo.ArticleVo;

public interface ArticleService extends BaseService<Article,ArticleSearch> {
	
	/**
	 * 添加读取情况
	 * @param articleRead
	 * @return
	 */
	public Boolean addReadInfo(ArticleRead articleRead);
	
	/**
	 * 查询读取情况
	 * @param articleSearch
	 * @return
	 */
	public List<ArticleReadVo> getReadInfo(ArticleSearch articleSearch);
	
	/**
	 * 查询回复情况
	 * @param articleSearch
	 * @return
	 */
	public List<ArticleReplyVo> getReplyInfo(ArticleSearch articleSearch);
	
	/**
	 * 获取回复分页列表
	 */
	public Page<ArticleReplyVo> getReplyPage(ArticleSearch articleSearch);
	
	/**
	 * 添加回复情况
	 * @param articleReply
	 * @return
	 */
	public Boolean addReplyInfo(ArticleReply articleReply);
	
	/**
	 * 更新点赞数
	 * @param articleId
	 * @return
	 */
	public Result praise(Integer articleId);
	
	/**
	 * 更新分享数
	 * @param articleId
	 * @return
	 */
	public Result share(Integer articleId);
	
	/**
	 * 发表文章
	 * @param articleVo
	 * @param memberId
	 * @return
	 */
	public Result publishArticle(ArticleVo articleVo, String memberId);
	
	/**
	 * 获取文章详情
	 * @param articleId
	 * @param memberId
	 * @return
	 */
	public Result getArticle(Integer articleId, String memberId);
	
	/**
	 * 回复文章
	 * @param articleReply
	 * @return
	 */
	public Result replyArticle(ArticleReply articleReply, String memberId);
	
	
}
