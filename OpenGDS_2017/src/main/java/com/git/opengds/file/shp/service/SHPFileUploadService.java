package com.git.opengds.file.shp.service;

import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.user.domain.UserVO;

public interface SHPFileUploadService {

	public FileMeta shpUpload(UserVO userVO, FileMeta fileMeta) throws Exception, Throwable;
}
