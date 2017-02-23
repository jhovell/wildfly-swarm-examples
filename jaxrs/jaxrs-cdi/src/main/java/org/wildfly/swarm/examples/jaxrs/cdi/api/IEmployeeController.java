package org.wildfly.swarm.examples.jaxrs.cdi.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.wildfly.swarm.examples.jaxrs.cdi.domain.model.Employee;

import javax.validation.Valid;

/**
 * @author Yoshimasa Tanabe
 */
@Path("/employees")
public interface IEmployeeController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> findAll();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Employee postEmployee(@Valid final Employee employee);
}
