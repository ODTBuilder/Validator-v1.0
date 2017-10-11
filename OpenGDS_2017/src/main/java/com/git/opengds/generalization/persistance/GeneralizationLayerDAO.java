package com.git.opengds.generalization.persistance;

import java.util.HashMap;

import com.git.opengds.user.domain.UserVO;

public interface GeneralizationLayerDAO {

	public void createGenLayerTb(UserVO userVO, HashMap<String, Object> createTbQuery);

	public void insertGenFeature(UserVO userVO, HashMap<String, Object> insertQuery);

}
