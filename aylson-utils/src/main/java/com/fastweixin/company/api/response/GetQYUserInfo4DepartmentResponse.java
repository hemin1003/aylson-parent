package com.fastweixin.company.api.response;/**
 * Created by Nottyjay on 2015/6/11.
 */

import com.alibaba.fastjson.annotation.JSONField;
import com.fastweixin.api.response.BaseResponse;
import com.fastweixin.company.api.entity.QYUser;

import java.util.List;

/**
 * ====================================================================
 * 上海聚攒软件开发有限公司
 * --------------------------------------------------------------------
 *
 * @author Nottyjay
 * @version 1.0.beta
 *          ====================================================================
 */
public class GetQYUserInfo4DepartmentResponse extends BaseResponse {

    @JSONField(name = "userlist")
    public List<QYUser> userList;

    public List<QYUser> getUserList() {
        return userList;
    }

    public void setUserList(List<QYUser> userList) {
        this.userList = userList;
    }
}
