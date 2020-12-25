package vnteleco.com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vnteleco.com.entity.CallbotFields;
import vnteleco.com.responsity.CallbotFieldsRepository;
import vnteleco.com.service.CallbotFieldsService;

@Service
@Transactional
public class CallbotFieldsServiceImpl implements CallbotFieldsService {
	
	@Autowired
	CallbotFieldsRepository callbotFieldsRepository;

	@Override
	public List<CallbotFields> findByCallbotId(int callbotId) {
		// TODO Auto-generated method stub
		return callbotFieldsRepository.findByCallbotId(callbotId);
	}
	


}
