package Listrner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import model.Ballot;
import model.Citizen;
import model.Party;
import model.Set;

public interface ViewListenable {

	void viewUpdateOnAddBallot(String address, int numType);

	boolean viewUpdateOnAddCitizen(String name, String id, int yearOfBirth, boolean isIsolation, Party party,
			int dayOfSick);

	void viewUpdateOnAddParty(String name, int year, String type, String description);

	Vector<Ballot<? extends Citizen>> viewAsksForBallotsList();

	Set<Citizen> viewAsksForCitizenList();

	Vector<Party> viewAsksForPartyList();

	void viewUpdateOnVotingDay();

	String viewAsksForResultsData();
		
	Vector<Integer> viewAsksForCalculateResults();

	Party viewAsksForParty(String nameParty);

//	double viewAskForResultPerParty(int index);

	void viewaskForSave() throws FileNotFoundException, IOException;

}
