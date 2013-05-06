package com.enhancesys.workflow.engine.interfaces.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.enhancesys.workflow.engine.entities.ProcessDefinition;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
@Path("/workflow-services")
public class WorkflowManagementImpl 
{
	
	@PersistenceContext(unitName="com.enhancesys.workflow")
	EntityManager entityManager;
	
	@PUT
	@Path("/addProcessDefinition")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ProcessDefinition addProcessDefinition(ProcessDefinition processDefinition)
	{
		entityManager.persist(processDefinition);
		return processDefinition;
	}
	
}
