package delta.games.rally1000.gameplay;

/**
 * Interface of a score computer.
 * @author DAM
 */
public interface ScoreComputer
{
  /**
   * Compute the score of a team in a single game.
   * @param manche Game information.
   * @param team Team to evaluate.
   * @return A score.
   */
  public int getScore(Game manche, Team team);
}
