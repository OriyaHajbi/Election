package view;

import java.util.Scanner;


public class ConsoleUI implements Messageable  {

	private Scanner scan = new Scanner(System.in);

	public void cleanBuffer() {
		scan.nextLine();
	}

	@Override
	public void showMessage(String msg) {
		System.out.println(msg);
	}

	@Override
	public void showMessage(StringBuffer msg) {
		System.out.println(msg);
	}

	@Override
	public String getStrLineMessage(String msg) {
		System.out.println(msg);
		String str =scan.nextLine();		
		return str;
	}

	@Override
	public boolean getBooleanMessage(String msg)  {
		System.out.println(msg);
		return scan.nextBoolean();
		
	}
	

	@Override
	public int getIntMessage(String msg) {
		System.out.println(msg);
		int num =scan.nextInt();
		cleanBuffer();
		return num;
	}

}
