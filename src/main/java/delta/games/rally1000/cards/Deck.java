package delta.games.rally1000.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import delta.common.utils.text.EndOfLine;

/**
 * Rally-100 deck.
 * @author DAM
 */
public class Deck
{
  private DeckModel _model;
  private Random _random;
  private ArrayList<Card> _cards;

  /**
   * Constructor.
   * @param deckModel Deck model.
   */
  public Deck(DeckModel deckModel)
  {
    _model=deckModel;
    _random=new Random();
    _cards=new ArrayList<Card>();
    init();
  }

  /**
   * Get the associated deck model.
   * @return the associated deck model.
   */
  public DeckModel getModel()
  {
    return _model;
  }

  /**
   * Remove all cards from this deck.
   * @return A list of removed cards. 
   */
  public List<Card> popAllCards()
  {
    List<Card> cards=new ArrayList<Card>(_cards);
    _cards.clear();
    return cards;
  }

  /**
   * Shuffle the cards in this deck.
   */
  public void shuffle()
  {
    int nbCartes=_cards.size();
    Random random=new Random(); // NOSONAR
    for(int i=0;i<200;i++)
    {
      int c1=random.nextInt(nbCartes);
      int c2=random.nextInt(nbCartes);

      // Swap cards c1 and c2
      Card carte1=_cards.get(c1);
      Card carte2=_cards.get(c2);
      _cards.set(c1, carte2);
      _cards.set(c2, carte1);
    }
  }

  /**
   * Cut this deck.
   */
  public void cut()
  {
    int nbCards=_cards.size();
    int indiceCoupe=_random.nextInt(nbCards);
    int nbCartes=_cards.size();
    int nbCartesADeplacer=nbCartes-indiceCoupe;
    if((nbCartesADeplacer<0)||(nbCartesADeplacer==nbCartes))
    {
      // Nothing to do !!
    }
    else
    {
      for(int i=0;i<nbCartesADeplacer;i++)
      {
        _cards.add(0, _cards.get(nbCartes-1));
        _cards.remove(nbCartes);
      }
    }
  }

  /**
   * Initialize the contents of this deck, using the
   * deck model to know which and how many cards to use. 
   */
  private void init()
  {
    _cards.clear();
    List<CardModel> models=_model.getCardModels();
    for(CardModel cardModel : models)
    {
      int nb=_model.getCardinality(cardModel);
      for(int i=0;i<nb;i++)
      {
        _cards.add(new Card(cardModel));
      }
    }
  }

  /**
   * Push a list of cards back in this deck.
   * @param cards Cards to grab.
   */
  public void putCards(List<Card> cards)
  {
    _cards.addAll(cards);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Deck: ");
    sb.append(EndOfLine.NATIVE_EOL);
    for(Card c : _cards)
    {
      sb.append(c);
      sb.append(EndOfLine.NATIVE_EOL);
    }
    String ret=sb.toString();
    return ret;
  }
}
