package id313220469_id205481377;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.rmi.server.UID;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import Exception.CitizenIsNotAdultException;
import Exception.NumOfDigitInvalidException;
import Exception.PartyExsistException;
import Exception.TypeNotValidException;
import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.ElectionRound;
import model.Party;
import view.ConsoleUI;
import view.GraphicalUI;
import view.Messageable;
import view.ViewJavaFx;

//Oriya Hajbi 313220469
//Shahar Hatan 205481377 

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) throws Exception {
		GraphicalUI ui = new GraphicalUI();
		ElectionRound election = null;
		int num;
		do {
			num = ui.getIntMessage(
					"where are you want the data of the election round?\npress 1 --- File\npress 2 --- hardcoded");
		} while (!((num == 1) || (num == 2)));
		switch (num) {
		case 1:
			ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("election.data"));
			election = (ElectionRound) inFile.readObject();
			if (election == null)
				election = new ElectionRound();
			election.setListeners();
			break;
		case 2:
			election = new ElectionRound(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
			hardCoded(election);
			break;
		}

		ViewJavaFx electionView = new ViewJavaFx(theStage);
		Controller c = new Controller(election, electionView);

	}

	public static void hardCoded(ElectionRound election)

			throws NumOfDigitInvalidException, TypeNotValidException, CitizenIsNotAdultException, PartyExsistException {

		election.addNewBallot("shenkin 17", 4);
		election.addNewBallot("palmahim", 2);
		election.addNewBallot("hakirya base", 1);
		election.addNewBallot("Tel-Aviv-City", 3);

		election.addNewParty("yarok lavan", "left", 2019, "just no BiBi");
		election.addNewParty("Pirat2Piratim", "right", 2018,
				"this party will take care of all the pirates for larger roaming spaces");
		election.addNewParty("the Green flower", "center", 2017, "Live and let Live");

		election.addNewCitizen("Thank", "154865975", 1978, true, null, 6);
		election.addNewCitizen("Koko", "856487265", 1953, false, null, 5);
		election.addNewCitizen("you", "753648512", 1975, true, null, 7);
		election.addNewCitizen("Lolo", "254861596", 1990, false, null, 3);
		election.addNewCitizen("Moka", "134587596", 1999, false, null, 4);
		election.addNewCitizen("Dor", "154867975", 1968, true, null, 8);
		election.addNewCitizen("neomi", "856487965", 1963, false, null, 3);
		election.addNewCitizen("you", "853648512", 1965, true, null, 5);
		election.addNewCitizen("michal", "137587596", 1995, false, null, 2);
		election.addNewCitizen("miya", "159645826", 1995, false, null, 1);
		election.addNewCitizen("are", "154855975", 1968, true, null, 5);
		election.addNewCitizen("romi", "853487265", 1963, false, null, 5);
		election.addNewCitizen("a", "754648512", 1955, true, null, 15);
		election.addNewCitizen("kolopo", "354861596", 2000, true, null, 14);
		election.addNewCitizen("moti", "132587596", 1999, false, null, 7);
		election.addNewCitizen("good", "754647512", 1945, true, null, 3);
		election.addNewCitizen("friend", "754448512", 1935, true, null, 1);

		election.addNewCitizen("mimi", "456325874", 1978, false, (Party) election.getAllParties().get(0), 2);
		election.addNewCitizen("lili", "562125632", 1996, false, (Party) election.getAllParties().get(1), 4);
		election.addNewCitizen("mimi", "632541256", 2001, true, (Party) election.getAllParties().get(2), 5);
		election.addNewCitizen("marina", "753698425", 1985, false, (Party) election.getAllParties().get(2), 5);
		election.addNewCitizen("nikita", "123652487", 1972, true, (Party) election.getAllParties().get(1), 2);
		election.addNewCitizen("soso", "963147321", 1989, false, (Party) election.getAllParties().get(0), 1);
		election.addNewCitizen("shoham", "456312374", 1968, false, (Party) election.getAllParties().get(0), 5);
		election.addNewCitizen("yuli", "562125123", 1996, false, (Party) election.getAllParties().get(1), 7);
		election.addNewCitizen("lilach", "632542856", 2002, true, (Party) election.getAllParties().get(2), 3);
		election.addNewCitizen("mariana", "753629425", 1985, false, (Party) election.getAllParties().get(2), 6);
		election.addNewCitizen("nikota", "123656587", 1962, true, (Party) election.getAllParties().get(1), 8);
		election.addNewCitizen("sososona", "963837852", 1989, false, (Party) election.getAllParties().get(0), 3);
		election.addNewCitizen("sososona", "963839852", 1989, false, null, 3);

	}

	public static void manu(ElectionRound election, Messageable ui) throws TypeNotValidException, PartyExsistException {
		try {
			boolean vote = false;
			int num = 0;
			while (num != 10) {
				do {
					num = ui.getIntMessage("______________________________________________________\n"
							+ "______________________________________________________\n"
							+ "For add ballot ------------------------------- press 1\n"
							+ "For add citizen ------------------------------ press 2\n"
							+ "For add party -------------------------------- press 3\n"
							+ "For add applicant ---------------------------- press 4\n"
							+ "For show all the ballots --------------------- press 5\n"
							+ "For show all the citizen --------------------- press 6\n"
							+ "For show all the parties --------------------- press 7\n"
							+ "For election round --------------------------- press 8\n"
							+ "For show the result of the election round ---- press 9\n"
							+ "For exit ------------------------------------- press 10\n"
							+ "______________________________________________________\n"
							+ "______________________________________________________\n");
				} while (!((num > 0) || (num < 11)));

				switch (num) {

				case 1:// add ballot
					String address = ui.getStrLineMessage("What is the address of the ballot");
					int ballotType = ui.getIntMessage("If its a soldier-corona ballot press 1\n"
							+ "If its a regular-soldier ballot press 2\n" + "If its a corona-citizen ballot press 3\n"
							+ "If its a regular-citizen ballot press 4");
					election.addNewBallot(address, ballotType);

					switch (ballotType) {
					case 1:// soldier-corona
						ui.showMessage("Soldier-corona ballot created");
						break;
					case 2:// regular-soldier
						ui.showMessage("Regular-soldier ballot created");
						break;
					case 3:// corona-citizen
						ui.showMessage("Corona-citizen ballot created");
						break;
					case 4: // regular-citizen
						ui.showMessage("Regular-citizen ballot created");
						break;

					default:
						ui.showMessage("The number must be beween 1-4");
					}
					break;

				case 2:// add citizen
				case 4:// add applicant

					boolean isValidInput = false;
					while (!isValidInput) {

						try {
							String name = ui.getStrLineMessage("What is the citizen name?");
							String id = ui.getStrLineMessage("What is the ID number?");
							int yearOfBirth = ui.getIntMessage("What is the year of birth of the citizen?");
							boolean isolation = ui.getBooleanMessage("Is the citizen in isolation? (true/false)");

							int dayOfSick = 0;
							if (isolation)
								dayOfSick = ui.getIntMessage("how much days you are insulator?");

							Party party = null;
							if (num == 4) {
								int applicantParty;
								do {
									StringBuffer sb = new StringBuffer();
									sb.append("Which party does the applicant belong to?/n");
									for (int i = 0; i < election.getCountParties(); i++) {
										sb.append(((Party) election.getAllParties().get(i)).getName() + "--" + " press "
												+ (i + 1));
									}
									applicantParty = ui.getIntMessage(sb.toString());
									party = (Party) election.getAllParties().get(applicantParty - 1);
								} while (applicantParty < 0 || applicantParty > election.getCountParties());
							}

							if (election.addNewCitizen(name, id, yearOfBirth, isolation, party, dayOfSick)) {
								isValidInput = true;
								if (party == null)
									ui.showMessage("Citizen created successfully");
								else
									ui.showMessage("Applicant created successfully");
							} else
								ui.showMessage("the Citizen exsist");

						} catch (InputMismatchException e) {
							ui.showMessage("Input is not valid");
							ui.cleanBuffer();

						} catch (NumberFormatException | CitizenIsNotAdultException | NumOfDigitInvalidException
								| TypeNotValidException e) {
							ui.showMessage(e.getMessage());
							ui.cleanBuffer();

							// not sure that we need the first one
							// throw when we try to convert String into x but x is not of the type that we
							// try to convert
						}
					}
					break;

				case 3:// add party
					String name = ui.getStrLineMessage("What the name of the party?");
					String side = ui.getStrLineMessage("What is the side of the party");
					String descripion = ui.getStrLineMessage("What is the descripion of the party");
					int yearOfEstablishment = ui.getIntMessage("When was the party established?");

					if (election.addNewParty(name, side, yearOfEstablishment, descripion))
						ui.showMessage("Party created successfully");
					break;

				case 5:// show all ballots
					StringBuffer showBallots = new StringBuffer();
					showBallots.append("Now you can show all the ballots\n");
					for (int i = 0; i < election.getAllBallots().size(); i++) {
						showBallots.append("\n(" + (i + 1) + ") " + "" + election.getAllBallots().get(i).toString());
					}
					ui.showMessage(showBallots);
					break;

				case 6:// show all citizen
					StringBuffer showCitizens = new StringBuffer();
					showCitizens.append("Now you can show all the citizen\n\n");
					for (int i = 0; i < election.getAllCitizens().size(); i++) {
						showCitizens.append("(" + (i + 1) + ") " + election.getAllCitizens().get(i).toString() + "\n");
					}
					ui.showMessage(showCitizens);
					break;

				case 7:// show all parties
					StringBuffer showParties = new StringBuffer();
					showParties.append("Now you can show all the parties\n");
					for (int i = 0; i < election.getAllParties().size(); i++) {
						showParties.append("\n(" + (i + 1) + ") " + election.getAllParties().get(i).toString());
					}
					ui.showMessage(showParties);
					break;

				case 8:// election round
					election.votingDay();
					vote = true;
					ui.showMessage("The election round is done");
					break;

				case 9:// show the result of the election round
					if (!vote) {
						ui.showMessage("There was no election round yet(for election round press 8)");
					} else {
						ui.showMessage("The result of the election round is:\n" + election.toString());
					}
					break;
				}
			}
			ui.showMessage("Thank you very much my friend");

		} catch (NumberFormatException e) {
			ui.showMessage(e.getMessage());
			ui.cleanBuffer();

		}
	}

}

/*
 * public static void main(String[] args) throws NumOfDigitInvalidException,
 * TypeNotValidException, CitizenIsNotAdultException {
 * 
 * ElectionRound election = new ElectionRound(LocalDate.now().getYear(),
 * LocalDate.now().getMonthValue()); hardCoded(election); Messageable g = new
 * GraphicalUI();
 * 
 * boolean answer = g.
 * getBooleanMessage("Would you like to use the geaphical interface? (true/false)"
 * ); if (answer == true) manu(election, g); else { Messageable c = new
 * ConsoleUI(); manu(election, c); } }
 */
