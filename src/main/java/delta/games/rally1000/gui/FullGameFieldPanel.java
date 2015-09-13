package delta.games.rally1000.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.Player;
import delta.games.rally1000.gameplay.Team;

/**
 * Panel that displays all cards for all teams.
 * @author DAM
 */
public class FullGameFieldPanel extends JPanel
{
  private GameFieldPanel _mine;
  // TODO manage more than 1 other team
  private GameFieldPanel _others;

  /**
   * Constructor.
   * @param game Game to use.
   * @param player Main player (whose view is displayed).
   */
  public FullGameFieldPanel(Game game, Player player)
  {
    super(new BorderLayout());
    _mine=new GameFieldPanel(game,player);
    Team myTeam=player.getTeam();
    Team[] teams=game.getTeams();
    Team otherTeam=null;
    for(int i=0;i<teams.length;i++)
    {
      if (teams[i]!=myTeam)
      {
        otherTeam=teams[i];
        break;
      }
    }
    _others=new GameFieldPanel(game,otherTeam);
    add(_mine,BorderLayout.SOUTH);
    add(_others,BorderLayout.NORTH);
  }
}
