package com.aylson.dc.sys.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.AttachmentDao;
import com.aylson.dc.sys.po.Attachment;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.service.AttachmentService;
import com.aylson.dc.sys.vo.AttachmentVo;
import com.aylson.utils.DateUtil;


@Service
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment,AttachmentSearch> implements AttachmentService {

	@Autowired
	private AttachmentDao attachmentDao;

	@Override
	protected BaseDao<Attachment,AttachmentSearch> getBaseDao() {
		return attachmentDao;
	}

	@Override
	public Map<String, List<AttachmentVo>> getAttachmentGroup(List<AttachmentVo> list) {
		Map<String, List<AttachmentVo>> map = new HashMap<String, List<AttachmentVo>>();
		if(list != null && list.size() >0){
			for(AttachmentVo attachmentVo :list){
				String createTime = DateUtil.format(attachmentVo.getCreateTime(), true);
				if(map.containsKey(createTime)){
					map.get(createTime).add(attachmentVo);
				}else{
					List<AttachmentVo> dateGroup = new ArrayList<AttachmentVo>();
					dateGroup.add(attachmentVo);
					map.put(createTime, dateGroup);
				}
			}
		}
		return map;
	}


}
