
package com.rfa.n26.transactions.config;

import com.rfa.n26.transactions.json.ObjectMapperContextResolver;
import com.rfa.n26.transactions.resource.StatsResource;
import com.rfa.n26.transactions.resource.TransactionResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;

@ApplicationPath("/rest")
public class JerseyAppConfiguration extends ResourceConfig {

    public JerseyAppConfiguration(@Context ServletContext context) {
        WebApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(context);
        register(appCtx.getBean(ObjectMapperContextResolver.class));

        register(appCtx.getBean(TransactionResource.class));
        register(appCtx.getBean(StatsResource.class));
    }

}
