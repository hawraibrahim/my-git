/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman.controllers.moveControllers;

import java.util.ArrayList;
import pacman.game.Constants.MOVE;
import pacman.game.Game;


//The base class to build a Tree
public class Node {
	
	private Game gameState;
	private ArrayList<Node> neighbors;
	private MOVE move;
	private int totalScore;
	private int depth;
	private int position;
	private int [] activePills;
	private int value;

	public Node() {
		this(MOVE.NEUTRAL, null);
	}
	
	public Node(MOVE move, Node predecessor) {
		this.move = move;
	}
	
	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<Node> neighbors) {
		this.neighbors = neighbors;
	}

	public MOVE getMove() {
		return move;
	}

	public void setMove(MOVE move) {
		this.move = move;
	}
	
	public Game getGameState() {
		return gameState;
	}
	
	public void setGameState(Game gameState) {
		this.gameState = gameState;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int cost) {
		this.totalScore = cost;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}


	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int [] getActivePills() {
		return activePills;
	}

	public void setActivePills(int [] activePills) {
		this.activePills = activePills;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
