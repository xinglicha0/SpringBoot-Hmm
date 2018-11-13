package com.xlc.hmm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Table3{
	@Id
	@Column(name="id")
	private int id;
	@Column(name="rowb")
	private String rowB;
    
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRowB() {
		return rowB;
	}

	public void setRowB(String rowB) {
		this.rowB = rowB;
	}
    
}