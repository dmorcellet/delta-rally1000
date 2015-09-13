package delta.games.rally1000.gameplay.actions;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.gameplay.Game;

/**
 * Base class for game actions.
 * @author DAM
 */
public abstract class AbstractAction
{
  private Card _card;

  /**
   * Invoke action.
   * @param game Associated game.
   */
  public abstract void doIt(Game game);

  /**
   * Protected constructor.
   * @param card Associated card.
   */
  protected AbstractAction(Card card)
  {
    _card=card;
  }

  /**
   * Get the card involved in this action.
   * @return A card.
   */
  public Card getCard()
  {
    return _card;
  }
}
