package springSession.springBeans;

import springSession.config.DataAccessConfiguration;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SingletonBeanApplicationContextAwareTest {
    @Test
    public void should_set_prototype_bean() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        SingletonBeanApplicationContextAware singletonBean = applicationContext.getBean("singletonBeanAware", SingletonBeanApplicationContextAware.class);

        String state = "state";
        PrototypeBean prototypeBean = singletonBean.process(state);

        assertThat(prototypeBean.getState(), is(state));
    }
    
    @Test
    public void should_get_prototype_bean_from_config_class() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataAccessConfiguration.class);
        SingletonBeanApplicationContextAware singletonBean = context.getBean("singletonBean", SingletonBeanApplicationContextAware.class);

        String state = "state";
        PrototypeBean prototypeBean = singletonBean.process(state);

        assertThat(prototypeBean.getState(), is(state));
    }
}
