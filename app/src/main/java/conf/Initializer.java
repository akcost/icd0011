package conf;

import conf.security.SecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {SecurityConfig.class, Config.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {  };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/*"};
    }
}