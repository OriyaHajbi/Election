package view;

import Listrner.ViewListenable;

public interface Viewable {
	
	void registerListener(ViewListenable l);
	void messageException(String msg);
	public void updateBallotIsAdd();
	public void updateCitizenIsAdd();
	public void updatePartyIsAdd();
	public void updateApplicantIsAdd();


}
