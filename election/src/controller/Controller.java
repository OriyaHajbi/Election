package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

import Exception.CitizenIsNotAdultException;
import Exception.NumOfDigitInvalidException;
import Exception.TypeNotValidException;
import Listrner.ModelListenable;
import Listrner.ViewListenable;
import model.Ballot;
import model.Citizen;
import model.ElectionRound;
import model.Party;
import model.Set;
import view.Viewable;

public class Controller implements ModelListenable , ViewListenable {

	private ElectionRound model;
	private Viewable view;

	public Controller(ElectionRound model ,Viewable view) {
		this.model = model;
		this.view = view;

		model.registerListener(this);
		view.registerListener(this);
	}

	@Override
	public void viewUpdateOnAddBallot(String address, int numType) { // finish
		// TODO Auto-generated method stub
		try {
			model.addNewBallot(address, numType);
		} catch (Exception e) {
			view.messageException(e.getMessage());
		}
	}

	@Override
	public boolean viewUpdateOnAddCitizen(String name, String id, int yearOfBirth, boolean isIsolation, Party party, int dayOfSick) { // finish
		// TODO Auto-generated method stub
		try {
			model.addNewCitizen(name, id, yearOfBirth, isIsolation, party, dayOfSick);
			return true;
		} catch (TypeNotValidException e) {
			view.messageException(e.getMessage());
			return false;
		} catch (NumOfDigitInvalidException e) {
			view.messageException(e.getMessage());
			return false;
		} catch (CitizenIsNotAdultException e) {
			view.messageException(e.getMessage());
			return false;
		}
	}

	@Override
	public Vector<Ballot<? extends Citizen>> viewAsksForBallotsList() {	
		return model.getAllBallots();
	}

	@Override
	public Set<Citizen> viewAsksForCitizenList() {	
		return model.getAllCitizens();
	}

	@Override
	public Vector<Party> viewAsksForPartyList() { 
		return model.getAllParties();
	}

	@Override
	public void viewUpdateOnVotingDay() {   
		model.votingDay();		
	}

	@Override

	public String viewAsksForResultsData() {	
		return model.getResults();
	}  
	
	public Vector<Integer> viewAsksForCalculateResults() {
		return model.calculateResultsByParty();
	}

	@Override
	public void modelUpdateBallotIsAdded() {
		view.updateBallotIsAdd();
	}

	@Override
	public void modelUpdateCitizenIsAdded() {
		view.updateCitizenIsAdd();
	}

	@Override
	public void modelUpdatePartyIsAdded() {
		view.updatePartyIsAdd();
	}

	@Override
	public void modelUpdateApplicantIsAdded() {
		view.updateApplicantIsAdd();
	}

	@Override
	public void viewUpdateOnAddParty(String name, int year, String type, String description) { // finish
		try {
			model.addNewParty(name, type, year, description);
		} catch (Exception e) {
			view.messageException(e.getMessage());
		}
		
	}

	@Override
	public Party viewAsksForParty(String nameParty) { // finish
		return model.getPartyByName(nameParty);
	}

	@Override
	public void viewaskForSave() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		model.saveProject();
		
	}

}


