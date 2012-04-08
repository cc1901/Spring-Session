package springSession.springBeans;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SingletonBeanMethodLookUpTest {
    @Test
    public void should_method_look_up(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        SingletonBeanMethodLookUp singletonBean = applicationContext.getBean("singletonBean", SingletonBeanMethodLookUp.class);
        String state = "string";
        PrototypeBean prototype = singletonBean.process(state);

        assertThat(prototype.getState(), is(state));
    }
}
