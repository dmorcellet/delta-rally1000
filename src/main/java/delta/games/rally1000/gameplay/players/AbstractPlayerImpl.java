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
  protected AbstractPlayerImpl(GameEngine engine, Player player)
  {
    _engine=engine;
    _player=player;
  }

  /**
   * Get the associated game engine.
   * @return the game engine.
   */
  public GameEngine getGameEngine()
  {
    return _engine;
  }

  /**
   * Get the associated game.
   * @return the associated game.
   */
  public Game getGame()
  {
    return _engine.getGame();
  }

  /**
   * Get the associated player.
   * @return the associated player.
   */
  public Player getPlayer()
  {
    return _player;
  }

  /**
   * Choose an action.
   * @return an action.
   */
  public abstract AbstractAction chooseAction();
}
