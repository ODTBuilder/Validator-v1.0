/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.opengds.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;



/**
 * Session을 관리한다.
 * @author SG.Lee
 * @Date 2016.02
 * */
@Controller
public class AbstractController {
	/**
	 * 세션 저장
	 * 
	 * @param request
	 * @param sessionName
	 *            : 세션 이름(EnSessionName에 정의)
	 * @param object
	 *            : 저장할 객체
	 */
	public void setSession(HttpServletRequest request, String sessionName,
			Object object) {
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute(sessionName, object);
	}

	/**
	 * 세션 로드
	 * 
	 * @param request
	 * @param sessionName
	 *            : 불러올 세션 이름(EnSessionName에 정의)
	 * @return
	 */
	public Object getSession(HttpServletRequest request, String sessionName) {
		HttpSession httpSession = request.getSession();
		return httpSession.getAttribute(sessionName);
	}

	/**
	 * 세션 삭제
	 * 
	 * @param request
	 * @param sessionName
	 *            : 삭제할 세션 이름(EnSessionName에 정의)
	 */
	public void removeSession(HttpServletRequest request, String sessionName) {
		HttpSession httpSession = request.getSession();
		httpSession.removeAttribute(sessionName);
	}

	/**
	 * 세션 변경
	 * 
	 * @param request
	 * @param sessionName
	 */
	public void updateSession(HttpServletRequest request, String sessionName,
			Object object) {
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute(sessionName, object);
	}
}
