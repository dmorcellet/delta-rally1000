package delta.games.rally1000.gameplay.players;

import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.GameEngine;
import delta.games.rally1000.gameplay.Player;
import delta.games.rally1000.gameplay.actions.AbstractAction;

/**
 * Base class for rally-1000 player implementations.
 * @author DAM
 */
public abstract class AbstractPlayerImpl
{
  private GameEngine _engine;
  private Player _player;

  /**
   * Constructor.
   * @param engine Game engine.
   * @param player Player.
   */
  public AbstractPlayerImpl(GameEngine engine, Player player)
  {
    _engine=engine;
    _player=player;
  }

  public GameEngine getGameEngine()
  {
    return _engine;
  }

  public Game getGame()
  {
    return _engine.getGame();
  }

  public Player getPlayer()
  {
    return _player;
  }

  public abstract AbstractAction chooseAction();
}
