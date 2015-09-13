package delta.games.rally1000.cards;

/**
 * Rally-1000 card model.
 * @author DAM
 */
public class CardModel
{
  private String _name;
  private CardFamily _family;
  private int _kilometers;
  private CardModel _associatedAttack;
  private CardModel _associatedParade;
  private CardModel _associatedSafety;

  /**
   * 25 kilometers.
   */
  public static final CardModel KM_25=buildKilomoters(CardNames.KM_25,25);
  /**
   * 50 kilometers.
   */
  public static final CardModel KM_50=buildKilomoters(CardNames.KM_50,50);
  /**
   * 75 kilometers.
   */
  public static final CardModel KM_75=buildKilomoters(CardNames.KM_75,75);
  /**
   * 100 kilometers.
   */
  public static final CardModel KM_100=buildKilomoters(CardNames.KM_100,100);
  /**
   * 200 kilometers.
   */
  public static final CardModel KM_200=buildKilomoters(CardNames.KM_200,200);

  /**
   * Car crash.
   */
  public static final CardModel CAR_CRASH=buildAttack(CardNames.CAR_CRASH);
  /**
   * Repair car.
   */
  public static final CardModel REPAIRS=buildParade(CardNames.REPAIRS);
  /**
   * Driving ace.
   */
  public static final CardModel DRIVING_ACE=buildSafety(CardNames.DRIVING_ACE);

  /**
   * Flat tire.
   */
  public static final CardModel FLAT_TIRE=buildAttack(CardNames.FLAT_TIRE);
  /**
   * Spare tire.
   */
  public static final CardModel SPARE_TIRE=buildParade(CardNames.SPARE_TIRE);
  /**
   * Puncture proof.
   */
  public static final CardModel PUNCTURE_PROOF=buildSafety(CardNames.PUNCTURE_PROOF);

  /**
   * Out of gas.
   */
  public static final CardModel OUT_OF_GAS=buildAttack(CardNames.OUT_OF_GAS);
  /**
   * Gasoline.
   */
  public static final CardModel GASOLINE=buildParade(CardNames.GAS_CAN);
  /**
   * Extra tank.
   */
  public static final CardModel EXTRA_TANK=buildSafety(CardNames.EXTRA_TANK);

  /**
   * Stop.
   */
  public static final CardModel RED_LIGHT=buildAttack(CardNames.RED_LIGHT);
  /**
   * Speed limitation.
   */
  public static final CardModel SPEED_LIMIT=buildAttack(CardNames.SPEED_LIMIT);
  /**
   * Green light.
   */
  public static final CardModel ROLL=buildParade(CardNames.GREEN_LIGHT);
  /**
   * End of speed limitation.
   */
  public static final CardModel END_OF_SPEED_LIMIT=buildParade(CardNames.END_OF_SPEED_LIMIT);
  /**
   * Right of way.
   */
  public static final CardModel RIGHT_OF_WAY=buildSafety(CardNames.RIGHT_OF_WAY);

  static
  {
    associateCards(CAR_CRASH,REPAIRS,DRIVING_ACE);
    associateCards(FLAT_TIRE,SPARE_TIRE,PUNCTURE_PROOF);
    associateCards(OUT_OF_GAS,GASOLINE,EXTRA_TANK);
    associateCards(RED_LIGHT,ROLL,RIGHT_OF_WAY);
    associateCards(SPEED_LIMIT,END_OF_SPEED_LIMIT,RIGHT_OF_WAY);
  }

  private CardModel(String name, CardFamily family)
  {
    _name=name;
    _family=family;
  }

  /**
   * Get the family of this card.
   * @return the family of this card.
   */
  public CardFamily getFamily()
  {
    return _family;
  }

  /**
   * Get the name of this card.
   * @return the name of this card.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the number of kilometers of this card.
   * @return the number of kilometers of this card.
   */
  public int getKilometers()
  {
    return _kilometers;
  }

  /**
   * Get the associated attack card model.
   * @return the associated attack card model.
   */
  public CardModel getAssociatedAttack()
  {
    return _associatedAttack;
  }

  /**
   * Get the associated parade card model.
   * @return the associated parade card model.
   */
  public CardModel getAssociatedParade()
  {
    return _associatedParade;
  }

  /**
   * Get the associated safety card model.
   * @return the associated safety card model.
   */
  public CardModel getAssociatedSafety()
  {
    return _associatedSafety;
  }

  private static CardModel buildKilomoters(String name, int kilometers)
  {
    CardModel model=new CardModel(name,CardFamily.KILOMETER);
    model._kilometers=kilometers;
    return model;
  }

  private static CardModel buildAttack(String name)
  {
    CardModel model=new CardModel(name,CardFamily.ATTACK);
    model._kilometers=0;
    return model;
  }

  private static CardModel buildParade(String name)
  {
    CardModel model=new CardModel(name,CardFamily.PARADE);
    model._kilometers=0;
    return model;
  }

  private static CardModel buildSafety(String name)
  {
    CardModel model=new CardModel(name,CardFamily.SAFETY);
    model._kilometers=0;
    return model;
  }

  private static void associateCards(CardModel attack, CardModel parade, CardModel safety)
  {
    attack._associatedParade=parade;
    attack._associatedSafety=safety;
    parade._associatedAttack=attack;
    parade._associatedSafety=safety;
    safety._associatedParade=parade;
    safety._associatedAttack=attack;
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
