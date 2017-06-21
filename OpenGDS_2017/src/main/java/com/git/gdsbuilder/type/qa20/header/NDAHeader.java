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
 * NDAHeader 정보를 담고있는 클래스
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:37:50
 * */
public class NDAHeader {

	private String version;
	private List<NDAField> aspatial_field_def;

	
	/**
	 * NDAHeader 생성자
	 */
	public NDAHeader() {

	}
	
	/**
	 * NDAHeader 생성자
	 * @param version
	 * @param aspatial_field_def
	 */
	public NDAHeader(String version, List<NDAField> aspatial_field_def) {
		super();
		this.version = version;
		this.aspatial_field_def = aspatial_field_def;
	}

	/**
	 * version getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:38:03
	 * @return String
	 * @throws
	 * */
	public String getVersion() {
		return version;
	}

	/**
	 * version setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:38:06
	 * @param version void
	 * @throws
	 * */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * aspatial_field_def getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:38:08
	 * @return List<NGIField>
	 * @throws
	 * */
	public List<NDAField> getAspatial_field_def() {
		return aspatial_field_def;
	}

	/**
	 * aspatial_field_def setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:38:09
	 * @param aspatial_field_def void
	 * @throws
	 * */
	public void setAspatial_field_def(List<NDAField> aspatial_field_def) {
		this.aspatial_field_def = aspatial_field_def;
	}

	/**
	 * aspatial_field_def에 field를 더함
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:38:13
	 * @param field void
	 * @throws
	 * */
	public void addField(NDAField field) {
		aspatial_field_def.add(field);
	}

	/**
	 * aspatial_field_def에 포함된 field의 Name을 가져옴 
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:38:14
	 * @return List<String>
	 * @throws
	 * */
	public List<String> getFieldNames() {
		List<String> fieldNames = new ArrayList<String>();
		for (NDAField field : this.aspatial_field_def) {
			fieldNames.add(field.getFieldName());
		}
		return fieldNames;
	}
}
