/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fet.carmichael.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import fet.carmichael.controller.GreetingController;

/**
 *
 * @author steven
 */
public class WebInit implements WebApplicationInitializer{

    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(new ContextLoaderListener(appContext()));
        servletContext.addServlet("h2Console", new WebServlet()).addMapping("/console/*");
        ServletRegistration.Dynamic appServlet = servletContext.addServlet("app", new DispatcherServlet(webContext()));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("*.do","/npp/*");
    }

    private WebApplicationContext appContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register( JDBCConfiguration.class, AppConfig.class);
        return context;
    }
	    
    private WebApplicationContext webContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(MvcConfiguration.class, GreetingController.class);
        return context;
    }
}
