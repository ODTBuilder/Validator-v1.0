package com.git.opengds.validator.service;

import org.springframework.stereotype.Service;

@Service
public class ValidatorProgressServiceImpl implements ValidatorProgressService {
	
	public boolean setStateToRequest(String collectionName, String fileType, String requestTime) {
		
		return true;
	}
	
	
	public boolean setStateToProgressing(String collectionName, String fileType) {
		
		return true;
	}
}
