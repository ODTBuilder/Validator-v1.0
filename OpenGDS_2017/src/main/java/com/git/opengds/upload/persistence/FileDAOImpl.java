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

package com.git.opengds.upload.persistence;

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
public class FileDAOImpl extends DataSourceFactory implements FileDAO {
	private SqlSession sqlSession;

	private static final String namespace = "com.git.mappers.fileMappers.FileMapper";

/*	public FileDAOImpl(UserVO user) {
		// TODO Auto-generated constructor stub
		sqlSession = super.getSqlSession(user.getId());
	}*/
	
	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param fileName
	 * @return
	 * @see com.git.opengds.upload.persistence.FileDAO#selectDuplicateCheck(java.lang.String)
	 */
	@Override
	public boolean selectNGIDuplicateCheck(UserVO userVO, String fileName){
		sqlSession = super.getSqlSession(userVO.getId());
		int duplicateNums = 0;
		try{
			duplicateNums = sqlSession.selectOne(namespace + ".selectNGIDuplicateCheck", fileName);
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
	
	@Override
	public boolean selectDXFDuplicateCheck(UserVO userVO, String fileName){
		sqlSession = super.getSqlSession(userVO.getId());
		int duplicateNums = 0;
		try{
			duplicateNums = sqlSession.selectOne(namespace + ".selectDXFDuplicateCheck", fileName);
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
	
	@Override
	public boolean selectSHPDuplicateCheck(UserVO userVO, String fileName){
		sqlSession = super.getSqlSession(userVO.getId());
		int duplicateNums = 0;
		try{
			duplicateNums = sqlSession.selectOne(namespace + ".selectSHPDuplicateCheck", fileName);
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
