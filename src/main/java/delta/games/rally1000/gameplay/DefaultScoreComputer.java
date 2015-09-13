package delta.games.rally1000.gameplay;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.cards.CardFamily;
import delta.games.rally1000.cards.CardModel;
import delta.games.rally1000.cards.DeckModel;

/**
 * Official score computer.
 * @author DAM
 */
public class DefaultScoreComputer implements ScoreComputer
{
  private static final int SAFETY=100;
  private static final int COUP_FOURRE_BONUS=300;
  private static final int ALL_SAFETIES_BONUS=300;
  private static final int TRIP_COMPLETED_BONUS=400;
  private static final int SAFE_TRIP_BONUS=300;
  private static final int DELAYED_ACTION_BONUS=300;
  private static final int SHUTOUT_BONUS=500;

  /**
   * Compute the score of a team in a single game.
   * @param game Game information.
   * @param team Team to evaluate.
   * @return A score.
   */
  public int getScore(Game game, Team team)
  {
    ExposedCards shownCards=game.getShownCards(team);
    GameParameters params=game.getParameters();
    // Compute kilometers score
    int kilometersScore=0;
    {
      int nbKilometers=shownCards.getKilometers();
      kilometersScore=nbKilometers;
      int nbKilometersMax=params.getNbKilometers();
      if (nbKilometers==nbKilometersMax)
      {
        kilometersScore+=TRIP_COMPLETED_BONUS;
        // Compute safe trip bonus
        {
          Card[] kilometers=shownCards.getKilometersCards();
          boolean has200kmsCars=false;
          for(int i=0;i<kilometers.length;i++)
          {
            if (kilometers[i].getModel()==CardModel.KM_200)
            {
              has200kmsCars=true;
              break;
            }
          }
          if (!has200kmsCars)
          {
            kilometersScore+=SAFE_TRIP_BONUS;
          }
        }
        // Compute delayed action bonus
        {
          PilesManager pilesMgr=game.getPilesManager();
          int nbCards=pilesMgr.getNbAvailableCards();
          if (nbCards==0)
          {
            kilometersScore+=DELAYED_ACTION_BONUS;
          }
        }
        // Computes shut-out bonus
        int shutoutBonus=0;
        {
          Team[] teams=game.getOtherTeams(team);
          for(int i=0;i<teams.length;i++)
          {
            Team aTeam=teams[i];
            ExposedCards shownCardsForATeam=game.getShownCards(aTeam);
            int nbKilometersForATeam=shownCardsForATeam.getKilometers();
            if (nbKilometersForATeam==0)
            {
              shutoutBonus+=SHUTOUT_BONUS;
            }
          }
        }
        kilometersScore+=shutoutBonus;
      }
    }
    // Computes safeties score
    int safetiesScore=0;
    {
      ExposedSafetyCard[] safeties=shownCards.getExposedSafeties();
      int nb=safeties.length;
      for(int i=0;i<nb;i++)
      {
        safetiesScore+=SAFETY;
        if (safeties[i].isCoupFourre())
        {
          safetiesScore+=COUP_FOURRE_BONUS;
        }
      }

      DeckModel deckModel=game.getDeck().getModel();
      int nbSafetiesMax=deckModel.getCardinality(CardFamily.SAFETY);
      if (nb==nbSafetiesMax)
      {
        safetiesScore+=ALL_SAFETIES_BONUS;
      }
    }
    int score=kilometersScore+safetiesScore;
    return score;
  }
}
