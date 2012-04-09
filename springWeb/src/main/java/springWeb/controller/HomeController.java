package springWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import springWeb.beans.StateBean;
import springWeb.service.HomeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Inject
    @Named
    private StateBean requestBean;

    @Inject
    private StateBean sessionBean;

    @Autowired
    private HomeService homeService;

    @RequestMapping("/test")
    public void index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++" + sessionBean.getState());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++" + requestBean.getState());
        requestBean.setState("new state");
        sessionBean.setState("new state");
    }
}
