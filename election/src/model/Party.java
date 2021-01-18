package model;

import java.io.Serializable;
import java.util.Vector;

public class Party implements Serializable {

	public enum Type {
		left, center, right
	}

	private String name;
	private String descripion;
	private int idParty;
	private int count;
	private Type theType;
	private int yearOfEnter; 
	private Vector<Applicant> applicants;
	

	public Party(String name, String type, int yearOfEnter, String descripion) {
		this.name = name;
		this.descripion = descripion;
		idParty = ++count;
		this.theType = Type.valueOf(type);
		this.yearOfEnter = yearOfEnter;
		applicants = new Vector<Applicant>();

	}

	public int getIdParty() {
		return idParty;
	}

	public String getName() {
		return name;
	}

	public Type getTheType() {
		return theType;
	}

	public int getYearOfEnter() {
		return yearOfEnter;
	}

	public Vector<Applicant> getApplicants() {
		return applicants;
	}

	public int getCountApplicants() {
		return applicants.size();
	}
// TODO
	public void addNewApplicant(Applicant newApplicant) {
		applicants.add(newApplicant);
	}

	public void sortByAge(){
		for(int i=applicants.size()-1 ; i>0 ; i--) {
			for(int j=0 ; j<i ; j++) {
				if(applicants.get(j).getAge() < applicants.get(j+1).getAge()) {
					Applicant oldApplicant = applicants.set( j , applicants.get(j+1));
					applicants.set(j+1 , oldApplicant);
				}
			}
		}
	}
	


	public boolean equals(Object other) {
		if (!(other instanceof Party))
			return false;
		Party p = (Party) other;
		return name == p.name;
	}

	public String toString() {
		StringBuffer str = new StringBuffer("");
		str.append(getClass().getSimpleName()+" "+ name + " The type is: " + theType + " Year of enter: " 
				+ yearOfEnter +"\n\t" + "Descripion:"+descripion + "\n\n\t");
		if (applicants.size()>0)
			str.append("All the applicants is:\n");
		for (int i = 0; i < applicants.size(); i++) {
			str.append("\t- " + applicants.get(i).toString() + "\n");
		}
		return str.toString();

	}

}
