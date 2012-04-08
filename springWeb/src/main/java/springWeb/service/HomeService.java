package springWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import springWeb.beans.StateBean;

public class HomeService {
    @Autowired
    private StateBean requestBean;

    @Autowired
    private StateBean sessionBean;

    public void getBeanState() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++" + requestBean.getState());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++" + sessionBean.getState());
    }
}
