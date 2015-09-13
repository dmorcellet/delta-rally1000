package delta.games.rally1000.gameplay;

/**
 * Represents a player.
 * @author DAM
 */
public class Player
{
  private String _name;
  private Team _team;
  private PlayersHand _hand;

  /**
   * Constructor.
   * @param name Player name.
   */
  public Player(String name)
  {
    _name=name;
    _team=null;
    _hand=null;
  }

  /**
   * Get the name of this player.
   * @return a player name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the team of this player.
   * @return a team or <code>null</code> if no associated team.
   */
  public Team getTeam()
  {
    return _team;
  }

  /**
   * Set the team for this player.
   * @param team Team to set.
   */
  public void setTeam(Team team)
  {
    _team=team;
  }

  /**
   * Get the current hand of this player.
   * @return A player hand.
   */
  public PlayersHand getHand()
  {
    return _hand;
  }

  /**
   * Set the player hand.
   * @param hand Hand to set.
   */
  public void setHand(PlayersHand hand)
  {
    _hand=hand;
  }

  @Override
  public String toString()
  {
    return "Player ["+_name+"] "+_team;
  }
}
