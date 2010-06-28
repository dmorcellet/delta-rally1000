package delta.games.rally1000.gameplay;

/**
 * Storage class for all game parameters.
 * @author DAM
 */
public class GameParameters
{
  /**
   * Default number of cards in each player's hand at game startup.
   */
  public static final int DEFAULT_NB_CARDS_AT_STARTUP=6;
  /**
   * Default number of kilometers to run in a single game.
   */
  public static final int DEFAULT_NB_KILOMETERS=1000;

  private int _nbKms;
  private int _nbCards;

  /**
   * Default constructor.
   */
  public GameParameters()
  {
    _nbKms=DEFAULT_NB_KILOMETERS;
    _nbCards=DEFAULT_NB_CARDS_AT_STARTUP;
  }

  /**
   * Full constructor.
   * @param nbKilometers Number of kilometers to run.
   * @param nbCards Number of cards at startup.
   */
  public GameParameters(int nbKilometers, int nbCards)
  {
    _nbKms=nbKilometers;
    _nbCards=nbCards;
  }

  /**
   * Get the number of kilometers to run in a single game.
   * @return the number of kilometers to run in a single game.
   */
  public int getNbKilometers()
  {
    return _nbKms;
  }

  /**
   * Get the number of cards in each player's hand at game startup.
   * @return a number of cards.
   */
  public int getNumberOfCardsAtStartup()
  {
    return _nbCards;
  }
}
