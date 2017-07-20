package com.git.opengds.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.git.opengds.user.domain.UserVO;
import com.git.opengds.user.domain.UserVO.EnUserType;


/**
 * 세션에 대한 접근권한을 관리한다.
 *
 * @author SG.Lee
 * @Date 2016.02
 * */
public class LoginCheckFilter implements Filter{

	//로그인 세션 체크 예외 URL
	private final static String[] NOT_CHECK_LOGIN_SESSION = {
		"/main.do", "/layer/loadApprovalMap.do", "/generalization/loadGeneralizationMap.do"
	};
	private final static String[] CHECK_INSPECTOR_SESSION = {
		"/main.do", "/user/logout.do", "/layer/qaStart.do", "/receipt/approvalRequest.do", "/layer/loadApprovalMap.do", "/qa/downloadExcel.do"
	};
	//로그인 세션 체크 예외 URL
	private final static String[] CHECK_CHAIRMAN_SESSION = {
		"/main.do", "/user/logout.do", "/chairman/detailInfoView.do", "layer/loadApprovalMap.do"
	};
	
	
	@Override
	public void destroy() {
	}

	
	/**
	 * 해당 세션에 대한 접근권한을 체크한다.
	 * 
	 * @author SG.Lee
	 * @data 2016.02
	 * @param req
	 *            ServletRequest
	 * @param res
	 *            ServerResponse
	 * @param chain
	 *            ServerResponse
	 * @return 
	 * @throws IOException, ServletException
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(false);
		boolean isFlag = false;
		for(String str : NOT_CHECK_LOGIN_SESSION ) {
			if (request.getRequestURI().endsWith(str)) {
				isFlag = true;
			}
		}
		
		
		if (!isFlag) {
			if( session == null ) {
				//세션없을때 무조건 메인창 콜백
				RequestDispatcher dispatcher = req.getRequestDispatcher("/main.do");
				dispatcher.forward(req, res);
			} else {
				UserVO user = (UserVO) session.getAttribute(EnUserType.GENERAL.getTypeName());
				
				boolean isPerUrl = false; 
				if( user != null ) {
					for(String str : CHECK_INSPECTOR_SESSION ) {
						if (request.getRequestURI().endsWith(str)) {
							isPerUrl = true;
						}
					}
				}
				
				
				
				if( isPerUrl ) {
					chain.doFilter(req, res);
				} else {
					session.removeAttribute(EnUserType.GENERAL.getTypeName());
					RequestDispatcher dispatcher = req.getRequestDispatcher("/main.do");
					dispatcher.forward(req, res);
				}
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
