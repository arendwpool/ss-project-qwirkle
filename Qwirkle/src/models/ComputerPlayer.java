package models;

import java.util.ArrayList;

public class ComputerPlayer implements Player {
	private static final String name = "Computer";
	private ArrayList<Tile> hand;
	
	public ComputerPlayer() {
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int determineMove() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ArrayList<Tile> getHand() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void makeMove() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void addScore() {
		// TODO Auto-generated method stub
		
	}
	
	public static void dostuff(int number){
		number = number /5;
	}
	public static int returnstuff(int number){
		dostuff(number);
		return number;
	}
	
	public static void main(String[] args){
		returnstuff(3);
	}
}
