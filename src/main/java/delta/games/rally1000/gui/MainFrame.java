package delta.games.rally1000.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.GameEngine;
import delta.games.rally1000.gameplay.Player;

/**
 * Main frame for the Rally 100 application.
 * @author DAM
 */
public class MainFrame extends JFrame
{
  private transient GameEngine _gameEngine;

  /**
   * Constructor.
   * @param title Window title.
   * @param gameEngine Game engine.
   */
  public MainFrame(String title, GameEngine gameEngine)
  {
    super(title);
    _gameEngine=gameEngine;
    buildMenuBar();
    buildGamePanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
  }

  private void buildGamePanel()
  {
    Game game=_gameEngine.getGame();
    Player player=game.getPlayer(0);
    FullGameFieldPanelController terrain=new FullGameFieldPanelController(_gameEngine, player);
    JPanel mainPanel=terrain.getPanel();
    getContentPane().add(mainPanel);
  }

  private void buildMenuBar()
  {
    JMenuBar menuBar=new JMenuBar();
    setJMenuBar(menuBar);

    // Game menu
    JMenu gameMenu=new JMenu("Game");
    gameMenu.setMnemonic(KeyEvent.VK_P);
    menuBar.add(gameMenu);

    // New game...
    JMenuItem newMenuItem=new JMenuItem("New...", KeyEvent.VK_N);
    newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
    gameMenu.add(newMenuItem);

    gameMenu.addSeparator();

    // Quit
    JMenuItem quitMenuItem=new JMenuItem("Quit", KeyEvent.VK_Q);
    quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
    gameMenu.add(quitMenuItem);
  }
}
