package delta.games.rally1000.gameplay;

import java.util.ArrayList;
import java.util.List;

import delta.games.rally1000.cards.Card;

/**
 * Manages the draw pile and the discard pile.
 * @author DAM
 */
public class PilesManager
{
  private ArrayList<Card> _drawPile;
  private ArrayList<Card> _discardPile;

  /**
   * Constructor.
   */
  public PilesManager()
  {
    _drawPile=new ArrayList<Card>();
    _discardPile=new ArrayList<Card>();
  }

  /**
   * Insert some cards into the draw pile.
   * @param cards list of cards to insert.
   */
  public void putCards(List<Card> cards)
  {
    _drawPile.addAll(cards);
  }

  /**
   * Draw a card from the draw pile.
   * @return drawn card.
   */
  public Card drawCard()
  {
    Card ret=null;
    if (!_drawPile.isEmpty())
    {
      ret=_drawPile.remove(0);
    }
    return ret;
  }

  /**
   * Get the number of available cards.
   * @return the number of available cards.
   */
  public int getNbAvailableCards()
  {
    return _drawPile.size();
  }

  /**
   * Discard a card.
   * @param card Card to be discarded.
   */
  public void discard(Card card)
  {
    _discardPile.add(card);
  }

  /**
   * Remove all cards from both piles and build a list which contains all those cards.
   * @return the newly built list of removed cards.
   */
  public List<Card> removeAllCards()
  {
    List<Card> cards=new ArrayList<Card>();
    cards.addAll(_discardPile);
    _discardPile.clear();
    cards.addAll(_drawPile);
    _drawPile.clear();
    return cards;
  }
}
