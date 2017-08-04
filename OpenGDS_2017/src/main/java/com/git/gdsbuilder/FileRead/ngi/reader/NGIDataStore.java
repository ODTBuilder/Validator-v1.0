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

package com.git.gdsbuilder.FileRead.ngi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * ngi/nda 파일을 각각 BufferedReader 객체로 변환하는 클래스
 * @author DY.Oh
 * @Date 2017. 3. 11. 오전 11:35:53
 * */
public class NGIDataStore {

	private BufferedReader ngiReader;
	private BufferedReader ndaReader;
	boolean isNDA = false;

	/**
	 * QA20DataStore 생성자
	 */
	public NGIDataStore() {
		super();
	}
	
	/**
	 * QA20DataStore 생성자
	 * @param ngiFile
	 * @param charset
	 * @param crs
	 * @throws IOException
	 */
	public NGIDataStore(File ngiFile, Charset charset, CoordinateReferenceSystem crs) throws IOException {

		// Linux
		final int endIndex = ngiFile.getPath().length() - 4;
		File ndaFile = new File(ngiFile.getPath().substring(0, endIndex) + ".nda");
		if (!ndaFile.exists()) {
			ndaFile = new File(ngiFile.getPath().substring(0, endIndex) + ".NDA");
		}

		if (!ndaFile.exists()) {
			System.out.println("NDA file does not exist!");
			this.ngiReader = new BufferedReader(new InputStreamReader(new FileInputStream(ngiFile), charset));
		} else {
			this.ngiReader = new BufferedReader(new InputStreamReader(new FileInputStream(ngiFile), charset));
			this.ndaReader = new BufferedReader(new InputStreamReader(new FileInputStream(ndaFile), charset));
			isNDA = true;
		}
	}

	/**
	 * ngiReader getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:37:28
	 * @return BufferedReader
	 * @throws
	 * */
	public BufferedReader getNgiReader() {
		return ngiReader;
	}

	/**
	 * ngiReader setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:37:30
	 * @param ngiReader void
	 * @throws
	 * */
	public void setNgiReader(BufferedReader ngiReader) {
		this.ngiReader = ngiReader;
	}

	/**
	 * ndaReader getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:37:32
	 * @return BufferedReader
	 * @throws
	 * */
	public BufferedReader getNdaReader() {
		return ndaReader;
	}

	/**
	 * ndaReader setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:37:41
	 * @param ndaReader void
	 * @throws
	 * */
	public void setNdaReader(BufferedReader ndaReader) {
		this.ndaReader = ndaReader;
	}

}
