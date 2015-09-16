package delta.games.rally1000.gui;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.GameEngine;
import delta.games.rally1000.gameplay.Player;
import delta.games.rally1000.gameplay.PlayersHand;
import delta.games.rally1000.gameplay.Team;
import delta.games.rally1000.gameplay.actions.AbstractAction;
import delta.games.rally1000.gameplay.actions.DiscardAction;

/**
 * Panel that displays all cards for a single team.
 * @author DAM
 */
public class ExposedCardsController
{
  private Game _game;
  private Team _team;
  private Player _player;
  private PlayersHand _playersHand;
  private ExposedCardsPanel _canvas;
  private MouseListener _playCardListener;

  /**
   * Constructor (opponent team view).
   * @param game Associated game.
   * @param team Associated team.
   * @param imagesMgr Images manager.
   */
  public ExposedCardsController(Game game, Team team, ImagesManager imagesMgr)
  {
    super();
    _playersHand=null;
    _canvas=new ExposedCardsPanel(game,team,imagesMgr);
    init(game,team);
  }

  /**
   * Constructor (main player view).
   * @param game Associated game.
   * @param player Associated player.
   * @param imagesMgr Images manager.
   */
  public ExposedCardsController(Game game, Player player, ImagesManager imagesMgr)
  {
    super();
    _player=player;
    _playersHand=player.getHand();
    _canvas=new ExposedCardsPanel(game,player,imagesMgr);
    init(game,player.getTeam());
  }

  /**
   * Get the managed component.
   * @return the managed component.
   */
  public JPanel getCanvas()
  {
    return _canvas;
  }

  private void init(Game game, Team team)
  {
    _game=game;
    _team=team;
    if (_playersHand!=null)
    {
      _playCardListener=new MouseAdapter()
      {
        @Override
        public void mouseClicked(MouseEvent e)
        {
          handleMouseClicked(e);
        }
      };
      _canvas.addMouseListener(_playCardListener);
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_canvas!=null)
    {
      _canvas.removeMouseListener(_playCardListener);
      _canvas=null;
    }
    _playCardListener=null;
  }

  private void postAction(AbstractAction action)
  {
    if (action!=null)
    {
      System.out.println("Selected action: "+action);
      GameEngine.getInstance().postPlayerAction(_player,action);
    }
  }

  private void handleMouseClicked(MouseEvent e)
  {
    int button=e.getButton();
    if ((button==MouseEvent.BUTTON1) || (button==MouseEvent.BUTTON3))
    {
      Point p=e.getPoint();
      System.out.println(p);
      Card card=_canvas.getCardAt(p);
      if (card!=null)
      {
        AbstractAction action=null;
        if (button==MouseEvent.BUTTON1)
        {
          action=_game.buildAction(_team,card);
        }
        else
        {
          action=new DiscardAction(card);
        }
        postAction(action);
      }
    }
  }
}
