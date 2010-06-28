package delta.games.rally1000.gameplay.actions;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.PilesManager;

/**
 * @author DAM
 */
public class DiscardAction extends AbstractAction
{
  public DiscardAction(Card card)
  {
    super(card);
  }

  @Override
  public void doIt(Game game)
  {
    Card card=getCard();
    System.out.println("Carte défaussée :"+card);
    PilesManager pilesManager=game.getPilesManager();
    pilesManager.discard(card);
  }
}
