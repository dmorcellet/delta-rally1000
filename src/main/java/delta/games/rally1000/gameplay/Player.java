package delta.games.rally1000.gameplay;

public class Player
{
  private String _name;
  private Team _team;
  private PlayersHand _hand;

  public Player(String name)
  {
    _name=name;
    _team=null;
    _hand=null;
  }

  public String getName()
  {
    return _name;
  }

  public Team getTeam()
  {
    return _team;
  }

  public void setTeam(Team equipe)
  {
    _team=equipe;
  }

  public PlayersHand lireMain()
  {
    return _hand;
  }

  public void fixerMain(PlayersHand main)
  {
    _hand=main;
  }

  @Override
  public String toString()
  {
    return "Joueur ["+_name+"] "+_team;
  }
}
