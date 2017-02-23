package org.wildfly.swarm.examples.jaxrs.cdi.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

/**
 * @author Yoshimasa Tanabe
 */
@ApplicationPath("/")
@ApplicationScoped
public class JaxRsActivator extends Application {
    @Inject
    private BeanManager beanManager;
        
    private final Set<Class<?>> empty = new HashSet<>();
  
    public Set<Class<?>> getClasses() {
        return this.empty;
    }
 
    public Set<Object> getSingletons() {
        // We need to get the actual singletons, not CDI proxies since the proxies apparently
        // do not have the javax.validation JAX RS annotations :(
        return Collections.unmodifiableSet(new HashSet<Object>(Arrays.asList(
                getInstance(IEmployeeController.class)
                )));
    }
    
    private <T> T getInstance(final Class<T> clazz) {
        @SuppressWarnings("unchecked")
        final Bean<T> bean = (Bean<T>) beanManager.resolve(beanManager.getBeans(clazz));
        return beanManager.getContext(bean.getScope()).get(bean, beanManager.createCreationalContext(bean));
    }
}
