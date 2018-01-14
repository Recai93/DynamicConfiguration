package com.trendyol.form.service;

import java.util.List;

import model.ConfRecord;

public interface ConfRecordService {

	ConfRecord findById(Integer id);
	
	List<ConfRecord> findAll();

	void saveOrUpdate(ConfRecord confRecord);
	
	void delete(Integer id);

	boolean isExist(String name);
}