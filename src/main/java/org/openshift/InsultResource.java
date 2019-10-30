package org.openshift;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@RequestScoped
@Path("/insult")
public class InsultResource {
    @GET
    @Produces("application/json")
    public Map<String, String> getInsult() {
        return Map.of("insult", new InsultGenerator().generateInsult());
    }
}
