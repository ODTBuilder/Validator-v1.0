package com.git.opengds.file.dxf.persistence;

import java.util.HashMap;

import org.postgresql.util.PSQLException;

public interface QA10LayerCollectionDAO {
	
	public void createQA10LayerTb(HashMap<String, Object> qa20Layertb) throws PSQLException;
	
	public void insertQA10Layer(HashMap<String, Object> qa10Layer);

}
