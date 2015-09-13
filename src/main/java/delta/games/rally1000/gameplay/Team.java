package delta.games.rally1000.gameplay;

/**
 * Represents a rally-1000 team.
 * @author DAM
 */
public class Team
{
  private static final int MAX_PLAYERS_IN_A_TEAM=2;
  private String _name;
  private Player[] _players;
  private int _nbPlayers;
  private ExposedCards _exposedCards;

  /**
   * Constructor.
   * @param name Name of this team.
   */
  public Team(String name)
  {
    _name=name;
    _players=new Player[MAX_PLAYERS_IN_A_TEAM];
    for(int i=0;i<MAX_PLAYERS_IN_A_TEAM;i++)
    {
      _players[i]=null;
    }
    _nbPlayers=0;
    _exposedCards=null;
  }

  /**
   * Add a player in this team.
   * @param player Player to add.
   */
  public void addPlayer(Player player)
  {
    if(_nbPlayers<MAX_PLAYERS_IN_A_TEAM)
    {
      _players[_nbPlayers]=player;
      player.setTeam(this);
      _nbPlayers++;
    }
  }

  /**
   * Set the cards setup for this team.
   * @param exposedCards Setup to associate to this team.
   */
  public void setCards(ExposedCards exposedCards)
  {
    _exposedCards=exposedCards;
  }

  /**
   * Get the cards setup for this team.
   * @return the cards setup for this team.
   */
  public ExposedCards getExposedCards()
  {
    return _exposedCards;
  }

  @Override
  public String toString()
  {
    return "Team ["+_name+"]";
  }
}
