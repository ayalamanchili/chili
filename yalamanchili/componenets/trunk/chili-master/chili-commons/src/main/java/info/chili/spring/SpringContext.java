/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author ayalamanchili
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static Object getBean(Class beanCls) {
        return context.getBean(beanCls.getSimpleName().substring(0, 1).toLowerCase() + beanCls.getSimpleName().substring(1));
    }
}
