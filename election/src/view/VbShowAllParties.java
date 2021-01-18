package view;

import java.util.Vector;

import Listrner.ViewListenable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import model.Party;

public class VbShowAllParties extends VBox {

	public VbShowAllParties(ViewListenable l) {
		Label lblTitel = new Label("All Parties :");

		Accordion acrParty = new Accordion();

		Vector<Party> allParties = l.viewAsksForPartyList();
		for (int i = 0; i < allParties.size(); i++) {
			Party party = allParties.get(i);
			TitledPane partyPane = new TitledPane(party.getName(), new Label(party.toString()));
			acrParty.getPanes().add(partyPane);
		}

		this.setAlignment(Pos.TOP_LEFT);
		this.getChildren().addAll(lblTitel, acrParty);
	}

}
