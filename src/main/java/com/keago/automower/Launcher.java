package com.keago.automower;

import com.keago.automower.ws.AutomowException;
import com.keago.automower.ws.AutomowResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationFeature;
import org.glassfish.jersey.servlet.ServletContainer;


/**
 * Automow launcher.
 *
 */
public class Launcher {    
    public static void main(String[] args) throws Exception {
        ServletContextHandler sch = new ServletContextHandler();
        sch.setContextPath("/");

        ResourceConfig rc = new ResourceConfig()
                // Now you can expect validation errors to be sent to the client.
                .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
                // @ValidateOnExecution annotations on subclasses won't cause errors.
                .property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        rc.register(AutomowResource.class);
        rc.register(ValidationFeature.class);
        rc.register(JacksonFeature.class);
        rc.register(AutomowException.class);

        ServletContainer sc = new ServletContainer(rc);
        ServletHolder holder = new ServletHolder(sc);
        sch.addServlet(holder, "/*");

        Server server = new Server(8080);
        server.setHandler(sch);

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

}
