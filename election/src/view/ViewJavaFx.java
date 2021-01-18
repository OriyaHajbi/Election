package view;

import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import Listrner.ViewListenable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Party;

public class ViewJavaFx implements Viewable {

	private Vector<ViewListenable> allListeners;
	private BorderPane bpRoot;
	private Vector<Button> btnAllButtons;
	private final int ADD_BALLOT = 0;
	private final int ADD_CITIZEN = 1;
	private final int ADD_PARTY = 2;
	private final int ADD_APPLICANT = 3;
	private final int SHOW_BALLOTS = 4;
	private final int SHOW_CITIZENS = 5;
	private final int SHOW_PARTIES = 6;
	private final int VOTING_DAY = 7;
	private final int SHOW_RESULTS = 8;
	private final int EXIT = 9;


	public ViewJavaFx(Stage theStage) {


		allListeners = new Vector<ViewListenable>();
		bpRoot = new BorderPane();
		bpRoot.setPadding(new Insets(20));

		Text lblTop = new Text("ELECTION  ROUND  SYSTEM");
		lblTop.setFill(Color.DIMGRAY);

		lblTop.setTextOrigin(VPos.CENTER);
		lblTop.setFont(Font.font("Cooper Black", FontWeight.BOLD, 40));
		VBox vTop = new VBox();
		vTop.setPrefHeight(100);
		vTop.getChildren().add(lblTop);
		bpRoot.setTop(vTop);

		bpRoot.setLeft(createManu());
		btnAllButtons.get(ADD_BALLOT).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				GridPane grpAddBallot = new GridPane();
				grpAddBallot.setAlignment(Pos.TOP_LEFT);
				grpAddBallot.setHgap(15);
				grpAddBallot.setVgap(10);

				Label lblTitelBallot = new Label("To add a new ballot please fill the details:");
				Label lblBallotAddress = new Label("Ballot Address: ");
				TextField txfBallotAddress = new TextField();
				txfBallotAddress.setPrefWidth(150);
				Label lblBallotType = new Label("Ballot Type: ");
				ComboBox<String> comboBallotBox = new ComboBox<String>();
				comboBallotBox.setPrefWidth(150);
				comboBallotBox.getItems().add("CoronaSoldier");
				comboBallotBox.getItems().add("Soldier");
				comboBallotBox.getItems().add("CoronaCitizen");
				comboBallotBox.getItems().add("Citizen");

				Button btnEnter = new Button("Enter");
				btnEnter.setPrefSize(50, 50);

				grpAddBallot.add(lblTitelBallot, 0, 0, 2, 1);
				grpAddBallot.add(lblBallotAddress, 0, 3);
				grpAddBallot.add(txfBallotAddress, 1, 3);
				grpAddBallot.add(lblBallotType, 0, 4);
				grpAddBallot.add(comboBallotBox, 1, 4);
				grpAddBallot.add(btnEnter, 0, 8);

				bpRoot.setCenter(grpAddBallot);

				btnEnter.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {

						String address = txfBallotAddress.getText();
						int numType = comboBallotBox.getSelectionModel().getSelectedIndex() + 1;

						for (ViewListenable l : allListeners) {
							l.viewUpdateOnAddBallot(address, numType);
						}
						txfBallotAddress.setText("");
						comboBallotBox.setValue(null);
						btnAllButtons.get(SHOW_RESULTS).setVisible(false);
						btnAllButtons.get(VOTING_DAY).setDisable(false);
					}
				});

			}
		});

		btnAllButtons.get(ADD_CITIZEN).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				GridPane grpAddCitizen = new GridPane();

				Label lblTitel = new Label("To add a new citizen please fill the details:");
				Label lblName = new Label("Citizen name : ");
				TextField txfName = new TextField();
				txfName.setPrefWidth(150);
				Label lblId = new Label("Citizen id : ");
				TextField txfId = new TextField();
				txfId.setPrefWidth(150);
				Label lblDate = new Label("Citizen birthday : ");
				DatePicker dPDate = new DatePicker();
				dPDate.setPrefWidth(150);
				CheckBox ckbIsolated = new CheckBox("Citizen isolated");
				Label lblDaysInIsolated = new Label("how much days you are insulator?");
				TextField txfDaysInIsolated = new TextField();
				txfDaysInIsolated.setPrefWidth(150);
				lblDaysInIsolated.setVisible(false);
				txfDaysInIsolated.setVisible(false);

				ckbIsolated.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						lblDaysInIsolated.setVisible(ckbIsolated.isSelected());
						txfDaysInIsolated.setVisible(ckbIsolated.isSelected());
					}
				});

				Button btnEnter = new Button("Enter");
				btnEnter.setPrefSize(50, 50);
				btnEnter.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						String name = txfName.getText();
						String id = txfId.getText();
						int yearOfBirth = dPDate.getValue().getYear();
						boolean isIsolation = ckbIsolated.isSelected();
						int daysOfSick = 0;
						if (ckbIsolated.isSelected())
							daysOfSick = Integer.parseInt(txfDaysInIsolated.getText());

						for (ViewListenable l : allListeners) {
							l.viewUpdateOnAddCitizen(name, id, yearOfBirth, isIsolation, null, daysOfSick);
						}
						txfName.setText("");
						txfId.setText("");
						ckbIsolated.setSelected(false);
						lblDaysInIsolated.setVisible(false);
						txfDaysInIsolated.setText("");
						txfDaysInIsolated.setVisible(false);
						dPDate.getEditor().clear();
						btnAllButtons.get(SHOW_RESULTS).setVisible(false);
						btnAllButtons.get(VOTING_DAY).setDisable(false);
					}
				});

				grpAddCitizen.add(lblTitel, 0, 0, 2, 1);
				grpAddCitizen.add(lblName, 0, 3);
				grpAddCitizen.add(txfName, 1, 3);
				grpAddCitizen.add(lblId, 0, 4);
				grpAddCitizen.add(txfId, 1, 4);
				grpAddCitizen.add(lblDate, 0, 5);
				grpAddCitizen.add(dPDate, 1, 5);
				grpAddCitizen.add(ckbIsolated, 0, 6);
				grpAddCitizen.add(lblDaysInIsolated, 0, 7);
				grpAddCitizen.add(txfDaysInIsolated, 1, 7);
				grpAddCitizen.add(btnEnter, 0, 10);

				grpAddCitizen.setHgap(15);
				grpAddCitizen.setVgap(10);
				grpAddCitizen.setAlignment(Pos.TOP_LEFT);
				bpRoot.setCenter(grpAddCitizen);
			}
		});

		btnAllButtons.get(ADD_PARTY).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				GridPane grpAddParty = new GridPane();

				Label lblTitelParty = new Label("To add a new Party please fill the details:");
				Label lblPartyName = new Label("Party name: ");
				TextField txfPartyName = new TextField();
				txfPartyName.setPrefWidth(150);
				Label lblPartyDate = new Label("when was it created : ");
				DatePicker dPPartyDate = new DatePicker();
				dPPartyDate.setPrefWidth(150);
				Label lblPartyType = new Label("Party Type: ");
				ComboBox<String> comboPartyBox = new ComboBox<String>();
				comboPartyBox.setPrefWidth(150);
				comboPartyBox.getItems().add("Right");
				comboPartyBox.getItems().add("Center");
				comboPartyBox.getItems().add("Left");
				Label lblPartyDescription = new Label("Party description: ");
				TextField txtDescription = new TextField();
				txtDescription.setPrefSize(150, 60);

				Button btnEnter = new Button("Enter");
				btnEnter.setPrefSize(50, 50);
				btnEnter.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {

						String partyName = txfPartyName.getText();
						int year = dPPartyDate.getValue().getYear();
						String numType = (String) comboPartyBox.getSelectionModel().getSelectedItem();
						String description = txtDescription.getText();

						for (ViewListenable l : allListeners) {
							l.viewUpdateOnAddParty(partyName, year, numType, description);
						}

						txfPartyName.setText("");
						txtDescription.setText("");
						dPPartyDate.getEditor().clear();
						comboPartyBox.setValue(null);
						btnAllButtons.get(SHOW_RESULTS).setVisible(false);
						btnAllButtons.get(VOTING_DAY).setDisable(false);
					}
				});

				grpAddParty.add(lblTitelParty, 0, 0, 2, 1);
				grpAddParty.add(lblPartyName, 0, 3);
				grpAddParty.add(txfPartyName, 1, 3);
				grpAddParty.add(lblPartyDate, 0, 4);
				grpAddParty.add(dPPartyDate, 1, 4);
				grpAddParty.add(lblPartyType, 0, 5);
				grpAddParty.add(comboPartyBox, 1, 5);
				grpAddParty.add(lblPartyDescription, 0, 6);
				grpAddParty.add(txtDescription, 1, 6 , 1 , 3);
				grpAddParty.add(btnEnter, 0, 11);

				grpAddParty.setVgap(10);
				grpAddParty.setHgap(15);
				grpAddParty.setAlignment(Pos.TOP_LEFT);
				bpRoot.setCenter(grpAddParty);
			}
		});

		btnAllButtons.get(ADD_APPLICANT).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				GridPane grpAddApplicant = new GridPane();

				Label lblTitel = new Label("To add a new applicant please fill the details:");
				Label lblName = new Label("Applicant name : ");
				TextField txfName = new TextField();
				txfName.setPrefWidth(150);
				Label lblId = new Label("Applicant id : ");
				TextField txfId = new TextField();
				txfId.setPrefWidth(150);
				Label lblDate = new Label("Applicant birthday : ");
				DatePicker dPDate = new DatePicker();
				dPDate.setPrefWidth(150);
				CheckBox ckbIsolated = new CheckBox("Applicant isolated");
				Label lblDaysInIsolated = new Label("how much days you are insulator?");
				TextField txfDaysInIsolated = new TextField();
				txfDaysInIsolated.setPrefWidth(150);
				lblDaysInIsolated.setVisible(false);
				txfDaysInIsolated.setVisible(false);
				Label lblParty = new Label("Party: ");
				ComboBox<String> comboPartyBox = new ComboBox<String>();
				comboPartyBox.setPrefWidth(150);

				for (int i = 0; i < allListeners.get(0).viewAsksForPartyList().size(); i++) {
					comboPartyBox.getItems().add(allListeners.get(0).viewAsksForPartyList().get(i).getName());
				}

				ckbIsolated.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						lblDaysInIsolated.setVisible(ckbIsolated.isSelected());
						txfDaysInIsolated.setVisible(ckbIsolated.isSelected());
					}
				});

				Button btnEnter = new Button("Enter");
				btnEnter.setPrefSize(50, 50);
				btnEnter.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						String name = txfName.getText();
						String id = txfId.getText();
						int yearOfBirth = dPDate.getValue().getYear();
						boolean isIsolation = ckbIsolated.isSelected();
						int daysOfSick = 0;
						if (ckbIsolated.isSelected())
							daysOfSick = Integer.parseInt(txfDaysInIsolated.getText());
						Party party = allListeners.get(0).viewAsksForParty(comboPartyBox.getValue());

						for (ViewListenable l : allListeners) {
							l.viewUpdateOnAddCitizen(name, id, yearOfBirth, isIsolation, party, daysOfSick);
						}
						
						txfName.setText("");
						txfId.setText("");
						ckbIsolated.setSelected(false);
						lblDaysInIsolated.setVisible(false);
						txfDaysInIsolated.setText("");
						txfDaysInIsolated.setVisible(false);
						dPDate.getEditor().clear();
						btnAllButtons.get(SHOW_RESULTS).setVisible(false);
						btnAllButtons.get(VOTING_DAY).setDisable(false);
						comboPartyBox.setValue(null);
					}
				});

				grpAddApplicant.add(lblTitel, 0, 0, 2, 1);
				grpAddApplicant.add(lblName, 0, 3);
				grpAddApplicant.add(txfName, 1, 3);
				grpAddApplicant.add(lblId, 0, 4);
				grpAddApplicant.add(txfId, 1, 4);
				grpAddApplicant.add(lblDate, 0, 5);
				grpAddApplicant.add(dPDate, 1, 5);
				grpAddApplicant.add(lblParty, 0, 6);
				grpAddApplicant.add(comboPartyBox, 1, 6);
				grpAddApplicant.add(ckbIsolated, 0, 7);
				grpAddApplicant.add(lblDaysInIsolated, 0, 8);
				grpAddApplicant.add(txfDaysInIsolated, 1, 8);
				grpAddApplicant.add(btnEnter, 0, 11);

				grpAddApplicant.setHgap(15);
				grpAddApplicant.setVgap(10);
				grpAddApplicant.setAlignment(Pos.TOP_LEFT);
				bpRoot.setCenter(grpAddApplicant);
			}
		});

		btnAllButtons.get(SHOW_BALLOTS).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				bpRoot.setRight(null);
				VBox vBallot = new VbShowAllBallots(allListeners.get(0));
				ScrollPane scpBallot = new ScrollPane();
				scpBallot.setContent(vBallot);
				bpRoot.setCenter(scpBallot);
			}
		});

		btnAllButtons.get(SHOW_CITIZENS).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				VBox vCitizen = new VbShowAllCitizens(allListeners.get(0));
				ScrollPane scpCitizen = new ScrollPane();
				scpCitizen.setContent(vCitizen);
				bpRoot.setCenter(scpCitizen);
			}
		});

		btnAllButtons.get(SHOW_PARTIES).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				bpRoot.setRight(null);
				VBox vPatry = new VbShowAllParties(allListeners.get(0));
				ScrollPane scpParty = new ScrollPane();
				scpParty.setContent(vPatry);
				bpRoot.setCenter(scpParty);
			}
		});

		btnAllButtons.get(VOTING_DAY).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				for (ViewListenable l : allListeners) {
					l.viewUpdateOnVotingDay();
				}
				btnAllButtons.get(SHOW_RESULTS).setVisible(true);
				btnAllButtons.get(VOTING_DAY).setDisable(true);
				VBox vVotingDay = new VBox(new Label("It is the voting day!\nTake a day of rest"));
				vVotingDay.setAlignment(Pos.TOP_LEFT);
				bpRoot.setCenter(vVotingDay);
			}
		});

		btnAllButtons.get(SHOW_RESULTS).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				PieChart pieResults = new PieChart();
				pieResults.getData().clear();
				ViewListenable l = allListeners.get(0);

				VBox vDetailsResults = new VBox(new Label(l.viewAsksForResultsData()));

				Vector<Party> allParties = l.viewAsksForPartyList();
				Vector<Integer> calculateResults = l.viewAsksForCalculateResults();

				for (int i = 0; i < allParties.size(); i++) {
					PieChart.Data sliceDetails = new PieChart.Data(
							allParties.get(i).getName(),
							calculateResults.elementAt(i));
					pieResults.getData().add(sliceDetails);
				}

				ScrollPane scpResultChart = new ScrollPane(pieResults);
				ScrollPane scpResultDetails = new ScrollPane(vDetailsResults);

				TabPane tabPaneResults = new TabPane();
				Tab tabChart = new Tab("Chart"  , scpResultChart);
				Tab tabDetails = new Tab("Details"  , scpResultDetails);
				tabPaneResults.getTabs().add(tabChart);
				tabPaneResults.getTabs().add(tabDetails);

				bpRoot.setCenter(tabPaneResults);
			}
		});

		btnAllButtons.get(EXIT).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (ViewListenable l : allListeners) {
					try {
						l.viewaskForSave();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				theStage.close();
			}
		});

		Scene scene = new Scene(bpRoot , 800 , 600);
//		theStage.setFullScreen(true);
		theStage.setScene(scene);
		theStage.setTitle("Election Round System");
		theStage.show();
	}

	private VBox createManu() {

		btnAllButtons = new Vector<Button>();
		btnAllButtons.addElement(new Button("Add ballot"));
		btnAllButtons.addElement(new Button("Add citizen"));
		btnAllButtons.addElement(new Button("Add party"));
		btnAllButtons.addElement(new Button("Add applicant"));
		btnAllButtons.addElement(new Button("Show all ballots"));
		btnAllButtons.addElement(new Button("Show all Citizens"));
		btnAllButtons.addElement(new Button("Show all parties"));
		btnAllButtons.addElement(new Button("Voting day"));
		btnAllButtons.addElement(new Button("Results"));
		btnAllButtons.addElement(new Button("Exit"));

		for(int i = 0 ; i<btnAllButtons.size() ; i++) {
			btnAllButtons.get(i).setPrefWidth(120);
		}

		btnAllButtons.get(SHOW_RESULTS).setVisible(false);		
		btnAllButtons.get(EXIT).setTextFill(Color.FIREBRICK);
		VBox vManu = new VBox();
		vManu.setPrefWidth(190);
		vManu.setSpacing(20);
		vManu.setAlignment(Pos.TOP_LEFT);
		vManu.getChildren().addAll(btnAllButtons);
		return vManu;
	}

	@Override
	public void registerListener(ViewListenable l) {
		allListeners.add(l);
	}

	@Override
	public void messageException(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void updateBallotIsAdd() {
		JOptionPane.showMessageDialog(null, "The ballot was successfully added");
	}

	@Override
	public void updateCitizenIsAdd() {
		JOptionPane.showMessageDialog(null, "The citizen was successfully added");
	}

	@Override
	public void updatePartyIsAdd() {
		JOptionPane.showMessageDialog(null, "The party was successfully added");
	}

	@Override
	public void updateApplicantIsAdd() {
		JOptionPane.showMessageDialog(null, "The applicant was successfully added");
	}



}
