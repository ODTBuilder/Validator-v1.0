package com.git.gdsbuilder.validator.collection.opt;

import java.util.HashMap;
import java.util.List;

import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption.ValCollectionOptionType;

/**
 * 인접검수 항목
 * 
 * @author SG.Lee
 * @Date 2017. 7. 4. 오후 4:14:36
 */
public class ValCollectionOption extends HashMap<ValCollectionOptionType, Object> {

	private static final long serialVersionUID = 1L;

	public enum ValCollectionOptionType {
		ENTITYNONE("EntityNONE"), EDGEMATCHMISS("EdgeMatchMiss"), REFATTRIBUTEMISS("RefAttributeMiss");

		private String typeName;

		public String getTypeName() {
			return typeName;
		}

		ValCollectionOptionType(String typeName) {
			this.typeName = typeName;
		}
	}

	/**
	 * 인접 요소 오류 검수 항목 추가
	 * 
	 * @param flag
	 *            ENTITYNONE 검수 여부
	 */
	public void putEntityNoneOption(boolean flag) {
		super.put(ValCollectionOptionType.ENTITYNONE, flag);
	}

	/**
	 * 인접 요소 오류 검수 항목 추가
	 * 
	 * @param flag
	 *            EDGEMATCHMISS 검수 여부
	 */
	public void putEdgeMatchMissOption(boolean flag) {
		super.put(ValCollectionOptionType.EDGEMATCHMISS, flag);
	}

	/**
	 * 인접 객체 속성 오류 검수 항목 추가
	 * 
	 * @param colunms
	 *            인접 객체 속성 컬럼값
	 */
	public void putRefAttributeMissOption(List<String> colunms) {
		super.put(ValCollectionOptionType.REFATTRIBUTEMISS, colunms);
	}
}
