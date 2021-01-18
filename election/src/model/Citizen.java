package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

public class Citizen implements Serializable {
	public enum eType {
		Soldier, SickInCorona, Regular, SoldierSickInCorona
	};

	protected String name;
	protected int id;
	protected int yearOfBirth;
	protected Ballot theBallot;
	protected boolean isIsolated;
	protected boolean hasPotectiveWear;


	public Citizen(String name, int id, int yearOfBirth) {
		this.name = name;
		this.id = id;
		this.yearOfBirth = yearOfBirth;
	}

	public int getAge() {
		return LocalDate.now().getYear() - yearOfBirth;
	}

	public <T> void setBallot(Ballot b) {
		this.theBallot = b;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public Ballot getBallot() {
		return theBallot;
	}

	public boolean isIsolated() {
		return isIsolated;
	}

	public boolean isCitizenWantToVote() {
		return new Random().nextBoolean();
	}

	public int vote(int numOfOChoices) {
		Random r = new Random();
		return r.nextInt(numOfOChoices);
	} 
	
	public boolean equals(Object other) {
		if (!(other instanceof Citizen))
			return false;
		Citizen c = (Citizen) other;
		return c.id == id;
	}

	public String toString() {
		return getClass().getSimpleName() + ", Name: " + name + "\nID: " + id + "\nage: "
				+ getAge() + "\nBallot: " + theBallot.getAddress()
				;
	}

}
