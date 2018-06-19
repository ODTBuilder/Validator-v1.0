package com.git.gdsbuilder.type.validate.result;

/**
 * ValidateProgress 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 *
 */
public class ValidateProgress {

	/**
	 * 검수 진행 결과 Table Index
	 */
	int pIdx;
	/**
	 * 검수 대상 파일명
	 */
	String collectionName;
	/**
	 * 검수 대상 파일 타입
	 */
	String fileType;
	/**
	 * 검수 진행 상태
	 */
	int state;
	/**
	 * 검수 요청 시간
	 */
	String requestTime;
	/**
	 * 검수 완료 시간
	 */
	String responseTime;
	/**
	 * 오류 레이어명
	 */
	String errLayerName;

	/**
	 * ValidateProgress 생성자
	 */
	public ValidateProgress() {

	}

	/**
	 * ValidateProgress 생성자
	 * 
	 * @param pIdx
	 *            검수 진행 결과 Table Index
	 * @param collectionName
	 *            검수 대상 파일명
	 * @param fileType
	 *            검수 대상 파일 타입
	 * @param state
	 *            검수 진행 상태
	 * @param requestTime
	 *            검수 요청 시간
	 * @param responseTime
	 *            검수 완료 시간
	 * @param errLayerName
	 *            오류 레이어명
	 */
	public ValidateProgress(int pIdx, String collectionName, String fileType, int state, String requestTime,
			String responseTime, String errLayerName) {
		super();
		this.pIdx = pIdx;
		this.collectionName = collectionName;
		this.fileType = fileType;
		this.state = state;
		this.requestTime = requestTime;
		this.responseTime = responseTime;
		this.errLayerName = errLayerName;
	}

	/**
	 * 검수 진행 결과 Table Index 반환
	 * 
	 * @return int
	 */
	public int getpIdx() {
		return pIdx;
	}

	/**
	 * 검수 진행 결과 Table Index 설정
	 * 
	 * @param pIdx
	 */
	public void setpIdx(int pIdx) {
		this.pIdx = pIdx;
	}

	/**
	 * 검수 대상 파일명 반환
	 * 
	 * @return String
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * 검수 대상 파일명 설정
	 * 
	 * @param collectionName
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * 검수 대상 파일 타입 반환
	 * 
	 * @return String
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * 검수 대상 파일 타입 설정
	 * 
	 * @param fileType
	 *            검수 대상 파일 타입
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * 검수 진행 상태 반환
	 * 
	 * @return int
	 */
	public int getState() {
		return state;
	}

	/**
	 * 검수 진행 상태 설정
	 * 
	 * @param state
	 *            검수 진행 상태
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * 검수 요청 시간 반환
	 * 
	 * @return String
	 */
	public String getRequestTime() {
		return requestTime;
	}

	/**
	 * 검수 요청 시간 설정
	 * 
	 * @param requestTime
	 *            검수 요청 시간
	 */
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	/**
	 * 검수 완료 시간 반환
	 * 
	 * @return String
	 */
	public String getResponseTime() {
		return responseTime;
	}

	/**
	 * 검수 완료 시간 설정
	 * 
	 * @param responseTime
	 *            검수 완료 시간
	 */
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	/**
	 * 오류 레이어명 반환
	 * 
	 * @return String
	 */
	public String getErrLayerName() {
		return errLayerName;
	}

	/**
	 * 오류 레이어명 설정
	 * 
	 * @param errLayerName
	 *            오류 레이어명
	 */
	public void setErrLayerName(String errLayerName) {
		this.errLayerName = errLayerName;
	}

}
