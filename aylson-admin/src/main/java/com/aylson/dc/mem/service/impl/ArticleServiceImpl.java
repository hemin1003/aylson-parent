package com.aylson.dc.mem.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.ArticlePublishChannel;
import com.aylson.dc.mem.dao.ArticleDao;
import com.aylson.dc.mem.po.Article;
import com.aylson.dc.mem.po.ArticleRead;
import com.aylson.dc.mem.po.ArticleReply;
import com.aylson.dc.mem.search.ArticleSearch;
import com.aylson.dc.mem.service.ArticleService;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.vo.ArticleReadVo;
import com.aylson.dc.mem.vo.ArticleReplyVo;
import com.aylson.dc.mem.vo.ArticleVo;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.utils.StringUtil;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article,ArticleSearch> implements ArticleService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private MemAccountService memAccountService;
	
	@Override
	protected BaseDao<Article,ArticleSearch> getBaseDao() {
		return this.articleDao;
	}

	@Override
	public Boolean addReadInfo(ArticleRead articleRead) {
		return this.articleDao.insertReadInfo(articleRead);
	}

	@Override
	public List<ArticleReadVo> getReadInfo(ArticleSearch articleSearch) {
		return this.articleDao.selectReadInfo(articleSearch);
	}

	@Override
	public List<ArticleReplyVo> getReplyInfo(ArticleSearch articleSearch) {
		return this.articleDao.selectReplyInfo(articleSearch);
	}

	@Override
	public Page<ArticleReplyVo> getReplyPage(ArticleSearch articleSearch) {
		Page<ArticleReplyVo> page = new Page<ArticleReplyVo>();
		articleSearch.setIsPage(true);
		List<ArticleReplyVo> list = this.getReplyInfo(articleSearch);
		page.setPage(articleSearch.getPage());
		page.setRows(articleSearch.getRows());
		page.setTotal(this.articleDao.selectReplyCount(articleSearch));
		page.setRowsObject(list);
		return page;
	}

	@Override
	public Boolean addReplyInfo(ArticleReply articleReply) {
		return this.articleDao.insertReplyInfo(articleReply);
	}

	@Override
	public Result praise(Integer articleId) {
		Result result = new Result();
		if(articleId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取文章详情失败");
			return result;
		}
		Boolean flag = this.articleDao.updatePraiseCount(articleId);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "点赞成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "点赞失败");
		}
		return result;
	}

	@Override
	public Result share(Integer articleId) {
		Result result = new Result();
		if(articleId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取文章详情失败");
			return result;
		}
		Boolean flag = this.articleDao.updateShareCount(articleId);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "分享成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "分享失败");
		}
		return result;
	}
	
	/**
	 * 发表文章
	 * @param articleVo
	 * @param memberId
	 * @return
	 */
	@Override
	public Result publishArticle(ArticleVo articleVo, String memberId) {
		Result result = new Result();
		//1、信息有效性校验  
		if(StringUtil.isEmpty(memberId)){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		MemAccountVo memAccountVo = this.memAccountService.getById(Integer.parseInt(memberId));
		if(memAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		//处理摘要
		String summary = StringUtil.parseRichContent(articleVo.getContent());
		if(StringUtil.isNotEmpty(summary)){
			if(summary.length() > 100){
				summary = summary.substring(0, 100)+"...";
			}
		}
		articleVo.setSummary(summary);
		memAccountVo.getId();
		articleVo.setCreateTime(new Date());
		articleVo.setPublishTime(new Date());
		articleVo.setChannel(ArticlePublishChannel.WX);
		articleVo.setPublisher(memAccountVo.getAccountName());
		articleVo.setPublisherId(memAccountVo.getId());
		articleVo.setStatus(1);
		Boolean flag = this.add(articleVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "发表成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "发表失败");
		}
		return result;
	}
	
	/**
	 * 获取文章详情
	 * @param articleId
	 * @param memberId
	 * @return
	 */
	@Override
	public Result getArticle(Integer articleId, String memberId) {
		Result result = new Result();
		if(articleId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取文章信息失败");
			return result;
		}
		//查询当前id
		ArticleVo articleVo = this.getById(articleId);
		if(articleVo != null && !articleVo.getIsRead()){
			if(StringUtil.isEmpty(memberId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			MemAccountVo memAccountVo = this.memAccountService.getById(Integer.parseInt(memberId));
			if(memAccountVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			//如果第一次进入，添加一条读取id
			ArticleRead articleRead = new ArticleRead();
			articleRead.setArticleId(articleId);
			articleRead.setReader(memAccountVo.getAccountName());
			articleRead.setReaderId(memAccountVo.getId());
			articleRead.setReadTime(new Date());
			this.addReadInfo(articleRead);
		}
		//获取读取列表
		ArticleSearch articleSearch = new ArticleSearch();
		articleSearch.setArticleId(articleId);
		articleSearch.setIsPage(true);
		List<ArticleReadVo> articleReadList = this.getReadInfo(articleSearch);
		articleVo.setArticleReadList(articleReadList);
		result.setOK(ResultCode.CODE_STATE_200, "", articleVo);
		return result;
	}
	
	/**
	 * 回复文章
	 * @param articleReply
	 * @return
	 */
	@Override
	public Result replyArticle(ArticleReply articleReply, String memberId) {
		Result result = new Result();
		Boolean flag = false;
		//1、信息有效性校验  
		if(articleReply.getArticleId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取文章信息失败");
			return result;
		}
		if(StringUtil.isEmpty(memberId)){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		MemAccountVo memAccountVo = this.memAccountService.getById(Integer.parseInt(memberId));
		if(memAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		articleReply.setReplier(memAccountVo.getAccountName());
		articleReply.setReplierId(memAccountVo.getId());
		articleReply.setReplyTime(new Date());
		flag = this.addReplyInfo(articleReply);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "发表成功",articleReply);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "发表失败");
		}
		return result;
	}

	
}
