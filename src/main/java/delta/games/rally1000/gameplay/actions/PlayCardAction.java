package delta.games.rally1000.gameplay.actions;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.gameplay.ExposedCards;
import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.Team;

/**
 * @author DAM
 */
public class PlayCardAction extends AbstractAction
{
  private Team _team;

  public PlayCardAction(Team team, Card card)
  {
    super(card);
    _team=team;
  }

  public Team getTeam()
  {
    return _team;
  }

  @Override
  public void doIt(Game game)
  {
    Card card=getCard();
    ExposedCards shownCards=_team.getExposedCards();
    //System.out.println("Carte jouée :"+card+" sur jeu "+shownCards);
    shownCards.putCard(card, false);
  }
}
