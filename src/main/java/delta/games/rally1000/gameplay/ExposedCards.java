package delta.games.rally1000.gameplay;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.rally1000.cards.Card;
import delta.games.rally1000.cards.CardFamily;
import delta.games.rally1000.cards.CardModel;

/**
 * Cards setup for a team.
 * @author DAM
 */
public class ExposedCards
{
  private Team _team;
  private PilesManager _deck;
  private ArrayList<Card> _speedLimitationStack;
  private ArrayList<Card> _attacksStack;
  private List<ExposedSafetyCard> _exposedSafeties;
  private List<Card> _kilometersCards;

  private CardModel _repairNeeded;
  private boolean _greenLightNeeded;
  private boolean _canRun;
  private boolean _speedLimited;
  private int _kilometers;
  private int _kilometersToRun;

  /**
   * Constructor.
   * @param parameters Game parameters.
   * @param team Associated team.
   * @param stacks Stacks manager.
   */
  public ExposedCards(GameParameters parameters, Team team, PilesManager stacks)
  {
    _team=team;
    _deck=stacks;
    _speedLimitationStack=new ArrayList<Card>();
    _attacksStack=new ArrayList<Card>();
    _exposedSafeties=new ArrayList<ExposedSafetyCard>();
    _kilometersCards=new ArrayList<Card>();
    _repairNeeded=null;
    _greenLightNeeded=true;
    _canRun=false;
    _speedLimited=false;
    _kilometers=0;
    _kilometersToRun=parameters.getNbKilometers();
  }

  /**
   * Get the associated team.
   * @return the associated team.
   */
  public Team getTeam()
  {
    return _team;
  }

  /**
   * Indicates if this team is allowed to run or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean canRun()
  {
    return _canRun;
  }

  /**
   * Indicates if this team is speed limited or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isSpeedLimited()
  {
    return _speedLimited;
  }

  /**
   * Indicates if the given card can be put on this setup
   * by an opponent.
   * @param card Card to test.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean canBePutByOpponent(Card card)
  {
    boolean ret=false;
    CardFamily cardFamily=card.getFamily();
    if (cardFamily==CardFamily.ATTACK)
    {
      CardModel cardModel=card.getModel();
      if (cardModel==CardModel.SPEED_LIMIT)
      {
        CardModel associatedSafety=cardModel.getAssociatedSafety();
        if ((!_speedLimited)&&(!hasSafety(associatedSafety)))
        {
          ret=true;
        }
      }
      else
      {
        if (_canRun)
        {
          CardModel associatedSafety=cardModel.getAssociatedSafety();
          if (!hasSafety(associatedSafety))
          {
            ret=true;
          }
        }
      }
    }
    return ret;
  }

  /**
   * Indicates if a card can be put on this setup.
   * @param card Card to test.
   * @return <code>true</code> if it is possible, <code>false</code> otherwise.
   */
  public boolean canBePut(Card card)
  {
    boolean ret=false;
    CardModel cardModel=card.getModel();
    CardFamily family=card.getFamily();
    if (family==CardFamily.KILOMETER)
    {
      if (_canRun)
      {
        int kms=cardModel.getKilometers();
        if ((!_speedLimited)||(kms<=50))
        {
          if (_kilometers+kms<=_kilometersToRun)
          {
            ret=true;
          }
        }
      }
    }
    else if (family==CardFamily.SAFETY)
    {
      ret=true;
    }
    else if (family==CardFamily.PARADE)
    {
      CardModel associatedSafety=cardModel.getAssociatedSafety();
      if (!hasSafety(associatedSafety))
      {
        if (cardModel==CardModel.END_OF_SPEED_LIMIT)
        {
          Card derniere=getLastSpeedLimitCard();
          if ((derniere!=null)&&(derniere.getModel()==CardModel.SPEED_LIMIT))
          {
            ret=true;
          }
        }
        else
        {
          if (_repairNeeded!=null)
          {
            if (_repairNeeded==cardModel)
            {
              ret=true;
            }
          }
          else
          {
            if ((_greenLightNeeded) && (cardModel==CardModel.GREEN_LIGHT))
            {
              ret=true;
            }
          }
        }
      }
    }

    return ret;
  }

  /**
   * Indicates if the given card has a chance to be usefull or not.
   * @param card Card to test.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean isUsefull(Card card)
  {
    boolean ret=true;
    CardFamily family=card.getFamily();
    if (family==CardFamily.KILOMETER)
    {
      int kms=card.getModel().getKilometers();
      if (_kilometers+kms>1000)
      {
        ret=false;
      }
    }
    else if (family==CardFamily.SAFETY)
    {
      ret=true;
    }
    else if (family==CardFamily.PARADE)
    {
      CardModel cardModel=card.getModel();
      CardModel associatedSafety=cardModel.getAssociatedSafety();
      if (hasSafety(associatedSafety))
      {
        ret=false;
      }
    }
    else if (family==CardFamily.ATTACK)
    {
      // todo handle the case where one other team has shown the matching safety
      ret=true;
    }
    return ret;
  }

  /**
   * Evaluate the value of the "can run" flag.
   */
  private void canRunEvaluation()
  {
    if ((_repairNeeded!=null) && (hasSafety(_repairNeeded.getAssociatedSafety())))
    {
      _repairNeeded=null;
    }
    if (_greenLightNeeded)
    {
      if (hasSafety(CardModel.RIGHT_OF_WAY))
      {
        _greenLightNeeded=false;
      }
    }

    if ((_repairNeeded!=null) || (_greenLightNeeded))
    {
      _canRun=false;
    }
    else
    {
      _canRun=true;
    }
    if (_canRun)
    {
      Card lastCardOfAttackStack=getLastCardOfAttackStack();
      if ((lastCardOfAttackStack!=null) && (lastCardOfAttackStack.getFamily()==CardFamily.ATTACK))
      {
        _attacksStack.remove(lastCardOfAttackStack);
        _deck.discard(lastCardOfAttackStack);
      }
    }
  }

  /**
   * Put a card.
   * @param card Card to put.
   * @param coupFourre Indicates if this is a 'coup fourré'.
   */
  public void putCard(Card card, boolean coupFourre)
  {
    CardFamily cardFamily=card.getFamily();
    CardModel cardModel=card.getModel();
    if (cardFamily==CardFamily.KILOMETER)
    {
      _kilometers+=cardModel.getKilometers();
      _kilometersCards.add(card);
    }
    else if (cardFamily==CardFamily.SAFETY)
    {
      ExposedSafetyCard exposedSafety=new ExposedSafetyCard(card,coupFourre);
      _exposedSafeties.add(exposedSafety);

      // todo Handle 'coup fourre'

      // Cleanup limitation stack
      Card dernierePileLimitations=getLastSpeedLimitCard();
      if (dernierePileLimitations!=null)
      {
        if ((dernierePileLimitations.getModel()==CardModel.SPEED_LIMIT)
            && (cardModel==CardModel.RIGHT_OF_WAY))
        {
          _speedLimitationStack.remove(dernierePileLimitations);
          _deck.discard(dernierePileLimitations);
          _speedLimited=false;
        }
      }
    }
    else if (cardFamily==CardFamily.ATTACK)
    {
      if (cardModel==CardModel.SPEED_LIMIT)
      {
        _speedLimitationStack.add(card);
        _speedLimited=true;
      }
      else
      {
        _attacksStack.add(card);
        _repairNeeded=cardModel.getAssociatedParade();
        _greenLightNeeded=true;
      }
    }
    else if (cardFamily==CardFamily.PARADE)
    {
      if (cardModel==CardModel.END_OF_SPEED_LIMIT)
      {
        _speedLimitationStack.add(card);
        _speedLimited=false;
      }
      else
      {
        _attacksStack.add(card);
        _repairNeeded=null;
        if (cardModel==CardModel.GREEN_LIGHT)
        {
          _greenLightNeeded=false;
        }
      }
    }
    else
    {
      assert false;
    }
    canRunEvaluation();
  }

  /**
   * Get the last card of the attacks stack, if any.
   * @return A card or <code>null</code>.
   */
  public Card getLastCardOfAttackStack()
  {
    Card ret=null;
    if (_attacksStack.size()>0)
    {
      ret=_attacksStack.get(_attacksStack.size()-1);
    }
    return ret;
  }

  /**
   * Get the last card of the speed limit stack, if any.
   * @return A card or <code>null</code>.
   */
  public Card getLastSpeedLimitCard()
  {
    Card ret=null;
    if (_speedLimitationStack.size()>0)
    {
      ret=_speedLimitationStack.get(_speedLimitationStack.size()-1);
    }
    return ret;
  }

  /**
   * Get the total number of exposed kilometers.
   * @return A number of kilometers.
   */
  public int getKilometers()
  {
    return _kilometers;
  }

  /**
   * Get the exposed safeties.
   * @return A possibly empty array of safety cards.
   */
  public ExposedSafetyCard[] getExposedSafeties()
  {
    int size=_exposedSafeties.size();
    ExposedSafetyCard[] ret=new ExposedSafetyCard[size];
    ret=_exposedSafeties.toArray(ret);
    return ret;
  }

  /**
   * Get an array of all exposed kilometers cards.
   * @return a possibly empty array of kilometer cards.
   */
  public Card[] getKilometersCards()
  {
    int size=_kilometersCards.size();
    Card[] ret=new Card[size];
    ret=_kilometersCards.toArray(ret);
    return ret;
  }

  private boolean hasSafety(CardModel safety)
  {
    CardFamily family=safety.getFamily();
    assert family==CardFamily.SAFETY;
    boolean ret=false;
    for(ExposedSafetyCard exposedSafety : _exposedSafeties)
    {
      Card card=exposedSafety.getSafety();
      if (card.getModel()==safety)
      {
        ret=true;
        break;
      }
    }
    return ret;
  }

  /**
   * Indicates if this team has finished his run.
   * @return <code>true</code> if it has, <code>false</code> otherwise...
   */
  public boolean hasFinished()
  {
    return _kilometers==_kilometersToRun;
  }

  /**
   * Remove all cards.
   * @return A list of all removed cards.
   */
  public List<Card> removeAllCards()
  {
    List<Card> cards=new ArrayList<Card>();
    cards.addAll(_speedLimitationStack);
    _speedLimitationStack.clear();
    cards.addAll(_attacksStack);
    _attacksStack.clear();
    for(ExposedSafetyCard exposedSafety : _exposedSafeties)
    {
      cards.add(exposedSafety.getSafety());
    }
    _exposedSafeties.clear();
    cards.addAll(_kilometersCards);
    _kilometersCards.clear();
    return cards;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Shown cards :");
    sb.append(EndOfLine.NATIVE_EOL);
    sb.append("Kilometers : ").append(_kilometers);
    sb.append(", canRun=").append(_canRun);
    sb.append(", speedLimited=").append(_speedLimited);
    sb.append(EndOfLine.NATIVE_EOL);
    Card lastAttack=getLastCardOfAttackStack();
    sb.append("lastAttack=");
    if (lastAttack!=null)
    {
      sb.append('(');
      sb.append(lastAttack);
      sb.append(')');
    }
    else
    {
      sb.append("none");
    }
    sb.append(EndOfLine.NATIVE_EOL);
    Card lastLimitation=getLastSpeedLimitCard();
    sb.append("lastLimititation=");
    if (lastLimitation!=null)
    {
      sb.append('(');
      sb.append(lastLimitation);
      sb.append(')');
    }
    else
    {
      sb.append("none");
    }
    sb.append(EndOfLine.NATIVE_EOL);
    int nbSafeties=_exposedSafeties.size();
    for(int i=0;i<nbSafeties;i++)
    {
      ExposedSafetyCard exposedSafety=_exposedSafeties.get(i);
      Card safety=exposedSafety.getSafety();
      CardModel model=safety.getModel();
      boolean coupFourre=exposedSafety.isCoupFourre();
      if (coupFourre)
      {
        sb.append("Coup Fourré ");
      }
      else
      {
        sb.append("Botte ");
      }
      sb.append(model.getName()).append(EndOfLine.NATIVE_EOL);
    }
    String ret=sb.toString();
    return ret;
  }
}
