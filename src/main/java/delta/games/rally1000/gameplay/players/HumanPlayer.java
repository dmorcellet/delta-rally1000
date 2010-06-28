package delta.games.rally1000.gameplay.players;

import delta.games.rally1000.gameplay.GameEngine;
import delta.games.rally1000.gameplay.Player;
import delta.games.rally1000.gameplay.actions.AbstractAction;

/**
 * @author DAM
 */
public class HumanPlayer extends AbstractPlayerImpl
{
  public HumanPlayer(GameEngine engine, Player player)
  {
    super(engine,player);
  }

  @Override
  public AbstractAction chooseAction()
  {
    GameEngine engine=getGameEngine();
    AbstractAction action=engine.waitForPlayerAction();
    return action;
  }
}
