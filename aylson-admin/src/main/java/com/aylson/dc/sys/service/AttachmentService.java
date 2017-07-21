package com.aylson.dc.sys.service;

import java.util.List;
import java.util.Map;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.Attachment;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.vo.AttachmentVo;

public interface AttachmentService extends BaseService<Attachment,AttachmentSearch> {
	
	/**
	 * 将附件列表根据时间分组
	 * @param list
	 * @return
	 */
	public Map<String, List<AttachmentVo>> getAttachmentGroup(List<AttachmentVo> list);
	
}
