package springSession.springBeans;

public abstract class SingletonBeanMethodLookUp {
    public PrototypeBean process(String state) {
        PrototypeBean prototypeBean = createPrototypeBean();
        prototypeBean.setState(state);
        return prototypeBean;
    }

    protected abstract PrototypeBean createPrototypeBean();

}
