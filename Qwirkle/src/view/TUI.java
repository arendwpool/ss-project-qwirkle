package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import models.Game;

public abstract class TUI implements Observer {

	private Game game;
	
	public TUI(Game game){
		this.game = game;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void start(){
		boolean terminated = false;
		while(terminated == false){
			
		}
	}
	
	public void renderBoard(){
		
	}
	
	/**
	 * 
	 * @param noMenuItems
	 * @param menuItems(op index 0 altijd een uitleg bij een menu! zonder uitleg null houden!
	 */
	public void renderMenu(int noMenuItems, String[] menuItems){ //TODO vervangen van Array?
		
	}
	
	public void determineOption(){
		
	}
	
}
