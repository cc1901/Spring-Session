package springSession.springBeans;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SingletonBeanMethodReplacementTest {
    @Test
    public void should_get_prototype_bean_by_replace_method(){
       ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        SingletonBeanMethodReplacement singletonBean = applicationContext.getBean("beanMethodReplacement", SingletonBeanMethodReplacement.class);
        String state = "string";
        PrototypeBean prototype = singletonBean.process(state);

        assertThat(prototype.getState(), is(state));
    }
}
