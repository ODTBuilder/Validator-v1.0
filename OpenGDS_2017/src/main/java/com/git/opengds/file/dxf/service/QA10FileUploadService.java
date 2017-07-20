package com.git.opengds.file.dxf.service;

import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.user.domain.UserVO;

public interface QA10FileUploadService {

	public FileMeta dxfUpload(UserVO userVO, FileMeta fileMeta) throws Exception;

}
