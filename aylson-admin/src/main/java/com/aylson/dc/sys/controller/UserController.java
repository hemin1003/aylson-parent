package com.aylson.dc.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.aylson.dc.sys.search.MenuSearch;
import com.aylson.dc.sys.search.UserSearch;
import com.aylson.dc.sys.service.AgentUserService;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.service.MenuService;
import com.aylson.dc.sys.service.OrgUserService;
import com.aylson.dc.sys.service.RoleService;
import com.aylson.dc.sys.service.UserService;
import com.aylson.dc.sys.vo.AgentUserVo;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.dc.sys.vo.MenuVo;
import com.aylson.dc.sys.vo.OrgUserVo;
import com.aylson.dc.sys.vo.RoleVo;
import com.aylson.dc.sys.vo.UserVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.MD5Encoder;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;

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
	private UserService userService;                     //用户服务
	@Autowired
	private OrgUserService orgUserService;               //机构用户服务
	@Autowired
	private AgentUserService agentUserService;           //代理商用户服务
	@Autowired
	private DictionaryService dictionaryService;         //数据字典服务
	@Autowired
	private RoleService roleService;                     //角色服务
	@Autowired
	private MenuService menuService;                     //菜单服务
	
	
	/**
	 * 登录
	 * @param userName
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(String userName, String pwd, String validateCode){
		Result result = new Result();
		try{
			//信息校验
			if(StringUtil.isEmpty(userName)){
				result.setError(ResultCode.CODE_STATE_4006, "用户名不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(pwd)){
				result.setError(ResultCode.CODE_STATE_4006, "密码不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(validateCode)){
				result.setError(ResultCode.CODE_STATE_4006, "验证码不能为空！");
				return result;
			}
			
			UserSearch userSearch = new UserSearch();
			userSearch.setUserName(userName.trim());
			List<UserVo> list = this.userService.getList(userSearch);
			if(list == null || list.size() == 0){
				result.setError(ResultCode.CODE_STATE_4006, "该用户名不存在");
				return result;
			}
			if(list != null && list.size() > 1){
				result.setError(ResultCode.CODE_STATE_4006, "存在多个用户，请联系管理员");
				return result;
			}
			UserVo userVo = list.get(0);//获取用户信息
			if(UserStatus.DISABLE == userVo.getStatus()){
				result.setError(ResultCode.CODE_STATE_4006, "该账号已经被禁用，启用请联系管理员");
				return result;
			}
			if(!userVo.getPwd().trim().equals(MD5Encoder.encodeByMD5(pwd.trim()))){
				result.setError(ResultCode.CODE_STATE_4006, "密码不正确");
				return result;
			}
			String code = (String)this.request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);  
			if(StringUtil.isEmpty(code)){
				result.setError(ResultCode.CODE_STATE_4006, "获取远程验证码失败");
				return result;
			}
			if(!validateCode.toUpperCase().equals(code.toUpperCase())){
				result.setError(ResultCode.CODE_STATE_4006, "验证码不正确");
				return result;
			}
			//信息有效
			Map<String, Object> userInfo = new HashMap<String, Object>();
			SessionInfo sessionInfo = new SessionInfo();//缓存用户信息
			System.out.println(this.request.getSession().getId()+"==============");
			sessionInfo.setUser(userVo);
			RoleVo roleVo = this.roleService.getById(userVo.getRoleId());
			sessionInfo.setRole(roleVo);
			//是否正式环境
			sessionInfo.setIsLiveMode(SystemConfig.isLiveMode());
			this.request.getSession().setAttribute("sessionInfo", sessionInfo);
			//获取用户菜单信息
			MenuSearch menuSearch = new MenuSearch();
			menuSearch.setRoleId(userVo.getRoleId());
			menuSearch.setIsOnlyRoleMenu(true);
			List<MenuVo> menuList =  this.menuService.getList(menuSearch);
			if(menuList != null && !menuList.isEmpty()){
				menuList = this.menuService.getSyncGridTree(menuList, 1);
			}
			userInfo.put("sessionInfo", sessionInfo);
			userInfo.put("menuList", menuList);
			result.setOK(ResultCode.CODE_STATE_200, "登录成功",userInfo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员");
		}
		return result;
	}
	
	/**
	 * 注销登录
	 * @return
	 */
	@RequestMapping(value = "/loginOut", method = RequestMethod.POST)
	@ResponseBody
	public Result loginOut(){
		Result result = new Result();
		try{
			if(this.request.getSession().getAttribute("sessionInfo") != null){
				this.request.getSession().removeAttribute("sessionInfo");
			}
			result.setOK(ResultCode.CODE_STATE_200, "注销成功");
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员");
		}
		return result;
	}
	
	/**
	 * 根据当前登录人信息查询用户信息
	 * @return
	 */
	@RequestMapping(value = "/getLoginerInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result getLoginerInfo(){
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
			return result;
		}
		UserVo userInfo = sessionInfo.getUser();
		if( UserType.ORG_USER == userInfo.getType()){         //总部用户
			OrgUserVo orgUserVo = this.orgUserService.getByUserId(userInfo.getId());
			if(orgUserVo != null && orgUserVo.getBirthday() != null){
				orgUserVo.setBirthdayStr(DateUtil.format(orgUserVo.getBirthday(), true));
			}
			result.setOK(ResultCode.CODE_STATE_200,"查询总部用户信息", orgUserVo);
		}else if( UserType.AGENT_USER == userInfo.getType()){ //经销商用户
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
			result.setOK(ResultCode.CODE_STATE_200,"查询经销商用户信息", agentUserVo);
		}
		return result;
	}
	
	/**
	 * 更新当前登录人用户信息
	 * @param orgUserVo
	 * @param agentUserVo
	 * @return
	 */
	@RequestMapping(value = "/updateLoginerInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result updateLoginerInfo(OrgUserVo orgUserVo,AgentUserVo agentUserVo){
		Result result = new Result();
		Boolean flag = false;
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
			return result;
		}
		UserVo userInfo = sessionInfo.getUser();
		try{
			if( UserType.ORG_USER == userInfo.getType()){              //总部用户
				if(orgUserVo.getId() == null){
					result.setError(ResultCode.CODE_STATE_4006, "获取总部用户信息id失败！");
					return result;
				}
				if( StringUtil.isEmpty(orgUserVo.getRealName())){
					result.setError(ResultCode.CODE_STATE_4006, "姓名不能为空！");
					return result;
				}
				if(orgUserVo.getOrgId() == null || StringUtil.isEmpty(orgUserVo.getOrgName())){
					result.setError(ResultCode.CODE_STATE_4006, "所属部门不能为空！");
					return result;
				}
				if(StringUtil.isNotEmpty(orgUserVo.getMobilePhone()) && !VerificationUtils.isValid(orgUserVo.getMobilePhone(), VerificationUtils.MOBILE)){
					result.setError(ResultCode.CODE_STATE_4006, "移动电话格式有误！");
					return result;
				}
				if(StringUtil.isNotEmpty(orgUserVo.getBirthdayStr())){
					orgUserVo.setBirthday(DateUtil.format(orgUserVo.getBirthdayStr(), "yyyy-MM-dd"));
				}
				flag = this.orgUserService.edit(orgUserVo);
				
			}else if( UserType.AGENT_USER == userInfo.getType()){      //经销商用户
				if(agentUserVo.getId() == null){
					result.setError(ResultCode.CODE_STATE_4006, "获取经销商用户信息id失败！");
					return result;
				}
				if( StringUtil.isEmpty(agentUserVo.getAgentName())){
					result.setError(ResultCode.CODE_STATE_4006, "代理商名称不能为空！");
					return result;
				}
				if(StringUtil.isNotEmpty(agentUserVo.getBirthdayStr())){
					agentUserVo.setBirthday(DateUtil.format(agentUserVo.getBirthdayStr(), "yyyy-MM-dd"));
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
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请售后再试或者联系管理员");
		}
		return result;
	}
	
	/**
	 * 修改我的密码
	 * @param oldPassWord
	 * @param newPassWord
	 * @param secondPassWord
	 * @return
	 */
	@RequestMapping(value = "/updateMyPwd", method = RequestMethod.POST)
	@ResponseBody
	public Result updateMyPwd(String oldPassWord, String newPassWord,String secondPassWord){
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			UserVo userInfo = sessionInfo.getUser();
			if(StringUtil.isEmpty(oldPassWord)){
				result.setError(ResultCode.CODE_STATE_4006, "旧密码不能为空");
				return result;
			}
			if(StringUtil.isEmpty(newPassWord)){
				result.setError(ResultCode.CODE_STATE_4006, "新密码不能为空");
				return result;
			}
			if(StringUtil.isEmpty(secondPassWord)){
				result.setError(ResultCode.CODE_STATE_4006, "确认密码不能为空");
				return result;
			}
			if(!secondPassWord.equals(newPassWord)){
				result.setError(ResultCode.CODE_STATE_4006, "新密码和确认密码不一致");
				return result;
			}
			if(!MD5Encoder.encodeByMD5(oldPassWord).equals(userInfo.getPwd())){
				result.setError(ResultCode.CODE_STATE_4006, "旧密码错误");
				return result;		
			}
			UserVo user = new UserVo();
			user.setId(userInfo.getId());
			user.setPwd(MD5Encoder.encodeByMD5(newPassWord));
			Boolean flag = this.userService.edit(user);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "修改密码成功，新密码在下次登录生效");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "修改密码失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请售后再试或者联系管理员");
		}
		return result;	
	}
	
	/**
	 * 禁用/启用
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public  Result changeStatus(Integer id, Integer status) {
		Result result = new Result();
		try {
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "用户id不能为空");
				return result;
			}
			if(status == null){
				result.setError(ResultCode.CODE_STATE_4006, "用户id不能为空");
				return result;
			}
			UserVo user = new UserVo();
			user.setId(id);
			user.setStatus(status);
			Boolean flag = this.userService.edit(user);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员");
		}
		return result;
	}
	

	
}
