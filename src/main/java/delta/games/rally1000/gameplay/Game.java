package delta.games.rally1000.gameplay;

import java.util.ArrayList;
import java.util.List;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.cards.Deck;
import delta.games.rally1000.gameplay.actions.PlayCardAction;

public class Game
{
  private GameParameters _parameters;
  private List<Team> _teams;
  private List<Player> _players;
  private Deck _deck;
  private ExposedCards[] _shownCards;
  private PlayersHand[] _hands;
  private PilesManager _pilesManager;
  private ScoreComputer _scoreComputer;

  /**
   * Constructor.
   * @param params Game parameters.
   * @param teams Involved teams.
   * @param players Involved players.
   * @param deck Cards deck.
   */
  public Game(GameParameters params, List<Team> teams, List<Player> players, Deck deck)
  {
    _parameters=params;
    _teams=new ArrayList<Team>(teams);
    _players=new ArrayList<Player>(players);
    _deck=deck;
    _shownCards=new ExposedCards[teams.size()];
    _hands=new PlayersHand[players.size()];
    _pilesManager=new PilesManager();
    _scoreComputer=new DefaultScoreComputer();
  }

  public void start()
  {
    _deck.shuffle();
    _deck.cut();
    for(int i=0;i<_teams.size();i++)
    {
      _shownCards[i]=new ExposedCards(_parameters, _teams.get(i), _pilesManager);
      _teams.get(i).fixerJeu(_shownCards[i]);
    }
    for(int i=0;i<_players.size();i++)
    {
      _hands[i]=new PlayersHand(_players.get(i));
    }
    _pilesManager.putCards(_deck.popAllCards());
    distribute();
  }

  public void distribute()
  {
    int nbCards=_parameters.getNumberOfCardsAtStartup();
    for(int i=0;i<nbCards;i++)
    {
      for(int j=0;j<_players.size();j++)
      {
        Card carte=_pilesManager.drawCard();
        _hands[j].addCard(carte);
      }
    }
    /*
    for(int i=0;i<_players.size();i++)
    {
      System.out.println(_hands[i]);
    }
    */
  }

  public PlayCardAction buildAction(Team sourceTeam, Card card)
  {
    ExposedCards myCards=sourceTeam.getExposedCards();
    if (myCards.canPut(card))
    {
      return new PlayCardAction(sourceTeam,card);
    }
    for(Team team : _teams)
    {
      ExposedCards otherTeamShownCards;
      if (team!=sourceTeam)
      {
        otherTeamShownCards=team.getExposedCards();
        if (otherTeamShownCards.estPosableParAdversaire(card))
        {
          return new PlayCardAction(team,card);
        }
      }
    }
    return null;
  }

  public void returnCardsToDeck()
  {
    // Retrieve cards from piles
    List<Card> pilesCards=_pilesManager.removeAllCards();
    _deck.putCards(pilesCards);
    // Retrieve cards from teams
    for(Team team : _teams)
    {
      ExposedCards shownCards=team.getExposedCards();
      List<Card> cardsForTeam=shownCards.removeAllCards();
      _deck.putCards(cardsForTeam);
    }
    // Retrieve cards from players
    for(Player player : _players)
    {
      PlayersHand hand=player.lireMain();
      List<Card> cardsForPlayer=hand.removeAllCards();
      _deck.putCards(cardsForPlayer);
    }
  }

  public GameParameters getParameters()
  {
    return _parameters;
  }

  public ExposedCards getShownCards(Team team)
  {
    ExposedCards ret=null;
    int index=_teams.indexOf(team);
    if (index!=-1)
    {
      ret=_shownCards[index];
    }
    return ret;
  }

  public Deck getDeck()
  {
    return _deck;
  }

  public PilesManager getPilesManager()
  {
    return _pilesManager;
  }

  public Team[] getTeams()
  {
    int nbTeams=_teams.size();
    Team[] ret=new Team[nbTeams];
    ret=_teams.toArray(ret);
    return ret;
  }

  public Team[] getOtherTeams(Team team)
  {
    List<Team> teamsList=new ArrayList<Team>();
    {
      int nbTeams=_teams.size();
      for(int i=0;i<nbTeams;i++)
      {
        Team aTeam=_teams.get(i);
        if (aTeam!=team)
        {
          teamsList.add(aTeam);
        }
      }
    }
    int nbTeams=teamsList.size();
    Team[] ret=new Team[nbTeams];
    ret=teamsList.toArray(ret);
    return ret;
  }

  public List<Player> getPlayers()
  {
    return _players;
  }

  public Player getPlayer(int index)
  {
    return _players.get(index);
  }

  public PlayersHand getHand(int index)
  {
    return _hands[index];
  }

  /**
   * Get the current score of the given team.
   * @param team Targeted team.
   * @return A score.
   */
  public int getScore(Team team)
  {
    int ret=_scoreComputer.getScore(this,team);
    return ret;
  }

  /**
   * Get the current score of the given team.
   * @param team Targeted team.
   * @return A score.
   */
  public int getKilometers(Team team)
  {
    int ret=0;
    ExposedCards cards=getShownCards(team);
    if (cards!=null)
    {
      ret=cards.getKilometers();
    }
    return ret;
  }

  @Override
  public String toString()
  {
    String s_l="Manche :\n";
    for(int i=0;i<_players.size();i++)
    {
      s_l=s_l+_hands[i];
    }
    return s_l;
  }
}
