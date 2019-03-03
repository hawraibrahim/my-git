package pacman.entries.pacman;

import java.util.EnumMap;

import pacman.controllers.Controller;
import pacman.controllers.Evaluation.Evaluation;
import pacman.controllers.moveControllers.Tree;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>{
	
     public void myPacMan(){
          
     }
     
 @Override
	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
         
		// i take the moves of the ghost
		EnumMap<GHOST, MOVE> ghostMoves = new EnumMap<GHOST, MOVE>(GHOST.class);
		ghostMoves.put(GHOST.BLINKY, game.getGhostLastMoveMade(GHOST.BLINKY));
		ghostMoves.put(GHOST.INKY, game.getGhostLastMoveMade(GHOST.INKY));
		ghostMoves.put(GHOST.PINKY, game.getGhostLastMoveMade(GHOST.PINKY));
		ghostMoves.put(GHOST.SUE, game.getGhostLastMoveMade(GHOST.SUE));
		
		Tree tree = new Tree(Evaluation.DEPTH, game, ghostMoves);
		tree.getHeadNode().setGameState(game);

		//System.out.println(tree.getCont());
		
		return tree.getMove();

	}
}
