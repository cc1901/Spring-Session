package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import springWeb.beans.StateBean;

@Configuration
public class ApplicationConfig {
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public StateBean requestBean() {
        StateBean stateBean = new StateBean();
        stateBean.setState("state");
        return stateBean;
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public StateBean sessionBean() {
        return new StateBean();
    }
}
