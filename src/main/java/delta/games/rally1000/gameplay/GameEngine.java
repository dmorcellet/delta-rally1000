package delta.games.rally1000.gameplay;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

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
  private static GameEngine _instance=null;
  private Game _game;
  private List<AbstractPlayerImpl> _playerImpl;
  private Player _currentPlayer;
  private AbstractAction _currentAction;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static GameEngine getInstance()
  {
    return _instance;
  }

  /**
   * Constructor.
   * @param game Game description (teams, players, deck...).
   */
  public GameEngine(Game game)
  {
    _game=game;
    // TODO beurk
    _instance=this;
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
          // TODO beurk
          Rally1000Main._fp.repaint();
        }
      };
      int nbPlayers=_playerImpl.size();
      int nbJoueursImpotents=0;
      for(int i=0;i<nbPlayers;i++)
      {
        AbstractPlayerImpl playerImpl=_playerImpl.get(i);
        _currentPlayer=playerImpl.getPlayer();
        PlayersHand playersHand=_game.getHand(i);
        PilesManager pilesManager=_game.getPilesManager();

        Card drawnCard=pilesManager.drawCard();
        if(drawnCard!=null)
        {
          playersHand.addCard(drawnCard);
          SwingUtilities.invokeLater(r);
          /*
          System.out.println(playersHand);
          for(int j=0;j<_teams.size();j++)
          {
            System.out.println(j+" : "+_shownCards[j]);
          }
          */
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
        break;
      }
      // TODO manage the case where a team wins the game
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
