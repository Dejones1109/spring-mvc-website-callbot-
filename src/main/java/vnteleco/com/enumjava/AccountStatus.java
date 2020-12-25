package vnteleco.com.enumjava;

import java.util.HashMap;
import java.util.Map;

public enum AccountStatus {
	
	DISABLE(0,"Vô hiệu hóa"),
	ENABLE(1,"Kích hoạt");
	

    private final int key;
    private final String value;

    AccountStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }
    
    public static final Map<Integer, String> ACCOUNT_STATUS_MAP = new HashMap<Integer, String>();
    static {    	 
    	 for (AccountStatus s : AccountStatus.values()) {
    		 ACCOUNT_STATUS_MAP.put(s.getKey(), s.getValue());
    	 }
    }
	public int getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}

    

}
