package delta.games.rally1000.gameplay;

/**
 * Interface of a score computer.
 * @author DAM
 */
public interface ScoreComputer
{
  /**
   * Compute the score of a team in a single game.
   * @param game Game information.
   * @param team Team to evaluate.
   * @return A score.
   */
  int getScore(Game game, Team team);
}
