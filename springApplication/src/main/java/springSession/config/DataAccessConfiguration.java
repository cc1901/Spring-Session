package springSession.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import springSession.springBeans.PrototypeBean;
import springSession.springBeans.SingletonBeanApplicationContextAware;
import springSession.springBeans.SingletonBeanMethodLookUp;

@Configuration
public class DataAccessConfiguration {
    @Bean
    @Scope("prototype")
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }

    @Bean
    @Scope
    public SingletonBeanApplicationContextAware singletonBean() {
        return new SingletonBeanApplicationContextAware();
    }

    @Bean
    @Scope
    public SingletonBeanMethodLookUp singletonBeanMethodLookUp() {
        return new SingletonBeanMethodLookUp(){

         protected PrototypeBean createPrototypeBean(){
            return new PrototypeBean();
         }
      };
    }
}
