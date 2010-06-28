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
 * @author DAM
 */
public class GameEngine
{
  private static GameEngine _instance=null;
  private Game _game;
  private List<AbstractPlayerImpl> _playerImpl;
  private Player _currentPlayer;
  private AbstractAction _currentAction;

  public static GameEngine getInstance()
  {
    return _instance;
  }

  public GameEngine(Game game)
  {
    _game=game;
    _instance=this;
  }

  public Game getGame()
  {
    return _game;
  }

  private void initPlayers(List<Player> players)
  {
    int nbPlayers=players.size();
    _playerImpl=new ArrayList<AbstractPlayerImpl>(nbPlayers);
    for(int i=0;i<nbPlayers;i++)
    {
      Player player=players.get(i);
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
    }
    _game.returnCardsToDeck();
  }

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

  public synchronized AbstractAction waitForPlayerAction()
  {
    Tools.startWaiting(this);
    AbstractAction action=_currentAction;
    _currentAction=null;
    return action;
  }
}
