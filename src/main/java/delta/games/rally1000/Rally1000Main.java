package delta.games.rally1000;

import java.util.ArrayList;
import java.util.List;

import delta.games.rally1000.cards.Deck;
import delta.games.rally1000.cards.DeckModel;
import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.GameEngine;
import delta.games.rally1000.gameplay.GameParameters;
import delta.games.rally1000.gameplay.Player;
import delta.games.rally1000.gameplay.Team;
import delta.games.rally1000.gui.MainFrame;

/**
 * Main class for Rally 1000.
 * @author DAM
 */
public class Rally1000Main
{
  /**
   * Main frame.
   */
  // TODO beurk
  public static MainFrame _fp=null;

  static void buildUI(Game game)
  {
    _fp=new MainFrame("Mille bornes",game);
    _fp.setVisible(true);
  }

  /**
   * Main method for Rally 1000.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    DeckModel deckModel=new DeckModel();
    Deck deck=new Deck(deckModel);
    // TODO configurable teams/players
    // TODO networked game
    List<Team> teams=new ArrayList<Team>();
    Team team1=new Team("Eq1");
    teams.add(team1);
    Team team2=new Team("Eq2");
    teams.add(team2);
    List<Player> players=new ArrayList<Player>();
    Player player1=new Player("Joueur1");
    // TODO use team1.addPlayer(player1)
    player1.setTeam(team1);
    players.add(player1);
    Player player2=new Player("Joueur2");
    player2.setTeam(team2);
    players.add(player2);
    /*
    Player player3=new Player("Joueur3");
    player3.setTeam(team1);
    joueurs.add(player3);
    Player player4=new Player("Joueur4");
    player4.setTeam(team2);
    joueurs.add(player4);
*/
    GameParameters params=new GameParameters();
    Game game=new Game(params, teams, players, deck);
    game.start();
    buildUI(game);
    GameEngine engine=new GameEngine(game);
    engine.play();
  }
}
