package springSession.springBeans;

public class SingletonBeanMethodReplacement {
    public PrototypeBean process(String state) {
        PrototypeBean prototypeBean = createPrototypeBean();
        prototypeBean.setState(state);
        return prototypeBean;
    }

    public PrototypeBean createPrototypeBean() {
        return null;
    }
}
