package delta.games.rally1000.gameplay;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.rally1000.cards.Card;

/**
 * Hand of a player.
 * @author DAM
 */
public class PlayersHand
{
  /**
   * Number of cards in a player hand.
   */
  public static final int HAND_SIZE=7;

  private List<Card> _cards;
  private Player _player;

  /**
   * Constructor.
   * @param player Associated player.
   */
  public PlayersHand(Player player)
  {
    _cards=new ArrayList<Card>(HAND_SIZE);
    _player=player;
    _player.setHand(this);
  }

  /**
   * Get the current number of cards in this hand.
   * @return A number of cards.
   */
  public int getSize()
  {
    return _cards.size();
  }

  /**
   * Add a card in this hand.
   * @param card Card to add.
   */
  public void addCard(Card card)
  {
    if (_cards.size()<HAND_SIZE)
    {
      _cards.add(card);
    }
  }

  /**
   * Remove all cards from this hand.
   * @return A list of all removed cards.
   */
  public List<Card> removeAllCards()
  {
    List<Card> ret=new ArrayList<Card>(_cards);
    _cards.clear();
    return ret;
  }

  /**
   * Remove a card.
   * @param card Card to remove.
   */
  public void removeCard(Card card)
  {
    _cards.remove(card);
  }

  /**
   * Get a card using its index.
   * @param index An index, starting at 0.
   * @return A card.
   */
  public Card getCard(int index)
  {
    return _cards.get(index);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Hand: ");
    sb.append(_player).append(EndOfLine.NATIVE_EOL);
    for(Card card : _cards)
    {
      sb.append(card).append(EndOfLine.NATIVE_EOL);
    }
    String ret=sb.toString();
    return ret;
  }
}
