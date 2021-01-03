package com.example.hbkjgoa.model;

import java.io.Serializable;
import java.util.List;

public class CJCXs_XX implements Serializable {
	private List<XXQK_Bean> rows ;

	public void setRows(List<XXQK_Bean> rows){
	this.rows = rows;
	}
	public List<XXQK_Bean> getRows(){
	return this.rows;
	}

}