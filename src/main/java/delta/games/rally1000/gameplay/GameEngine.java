package delta.games.rally1000.gameplay;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import delta.common.utils.Tools;
import delta.games.rally1000.Rally1000Main;
import delta.games.rally1000.cards.Card;
import delta.games.rally1000.gameplay.actions.AbstractAction;
import delta.games.rally1000.gameplay.players.AbstractPlayerImpl;
import delta.games.rally1000.gameplay.players.HumanPlayer;
import delta.games.rally1000.gameplay.players.SiliconPlayer;

/**
 * Game engine.
 * Synchronizes actions of all players.
 * @author DAM
 */
public class GameEngine
{
  private static final Logger LOGGER=Logger.getLogger(GameEngine.class);

  private Game _game;
  private List<AbstractPlayerImpl> _playerImpl;
  private Player _currentPlayer;
  private AbstractAction _currentAction;

  /**
   * Constructor.
   * @param game Game description (teams, players, deck...).
   */
  public GameEngine(Game game)
  {
    _game=game;
  }

  /**
   * Get the associated game.
   * @return the associated game.
   */
  public Game getGame()
  {
    return _game;
  }

  private void initPlayers(Player[] players)
  {
    int nbPlayers=players.length;
    _playerImpl=new ArrayList<AbstractPlayerImpl>(nbPlayers);
    for(int i=0;i<nbPlayers;i++)
    {
      Player player=players[i];
      AbstractPlayerImpl playerImpl=null;
      if (i==0)
      {
        playerImpl=new HumanPlayer(this,player);
      }
      else
      {
        playerImpl=new SiliconPlayer(this,player);
      }
      _playerImpl.add(playerImpl);
    }
  }

  /**
   * Play a game:
   * <ul>
   * <li>initialize players.
   * <li>let each player play at his turn.
   * <li>until players cannot play anymore, or
   * <lI>until a team wins.
   * </ul>
   */
  public void play()
  {
    initPlayers(_game.getPlayers());
    while(true)
    {
      Runnable r=new Runnable()
      {
        public void run()
        {
          Rally1000Main._fp.repaint();
        }
      };
      PilesManager pilesManager=_game.getPilesManager();
      int nbPlayers=_playerImpl.size();
      int nbJoueursImpotents=0;
      for(int i=0;i<nbPlayers;i++)
      {
        AbstractPlayerImpl playerImpl=_playerImpl.get(i);
        _currentPlayer=playerImpl.getPlayer();
        PlayersHand playersHand=_game.getHand(i);
        Card drawnCard=pilesManager.drawCard();
        int nbCardsLeft=pilesManager.getNbAvailableCards();
        LOGGER.info("Nb cards left: "+nbCardsLeft);
        if (drawnCard!=null)
        {
          playersHand.addCard(drawnCard);
          SwingUtilities.invokeLater(r);
          AbstractAction action=playerImpl.chooseAction();
          action.doIt(_game);
          Card card=action.getCard();
          playersHand.removeCard(card);
          SwingUtilities.invokeLater(r);
        }
        else
        {
          nbJoueursImpotents++;
        }
      }
      if (nbJoueursImpotents==nbPlayers)
      {
        LOGGER.info("Players can't play anymore!");
        break;
      }
      Team[] teams=_game.getTeams();
      Team winningTeam=null;
      for(Team team : teams)
      {
        ExposedCards cards=_game.getShownCards(team);
        boolean finished=cards.hasFinished();
        if (finished)
        {
          winningTeam=team;
          break;
        }
      }
      if (winningTeam!=null)
      {
        // TODO display winner message
        LOGGER.info("Team "+winningTeam.getName()+" has won!");
        break;
      }
    }
    _game.returnCardsToDeck();
  }

  /**
   * Post a player action.
   * @param player Involved player.
   * @param action Action to do.
   */
  public void postPlayerAction(Player player, AbstractAction action)
  {
    if (player==_currentPlayer)
    {
      synchronized(this)
      {
        _currentAction=action;
        notify();
      }
    }
  }

  /**
   * Wait for a player to choose an action.
   * @return An action.
   */
  public synchronized AbstractAction waitForPlayerAction()
  {
    Tools.startWaiting(this);
    AbstractAction action=_currentAction;
    _currentAction=null;
    return action;
  }
}
