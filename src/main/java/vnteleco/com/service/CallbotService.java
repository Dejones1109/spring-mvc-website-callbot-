package vnteleco.com.service;

import java.util.List;

import vnteleco.com.entity.Callbot;

public interface CallbotService {
	public Callbot findCallbotById(int callbotId);
	public List<Callbot> findAllCallbot();
	public List<Callbot> findCallbotByCategoryId(int categoryId);
}
