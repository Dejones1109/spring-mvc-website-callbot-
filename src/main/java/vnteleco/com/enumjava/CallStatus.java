package vnteleco.com.enumjava;

import java.util.HashMap;
import java.util.Map;

public enum CallStatus {
	
	CREATED(0,"Vừa tạo"),
	SUCCESS(100, "Thành công"),
	HANG_UP(101," Khách hàng cúp máy giữa chừng"),
	TELEPHONIST(102,"Chuyển sang cho ĐTV"),
	MISSED(103,"Cuộc gọi nhỡ"),
	BUSY(104,"Cuộc gọi thuê bao"),
	SYSTEM_ERROR(105," Kết thúc do lỗi hệ thống");

    private final int key;
    private final String value;

    CallStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }
    
    public static final Map<Integer, String> CALL_STATUS_MAP = new HashMap<Integer, String>();
    static {    	 
    	 for (CallStatus s : CallStatus.values()) {
    		 CALL_STATUS_MAP.put(s.getKey(), s.getValue());
    	 }
    }
	public int getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}

    

}
