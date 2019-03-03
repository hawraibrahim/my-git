/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.controllers.Evaluation;

import pacman.game.Game;
import pacman.game.Constants.DM;

// Consists of Two Methods 
// 1) EvaluateGameState() : 
// 2) GetBestMove() :

public class Evaluation {

	// if true Pac-Man goes for level completion than high score
	// private static final boolean COMPLETE_LEVEL = true;
	private static final int MIN_GHOST_DISTANCE = 20;
	// private static final int MIN_EDIBLE_GHOST_DISTANCE = 100;

	// The maximum depth of the three that Astar is going to use
	public static final int DEPTH = 15;
	public static final int DISTANCEPILLS=200;
	public static final int VALUELIFE=100000;
	public static final int VALUESCORE=100;
	
	

	public static int evaluateGameState(Game gameState) {
		int pacmanNode = gameState.getPacmanCurrentNodeIndex();

		int distanceFromGhost = 0;

		int shortestEdibleGhostDistance = Integer.MAX_VALUE, shortestGhostDistance = Integer.MAX_VALUE,
				secondShortestGhostDistance = Integer.MAX_VALUE;

		// with this for MsPacman avoid better the ghosts, but in the end this is not so
		// helpful, should be implemented better and without errors
		/*
		 * for (GHOST ghost : GHOST.values()) {
		 * 
		 * 
		 * int distance = gameState.getShortestPathDistance(pacmanNode,
		 * gameState.getGhostCurrentNodeIndex(ghost));
		 * 
		 * 
		 * if (gameState.isGhostEdible(ghost)) { if (distance <
		 * shortestEdibleGhostDistance) { shortestEdibleGhostDistance = distance; } }
		 * else { if (distance < shortestGhostDistance) { secondShortestGhostDistance =
		 * shortestGhostDistance; shortestGhostDistance = distance; } } }
		 */

	/*	if (shortestGhostDistance != Integer.MAX_VALUE && shortestGhostDistance != -1
				&& shortestGhostDistance < MIN_GHOST_DISTANCE) {
			if (secondShortestGhostDistance != Integer.MAX_VALUE && secondShortestGhostDistance != -1
					&& secondShortestGhostDistance < MIN_GHOST_DISTANCE) {

				int avgGhostDistance = (shortestGhostDistance + secondShortestGhostDistance) / 2;
				distanceFromGhost += avgGhostDistance * 10000;
			} else {
				// increase heuristic the farther Ms Pacman is from the nearest ghost
				distanceFromGhost += shortestGhostDistance * 10000;
			}
		} else {

			// this prevents Ms Pacman from staying near MIN_GHOST_DISTANCE
			distanceFromGhost += (MIN_GHOST_DISTANCE + 10) * 10000;
		}*/

		// it updates the pill's indexes
		int[] activePillIndices = gameState.getActivePillsIndices();
		int[] activePowerPillIndices = gameState.getActivePowerPillsIndices();
		int[] pillIndices = new int[activePillIndices.length + activePowerPillIndices.length];
		System.arraycopy(activePillIndices, 0, pillIndices, 0, activePillIndices.length);
		System.arraycopy(activePowerPillIndices, 0, pillIndices, activePillIndices.length,
				activePowerPillIndices.length);

		int shortestPillDistance = gameState.getShortestPathDistance(pacmanNode,
				gameState.getClosestNodeIndexFromNodeIndex(pacmanNode, pillIndices, DM.PATH));

		int random = (int) Math.random();
		//System.out.println(gameState.getScore() * VALUESCORE + gameState.getPacmanNumberOfLivesRemaining() * VALUELIFE);
		return gameState.getScore() * VALUESCORE + gameState.getPacmanNumberOfLivesRemaining() * VALUELIFE
				+ (DISTANCEPILLS - shortestPillDistance);

		// other euristhics that have to be fixed
		// return distanceFromGhost + gameState.getScore() * 5 +
		// (4-gameState.getNumberOfActivePowerPills())*800 +
		// gameState.getPacmanNumberOfLivesRemaining() * 10000 + (200 -
		// shortestPillDistance);
		// return distanceFromGhost + (4-gameState.getNumberOfActivePowerPills())*800 +
		// gameState.getPacmanNumberOfLivesRemaining() * 10000;

	}

}