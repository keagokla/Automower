package com.keago.automower;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.keago.automower.ws.AutomowBean;
import com.keago.automower.ws.AutomowException;
import com.keago.automower.ws.AutomowResource;
import com.keago.automower.ws.MowerBean;

import java.util.ArrayList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.mockito.Mockito;

public class AutomowResourceTest extends JerseyTest {

    private static final String MOWER_1_ACTIONS = "GAGAGAGAA";
    private static final String MOWER_1_POSITION = "1 2 N";
    private static final String AREA = "5 5";
    private static final String MOWER_1_RESULT = "1 3 N";

    private AutomowResource resource;

    @Override
    public ResourceConfig configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        resource = Mockito.spy(AutomowResource.class);
        final ResourceConfig rc = new ResourceConfig();
        rc.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        rc.register(resource);
        return rc;
    }
    
    @Test
    public void testNominalCase() throws AutomowException {
        AutomowBean bean = buildAutomowBean(AREA, MOWER_1_POSITION, MOWER_1_ACTIONS);
        Mockito.when(resource.mowLawn(bean)).thenReturn(MOWER_1_RESULT);
        Response output = target("/libon/mow").request().post(Entity.json(bean));

        assertEquals("Should return status 200", 200, output.getStatus());
        assertNotNull("Should return mower final position", output.getEntity());
        Mockito.verify(resource, Mockito.times(1)).mowLawn(Mockito.any(AutomowBean.class));
    }
    
    @Test
    public void testErrorCaseNoPayload() throws AutomowException {
        Response output = target("/libon/mow").request().post(Entity.json(null));
        
        assertResponse(output, "Request body may not be null");
    }
    
    @Test
    public void testErrorCaseNoArea() throws AutomowException {
        AutomowBean bean = buildAutomowBean(null, MOWER_1_POSITION, MOWER_1_ACTIONS);
        Response output = target("/libon/mow").request().post(Entity.json(bean));

        assertResponse(output, "Lawn's dimensions are mandatory");
    }
    
    @Test
    public void testErrorCaseNoMowerPosition() throws AutomowException {
        AutomowBean bean = buildAutomowBean(AREA, null, MOWER_1_ACTIONS);
        Response output = target("/libon/mow").request().post(Entity.json(bean));

        assertResponse(output, "Mower's initial position is mandatory");
    }
    
    
    @Test
    public void testErrorCaseNoMowerActions() throws AutomowException {
        AutomowBean bean = buildAutomowBean(AREA, MOWER_1_POSITION, null);
        Response output = target("/libon/mow").request().post(Entity.json(bean));

        assertResponse(output, "Mower's movements are mandatory");
    }

    private void assertResponse(Response output, String validationError) throws AutomowException {
        assertEquals("Should return status 400", 400, output.getStatus());
        assertThat(output.readEntity(String.class), containsString(validationError));
        Mockito.verify(resource, Mockito.times(0)).mowLawn(Mockito.any(AutomowBean.class));
    }

    private AutomowBean buildAutomowBean(String area, String position, String actions) {
        final AutomowBean automow = new AutomowBean();
        final MowerBean mower = new MowerBean();
        mower.setPosition(position);
        mower.setActions(actions);
        final ArrayList<MowerBean> mowers = new ArrayList<MowerBean>();
        mowers.add(mower);
        automow.setLawnArea(area);
        automow.setMowers(mowers);

        return automow;
    }

}
