package springBeans;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SingletonBean implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private PrototypeBean prototypeBean;

    public PrototypeBean process(String state) {
        prototypeBean = createPrototypeBean();
        prototypeBean.setState(state);
        return prototypeBean;
    }

    protected PrototypeBean createPrototypeBean() {
        try {
            return applicationContext.getBean("prototypeBean", PrototypeBean.class);
        } catch (BeansException e) {
            return null;
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
