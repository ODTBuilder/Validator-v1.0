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

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * 파일에 대한 DB처리를 하는 클래스
 * @author SG.Lee
 * @Date 2017. 5. 12. 오전 2:24:03
 * */
@Repository

public class FileDAOImpl implements FileDAO {
	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.git.mappers.fileMappers.FileMapper";

	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param fileName
	 * @return
	 * @see com.git.opengds.upload.persistence.FileDAO#selectDuplicateCheck(java.lang.String)
	 */
	@Override
	public boolean selectNGIDuplicateCheck(String fileName){
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
	public boolean selectDXFDuplicateCheck(String fileName){
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
}
