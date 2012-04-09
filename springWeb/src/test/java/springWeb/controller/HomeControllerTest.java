package springWeb.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import springWeb.beans.StateBean;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HomeControllerTest {
    @Test
    public void should_inject_dependency() {
        Injector injector = Guice.createInjector(new TestConfigure());
        HomeController homeController = injector.getInstance(HomeController.class);
        homeController.index(new ModelMap(), new MockHttpServletRequest(), new MockHttpServletResponse());
    }
}

class TestConfigure extends AbstractModule {
    @Override
    protected void configure() {
        StateBean stateBean = mock(StateBean.class);
        when(stateBean.getState()).thenReturn("test");
        bind(StateBean.class).toInstance(stateBean);
        bind(StateBean.class).toInstance(stateBean);
    }
}
