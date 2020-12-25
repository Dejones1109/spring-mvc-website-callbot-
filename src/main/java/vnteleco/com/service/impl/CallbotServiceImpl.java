package vnteleco.com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vnteleco.com.entity.Callbot;
import vnteleco.com.responsity.CallbotRepository;
import vnteleco.com.service.CallbotService;

@Service
@Transactional
public class CallbotServiceImpl implements CallbotService {
	
	@Autowired
	CallbotRepository callbotRepository;

	@Override
	public Callbot findCallbotById(int callbotId) {
		// TODO Auto-generated method stub
		return callbotRepository.findCallbotById(callbotId);
	}

	@Override
	public List<Callbot> findAllCallbot() {
		// TODO Auto-generated method stub
		return callbotRepository.findAllCallbot();
	}

	@Override
	public List<Callbot> findCallbotByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return callbotRepository.findCallbotByCategoryId(categoryId);
	}
	


}
