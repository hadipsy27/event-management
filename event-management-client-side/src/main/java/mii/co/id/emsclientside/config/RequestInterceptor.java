package mii.co.id.emsclientside.config;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//</editor-fold>

public class RequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!request.getURI().getPath().equals("/api/login") && !request.getURI().getPath().equals("/api/master-status/2") 
                && !request.getURI().getPath().equals("/api/user/count") 
                && !request.getURI().getPath().equals("/api/event/count")
                && !request.getURI().getPath().equals("/api/user-event/count")
                && !request.getURI().getPath().equals("/api/event/top-ten")
                && !request.getURI().getPath().equals("/api/event")
                && !request.getURI().getPath().equals("/api/user")
                && !request.getURI().getPath().equals("/api/event/topic/1")
                ) {
            request.getHeaders().add("Authorization", "Basic " + auth.getCredentials());
        }

        ClientHttpResponse response = execution.execute(request, body);

        return response;
    }
}
