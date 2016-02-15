package tn.taktak.GestCommerciale_V1;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import tn.taktak.GestCommerciale_V1.SpringConfiguration;

public class MyWebAppInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext container) throws ServletException {
		//Create the root Spring application Context
		AnnotationConfigWebApplicationContext rootcontext=new AnnotationConfigWebApplicationContext();
		rootcontext.register(SpringConfiguration.class);
		//Manage the lifecycle of the root application context
		container.addListener(new ContextLoaderListener(rootcontext));

	}
}
