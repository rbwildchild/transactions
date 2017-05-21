
package com.rfa.n26.transactions.config;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.logging.LogManager;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringWebContainerInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        registerContextLoaderListener(servletContext);

        servletContext.setInitParameter("contextConfigLocation", "");
    }
    
    private void registerContextLoaderListener(ServletContext servletContext) {
        WebApplicationContext webContext;
        webContext = createWebAplicationContext(SpringAppConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(webContext));
    }
    
    public WebApplicationContext createWebAplicationContext(Class ...configClasses) {
        AnnotationConfigWebApplicationContext context;
        context = new AnnotationConfigWebApplicationContext();
        context.getEnvironment().setActiveProfiles("local");
        context.register(configClasses);
//        context.scan(configClasses.getPackage().getName());
        return context;
    }
}
