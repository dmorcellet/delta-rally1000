package delta.games.rally1000.gameplay;

import delta.games.rally1000.cards.Card;

/**
 * Exposed safety card.
 * @author DAM
 */
public class ExposedSafetyCard
{
  private Card _safety;
  private boolean _coupFourre;

  /**
   * Constructor.
   * @param safety
   * @param coupFourre
   */
  public ExposedSafetyCard(Card safety, boolean coupFourre)
  {
    _safety=safety;
    _coupFourre=coupFourre;
  }

  /**
   * Get the safety card.
   * @return the safety card.
   */
  public Card getSafety()
  {
    return _safety;
  }

  /**
   * Get the exposition mode.
   * @return <code>true</code> for 'coup fourré', <code>false</code> otherwise.
   */
  public boolean isCoupFourre()
  {
    return _coupFourre;
  }
}
