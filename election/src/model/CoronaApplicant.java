package model;

import java.util.Random;

public class CoronaApplicant extends CoronaCitizen implements Sickable {
	
	private Party party;

	public CoronaApplicant(String name, int id, int yearOfBirth, int days , Party otherParty) {
		super(name, id, yearOfBirth, days);
		party = otherParty;
	}
	
	public Party getParty() {
		return party;
	}
	
	public boolean hasProtectiveWear() {
		return new Random().nextBoolean();
	}
	public String toString() {
		return super.toString() + " I belong to party: " + party.getName();
	}

}
