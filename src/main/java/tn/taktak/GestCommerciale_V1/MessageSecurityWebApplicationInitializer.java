package tn.taktak.GestCommerciale_V1;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class MessageSecurityWebApplicationInitializer
extends AbstractSecurityWebApplicationInitializer{

//	@Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { SpringConfiguration.class };
    }
}
