package com.xlc.hmm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Table1 {
	
	@Id
	@Column(name="id")
	private int id;
	@Column(name="wordlist")
	private String wordList;
	@Column(name="labellist")
	private String labelList;
	@Column(name="wordsize")
	private int wordSize;
	@Column(name="labelsize")
	private int labelSize;
	@Column(name="pi")
	private String pi;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWordList() {
		return wordList;
	}
	public void setWordList(String wordList) {
		this.wordList = wordList;
	}
	public String getLabelList() {
		return labelList;
	}
	public void setLabelList(String labelList) {
		this.labelList = labelList;
	}
	public int getWordSize() {
		return wordSize;
	}
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
	}
	public int getLabelSize() {
		return labelSize;
	}
	public void setLabelSize(int labelSize) {
		this.labelSize = labelSize;
	}
	public String getPi() {
		return pi;
	}
	public void setPi(String pi) {
		this.pi = pi;
	}
	@Override
	public String toString() {
		return "Table1 [id=" + id + ", wordList=" + wordList + ", labelList=" + labelList + ", wordSize=" + wordSize
				+ ", labelSize=" + labelSize + ", pi=" + pi + "]";
	}
	
	
}
