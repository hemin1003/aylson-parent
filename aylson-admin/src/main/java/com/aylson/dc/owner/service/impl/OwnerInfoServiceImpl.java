package com.aylson.dc.owner.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.SourceTable;
import com.aylson.dc.base.GeneralConstant.AttachmentType;
import com.aylson.dc.owner.dao.OwnerInfoDao;
import com.aylson.dc.owner.po.OwnerInfo;
import com.aylson.dc.owner.search.OwnerInfoSearch;
import com.aylson.dc.owner.service.OwnerInfoService;
import com.aylson.dc.owner.vo.OwnerInfoVo;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.service.AttachmentService;
import com.aylson.dc.sys.vo.AttachmentVo;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;


@Service
public class OwnerInfoServiceImpl extends BaseServiceImpl<OwnerInfo,OwnerInfoSearch> implements OwnerInfoService {

	@Autowired
	private OwnerInfoDao ownerInfoDao;
	@Autowired
	private AttachmentService attachmentService;             //附件服务管理

	@Override
	protected BaseDao<OwnerInfo,OwnerInfoSearch> getBaseDao() {
		return ownerInfoDao;
	}

	@Override
	@Transactional
	public Result addInfo(OwnerInfoVo ownerInfoVo) {
		Result result = new Result();
		Boolean flag = false;
		//信息校验
		if(ownerInfoVo.getCreaterId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "当前登录人id不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(ownerInfoVo.getName())){
			result.setError(ResultCode.CODE_STATE_4006, "客户姓名不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(ownerInfoVo.getMobilePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(ownerInfoVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		List<AttachmentVo> attachList = ownerInfoVo.getAttachList();  //附件列表
		if(attachList != null && attachList.size()>0){
			for(AttachmentVo attach : attachList){
				if(StringUtil.isEmpty(attach.getAttachName())){
					result.setError(ResultCode.CODE_STATE_4006, "附件名称不能为空");
					return result;
				}
				if(StringUtil.isEmpty(attach.getAttachAddress())){
					result.setError(ResultCode.CODE_STATE_4006, "附件地址不能为空");
					return result;
				}
			}
		}
		//信息有效
		Date curDate = new Date();   //当前时间
		//保存客户信息
		ownerInfoVo.setSourceType(2);//后台新增
		ownerInfoVo.setCreateTime(curDate);
		flag = this.add(ownerInfoVo);            //保存客户信息
		if(flag){
			if(attachList != null && attachList.size()>0){//如果客户信息存在附件，保存附件到附件表
				for(AttachmentVo attach : attachList){
					attach.setCreateTime(curDate);
					attach.setSourceId(ownerInfoVo.getId());
					attach.setType(AttachmentType.OWNERINFO);
					attach.setSourceType(SourceTable.OWNER_INFO);
					attach.setRemark(AttachmentType.AttachmentTypeMap.get(AttachmentType.OWNERINFO));
				}
				flag = this.attachmentService.batchAdd(attachList);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "保存客户附件信息失败");
					throw new ServiceException("保存客户附件信息失败");
				}
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存客户信息失败");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	@Override
	@Transactional
	public Result updateInfo(OwnerInfoVo ownerInfoVo) {
		Result result = new Result();
		Boolean flag = false;
		//信息校验
		if(ownerInfoVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "主键不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(ownerInfoVo.getName())){
			result.setError(ResultCode.CODE_STATE_4006, "客户姓名不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(ownerInfoVo.getMobilePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(ownerInfoVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		List<AttachmentVo> attachList = ownerInfoVo.getAttachList();  //附件列表
		if(attachList != null && attachList.size()>0){
			for(AttachmentVo attach : attachList){
				if(StringUtil.isEmpty(attach.getAttachName())){
					result.setError(ResultCode.CODE_STATE_4006, "附件名称不能为空");
					return result;
				}
				if(StringUtil.isEmpty(attach.getAttachAddress())){
					result.setError(ResultCode.CODE_STATE_4006, "附件地址不能为空");
					return result;
				}
			}
		}
		//信息有效
		Date curDate = new Date();   //当前时间
		//保存客户信息
		ownerInfoVo.setSourceType(2);            //后台新增
		ownerInfoVo.setUpdateTime(curDate);
		flag = this.edit(ownerInfoVo);            //更新客户信息
		//处理附件信息
		if(flag){
			AttachmentSearch attachmentSearch = new AttachmentSearch();
			attachmentSearch.setType(AttachmentType.OWNERINFO);
			attachmentSearch.setSourceId(ownerInfoVo.getId());
			attachmentSearch.setSourceType(SourceTable.OWNER_INFO);
			List<AttachmentVo> attachListOld = this.attachmentService.getList(attachmentSearch);
			if(attachListOld != null && attachListOld.size() > 0){//已经存在附件
				for(AttachmentVo current : attachList){
					//处理附件信息的创建时间
					current.setCreateTime(curDate);  //时间修改为当前时间
					for(AttachmentVo old : attachListOld){
						if(old.getAttachAddress().equals(current.getAttachAddress())){//没有修改过
							current.setCreateTime(old.getCreateTime());//时间不变
							break;
						}
					}
					current.setSourceId(ownerInfoVo.getId());
					current.setSourceType(SourceTable.OWNER_INFO);
					current.setType(AttachmentType.OWNERINFO);
					current.setRemark(AttachmentType.AttachmentTypeMap.get(AttachmentType.OWNERINFO));
				}
				//attachList为处理好的新的附件信息
				flag = this.attachmentService.delete(attachmentSearch);//删除原来的信息
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "删除原来的附件信息失败");
					throw new ServiceException("删除原来的附件信息失败");
				}
			}
			//更新附件信息
			if(attachList != null && attachList.size()>0){//如果客户信息存在附件，保存附件到附件表
				flag = this.attachmentService.batchAdd(attachList);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "保存客户附件信息失败");
					throw new ServiceException("保存客户附件信息失败");
				}
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存客户信息失败");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}

	@Override
	@Transactional
	public Result delInfo(Integer id) {
		Result result = new Result();
		Boolean flag = false;
		//信息校验
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
			return result;
		}
		flag = this.deleteById(id);
		if(flag){
			AttachmentSearch attachmentSearch = new AttachmentSearch();
			attachmentSearch.setStatus(1);//有效附件
			attachmentSearch.setType(AttachmentType.OWNERINFO);
			attachmentSearch.setSourceId(id);
			List<AttachmentVo> attachList = this.attachmentService.getList(attachmentSearch);
			if(attachList != null && attachList.size() > 0){
				flag = this.attachmentService.delete(attachmentSearch);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "删除客户附件信息失败，请稍后再试或者联系管理员！");
					throw new ServiceException("删除客户附件信息失败");
				}
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "删除客户信息失败，请稍后再试或者联系管理员！");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}


}
