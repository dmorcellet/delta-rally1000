package delta.games.rally1000.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.Player;

public class MainFrame extends JFrame
{
  private Game _game;

  public MainFrame(String title, Game game)
  {
    super(title);
    _game=game;
    buildMenuBar();
    buildGamePanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
  }

  private void buildGamePanel()
  {
    Player player=_game.getPlayer(0);
    FullGameFieldPanel terrain=new FullGameFieldPanel(_game, player);
    getContentPane().add(terrain);
  }

  private void buildMenuBar()
  {
    JMenuBar menuBar=new JMenuBar();
    setJMenuBar(menuBar);

    // Menu partie
    JMenu partie=new JMenu("Partie");
    partie.setMnemonic(KeyEvent.VK_P);
    partie.getAccessibleContext().setAccessibleDescription("toto");
    menuBar.add(partie);

    // Items de menu
    JMenuItem nouvelle=new JMenuItem("Nouvelle...", KeyEvent.VK_N);
    nouvelle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
    nouvelle.getAccessibleContext().setAccessibleDescription("Nouvelle partie");
    partie.add(nouvelle);

    partie.addSeparator();

    JMenuItem quitter=new JMenuItem("Quitter !", KeyEvent.VK_Q);
    quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
    quitter.getAccessibleContext().setAccessibleDescription("Quitter");
    partie.add(quitter);
  }
}
