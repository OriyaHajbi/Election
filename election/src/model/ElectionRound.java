package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Vector;

import Exception.CitizenIsNotAdultException;
import Exception.NumOfDigitInvalidException;
import Exception.PartyExsistException;
import Exception.TypeNotValidException;
import Listrner.ModelListenable;
import model.Ballot.eType;

public class ElectionRound implements Serializable {

	private int year;
	private int month;

	private Set<Citizen> allCitizens = new Set<Citizen>();
	private Vector<Party> allParties = new Vector<Party>();

	/*
	 * private Vector<Ballot<Citizen>> ballotCitizens = new
	 * Vector<Ballot<Citizen>>(); private Vector<Ballot<CoronaCitizen>>
	 * ballotSickCitizens = new Vector<Ballot<CoronaCitizen>>(); private
	 * Vector<Ballot<Soldier>> ballotSoldiers = new Vector<Ballot<Soldier>>();
	 * private Vector<Ballot<CoronaSoldier>> ballotSickSoldier = new
	 * Vector<Ballot<CoronaSoldier>>();
	 */
	private int nextBallotInTurn;
	private boolean isVotingDayHappened = false;

	private Vector<Ballot<? extends Citizen>> allBallotsVector = new Vector<Ballot<? extends Citizen>>();

	private transient Vector<ModelListenable> allListeners;

	Vector<Integer> resultsByparties;

	public ElectionRound() {

	}

	public ElectionRound(int year, int month) {
		this.year = year;
		this.month = month;
		allListeners = new Vector<ModelListenable>();
	}

	public void registerListener(ModelListenable l) {
		allListeners.add(l);
	}

	public boolean addNewCitizen(String name, String id, int yearOfBirth, boolean isolation, Party party, int dayOfSick)
			throws TypeNotValidException, NumOfDigitInvalidException, CitizenIsNotAdultException,
			TypeNotValidException {
		if ((LocalDate.now().getYear() - yearOfBirth) < 18) // add120
			throw new CitizenIsNotAdultException("The citizen is not aduld, the citizen age must be over 18");

		if (!numOfDigitsIsInvalid(id))
			throw new NumOfDigitInvalidException("The id must contain exactly 9 digits!");

		Citizen c;
		if (party == null) {
			if (isolation) {
				if (((LocalDate.now().getYear() - yearOfBirth) >= 18)
						&& (LocalDate.now().getYear() - yearOfBirth) <= 21) {
					c = new CoronaSoldier(name, Integer.parseInt(id), yearOfBirth, dayOfSick);
				} else {
					c = new CoronaCitizen(name, Integer.parseInt(id), yearOfBirth, dayOfSick);
				}
			} else {
				if (((LocalDate.now().getYear() - yearOfBirth) >= 18)
						&& (LocalDate.now().getYear() - yearOfBirth) <= 21)
					c = new Soldier(name, Integer.parseInt(id), yearOfBirth);
				else
					c = new Citizen(name, Integer.parseInt(id), yearOfBirth);
			}
		} else {
			if (isolation)
				c = new CoronaApplicant(name, Integer.parseInt(id), yearOfBirth, dayOfSick, party);
			else
				c = new Applicant(name, Integer.parseInt(id), yearOfBirth, party);
		}
		// all the time we send Citizen..
		if (!(c instanceof Citizen))
			throw new TypeNotValidException("The type of this object is not valid");

		if (allCitizens.add(c)) {
			if (c instanceof Applicant) {
				party.sortByAge();
				fireModelUpdateApplicantIsAdded();
			}
			else {
				fireModelUpdateCitizenIsAdded();
			}
			manageCitizenToBallot(c);
			return true;
		} else
			return false;
	}
	
	private void fireModelUpdateCitizenIsAdded() {
		for (ModelListenable l : allListeners) {
			l.modelUpdateCitizenIsAdded();
		}
	}
	
	private void fireModelUpdateApplicantIsAdded() {
		for (ModelListenable l : allListeners) {
			l.modelUpdateApplicantIsAdded();
		}
	}
	
	public boolean numOfDigitsIsInvalid(String id) {
		if (id.length() == 9)
			return true;
		return false;
	}

	public boolean addNewBallot(String address, int ballotType) throws TypeNotValidException {
		Ballot b = null;
		boolean isValidBallotType = true;

		if (ballotType == 1) {// SoldierSickInCoronaBallot
			Ballot<CoronaSoldier> bCoronaSoldier = new Ballot<CoronaSoldier>(address);
			bCoronaSoldier.setType(eType.SoldierSickInCoronaBallot);
			b = bCoronaSoldier;
//			System.out.println("hay");

		} else if (ballotType == 2) { // SoldierBallot
			Ballot<Soldier> bSoldier = new Ballot<Soldier>(address);
			bSoldier.setType(eType.SoldierBallot);
			b = bSoldier;

		} else if (ballotType == 3) { // CoronaBallot
			Ballot<CoronaCitizen> bCorona = new Ballot<CoronaCitizen>(address);
			bCorona.setType(eType.CoronaBallot);
			b = bCorona;

		} else if (ballotType == 4) { // RegularBallot
			Ballot<Citizen> ballot = new Ballot<Citizen>(address);
			ballot.setType(eType.RegularBallot);
			b = ballot;

		} else
			return false;

		allBallotsVector.add(b);
		fireModelUpdateBallotIsAdded();
		return true;
	}
	
	private void fireModelUpdateBallotIsAdded() {
		for (ModelListenable l : allListeners) {
			l.modelUpdateBallotIsAdded();
		}
	}

	public boolean addNewParty(String name, String type, int yearOfEnter, String descripion)
			throws TypeNotValidException, PartyExsistException {
		if (ifPartyExists(name)) {
			throw new PartyExsistException("this party existed");
		}
		type = type.toLowerCase();
		if (typeCorrect(type)) {
			System.out.println("Type of party doesnt exists");
			return false;
		}
		Party p = new Party(name, type, yearOfEnter, descripion);
		allParties.add(p);
		fireModelUpdatePartyIsAdded();

		return true;
	}

	private void fireModelUpdatePartyIsAdded() {
		for (ModelListenable l : allListeners) {
			l.modelUpdatePartyIsAdded();
		}
	}
	
	public boolean typeCorrect(String type) {
		if (!type.equalsIgnoreCase("left") && !type.equalsIgnoreCase("right") && !type.equalsIgnoreCase("center"))
			return true;
		return false;
	}

	public boolean ifPartyExists(String name) {
		boolean exist = false;
		for (int i = 0; i < allParties.size(); i++) {
			if (allParties.get(i).getName().equalsIgnoreCase(name))
				exist = true;
		}
		return exist;
	}

	public void manageCitizenToBallot(Citizen citizen) {
		boolean isMatchingBallot = false;
		while (!isMatchingBallot) {
			if (citizen.getClass() == CoronaSoldier.class
					&& allBallotsVector.get(nextBallotInTurn).getType() == Ballot.eType.SoldierSickInCoronaBallot) {
				allBallotsVector.get(nextBallotInTurn).addCitizenToBallot((CoronaSoldier) citizen);
				((CoronaSoldier) citizen).setBallot(allBallotsVector.get(nextBallotInTurn));
				isMatchingBallot = true;
			} else if ((citizen.getClass() == CoronaCitizen.class || citizen.getClass() == CoronaApplicant.class)
					&& allBallotsVector.get(nextBallotInTurn).getType() == Ballot.eType.CoronaBallot) {
				allBallotsVector.get(nextBallotInTurn).addCitizenToBallot((CoronaCitizen) citizen);
				((CoronaCitizen) citizen).setBallot(allBallotsVector.get(nextBallotInTurn));
				isMatchingBallot = true;
			} else if (citizen.getClass() == Soldier.class
					&& allBallotsVector.get(nextBallotInTurn).getType() == Ballot.eType.SoldierBallot) {
				allBallotsVector.get(nextBallotInTurn).addCitizenToBallot((Soldier) citizen);
				((Soldier) citizen).setBallot(allBallotsVector.get(nextBallotInTurn));
				isMatchingBallot = true;
			} else if ((citizen.getClass() == Citizen.class || citizen.getClass() == Applicant.class)
					&& allBallotsVector.get(nextBallotInTurn).getType() == Ballot.eType.RegularBallot) {
				allBallotsVector.get(nextBallotInTurn).addCitizenToBallot((Citizen) citizen);
				((Citizen) citizen).setBallot(allBallotsVector.get(nextBallotInTurn));
				isMatchingBallot = true;
			}
			nextBallotInTurn++;
			nextBallotInTurn = nextBallotInTurn % allBallotsVector.size();
		}
	}

	public void votingDay() {
		for (int i = 0; i < allBallotsVector.size(); i++) {
			allBallotsVector.get(i).ballotResultsOn(allParties.size());
			allBallotsVector.get(i).votingDay();
		}
		isVotingDayHappened = true;
	}

	public boolean copyId(int id) {
		for (int i = 0; i < allCitizens.size(); i++) {
			if (allCitizens.getAllCitizen().get(i).getId() == id)
				return false;
		}
		return true;
	}

	public Set<Citizen> getAllCitizens() {
		return allCitizens;
	}

	public Vector<Ballot<? extends Citizen>> getAllBallots() {
		return allBallotsVector;
	}

	public Vector<Party> getAllParties() {
		return allParties;
	}

	public int getCountCitizen() {
		return allCitizens.size();
	}

	public int getCountBallot() {
		return allBallotsVector.size();
	}

	public int getCountParties() {
		return allParties.size();
	}

	public Vector<Integer> calculateResultsByParty() {
		Vector<Integer> resultsByParties = new Vector<Integer>(allParties.size());
		for (int i = 0; i < allParties.size(); i++) {
			resultsByParties.add(0);
		}
		for (int i = 0; i < allBallotsVector.size(); i++) {
			for (int j = 0; j < allParties.size(); j++) {
				int calculator = allBallotsVector.get(i).getBallotResults().get(j) + resultsByParties.get(j);
				resultsByParties.setElementAt(calculator, j);
			}
		}
		return resultsByParties;
	}

	public boolean equals(Object other) {
		if (!(other instanceof ElectionRound))
			return false;
		ElectionRound e = (ElectionRound) other;
		return e.month == month && e.year == year;
	}

	public int getResultPerParty(int index) {
		return resultsByparties.get(index);
	}

	public Party getPartyByName(String nameParty) {
		int index = 0;
		for (int i = 0; i < allParties.size(); i++) {
			if (allParties.get(i).getName().equalsIgnoreCase(nameParty))
				index = i;
		}
		return allParties.get(index);
	}

	public String getResults() {
		if (!isVotingDayHappened)
			return "There was no election round yet";
		else {
/*			StringBuffer sb = new StringBuffer();
			sb.append("Election Round " + month + "\\" + year + "  -  final results: ");

			for (int i = 0; i < allBallotsVector.size(); i++) {
				Ballot<? extends Citizen> b = allBallotsVector.get(i);
				sb.append("\n\n(" + (i + 1) + ") " + b.getClass().getSimpleName() + " Id: " + b.getId()
						+ " Percent of Voters:" + b.percentOfVoters + "% results:");

				for (int j = 0; j < allParties.size(); j++) {
					sb.append("\n\t- " + "Party " + allParties.get(j).getName() + " votes: "
							+ b.getBallotResults().get(j));
				}
			}
			sb.append("\n\nFinal results by parties:");
			resultsByparties = calculateResultsByParty();
			for (int j = 0; j < allParties.size(); j++) {
				sb.append("\n\t(" + (j + 1) + ") Party " + allParties.get(j).getName() + " total votes: "
						+ resultsByparties.get(j));
			}*/
			return toString();
		}
	}

	public String toString() { // TODO have to make new toString

		StringBuffer sb = new StringBuffer();
		sb.append("Election Round " + month + "\\" + year + "  -  final results: ");

		for (int i = 0; i < allBallotsVector.size(); i++) {
			Ballot<? extends Citizen> b = allBallotsVector.get(i);
			sb.append("\n\n(" + (i + 1) + ") " + b.getClass().getSimpleName() + " Id: " + b.getId()
					+ " Percent of Voters:\t" + b.percentOfVoters + " % ");

			for (int j = 0; j < allParties.size(); j++) {
				sb.append("\n\t- " + "Party " + allParties.get(j).getName() + " votes:\t" + b.getBallotResults().get(j));
			}
		}
		sb.append("\n\nFinal results by parties:");
		Vector<Integer> resultsByparties = calculateResultsByParty();
		for (int j = 0; j < allParties.size(); j++) {
			sb.append("\n\t(" + (j + 1) + ") Party " + allParties.get(j).getName() + " total votes: "
					+ resultsByparties.get(j));
		}
		return sb.toString();
	}

	public void saveProject() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("election.data"));
		outFile.writeObject(this);
		outFile.close();

	}

	public void setListeners() {
		// TODO Auto-generated method stub
		allListeners = new Vector<ModelListenable>();

	}
}