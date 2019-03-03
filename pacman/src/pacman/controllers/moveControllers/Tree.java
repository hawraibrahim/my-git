/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.controllers.moveControllers;

import java.util.ArrayList;
import java.util.EnumMap;

import pacman.controllers.Evaluation.Evaluation;
import pacman.game.Game;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

// creates a tree for the Astar search
public class Tree {

	private Node headNode;
	private int nodeExpanded = 0;
	private int prof;
	private MOVE move;
	private final int MALUS = 7500;
	private final int MAXNUMBERNODE = 200;// 100
	private final int ARRAYLENGTH = 40000;
	ArrayList<Node> allNodes = new ArrayList<Node>(ARRAYLENGTH);
	

	public Tree(int depth, Game game, EnumMap<GHOST, MOVE> ghostMoves) {
		headNode = new Node();
		prof = depth;

		ArrayList<Node> currentDepthNodes = new ArrayList<Node>();
		ArrayList<Node> nextDepthNodes = new ArrayList<Node>();
		currentDepthNodes.add(headNode);

		// here a create 4 different copies of the actual game
		Game copy, copy1, copy2, copy3 = game.copy();

		Node node = headNode;
		headNode.setActivePills(game.getActivePillsIndices());
		headNode.setPosition(game.getPacmanCurrentNodeIndex());

		copy = game.copy();
		copy1 = game.copy();
		copy2 = game.copy();
		copy3 = game.copy();

		// and for every direction i try to search which is the best direction to take
		Node left = new Node(MOVE.LEFT, node);
		copy.advanceGame(MOVE.LEFT, ghostMoves);
		left.setValue((Evaluation.evaluateGameState(copy)));
		left.setTotalScore(left.getValue());
		left.setGameState(copy);
		left.setDepth(1);
		// i put the move inside the node because at the end i need to know what move i
		// have to do
		left.setMove(MOVE.LEFT);
		left.setActivePills(copy.getActivePillsIndices());
		left.setPosition(copy.getPacmanCurrentNodeIndex());

		Node right = new Node(MOVE.RIGHT, node);
		copy1.advanceGame(MOVE.RIGHT, ghostMoves);
		right.setValue(Evaluation.evaluateGameState(copy1));
		right.setTotalScore(right.getValue());
		right.setGameState(copy1);
		right.setDepth(1);
		right.setMove(MOVE.RIGHT);
		right.setActivePills(copy1.getActivePillsIndices());
		right.setPosition(copy1.getPacmanCurrentNodeIndex());

		Node up = new Node(MOVE.UP, node);
		copy2.advanceGame(MOVE.UP, ghostMoves);
		up.setValue(Evaluation.evaluateGameState(copy2));
		up.setTotalScore(up.getValue());
		up.setGameState(copy2);
		up.setDepth(1);
		up.setMove(MOVE.UP);
		up.setActivePills(copy2.getActivePillsIndices());
		up.setPosition(copy2.getPacmanCurrentNodeIndex());

		Node down = new Node(MOVE.DOWN, node);
		copy3.advanceGame(MOVE.DOWN, ghostMoves);
		down.setValue(Evaluation.evaluateGameState(copy3));
		down.setTotalScore(down.getValue());
		down.setGameState(copy3);
		down.setDepth(1);
		down.setMove(MOVE.DOWN);
		down.setActivePills(copy3.getActivePillsIndices());
		down.setPosition(copy3.getPacmanCurrentNodeIndex());

		nextDepthNodes.add(left);
		nextDepthNodes.add(right);
		nextDepthNodes.add(up);
		nextDepthNodes.add(down);

		//INITIAL STATE PROBLEM
		ArrayList<Node> fringe = new ArrayList<Node>(ARRAYLENGTH);
		ArrayList<Node> closed = new ArrayList<Node>(ARRAYLENGTH);
		fringe.add(left);
		fringe.add(right);
		fringe.add(up);
		fringe.add(down);
		allNodes=fringe;
		nodeExpanded++;

		// here i call the recursive function that helps me to spread until i reach the
		// max number of node expansions that i want to do
		move = fun(fringe, ghostMoves, closed);
		// System.gc();

	}

	private MOVE fun(ArrayList<Node> fringe, EnumMap<GHOST, MOVE> ghostMoves, ArrayList<Node> closed) {
		int maxTotalScore = 0;
		Node nodeToExpand = new Node();

		// if the fringe is empty, exit with error
		if (fringe.isEmpty())
			System.exit(0);

		// i find the node to expand that has not been expanded yet and that has the
		// maximum Total Score 
		for (Node node : fringe) {
			if (node.getTotalScore() > maxTotalScore) {
				maxTotalScore = node.getTotalScore();
				nodeToExpand = node;
			}
		}

		fringe.remove(nodeToExpand);

		// if i reach the max depth, i simply return the original move of the branch in
		// which i'm inside
		/*
		 * if (nodeToExpand.getDepth() == prof) return nodeToExpand.getMove();
		 */

		// GOAL TEST
		// if i have expanded the MAX number of nodes, i search the nodes with the
		// better Total Score
		maxTotalScore = 0;
		Node nodeFound = new Node();
		if (nodeExpanded >= MAXNUMBERNODE) {
			for (Node node2 : allNodes) {
				if (node2.getTotalScore() > maxTotalScore) {
					maxTotalScore = node2.getTotalScore();
					nodeFound = node2;
				}
			}
			return nodeFound.getMove();
		}

		boolean nodeInClosed = false;

		// control with the aim of avoiding the expanding of nodes that i have already seen
		// if i have the same pills in the maze and i have the same position of
		// Ms-Pacman, i have the same node
		if (!closed.isEmpty()) {
			for (Node node : closed) {
				if (node.getActivePills().equals(nodeToExpand.getActivePills())
						&& node.getPosition() == nodeToExpand.getPosition()) {
					nodeInClosed = true;
				}
			}
		}

		if (!nodeInClosed) {

			closed.add(nodeToExpand);

			Game copy = nodeToExpand.getGameState().copy();
			Game copy1 = nodeToExpand.getGameState().copy();
			Game copy2 = nodeToExpand.getGameState().copy();
			Game copy3 = nodeToExpand.getGameState().copy();

		//	System.out.println(Evaluation.evaluateGameState(copy));
			

			Node left = new Node(MOVE.LEFT, nodeToExpand);
			copy.advanceGame(MOVE.LEFT, ghostMoves);
			left.setValue(Evaluation.evaluateGameState(copy));
			left.setTotalScore(
					left.getValue() + nodeToExpand.getTotalScore() - MALUS * (nodeToExpand.getDepth() + 1));
			left.setGameState(copy);
			left.setDepth(nodeToExpand.getDepth() + 1);
			// i pass the type of move from the ancestor to the child, so i know in every
			// moment in which branch i am
			left.setMove(nodeToExpand.getMove());
			left.setActivePills(copy.getActivePillsIndices());
			left.setPosition(copy.getPacmanCurrentNodeIndex());

			Node right = new Node(MOVE.RIGHT, nodeToExpand);
			copy1.advanceGame(MOVE.RIGHT, ghostMoves);
			right.setValue(Evaluation.evaluateGameState(copy1));
			right.setTotalScore(
					right.getValue() + nodeToExpand.getTotalScore() - MALUS * (nodeToExpand.getDepth() + 1));
			right.setGameState(copy1);
			right.setDepth(nodeToExpand.getDepth() + 1);
			right.setMove(nodeToExpand.getMove());
			right.setActivePills(copy1.getActivePillsIndices());
			right.setPosition(copy1.getPacmanCurrentNodeIndex());

			Node up = new Node(MOVE.UP, nodeToExpand);
			copy2.advanceGame(MOVE.UP, ghostMoves);
			up.setValue(Evaluation.evaluateGameState(copy2));
			up.setTotalScore(
					up.getValue() + nodeToExpand.getTotalScore() - MALUS * (nodeToExpand.getDepth() + 1));
			up.setGameState(copy2);
			up.setDepth(nodeToExpand.getDepth() + 1);
			up.setMove(nodeToExpand.getMove());
			up.setActivePills(copy2.getActivePillsIndices());
			up.setPosition(copy2.getPacmanCurrentNodeIndex());

			Node down = new Node(MOVE.DOWN, nodeToExpand);
			copy3.advanceGame(MOVE.DOWN, ghostMoves);
			down.setValue(Evaluation.evaluateGameState(copy3));
			down.setTotalScore(
					down.getValue() + nodeToExpand.getTotalScore() - MALUS * (nodeToExpand.getDepth() + 1));
			down.setGameState(copy3);
			down.setDepth(nodeToExpand.getDepth() + 1);
			down.setMove(nodeToExpand.getMove());
			down.setActivePills(copy3.getActivePillsIndices());
			down.setPosition(copy3.getPacmanCurrentNodeIndex());

			fringe.add(left);
			fringe.add(right);
			fringe.add(up);
			fringe.add(down);
			nodeExpanded++;

			
			allNodes.add(left);
			allNodes.add(right);
			allNodes.add(up);
			allNodes.add(down);
		}

		// i call the recursive function
		return fun(fringe, ghostMoves, closed);

	}

	public int getCont() {
		return nodeExpanded;
	}

	public Node getHeadNode() {
		return headNode;
	}

	public MOVE getMove() {
		return move;
	}

	public void setMove(MOVE move) {
		this.move = move;
	}
}