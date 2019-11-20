package com.hazelcast.jet.h2o.model.iris;

import java.io.Serializable;

public class IrisDefinition implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long timestamp;
	private double sepal_len;
	private double sepal_wid;
	private double petal_len;
	private double petal_wid;
	private String actualType;
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public double getSepal_len() {
		return sepal_len;
	}
	public void setSepal_len(double sepal_len) {
		this.sepal_len = sepal_len;
	}
	public double getSepal_wid() {
		return sepal_wid;
	}
	public void setSepal_wid(double sepal_wid) {
		this.sepal_wid = sepal_wid;
	}
	public double getPetal_len() {
		return petal_len;
	}
	public void setPetal_len(double petal_len) {
		this.petal_len = petal_len;
	}
	public double getPetal_wid() {
		return petal_wid;
	}
	public void setPetal_wid(double petal_wid) {
		this.petal_wid = petal_wid;
	}
	public String getActualType() {
		return actualType;
	}
	public void setActualType(String actualType) {
		this.actualType = actualType;
	}

}
