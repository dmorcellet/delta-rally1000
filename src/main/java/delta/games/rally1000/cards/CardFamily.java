package delta.games.rally1000.cards;

/**
 * Family of a rally-1000 card.
 * @author DAM
 */
public final class CardFamily
{
  private static int _counter=0;

  private int _code;
  private String _meaning;

  /**
   * Kilometer card.
   */
  public static final CardFamily KILOMETER=new CardFamily("KILOMETER");
  /**
   * Attack card.
   */
  public static final CardFamily ATTACK=new CardFamily("ATTACK");
  /**
   * Parade card.
   */
  public static final CardFamily PARADE=new CardFamily("PARADE");
  /**
   * Safety card.
   */
  public static final CardFamily SAFETY=new CardFamily("SAFETY");

  private CardFamily(String meaning)
  {
    _code=_counter;
    _counter++;
    _meaning=meaning;
  }

  /**
   * Get the internal code for this family.
   * @return the internal code for this family.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Get the number of families.
   * @return the number of families.
   */
  public static int getNbFamilies()
  {
    return _counter;
  }

  @Override
  public String toString()
  {
    return _meaning;
  }
}
