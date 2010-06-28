package delta.games.rally1000.gameplay;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.rally1000.cards.Card;

public class PlayersHand
{
  public static final int TAILLE_MAIN=7;

  private List<Card> _cards;
  private Player _player;

  public PlayersHand(Player player)
  {
    _cards=new ArrayList<Card>(TAILLE_MAIN);
    _player=player;
    _player.fixerMain(this);
  }

  public int getSize()
  {
    return _cards.size();
  }

  public void addCard(Card carte)
  {
    if (_cards.size()<TAILLE_MAIN)
    {
      _cards.add(carte);
    }
  }

  public List<Card> removeAllCards()
  {
    List<Card> ret=new ArrayList<Card>(_cards);
    _cards.clear();
    return ret;
  }

  public void removeCard(Card carte)
  {
    _cards.remove(carte);
  }

  public Card getCard(int index)
  {
    return _cards.get(index);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Main :");
    sb.append(_player).append(EndOfLine.NATIVE_EOL);
    for(Card card : _cards)
    {
      sb.append(card).append(EndOfLine.NATIVE_EOL);
    }
    String ret=sb.toString();
    return ret;
  }
}
