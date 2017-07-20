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

package com.git.opengds.geoserver.persistence;

import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

/**
 * 파일에 대한 DB처리를 하는 클래스
 * @author SG.Lee
 * @Date 2017. 5. 12. 오전 2:24:03
 * */
@Repository
public class GeoserverDAOImpl extends DataSourceFactory implements GeoserverDAO {
	
	private SqlSession sqlSession;
	
/*	public GeoserverDAOImpl(UserVO user) {
		// TODO Auto-generated constructor stub
		sqlSession = super.getSqlSession(user.getId());
	}*/

	private static final String namespace = "com.git.mappers.geolayerMappers.GeolayerMapper";

	/**
	 * @since 2017. 6. 25.
	 * @author SG.Lee
	 * @param infoMap
	 * @return
	 * @see com.git.opengds.geoserver.persistence.GeoserverDAO#selectEditLayerDuplicateCheck(java.util.HashMap)
	 */
	@Override
	public boolean selectEditLayerDuplicateCheck(UserVO userVO, Map<String,Object> infoMap){
		sqlSession = super.getSqlSession(userVO.getId());
		int duplicateNums = 0;
		try{
			duplicateNums = sqlSession.selectOne(namespace + ".selectEditLayerDuplicateCheck", infoMap);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		boolean dupFlag = true;
		
		if(duplicateNums>0){
			dupFlag = true;
		}
		else{
			dupFlag = false;
		}
		return dupFlag;
	}
}
