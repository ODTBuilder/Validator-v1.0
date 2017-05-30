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

package com.git.gdsbuilder.type.qa20.header;

import java.util.ArrayList;
import java.util.List;

/** 
 * NGIHeader 정보를 담고있는 클래스
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:37:50
 * */
public class NGIHeader {
	
	private String version;
	private String geometric_metadata;
	private String dim;
	private String bound;
	private List<String> point_represent;
	private List<String> line_represent;
	private List<String> region_represent;
	private List<String> text_represent;
	
	/**
	 * NGIHeader 생성자
	 */
	public NGIHeader() {
		this.version = "";
		this.geometric_metadata = "";
		this.dim = "";
		this.bound = "";
		this.point_represent = new ArrayList<String>();
		this.line_represent = new ArrayList<String>();
		this.region_represent = new ArrayList<String>();
		this.text_represent = new ArrayList<String>();
	}
	
	/**
	 * NGIHeader 생성자
	 * @param version
	 * @param geometric_metadata
	 * @param dim
	 * @param bound
	 * @param point_represent
	 * @param line_represent
	 * @param region_represent
	 * @param text_represent
	 */
	public NGIHeader(String version, String geometric_metadata, String dim, String bound, List<String> point_represent, List<String> line_represent,
			List<String> region_represent, List<String> text_represent) {
		super();
		this.version = version;
		this.geometric_metadata = geometric_metadata;
		this.dim = dim;
		this.bound = bound;
		this.point_represent = point_represent;
		this.line_represent = line_represent;
		this.region_represent = region_represent;
		this.text_represent = text_represent;
	}

	/**
	 * version getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:12
	 * @return String
	 * @throws
	 * */
	public String getVersion() {
		return version;
	}

	/**
	 * version setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:14
	 * @param version void
	 * @throws
	 * */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * geometric_metadata getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:16
	 * @return String
	 * @throws
	 * */
	public String getGeometric_metadata() {
		return geometric_metadata;
	}

	/**
	 * geometric_metadata setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:17
	 * @param geometric_metadata void
	 * @throws
	 * */
	public void setGeometric_metadata(String geometric_metadata) {
		this.geometric_metadata = geometric_metadata;
	}

	/**
	 * dim getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:18
	 * @return String
	 * @throws
	 * */
	public String getDim() {
		return dim;
	}

	/**
	 * dim setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:20
	 * @param dim void
	 * @throws
	 * */
	public void setDim(String dim) {
		this.dim = dim;
	}

	/**
	 * bound getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:21
	 * @return String
	 * @throws
	 * */
	public String getBound() {
		return bound;
	}

	/**
	 * bound setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:23
	 * @param bound void
	 * @throws
	 * */
	public void setBound(String bound) {
		this.bound = bound;
	}

	/**
	 * point_represent getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:25
	 * @return List<String>
	 * @throws
	 * */
	public List<String> getPoint_represent() {
		return point_represent;
	}

	/**
	 * point_represent setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:26
	 * @param point_represent void
	 * @throws
	 * */
	public void setPoint_represent(List<String> point_represent) {
		this.point_represent = point_represent;
	}

	/**
	 * line_represent getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:28
	 * @return List<String>
	 * @throws
	 * */
	public List<String> getLine_represent() {
		return line_represent;
	}

	/**
	 * line_represent setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:32
	 * @param line_represent void
	 * @throws
	 * */
	public void setLine_represent(List<String> line_represent) {
		this.line_represent = line_represent;
	}

	/**
	 * region_represent getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:36
	 * @return List<String>
	 * @throws
	 * */
	public List<String> getRegion_represent() {
		return region_represent;
	}

	/**
	 * region_represent setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:37
	 * @param region_represent void
	 * @throws
	 * */
	public void setRegion_represent(List<String> region_represent) {
		this.region_represent = region_represent;
	}

	/**
	 * text_represent getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:40
	 * @return List<String>
	 * @throws
	 * */
	public List<String> getText_represent() {
		return text_represent;
	}

	/**
	 * text_represent setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:47:42
	 * @param text_represent void
	 * @throws
	 * */
	public void setText_represent(List<String> text_represent) {
		this.text_represent = text_represent;
	}

}
