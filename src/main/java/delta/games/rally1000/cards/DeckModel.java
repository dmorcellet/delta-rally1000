package delta.games.rally1000.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Rally-1000 deck model.
 * <br>
 * Such a model indicates:
 * <ul>
 * <li>which card models to use,
 * <li>how many card of each model to use.
 * </ul>
 * @author DAM
 */
public class DeckModel
{
  private HashMap<CardModel,Integer> _cardinalities;
  private Set<CardModel> _types;

  /**
   * Constructor.
   */
  public DeckModel()
  {
    _cardinalities=new HashMap<CardModel,Integer>();
    initCardinalities();
    _types=Collections.unmodifiableSet(_cardinalities.keySet());
  }

  /**
   * Get the number of cards of a given family.
   * @param family Targeted family.
   * @return A number of cards.
   */
  public int getCardinality(CardFamily family)
  {
    int nb=0;
    for(CardModel cardModel : _types)
    {
      if (cardModel.getFamily()==family)
      {
        Integer cardinality=_cardinalities.get(cardModel);
        nb+=cardinality.intValue();
      }
    }
    return nb;
  }

  /**
   * Get the number of cards of a given model.
   * @param model Targeted model.
   * @return A number of cards.
   */
  public int getCardinality(CardModel model)
  {
    Integer tmp=_cardinalities.get(model);
    if (tmp!=null)
    {
      return tmp.intValue();
    }
    return 0;
  }

  /**
   * Get all card models.
   * @return a list of card models.
   */
  public List<CardModel> getCardModels()
  {
    List<CardModel> list=new ArrayList<CardModel>(_types);
    return list;
  }

  private void initCardinalities()
  {
    _cardinalities.put(CardModel.KM_25,Integer.valueOf(10));
    _cardinalities.put(CardModel.KM_50,Integer.valueOf(10));
    _cardinalities.put(CardModel.KM_75,Integer.valueOf(10));
    _cardinalities.put(CardModel.KM_100,Integer.valueOf(12));
    _cardinalities.put(CardModel.KM_200,Integer.valueOf(4));
    _cardinalities.put(CardModel.CAR_CRASH,Integer.valueOf(3));
    _cardinalities.put(CardModel.FLAT_TIRE,Integer.valueOf(3));
    _cardinalities.put(CardModel.OUT_OF_GAS,Integer.valueOf(3));
    _cardinalities.put(CardModel.RED_LIGHT,Integer.valueOf(5));
    _cardinalities.put(CardModel.SPEED_LIMIT,Integer.valueOf(4));
    _cardinalities.put(CardModel.REPAIRS,Integer.valueOf(6));
    _cardinalities.put(CardModel.SPARE_TIRE,Integer.valueOf(6));
    _cardinalities.put(CardModel.GASOLINE,Integer.valueOf(6));
    _cardinalities.put(CardModel.GREEN_LIGHT,Integer.valueOf(14));
    _cardinalities.put(CardModel.END_OF_SPEED_LIMIT,Integer.valueOf(6));
    _cardinalities.put(CardModel.DRIVING_ACE,Integer.valueOf(1));
    _cardinalities.put(CardModel.PUNCTURE_PROOF,Integer.valueOf(1));
    _cardinalities.put(CardModel.EXTRA_TANK,Integer.valueOf(1));
    _cardinalities.put(CardModel.RIGHT_OF_WAY,Integer.valueOf(1));
  }
}
