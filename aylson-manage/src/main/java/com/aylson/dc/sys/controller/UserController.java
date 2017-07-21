package com.aylson.dc.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.GeneralConstant.UserStatus;
import com.aylson.dc.base.GeneralConstant.UserType;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.search.UserSearch;
import com.aylson.dc.sys.service.AgentUserService;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.service.OrgUserService;
import com.aylson.dc.sys.service.RoleService;
import com.aylson.dc.sys.service.UserService;
import com.aylson.dc.sys.vo.AgentUserVo;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.dc.sys.vo.OrgUserVo;
import com.aylson.dc.sys.vo.RoleVo;
import com.aylson.dc.sys.vo.UserVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.MD5Encoder;
import com.aylson.utils.StringUtil;

/**
 * 用户管理
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;           //用户服务
	@Autowired
	private OrgUserService orgUserService;     //机构用户服务
	@Autowired
	private AgentUserService agentUserService; //代理商用户服务
	@Autowired
	private DictionaryService dictionaryService;   //数据字典服务
	@Autowired
	private RoleService roleService;               //角色服务
	
	/**
	 * 后台-系统用户登录
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	@ResponseBody
	public Result adminLogin(UserVo userVo){
		Result result = new Result();
		try{
			UserSearch userSearch = new UserSearch();
			userSearch.setUserName(userVo.getUserName().trim());
			List<UserVo> list = this.userService.getList(userSearch);
			if(list == null || list.size() == 0){
				result.setError(ResultCode.CODE_STATE_4006, "该用户名不存在");
				return result;
			}
			UserVo loginUser = list.get(0);
			if(UserStatus.DISABLE == loginUser.getStatus()){
				result.setError(ResultCode.CODE_STATE_4006, "该账号已经被禁用，启用请联系管理员");
				return result;
			}
			if(!loginUser.getPwd().trim().equals(MD5Encoder.encodeByMD5(userVo.getPwd().trim()))){
				result.setError(ResultCode.CODE_STATE_4006, "密码不正确");
				return result;
			}
			/*String code = (String)this.request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);  
			if(!userVo.getValidateCode().toUpperCase().equals(code.toUpperCase())){
				result.setError(ResultCode.CODE_STATE_4006, "验证码不正确");
				return result;
			}*/
			
			this.request.getSession().setAttribute("userVo", loginUser);
			SessionInfo sessionInfo = new SessionInfo();//缓存信息
			sessionInfo.setUser(loginUser);
			RoleVo roleVo = this.roleService.getById(loginUser.getRoleId());
			sessionInfo.setRole(roleVo);
			this.request.getSession().setAttribute("sessionInfo", sessionInfo);
			this.request.getSession().setAttribute("isLiveMode", SystemConfig.isLiveMode());
			result.setOK(ResultCode.CODE_STATE_200, "/index");
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-系统用户注销登录
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value = "/admin/loginOut", method = RequestMethod.POST)
	@ResponseBody
	public Result adminloginOut(UserVo userVo){
		Result result = new Result();
		try{
			if(this.request.getSession().getAttribute("sessionInfo") != null){
				this.request.getSession().removeAttribute("sessionInfo");
			}
			result.setOK(ResultCode.CODE_STATE_200, "注销成功");
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取用户列表
	 * @param userSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public List<UserVo> getUsers(UserSearch userSearch) {
		List<UserVo> list = this.userService.getList(userSearch);
		return list;
	}
	
	/**
	 * 编辑个人资料页面
	 * @return
	 */
	@RequestMapping(value = "/toEdit", method = RequestMethod.GET)
	public String toEdit() {
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
		if(sessionInfo != null && sessionInfo.getUser() != null){
			UserVo userInfo = sessionInfo.getUser();
			if( UserType.ORG_USER == userInfo.getType()){
				OrgUserVo orgUserVo = this.orgUserService.getByUserId(userInfo.getId());
				if(orgUserVo != null && orgUserVo.getBirthday() != null){
					orgUserVo.setBirthdayStr(DateUtil.format(orgUserVo.getBirthday(), true));
				}
				this.request.setAttribute("orgUserVo",orgUserVo);
				this.request.setAttribute("type",userInfo.getType());
			}else if( UserType.AGENT_USER == userInfo.getType()){
				AgentUserVo agentUserVo = this.agentUserService.getByUserId(userInfo.getId());
				DictionarySearch dictionarySearch = new DictionarySearch();
				dictionarySearch.setDicType("ProductCategory_bigType");
				List<DictionaryVo> productTypesList = this.dictionaryService.getList(dictionarySearch);//获取产品类型列表
				if(agentUserVo != null && StringUtil.isNotEmpty(agentUserVo.getProductIds())){
					String[] productIdsArray = agentUserVo.getProductIds().split(",");
					if(productIdsArray != null && productIdsArray.length > 0){
						for(int i=0; i<productIdsArray.length; i++){
							for(DictionaryVo dic : productTypesList){
								int productId = Integer.parseInt(productIdsArray[i]);
								if(dic.getId() == productId){
									dic.setCk(true);
									break;
								}
							}
						}
					}
				}
				if(agentUserVo.getBirthday() != null){
					agentUserVo.setBirthdayStr(DateUtil.format(agentUserVo.getBirthday(), true));
				}
				agentUserVo.setProductTypesList(productTypesList);
				this.request.setAttribute("agentUserVo",agentUserVo);
				this.request.setAttribute("type",userInfo.getType());
			}
		}
		return "/jsp/sys/admin/user/add";
	}
	
	/**
	 * 编辑个人资料保存
	 * @param orgUserVo
	 * @param agentUserVo
	 * @param userType
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(OrgUserVo orgUserVo,AgentUserVo agentUserVo, Integer userType){
		Result result = new Result();
		try{
			Boolean flag = false;
			if( UserType.ORG_USER == userType){
				flag = this.orgUserService.edit(orgUserVo);
			}else if( UserType.AGENT_USER == userType){
				String[] productArray = this.request.getParameterValues("product");
				String[] productIdArray = this.request.getParameterValues("productId");
				if(productArray != null && productArray.length > 0){
					StringBuffer products = new StringBuffer();
					StringBuffer productIds = new StringBuffer();
					for(int i=0; i<productArray.length; i++){
						if(i == productArray.length-1){
							products.append(productArray[i]);
							productIds.append(productIdArray[i]);
						}else{
							products.append(productArray[i]).append(",");
							productIds.append(productIdArray[i]).append(",");
						}
					}
					agentUserVo.setProducts(products.toString());
					agentUserVo.setProductIds(productIds.toString());
				}
				flag = this.agentUserService.edit(agentUserVo);
			}
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/admin/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public Result changeStatus(UserVo userVo){
		Result result = new Result();
		try{
			Boolean flag = this.userService.edit(userVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 跳转到修改密码页面
	 * @return
	 */
	@RequestMapping(value = "/toEditPwd", method = RequestMethod.GET)
	public String toEditPwd() {
		return "/jsp/sys/admin/user/editPassWord";
	}	
	
	/**
	 * 修改密码
	 * @param oldPassWord
	 * @param newPassWord
	 * @param secondPassWord
	 * @return Result
	 */
	@RequestMapping(value = "/editPassWord", method = RequestMethod.POST)
	@ResponseBody
	public Result editPassWord(String oldPassWord, String newPassWord,String secondPassWord){
		Result result = new Result();
		try{
			UserVo currUser = null;
			SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo!=null){
				currUser = sessionInfo.getUser();
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
				return result;
			}
			if(!secondPassWord.equals(newPassWord)){
				result.setError(ResultCode.CODE_STATE_4006, "确认密码错误");
				return result;
			}
			if(!MD5Encoder.encodeByMD5(oldPassWord).equals(currUser.getPwd())){
				result.setError(ResultCode.CODE_STATE_4006, "旧密码错误");
				return result;		
			}
			UserVo user = new UserVo();
			user.setId(currUser.getId());
			user.setPwd(MD5Encoder.encodeByMD5(newPassWord));
			boolean flag = this.userService.edit(user);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "修改密码成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "修改密码失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;		
	}	
	
}
