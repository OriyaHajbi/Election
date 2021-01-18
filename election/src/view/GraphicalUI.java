package view;

import javax.swing.JOptionPane;

public class GraphicalUI implements Messageable {

	@Override
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(null ,msg );
	}
	
	@Override
	public void showMessage(StringBuffer msg) {
		JOptionPane.showMessageDialog(null ,msg );
	}

	@Override
	public String getStrLineMessage(String msg) {
		return JOptionPane.showInputDialog(msg);
	}

	@Override
	public boolean getBooleanMessage(String msg) {
		String ans = getStrLineMessage(msg);
		if(ans.equalsIgnoreCase("true"))
			return true;
		else
			return false;
	}

	@Override
	public int getIntMessage(String msg) {
		String ans = getStrLineMessage(msg);
		return Integer.parseInt(ans);
	}

	@Override
	public void cleanBuffer() {
		// TODO Auto-generated method stub
		
	}
	

}
