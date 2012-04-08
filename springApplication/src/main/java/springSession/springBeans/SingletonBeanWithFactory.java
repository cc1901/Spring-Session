package springSession.springBeans;

public class SingletonBeanWithFactory {

    private PrototypeBeanFactory prototypeBeanFactory;

    public PrototypeBean process(String state) {
        PrototypeBean prototypeBean = createPrototypeBean();
        prototypeBean.setState(state);
        return prototypeBean;
    }

    protected PrototypeBean createPrototypeBean() {
        prototypeBeanFactory = new PrototypeBeanFactory();
        return prototypeBeanFactory.createPrototypeBean();
    }
}
