package vnteleco.com.api.call;


import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnteleco.com.api.BizAbstractCommunication;
import vnteleco.com.api.request.BizCalloutRequest;
import vnteleco.com.api.request.BizCreateCallRequest;
import vnteleco.com.api.response.BizCalloutResponse;
import vnteleco.com.api.response.BizCreateCallResponse;
import vnteleco.com.api.response.BizGetDetailCallResponse;
import vnteleco.com.api.response.BizGetInputSlotsResponse;
import vnteleco.com.exeption.BizException;

@Component
@PropertySource("classpath:config.properties")
public class BizCallCommunication extends BizAbstractCommunication {
    
	@Value("${service.endpoint.call}")
    private String baseUrl;

    private BizCallCommunicate bizCommunicate;

    @PostConstruct
    public void intConnection() {
        this.bizCommunicate = buildSetting();
    }

    private BizCallCommunicate buildSetting() {
    	
    	 Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(baseUrl)
                 .addConverterFactory(GsonConverterFactory.create(dateFormaGson))
                 .client(this.buildCommunication())
                 .build();

         return retrofit.create(BizCallCommunicate.class);

    }
    
    public BizGetInputSlotsResponse getInputSlots(int callbotId, String token) throws BizException {
    	try {
            Call<BizGetInputSlotsResponse> callSync = bizCommunicate.getInputSlots(callbotId, token);

            Response<BizGetInputSlotsResponse> response = callSync.execute();
            BizGetInputSlotsResponse obj = null;
            if (response.isSuccessful()) {
            	obj = response.body();
			}else {
				ResponseBody responseBody = response.errorBody();
				
				Gson g = new Gson();
				obj = g.fromJson(response.errorBody().string(), BizGetInputSlotsResponse.class);
			}
            
            System.out.println("BizGetInputSlotsResponseDto = " + obj.toString() );
            return obj;
        } catch (IOException e) {
            throw new BizException("Get all failed");
        }
    }
    
    public BizCreateCallResponse createCall(String token, BizCreateCallRequest bizCreateCallRequest) throws BizException {
    	try {
            Call<BizCreateCallResponse> callSync = bizCommunicate.createCall(token, bizCreateCallRequest);

            Response<BizCreateCallResponse> response = callSync.execute();
            BizCreateCallResponse obj = null;
            if (response.isSuccessful()) {
            	obj = response.body();
			}else {
				ResponseBody responseBody = response.errorBody();
				
				Gson g = new Gson();
				obj = g.fromJson(response.errorBody().string(), BizCreateCallResponse.class);
			}
            
            System.out.println("BizCreateCallResponseDto = " + obj.toString() );
            return obj;
        } catch (IOException e) {
            throw new BizException("Get all failed");
        }
    }

    public BizCalloutResponse callout(String token, BizCalloutRequest bizCalloutRequest) throws BizException {
    	try {
            Call<BizCalloutResponse> callSync = bizCommunicate.callout(token, bizCalloutRequest);

            Response<BizCalloutResponse> response = callSync.execute();
            BizCalloutResponse obj = null;
            if (response.isSuccessful()) {
            	obj = response.body();
			}else {
				ResponseBody responseBody = response.errorBody();
				
				Gson g = new Gson();
				obj = g.fromJson(response.errorBody().string(), BizCalloutResponse.class);
			}
            
            System.out.println("BizCalloutResponseDto = " + obj.toString() );
            return obj;
        } catch (IOException e) {
            throw new BizException("Get all failed");
        }
    }
    
    public BizGetDetailCallResponse getConversation(String token, String conversationId) throws BizException {
    	try {
            Call<BizGetDetailCallResponse> callSync = bizCommunicate.getConversation(token, conversationId);

            Response<BizGetDetailCallResponse> response = callSync.execute();
            BizGetDetailCallResponse obj = null;
            if (response.isSuccessful()) {
            	obj = response.body();
			}else {
				ResponseBody responseBody = response.errorBody();
				
				Gson g = new Gson();
				obj = g.fromJson(response.errorBody().string(), BizGetDetailCallResponse.class);
			}
            
            System.out.println("BizGetDetailCallResponseDto = " + obj.toString() );
            return obj;
        } catch (IOException e) {
            throw new BizException("Get all failed");
        }
    }
}
