package model;

import java.util.Random;

public class Soldier extends Citizen implements weaponable{

	
	protected boolean isCarryWeapon;

	public Soldier(String name, int id, int yearOfBirth) {
		super(name, id, yearOfBirth);
	}
	
	
	public boolean isCarryWeapon() {
		isCarryWeapon= new Random().nextBoolean();
		return isCarryWeapon;
	}
	
	public String toString() {
		return super.toString() + (isCarryWeapon() ? " I am carryWeapon" : "");
	}
}
