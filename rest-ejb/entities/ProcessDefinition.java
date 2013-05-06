package com.enhancesys.workflow.engine.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ms_process_definition")
@Access(AccessType.FIELD)
public class ProcessDefinition 
{
	
	public String getProcessDefinitionId() 
	{
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) 
	{
		this.processDefinitionId = processDefinitionId;
	}

	public String getContextRoot() 
	{
		return contextRoot;
	}

	public void setContextRoot(String contextRoot) 
	{
		this.contextRoot = contextRoot;
	}

	public String getSearchCondition() 
	{
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) 
	{
		this.searchCondition = searchCondition;
	}

	@Id
	@Column(name="process_definition_id_v")
	String processDefinitionId;
	
	@Column(name="context_root_v")
	@Basic
	String contextRoot;
	
	@Column(name="search_condition_v")
	@Basic
	String searchCondition;
}
