package com.example.hbkjgoa.model;

import java.io.Serializable;
import java.util.List;

public class CJCXs implements Serializable {
	private List<CJCX> rows ;

	public void setRows(List<CJCX> rows){
	this.rows = rows;
	}
	public List<CJCX> getRows(){
	return this.rows;
	}

}