package vnteleco.com.service;

import java.util.List;

import vnteleco.com.entity.CallbotFields;

public interface CallbotFieldsService {
	public List<CallbotFields> findByCallbotId(int callbotId);
}
