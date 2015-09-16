package delta.games.rally1000.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;

import delta.games.rally1000.cards.Card;
import delta.games.rally1000.cards.CardModel;
import delta.games.rally1000.gameplay.ExposedCards;
import delta.games.rally1000.gameplay.ExposedSafetyCard;
import delta.games.rally1000.gameplay.Game;
import delta.games.rally1000.gameplay.Player;
import delta.games.rally1000.gameplay.PlayersHand;
import delta.games.rally1000.gameplay.Team;

/**
 * Panel that displays all cards for a single team.
 * @author DAM
 */
public class ExposedCardsPanel extends JPanel
{
  private static final int TOP_MARGIN=10;
  private static final int BOTTOM_MARGIN=10;
  private static final int LEFT_MARGIN=10;
  private static final int RIGHT_MARGIN=10;
  private static final int SPACE_BETWEEN_STACKS=5;
  private static final int SPACE_BETWEEN_ATTACKS_AND_KILOMETERS=20;
  private static final int CARD_WIDTH=75;
  private static final int CARD_HEIGHT=105;

  private static final int X_SAFETIES=(CARD_HEIGHT*2)/3;
  private static final int Y_SAFETIES=TOP_MARGIN;
  private static final int Y_COUPS_FOURRES=TOP_MARGIN+(CARD_HEIGHT-CARD_WIDTH)/2;

  private static final int Y_STACKS=Y_SAFETIES+CARD_HEIGHT+SPACE_BETWEEN_STACKS;

  private static final int X_SPEED_LIMIT_STACK=LEFT_MARGIN;
  private static final int Y_SPEED_LIMIT_STACK=Y_STACKS;

  private static final int X_ATTACK_STACK=X_SPEED_LIMIT_STACK+CARD_WIDTH+SPACE_BETWEEN_STACKS;
  private static final int Y_ATTACK_STACK=Y_STACKS;

  private static final int X_FIRST_KM_STACK=X_ATTACK_STACK+CARD_WIDTH+SPACE_BETWEEN_ATTACKS_AND_KILOMETERS;
  private static final int Y_FIRST_KM_STACK=Y_STACKS;
  private static final int SPACE_BETWEEN_KM_STACKS=CARD_WIDTH+SPACE_BETWEEN_STACKS;

  private static final int DELTA_Y_IN_KM_STACKS=CARD_HEIGHT/7;

  // 8*5=40*25=1000
  //private static final int NB_MAX_KM_CARDS=40;
  private static final int NB_KM_STACKS=8;
  private static final int NB_MAX_CARDS_PER_STACK=5;//NB_MAX_KM_CARDS/NB_KM_STACKS+(((NB_MAX_KM_CARDS%NB_KM_STACKS)!=0)?1:0);

  private static final int X_PLAYERS_HAND=X_SAFETIES;
  private static final int DELTA_Y_KM_STACKS=(NB_MAX_CARDS_PER_STACK-1)*DELTA_Y_IN_KM_STACKS+CARD_HEIGHT;
  private static final int Y_PLAYERS_HAND=Y_FIRST_KM_STACK+DELTA_Y_KM_STACKS+SPACE_BETWEEN_STACKS;

  private static final int X_SCORE=LEFT_MARGIN;
  private static final int Y_SCORE=TOP_MARGIN+CARD_HEIGHT/2;

  private Game _game;
  private Team _team;
  private ExposedCards _shownCards;
  private PlayersHand _playersHand;
  private boolean _showHand;
  private ImagesManager _imagesMgr;

  /**
   * Constructor (opponent team view).
   * @param game Associated game.
   * @param team Associated team.
   * @param imagesMgr Images manager.
   */
  public ExposedCardsPanel(Game game, Team team, ImagesManager imagesMgr)
  {
    super();
    _playersHand=null;
    _showHand=false;
    _imagesMgr=imagesMgr;
    init(game,team);
  }

  /**
   * Constructor (main player view).
   * @param game Associated game.
   * @param player Associated player.
   * @param imagesMgr Images manager.
   */
  public ExposedCardsPanel(Game game, Player player, ImagesManager imagesMgr)
  {
    super();
    _playersHand=player.getHand();
    _showHand=true;
    _imagesMgr=imagesMgr;
    init(game,player.getTeam());
  }

  private void init(Game game, Team team)
  {
    _game=game;
    _team=team;
    _shownCards=team.getExposedCards();
    Dimension d=computeSize();
    setPreferredSize(d);
    setSize(d);
  }

  private Dimension computeSize()
  {
    int width=X_FIRST_KM_STACK+NB_KM_STACKS*SPACE_BETWEEN_KM_STACKS-SPACE_BETWEEN_STACKS+RIGHT_MARGIN;
    int height;
    if (_showHand)
    {
      height=Y_PLAYERS_HAND+CARD_HEIGHT+BOTTOM_MARGIN;
    }
    else
    {
      height=Y_FIRST_KM_STACK+DELTA_Y_KM_STACKS+BOTTOM_MARGIN;
    }
    return new Dimension(width,height);
  }

  private void drawScaledCardAt(Graphics g,Image i,int x, int y)
  {
    int imageHeight=i.getHeight(null);
    int imageWidth=i.getWidth(null);
    int cardWidth=CARD_WIDTH;
    int cardHeight=CARD_HEIGHT;
    if (imageHeight<imageWidth)
    {
      cardHeight=CARD_WIDTH;
      cardWidth=CARD_HEIGHT;
    }
    g.drawImage(i,x,y,x+cardWidth,y+cardHeight,0,0,imageWidth,imageHeight,null);
  }

  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    // Draw safeties
    {
      int x=X_SAFETIES;
      ExposedSafetyCard[] safeties=_shownCards.getExposedSafeties();
      int size=safeties.length;
      for(int i=0;i<size;i++)
      {
        Card safety=safeties[i].getSafety();
        String cardName=safety.getModel().getName();
        if (safeties[i].isCoupFourre())
        {
          cardName="coupFourre_"+cardName;
        }
        Image image=_imagesMgr.getImage(cardName);
        if (safeties[i].isCoupFourre())
        {
          drawScaledCardAt(g,image,x,Y_COUPS_FOURRES);
          x+=CARD_HEIGHT;
        }
        else
        {
          drawScaledCardAt(g,image,x,Y_SAFETIES);
          x+=CARD_WIDTH;
        }
        x+=SPACE_BETWEEN_STACKS;
      }
    }
    // Draw speed limit stack
    Card speedLimit=_shownCards.getLastSpeedLimitCard();
    if (speedLimit!=null)
    {
      Image i=_imagesMgr.getImage(speedLimit.getModel().getName());
      drawScaledCardAt(g,i,X_SPEED_LIMIT_STACK,Y_SPEED_LIMIT_STACK);
    }
    // Draw attacks stack
    Card attackCard=_shownCards.getLastCardOfAttackStack();
    if (attackCard!=null)
    {
      Image i=_imagesMgr.getImage(attackCard.getModel().getName());
      drawScaledCardAt(g,i,X_ATTACK_STACK,Y_ATTACK_STACK);
    }
    // Draw kilometers
    {
      int stackNumber=0;
      int stackPosition=0;
      Card[] cards=_shownCards.getKilometersCards();
      for(int i=0;i<cards.length;i++)
      {
        CardModel model=cards[i].getModel();
        int x=X_FIRST_KM_STACK+stackNumber*SPACE_BETWEEN_KM_STACKS;
        int y=Y_FIRST_KM_STACK+(stackPosition*DELTA_Y_IN_KM_STACKS);
        Image image=_imagesMgr.getImage(model.getName());
        drawScaledCardAt(g,image, x, y);
        stackNumber++;
        if (stackNumber==NB_KM_STACKS)
        {
          stackNumber=0;
          stackPosition++;
        }
      }
    }
    // Draw players hand
    if (_showHand)
    {
      int nbCards=_playersHand.getSize();
      int x=X_PLAYERS_HAND;
      for(int i=0;i<nbCards;i++)
      {
        Card card=_playersHand.getCard(i);
        CardModel model=card.getModel();
        Image image=_imagesMgr.getImage(model.getName());
        drawScaledCardAt(g,image, x, Y_PLAYERS_HAND);
        x+=CARD_WIDTH+SPACE_BETWEEN_STACKS;
      }
    }
    // Draw score
    {
      int kilometers=_game.getKilometers(_team);
      int score=_game.getScore(_team);
      String scoreStr=String.valueOf(kilometers)+' '+String.valueOf(score);
      g.drawChars(scoreStr.toCharArray(),0,scoreStr.length(),X_SCORE,Y_SCORE);
    }
  }

  /**
   * Get the card under the given point.
   * @param p Point to use.
   * @return A card or <code>null</code>.
   */
  public Card getCardAt(Point p)
  {
    int nbCards=_playersHand.getSize();
    for(int i=0;i<nbCards;i++)
    {
      int minX=X_PLAYERS_HAND+i*(CARD_WIDTH+SPACE_BETWEEN_STACKS);
      int minY=Y_PLAYERS_HAND;
      int maxX=minX+CARD_WIDTH;
      int maxY=Y_PLAYERS_HAND+CARD_HEIGHT;

      if ((p.x>=minX) && (p.x<=maxX) && (p.y>=minY) && (p.y<=maxY))
      {
        Card card=_playersHand.getCard(i);
        return card;
      }
    }
    return null;
  }
}
