package model;

import java.util.Random;

public class CoronaCitizen extends Citizen implements Sickable {

	public int dayOfSick;

	public CoronaCitizen(String name, int id, int yearOfBirth, int days) {
		super(name, id, yearOfBirth);
		dayOfSick = days;
	}
	
	
	public boolean hasProtectiveWear() {
		return new Random().nextBoolean();
	}

	public String toString() {
		return super.toString() +" " + dayOfSick + " days in insulator";
	}

}
