package com.xlc.hmm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Table2 {
	
	@Id
	@Column(name="id")
	private int id;
	@Column(name="rowa")
	private String rowA;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRowA() {
		return rowA;
	}

	public void setRowA(String rowA) {
		this.rowA = rowA;
	}

	@Override
	public String toString() {
		return "Table2 [id=" + id + ", rowA=" + rowA + "]";
	}
	
}