package model;

import java.io.Serializable;
import java.util.Vector;


import Exception.TypeNotValidException;

public class Set<T extends Citizen> implements Serializable {
	
	
	private Vector<T> allObj;
	
	
	public Set() {
		allObj = new Vector<T>();
	}
	
	public boolean add(T obj) {
		if (!allObj.contains(obj)) {
			allObj.add(obj);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Vector<T> getAllCitizen(){
		return allObj;
	}
	public Citizen get(int i) {
		return allObj.get(i);
	}
	
	public int size() {
		return allObj.size();
	}

}
