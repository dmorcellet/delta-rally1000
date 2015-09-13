package delta.games.rally1000.gameplay.actions;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.PilesManager;

/**
 * Discard a card.
 * @author DAM
 */
public class DiscardAction extends AbstractAction
{
  /**
   * Constructor.
   * @param card Card to discard.
   */
  public DiscardAction(Card card)
  {
    super(card);
  }

  /**
   * Perform action:
   * <ul>
   * <li>grab card,
   * <li>put it in the stack of discarded cards.
   * </ul>
   */
  @Override
  public void doIt(Game game)
  {
    Card card=getCard();
    PilesManager pilesManager=game.getPilesManager();
    pilesManager.discard(card);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Discard card [");
    sb.append(getCard());
    sb.append("]");
    return sb.toString();
  }
}
