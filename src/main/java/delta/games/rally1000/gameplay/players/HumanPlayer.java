package delta.games.rally1000.gameplay.players;

import delta.games.rally1000.gameplay.GameEngine;
import delta.games.rally1000.gameplay.Player;
import delta.games.rally1000.gameplay.actions.AbstractAction;

/**
 * Human player.
 * @author DAM
 */
public class HumanPlayer extends AbstractPlayerImpl
{
  /**
   * Constructor.
   * @param engine Game engine.
   * @param player Player.
   */
  public HumanPlayer(GameEngine engine, Player player)
  {
    super(engine,player);
  }

  /**
   * Choose an action: wait for a player choice.
   */
  @Override
  public AbstractAction chooseAction()
  {
    GameEngine engine=getGameEngine();
    AbstractAction action=engine.waitForPlayerAction();
    return action;
  }
}
