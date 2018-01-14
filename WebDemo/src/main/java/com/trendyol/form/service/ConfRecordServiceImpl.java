package com.trendyol.form.service;

import model.ConfRecord;
import org.springframework.stereotype.Service;
import service.WebDemoService;

import java.util.List;

@Service("confRecordService")
public class ConfRecordServiceImpl implements ConfRecordService {

    private WebDemoService webDemoService = new WebDemoService("mongodb://admin:admin@ds247357.mlab.com:47357/confdb");

    public ConfRecord findById(Integer id) {
        return webDemoService.findById(id);
    }

    public List<ConfRecord> findAll() {
        return webDemoService.findAll();
    }

    @Override
    public List<ConfRecord> search(String searchText) {
        return webDemoService.search(searchText);
    }

    public void delete(Integer id) {
        webDemoService.delete(id);
    }

    public boolean isExist(String name){
        return webDemoService.isExist(name);
    }

    public void saveOrUpdate(ConfRecord confRecord) {

		if (findById(confRecord.getId())==null) {
			webDemoService.save(confRecord);
		} else {
            webDemoService.update(confRecord);
		}
    }
}