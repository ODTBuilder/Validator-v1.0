package com.git.opengds;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.git.opengds.common.AbstractController;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.user.domain.UserVO.EnUserType;


@Controller
public class MainController extends AbstractController{

	
	@SuppressWarnings("unused")
	@RequestMapping(value="/main.do")
	public ModelAndView loginView(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		UserVO generalUser  = (UserVO) getSession(request,EnUserType.GENERAL.getTypeName());
		
		mav.setViewName("/main/main");
		mav.addObject("user", generalUser);
			
		return mav;
	}
}
