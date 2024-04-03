package delta.games.rally1000.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.GameEngine;
import delta.games.rally1000.gameplay.Player;
import delta.games.rally1000.gameplay.Team;

/**
 * Panel that displays all cards for all teams.
 * @author DAM
 */
public class FullGameFieldPanelController
{
  private ExposedCardsController _mine;
  // TODO manage more than 1 other team
  private ExposedCardsController _others;
  private JPanel _panel;

  /**
   * Constructor.
   * @param gameEngine Game engine.
   * @param player Main player (whose view is displayed).
   */
  public FullGameFieldPanelController(GameEngine gameEngine, Player player)
  {
    ImagesManager imagesMgr=new ImagesManager();
    _mine=new ExposedCardsController(gameEngine,player,imagesMgr);
    Team myTeam=player.getTeam();
    Game game=gameEngine.getGame();
    Team[] otherTeams=game.getOtherTeams(myTeam);
    Team otherTeam=otherTeams[0];
    _others=new ExposedCardsController(gameEngine,otherTeam,imagesMgr);
    _panel=buildPanel();
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_mine!=null)
    {
      _mine.dispose();
      _mine=null;
    }
    if (_others!=null)
    {
      _others.dispose();
      _others=null;
    }
  }

  private JPanel buildPanel()
  {
    JPanel panel=new JPanel(new BorderLayout());
    JPanel myCanvas=_mine.getCanvas();
    JPanel otherCanvas=_others.getCanvas();
    panel.add(myCanvas,BorderLayout.SOUTH);
    panel.add(otherCanvas,BorderLayout.NORTH);
    return panel;
  }
}
