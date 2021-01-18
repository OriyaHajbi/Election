package view;

import java.util.Vector;

import Listrner.ViewListenable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import model.Ballot;
import model.Citizen;

public class VbShowAllBallots extends VBox {

	public VbShowAllBallots(ViewListenable l) {
		Label lblTitel = new Label("All Ballots :");

		Accordion acrBallot = new Accordion();

		Vector<Ballot<? extends Citizen>> allBallots = l.viewAsksForBallotsList();
		for (int i = 0; i < allBallots.size(); i++) {
			Ballot<Citizen> ballot = (Ballot<Citizen>) allBallots.get(i);
			//			TitledPane ballotPane = new TitledPane(ballot.getClass().getSimpleName() + " number " + ballot.getId() , new Label(ballot.toString()));
			//			acrBallot.getPanes().add(ballotPane);

			Accordion acrCitizen = new Accordion();
			Vector<Citizen> allCitizens = ballot.getAllCitizens();

			for (int j = 0; j < allCitizens.size(); j++) {
				Citizen citizen = allCitizens.get(j);
				TitledPane citizenPane = new TitledPane(
						(citizen.getClass().getSimpleName() + " " + citizen.getName() + " " + citizen.getId()),
						new Label(citizen.toString()));
				acrCitizen.getPanes().add(citizenPane);
			}

			TitledPane tpShowCitizens = new TitledPane("Show all the ballot citizens", acrCitizen);
			tpShowCitizens.setExpanded(false);

			Label lblBallotDetails = new Label("Ballot Type : " + ballot.getType() + "\nId : " + ballot.getId()
			+ "\nAddress : " + ballot.getAddress());
			VBox vBallotDetails = new VBox();
			vBallotDetails.getChildren().addAll(lblBallotDetails, tpShowCitizens);
			TitledPane ballotPane = new TitledPane(ballot.getClass().getSimpleName() + " number " + ballot.getId(),
					vBallotDetails);
			acrBallot.getPanes().add(ballotPane);
		}

		this.setAlignment(Pos.TOP_LEFT);
		this.getChildren().addAll(lblTitel, acrBallot);
	}

}
