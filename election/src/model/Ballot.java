package model;

import java.io.Serializable;
import java.util.Vector;

public class Ballot<T extends Citizen> implements Serializable {
	public enum eType {SoldierBallot, CoronaBallot, RegularBallot, SoldierSickInCoronaBallot};

	private eType type;
	private static int counter;	
	protected int id;
	protected String address;
	protected Vector<T> allCitizens;
	protected float percentOfVoters;
	protected Vector<Integer> ballotResults;

	public Ballot(String address) {
		this.address = address;
		id = ++counter;
		allCitizens = new Vector<T>();
	}

	public int getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public Vector<T> getAllCitizens() {
		return allCitizens;
	}

	public int getCountCitizen() {
		return allCitizens.size();
	}

	public Vector<Integer> getBallotResults() {
		return ballotResults;
	}

	public float getPercentOfVoters() {
		return percentOfVoters;
	}

	public void addCitizenToBallot(Citizen citizen) {
		allCitizens.add((T) citizen);
	}

	public void votingDay() {
		int numOfVoting = 0;
		for (int i = 0; i < allCitizens.size(); i++) {
			if (allCitizens.get(i).isCitizenWantToVote()) {
				numOfVoting++;

				int citizenVote = allCitizens.get(i).vote(ballotResults.size());
				int calculator = (ballotResults.get(citizenVote))+1;
				ballotResults.setElementAt(calculator, citizenVote);
			}
		}
		calcPercentOfVoters(numOfVoting);
	}

	private void calcPercentOfVoters(int numOfVoting) {
		if (allCitizens.size() != 0) { // countCitizen start with 0 ---- exception
			percentOfVoters = (((float) numOfVoting) / allCitizens.size()) * 100;
		}
		else 
			percentOfVoters = 0;
	}

	public void ballotResultsOn(int numOfParties) {
		
		ballotResults = new Vector<Integer>(numOfParties);
		for(int i = 0 ; i< numOfParties ; i++) {
			ballotResults.add(0);
		}
	}

	public boolean equals(Object other) {
		if (!(other instanceof Ballot))
			return false;
		Ballot b = (Ballot) other;
		return id == b.id;
	}

	public void setType(eType otherType) {
		type=otherType;
	}
	public eType getType() {
		return type;
	}

	public String toString() {
		StringBuffer str = new StringBuffer("");
		str.append(getClass().getSimpleName() +" " + type +"\nid is : " + id + "\naddress : " + address + "\n");
		if (allCitizens.size() == 0)
			str.append("There are no citizens associated with this ballot!\n");
		else
			str.append("All the citizen is :\n");
		for (int i = 0; i < allCitizens.size(); i++) {
			str.append(allCitizens.get(i).toString() + "\n");
		}
		return str.toString();
	}

}
