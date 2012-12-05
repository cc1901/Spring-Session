package springWeb.util;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

public class Client {
    public ApacheHttpClient createRESTFulClient() {
        ClientConfig clientConfig = new DefaultApacheHttpClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        return ApacheHttpClient.create(clientConfig);
    }
}
