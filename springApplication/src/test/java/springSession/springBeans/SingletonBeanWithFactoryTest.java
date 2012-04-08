package springSession.springBeans;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SingletonBeanWithFactoryTest {
    @Test
    public void should_get_prototype_bean_by_factory(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        SingletonBeanWithFactory singletonBean = applicationContext.getBean("singletonBeanWithFactory", SingletonBeanWithFactory.class);
        String state = "string";
        PrototypeBean prototype = singletonBean.process(state);

        assertThat(prototype.getState(), is(state));
    }
}
