package vnteleco.com.api.call;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vnteleco.com.api.request.BizCalloutRequest;
import vnteleco.com.api.request.BizCreateCallRequest;
import vnteleco.com.api.response.BizCalloutResponse;
import vnteleco.com.api.response.BizCreateCallResponse;
import vnteleco.com.api.response.BizGetDetailCallResponse;
import vnteleco.com.api.response.BizGetInputSlotsResponse;


public interface BizCallCommunicate {

    @GET("inputSlots")
    Call<BizGetInputSlotsResponse> getInputSlots(@Query("callbot_id") int callbotId, @Header("Authorization") String token);
  
    @POST("create")
    Call<BizCreateCallResponse> createCall(@Header("Authorization") String token, @Body BizCreateCallRequest bizCreateCallRequest);
    
    @POST("callout")
    Call<BizCalloutResponse> callout(@Header("Authorization") String token, @Body BizCalloutRequest bizCalloutRequest);
    
    @GET("getConversation")
    Call<BizGetDetailCallResponse> getConversation(@Header("Authorization") String token, @Query("conversation_id") String conversationId);
}
