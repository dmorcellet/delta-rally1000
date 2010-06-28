package delta.games.rally1000.gameplay;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.rally1000.cards.Card;
import delta.games.rally1000.cards.CardFamily;
import delta.games.rally1000.cards.CardModel;

public class ExposedCards
{
  private Team _equipe;
  private PilesManager _sabot;
  private ArrayList<Card> _speedLimitationStack;
  private ArrayList<Card> _attacksStack;
  private List<Card> _exposedSafeties;
  private List<Boolean> _coupFourres;
  private List<Card> _kilometersCards;

  private CardModel _repairNeeded;
  private boolean _greenLightNeeded;
  private boolean _canRun;
  private boolean _speedLimited;
  private int _kilometers;
  private int _distanceAParcourir;

  /**
   * Constructor.
   * @param parameters Game parameters.
   * @param team Associated team.
   * @param sabot
   */
  public ExposedCards(GameParameters parameters, Team team, PilesManager sabot)
  {
    _equipe=team;
    _sabot=sabot;
    _speedLimitationStack=new ArrayList<Card>();
    _attacksStack=new ArrayList<Card>();
    _exposedSafeties=new ArrayList<Card>();
    _coupFourres=new ArrayList<Boolean>();
    _kilometersCards=new ArrayList<Card>();
    _repairNeeded=null;
    _greenLightNeeded=true;
    _canRun=false;
    _speedLimited=false;
    _kilometers=0;
    _distanceAParcourir=parameters.getNbKilometers();
  }

  public Team getTeam()
  {
    return _equipe;
  }

  public boolean peutRouler()
  {
    return _canRun;
  }

  public boolean isSpeedLimited()
  {
    return _speedLimited;
  }

  public boolean estPosableParAdversaire(Card card)
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

  public boolean canPut(Card card)
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
          if (_kilometers+kms<=_distanceAParcourir)
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
            if ((_greenLightNeeded) && (cardModel==CardModel.ROLL))
            {
              ret=true;
            }
          }
        }
      }
    }

    return ret;
  }

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
      // todo handle the case where the other player has shown the matching safety
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
        _sabot.discard(lastCardOfAttackStack);
      }
    }
  }

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
      _exposedSafeties.add(card);
      _coupFourres.add(Boolean.valueOf(coupFourre));

      // todo Handle 'coup fourre'

      // Cleanup limitation stack
      Card dernierePileLimitations=getLastSpeedLimitCard();
      if (dernierePileLimitations!=null)
      {
        if ((dernierePileLimitations.getModel()==CardModel.SPEED_LIMIT)
            && (cardModel==CardModel.RIGHT_OF_WAY))
        {
          _speedLimitationStack.remove(dernierePileLimitations);
          _sabot.discard(dernierePileLimitations);
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
        if (cardModel==CardModel.ROLL)
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

  public Card getLastCardOfAttackStack()
  {
    Card retour=null;
    if (_attacksStack.size()>0)
    {
      retour=_attacksStack.get(_attacksStack.size()-1);
    }
    return retour;
  }

  public Card getLastSpeedLimitCard()
  {
    Card retour=null;
    if (_speedLimitationStack.size()>0)
    {
      retour=_speedLimitationStack.get(_speedLimitationStack.size()-1);
    }
    return retour;
  }

  public int getKilometers()
  {
    return _kilometers;
  }

  public Card[] getExposedSafeties()
  {
    int size=_exposedSafeties.size();
    Card[] ret=new Card[size];
    ret=_exposedSafeties.toArray(ret);
    return ret;
  }

  public Boolean[] getCoupFourres()
  {
    int size=_coupFourres.size();
    Boolean[] ret=new Boolean[size];
    ret=_coupFourres.toArray(ret);
    return ret;
  }

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
    for(Card card : _exposedSafeties)
    {
      if (card.getModel()==safety)
      {
        ret=true;
        break;
      }
    }
    return ret;
  }

  public List<Card> removeAllCards()
  {
    List<Card> cards=new ArrayList<Card>();
    cards.addAll(_speedLimitationStack);
    _speedLimitationStack.clear();
    cards.addAll(_attacksStack);
    _attacksStack.clear();
    cards.addAll(_exposedSafeties);
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
      Card card=_exposedSafeties.get(i);
      CardModel model=card.getModel();
      Boolean coupFourre=_coupFourres.get(i);
      if (coupFourre.booleanValue())
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
