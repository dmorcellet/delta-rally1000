package delta.games.rally1000.gameplay.players;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.gameplay.ExposedCards;
import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.GameEngine;
import delta.games.rally1000.gameplay.Player;
import delta.games.rally1000.gameplay.PlayersHand;
import delta.games.rally1000.gameplay.Team;
import delta.games.rally1000.gameplay.actions.AbstractAction;
import delta.games.rally1000.gameplay.actions.DiscardAction;
import delta.games.rally1000.gameplay.actions.PlayCardAction;

/**
 * @author DAM
 */
public class SiliconPlayer extends AbstractPlayerImpl
{
  public SiliconPlayer(GameEngine engine, Player player)
  {
    super(engine,player);
  }

  @Override
  public AbstractAction chooseAction()
  {
    Game game=getGame();
    Player player=getPlayer();
    Team myTeam=player.getTeam();
    PlayersHand playersHand=player.lireMain();
    ExposedCards myCards=myTeam.getExposedCards();
    for(int j=0;j<playersHand.getSize();j++)
    {
      Card card=playersHand.getCard(j);
      PlayCardAction action=game.buildAction(myTeam,card);
      if (action!=null)
      {
        return action;
      }
    }
    // Choose the card to remove
    int nbCards=playersHand.getSize();
    for(int j=0;j<nbCards;j++)
    {
      Card cardToEvaluate=playersHand.getCard(j);
      if (!myCards.isUsefull(cardToEvaluate))
      {
        return new DiscardAction(cardToEvaluate);
      }
    }
    return new DiscardAction(playersHand.getCard(0));
  }
}
