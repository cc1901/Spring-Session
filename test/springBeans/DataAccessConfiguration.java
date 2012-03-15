package springBeans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import springBeans.PrototypeBean;
import springBeans.SingletonBean;
import springBeans.SingletonBeanMethodLookUp;

@Configuration
public class DataAccessConfiguration {
    @Bean
    @Scope("prototype")
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }

    @Bean
    @Scope
    public SingletonBean singletonBean() {
        return new SingletonBean();
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
