package model;

public class Applicant  extends Citizen {

	private Party party;

	public Applicant(String name, int id, int yearOfBirth,  Party party) {

		super(name, id, yearOfBirth);
		this.party = party;
		party.addNewApplicant(this);
	}

	public String toString() {
		return super.toString() + " belong to the party: " + party.getName();
	}

}
