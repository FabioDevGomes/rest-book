package br.com.geladaonline.cache;

import java.util.Date;

public class EntityDatePair {

	private Object entity;
	private Date date;
	
	public EntityDatePair(Object entity, Date date) {
		super();
		this.entity = entity;
		this.date = date;
	}

	public Object getEntity() {
		return entity;
	}

	public Date getDate() {
		return date;
	}
}
