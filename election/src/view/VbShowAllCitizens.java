package view;

import Listrner.ViewListenable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import model.Citizen;
import model.Set;

public class VbShowAllCitizens extends VBox {

	public VbShowAllCitizens(ViewListenable l) {
		Label lblTitel = new Label("All Citizens :");

		Accordion acrCitizen = new Accordion();

		Set<Citizen> allCitizens = l.viewAsksForCitizenList();
		for (int i = 0; i < allCitizens.size(); i++) {
			Citizen citizen = allCitizens.get(i);
			TitledPane partyPane = new TitledPane(
					(citizen.getClass().getSimpleName() + " " + citizen.getName() + " " + citizen.getId()),
					new Label(citizen.toString()));
			acrCitizen.getPanes().add(partyPane);
		}

		this.setAlignment(Pos.TOP_LEFT);
		this.getChildren().addAll(lblTitel, acrCitizen);
	}

}
