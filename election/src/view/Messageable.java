package view;


public interface Messageable {

	void showMessage(String msg);
	void showMessage(StringBuffer msg);
	String getStrLineMessage(String msg);
	boolean getBooleanMessage(String msg);
	int getIntMessage(String msg);
	void cleanBuffer() ;


}

