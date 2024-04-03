package delta.games.rally1000.gameplay.actions;

import org.apache.log4j.Logger;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.gameplay.ExposedCards;
import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.Team;

/**
 * Play a card.
 * @author DAM
 */
public class PlayCardAction extends AbstractAction
{
  private static final Logger LOGGER=Logger.getLogger(PlayCardAction.class);

  private Team _team;

  /**
   * Constructor.
   * @param team Targeted team.
   * @param card Card to play.
   */
  public PlayCardAction(Team team, Card card)
  {
    super(card);
    _team=team;
  }

  /**
   * Get the targeted team.
   * @return the targeted team.
   */
  public Team getTeam()
  {
    return _team;
  }

  /**
   * Perform action:
   * <ul>
   * <li>grab card,
   * <li>put it in the cards of the targeted team.
   * </ul>
   */
  @Override
  public void doIt(Game game)
  {
    Card card=getCard();
    ExposedCards shownCards=_team.getExposedCards();
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Card: "+card+" put on: "+shownCards);
    }
    shownCards.putCard(card, false);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Play card [");
    sb.append(getCard());
    sb.append("] for team [");
    sb.append(_team);
    sb.append("]");
    return sb.toString();
  }
}
