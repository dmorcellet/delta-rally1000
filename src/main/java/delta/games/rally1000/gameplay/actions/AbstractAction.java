package delta.games.rally1000.gameplay.actions;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.gameplay.Game;

/**
 * @author DAM
 */
public abstract class AbstractAction
{
  private Card _card;
  public abstract void doIt(Game game);

  protected AbstractAction(Card card)
  {
    _card=card;
  }

  public Card getCard()
  {
    return _card;
  }
}
