package com.git.opengds.editor;

import java.util.HashMap;
import java.util.Map;

/**
 * EditDTLayerCondition 정보를 담고 있는 클래스. 레이어 편집 요청 성공/실패 상태 저장
 * 
 * @author GIT
 *
 */
public class EditDTLayerCondition {

	/**
	 * 레이어 편집 요청 성공 레이어 목록
	 */
	Map<String, Object> successedLayers;
	/**
	 * 레이어 편집 요청 실패 레이어 목록
	 */
	Map<String, Object> failedLayers;

	/**
	 * EditDTLayerCondition 생성자
	 */
	public EditDTLayerCondition() {
		successedLayers = new HashMap<String, Object>();
		failedLayers = new HashMap<String, Object>();
	}

	/**
	 * 레이어 편집 요청 성공 레이어 추가
	 * 
	 * @param collectionName
	 *            layer collection명
	 * @param layerName
	 *            layer명
	 */
	public void putSuccessedLayers(String collectionName, String layerName) {
		successedLayers.put(collectionName, layerName);
	}

	/**
	 * 레이어 편집 요청 실패 레이어 추가
	 * 
	 * @param collectionName
	 *            layer collection명
	 * @param layerName
	 *            layer명
	 */
	public void putFailedLayers(String collectionName, String layerName) {
		failedLayers.put(collectionName, layerName);
	}

	/**
	 * 레이어 편집 요청 성공 레이어 목록 반환
	 * 
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getSuccessedLayers() {
		return successedLayers;
	}

	/**
	 * 레이어 편집 요청 성공 레이어 목록 설정
	 * 
	 * @param successedLayers
	 *            레이어 편집 요청 성공 레이어 목록
	 */
	public void setSuccessedLayers(Map<String, Object> successedLayers) {
		this.successedLayers = successedLayers;
	}

	/**
	 * 레이어 편집 요청 실패 레이어 목록 반환
	 * 
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getFailedLayer() {
		return failedLayers;
	}

	/**
	 * 레이어 편집 요청 실패 레이어 목록 설정
	 * 
	 * @param failedLayer
	 *            레이어 편집 요청 실패 레이어 목록
	 */
	public void setFailedLayer(Map<String, Object> failedLayer) {
		this.failedLayers = failedLayer;
	}

}
