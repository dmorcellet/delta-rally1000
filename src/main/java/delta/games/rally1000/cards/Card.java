package delta.games.rally1000.cards;

/**
 * Rally-1000 card.
 * @author DAM
 */
public class Card
{
  private CardModel _model;

  /**
   * Package protected constructor.
   * @param model Card's model.
   */
  Card(CardModel model)
  {
    _model=model;
  }

  /**
   * Get the card's family.
   * @return the card's family.
   */
  public CardFamily getFamily()
  {
    return _model.getFamily();
  }

  /**
   * Get the card's model.
   * @return the card's model.
   */
  public CardModel getModel()
  {
    return _model;
  }

  /**
   * Get a string that represents this card.
   * @return a string.
   */
  @Override
  public String toString()
  {
    String s="Card: "+_model.getName();
    return s;
  }
}
