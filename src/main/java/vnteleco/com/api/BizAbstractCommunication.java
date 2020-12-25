package vnteleco.com.api;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Component
@PropertySource("classpath:config.properties")
public class BizAbstractCommunication {
	
	@Value("${service.max_request}")
    private int maxRequest = 100;
	@Value("${service.timeout}")
	private long timeOut = 60;
	@Value("${service.readout}")
	private long readOut = 120;

    protected Gson dateFormaGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    protected OkHttpClient buildCommunication() {
    	    	
    	 Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(8));
         dispatcher.setMaxRequests(maxRequest);
         dispatcher.setMaxRequestsPerHost(1);

         HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
         logger.setLevel(HttpLoggingInterceptor.Level.BODY);

         OkHttpClient okHttpClient = new OkHttpClient.Builder()
                 .dispatcher(dispatcher)
                 .addInterceptor(logger)
                 .connectTimeout(timeOut, TimeUnit.SECONDS)
                 .readTimeout(readOut, TimeUnit.SECONDS)
                 .connectionPool(new ConnectionPool(100, 60, TimeUnit.SECONDS)).build();

         return okHttpClient;
       
        
    }
}
