package configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.internal.ValidationExceptionMapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class MyRestApp extends Application {
    ResourceConfig rc = new ResourceConfig().property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
            .register(ValidationExceptionMapper.class);

}
