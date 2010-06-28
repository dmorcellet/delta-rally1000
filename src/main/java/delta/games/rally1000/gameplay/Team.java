package delta.games.rally1000.gameplay;

/**
 * Represents a rally-1000 team.
 * @author DAM
 */
public class Team
{
  private static final int MAX_JOUEURS_DANS_EQUIPE=2;
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
    _players=new Player[MAX_JOUEURS_DANS_EQUIPE];
    for(int i=0;i<MAX_JOUEURS_DANS_EQUIPE;i++)
    {
      _players[i]=null;
    }
    _nbPlayers=0;
    _exposedCards=null;
  }

  public void addPlayer(Player joueur)
  {
    if(_nbPlayers<MAX_JOUEURS_DANS_EQUIPE)
    {
      _players[_nbPlayers]=joueur;
      joueur.setTeam(this);
      _nbPlayers++;
    }
  }

  public void fixerJeu(ExposedCards exposedCards)
  {
    _exposedCards=exposedCards;
  }

  public ExposedCards getExposedCards()
  {
    return _exposedCards;
  }

  @Override
  public String toString()
  {
    return "Equipe ["+_name+"]";
  }
}
