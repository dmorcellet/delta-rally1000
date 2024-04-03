package delta.games.rally1000.gameplay;

import java.util.ArrayList;
import java.util.List;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.cards.Deck;
import delta.games.rally1000.gameplay.actions.PlayCardAction;

/**
 * Gather data about a game session.
 * @author DAM
 */
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

  /**
   * Start game:
   * <ul>
   * <li>shuffle and cut deck,
   * <li>setup exposed cards for each team,
   * <li>setup players hands,
   * <li>distribute cards.
   * </ul>
   */
  public void start()
  {
    _deck.shuffle();
    _deck.cut();
    for(int i=0;i<_teams.size();i++)
    {
      _shownCards[i]=new ExposedCards(_parameters, _teams.get(i), _pilesManager);
      _teams.get(i).setCards(_shownCards[i]);
    }
    for(int i=0;i<_players.size();i++)
    {
      _hands[i]=new PlayersHand(_players.get(i));
    }
    _pilesManager.putCards(_deck.popAllCards());
    distribute();
  }

  /**
   * Distribute cards to all players.
   */
  private void distribute()
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
  }

  /**
   * Build a 'play card' action.
   * @param sourceTeam Team that plays the card.
   * @param card Card to play.
   * @return An action or <code>null</code> if it is not possible.
   */
  public PlayCardAction buildAction(Team sourceTeam, Card card)
  {
    ExposedCards myCards=sourceTeam.getExposedCards();
    if (myCards.canBePut(card))
    {
      return new PlayCardAction(sourceTeam,card);
    }
    for(Team team : _teams)
    {
      if (team!=sourceTeam)
      {
        ExposedCards otherTeamShownCards=team.getExposedCards();
        if (otherTeamShownCards.canBePutByOpponent(card))
        {
          return new PlayCardAction(team,card);
        }
      }
    }
    return null;
  }

  /**
   * Return all cards to the main deck.
   */
  public void returnCardsToDeck()
  {
    // Retrieve cards from stacks
    List<Card> stackedCards=_pilesManager.removeAllCards();
    _deck.putCards(stackedCards);
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
      PlayersHand hand=player.getHand();
      List<Card> cardsForPlayer=hand.removeAllCards();
      _deck.putCards(cardsForPlayer);
    }
  }

  /**
   * Get game parameters.
   * @return the current game parameters.
   */
  public GameParameters getParameters()
  {
    return _parameters;
  }

  /**
   * Get the exposed cards for a team.
   * @param team Targeted team.
   * @return a set of exposed cards or <code>null</code>.
   */
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

  /**
   * Get the deck used for this game.
   * @return A deck.
   */
  public Deck getDeck()
  {
    return _deck;
  }

  /**
   * Get the stacks manager.
   * @return the stacks manager.
   */
  public PilesManager getPilesManager()
  {
    return _pilesManager;
  }

  /**
   * Get an array of all teams.
   * @return An array of teams.
   */
  public Team[] getTeams()
  {
    int nbTeams=_teams.size();
    Team[] ret=new Team[nbTeams];
    ret=_teams.toArray(ret);
    return ret;
  }

  /**
   * Get an array of all teams opposed to the given one.
   * @param team Team to use.
   * @return An array of teams.
   */
  public Team[] getOtherTeams(Team team)
  {
    List<Team> teamsList=new ArrayList<Team>(_teams);
    teamsList.remove(team);
    return teamsList.toArray(new Team[teamsList.size()]);
  }

  /**
   * Get an array of all players.
   * @return An array of players.
   */
  public Player[] getPlayers()
  {
    return _players.toArray(new Player[_players.size()]);
  }

  /**
   * Get a player using its index.
   * @param index An index, starting at 0.
   * @return A player.
   */
  public Player getPlayer(int index)
  {
    return _players.get(index);
  }

  /**
   * Get a player hand using its index.
   * @param index An index, starting at 0.
   * @return A player hand.
   */
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
    StringBuilder sb=new StringBuilder("Game:\n");
    for(int i=0;i<_players.size();i++)
    {
      sb.append(_hands[i]);
    }
    return sb.toString();
  }
}
