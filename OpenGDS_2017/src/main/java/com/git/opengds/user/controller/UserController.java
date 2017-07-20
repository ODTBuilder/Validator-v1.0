package com.git.opengds.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.git.opengds.common.AbstractController;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.user.domain.UserVO.EnUserType;
import com.git.opengds.user.service.UserService;

@Controller("loginController")
@RequestMapping("/user")
public class UserController extends AbstractController{
	@Autowired
	private UserService userService;

//	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/loginView.do")
	public ModelAndView loginView(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		UserVO generalUser  = (UserVO) getSession(request,EnUserType.GENERAL.getTypeName());
		
		
		//세션이 없을경우 login 페이지호출
		if(generalUser==null){
			mav.setViewName("/login/login");
			mav.addObject("user", generalUser);
		}
		else{
			mav.setViewName("redirect:/main.do");
		}
		return mav;
	}
	
	
	/**
	 * 로그인 요청을 처리한다.
	 * @author SG.Lee
	 * @data 2017.07
	 * @param request - 클라이언트의 요청과 관련된 정보와 동작을 가지고 있는 객체
	 * @param id - 사용자 아이디
	 * @param pw - 사용자 비밀번호
	 * @param loginType - 사용자 유형
	 * @return JSONObject 
	 */
	@RequestMapping(value = "/login.ajax")
	@ResponseBody
	public Map<String, Object> userLogin(HttpServletRequest request,@RequestBody JSONObject jsonObject) {
		// returnMap - <flag, loginType>
		Map<String, Object> returnMap = new HashMap<String, Object>();
		UserVO user = null;

		String id ="";
		String pw ="";
		
		id = (String) jsonObject.get("id");
		pw = (String) jsonObject.get("pw");
		
		if(!id.isEmpty()&&!pw.isEmpty()&&!id.equals("")&&!pw.equals("")){
			HashMap<String, Object> infoMap = new HashMap<String, Object>();
			infoMap.put("id", id);
			infoMap.put("pw", pw);
			
			
	
			user = userService.loginUserByInfo(infoMap);
	
			if (user != null) {
					setSession(request, EnUserType.GENERAL.getTypeName(), user);
			}
		}
		returnMap.put("user", user);
		return returnMap;
	}
	
	/**
	 * 로그아웃 요청을 처리한다.
	 * @author SG.Lee
	 * @data 2017.07
	 * @param request - 클라이언트의 요청과 관련된 정보와 동작을 가지고 있는 객체
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/logout.do")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		UserVO user = (UserVO) getSession(request, EnUserType.GENERAL.getTypeName());

		if (user != null) {
			removeSession(request, EnUserType.GENERAL.getTypeName());
		}

		mav.setViewName("redirect:/main.do");
		return mav;
	}
}
