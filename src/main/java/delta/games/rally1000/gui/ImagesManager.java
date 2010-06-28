package delta.games.rally1000.gui;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import delta.games.rally1000.cards.CardNames;
import delta.games.rally1000.utils.Rally1000Loggers;

public class ImagesManager
{
  private static final Logger _logger=Rally1000Loggers.getRally1000Logger();

  private static final String CARDS_IMAGES_PACKAGE="delta/games/rally1000/resources/images/cards";

  private static ImagesManager _instance=null;

  private HashMap<String,Image> _cache;
  private HashMap<String,String> _mapCardNamesToImageNames;

  public static ImagesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new ImagesManager();
    }
    return _instance;
  }

  private ImagesManager()
  {
    _cache=new HashMap<String,Image>();
    configureImageNames();
  }

  private void configureImageNames()
  {
    _mapCardNamesToImageNames=new HashMap<String,String>();
    _mapCardNamesToImageNames.put(CardNames.KM_200,"200");
    _mapCardNamesToImageNames.put(CardNames.KM_25,"25");
    _mapCardNamesToImageNames.put(CardNames.KM_50,"50");
    _mapCardNamesToImageNames.put(CardNames.KM_75,"75");
    _mapCardNamesToImageNames.put(CardNames.KM_100,"100");
    _mapCardNamesToImageNames.put(CardNames.ACCIDENT,"accident");
    _mapCardNamesToImageNames.put(CardNames.DRIVING_ACE,"asDuVolant");
    _mapCardNamesToImageNames.put(CardNames.EXTRA_TANK,"citerne");
    _mapCardNamesToImageNames.put(CardNames.FLAT_TIRE,"crevaison");
    _mapCardNamesToImageNames.put(CardNames.GAS_CAN,"essence");
    _mapCardNamesToImageNames.put(CardNames.RED_LIGHT,"feuRouge");
    _mapCardNamesToImageNames.put(CardNames.GREEN_LIGHT,"feuVert");
    _mapCardNamesToImageNames.put(CardNames.END_OF_SPEED_LIMIT,"finLimitationVitesse");
    _mapCardNamesToImageNames.put(CardNames.PUNCTURE_PROOF,"increvable");
    _mapCardNamesToImageNames.put(CardNames.SPEED_LIMIT,"limitationVitesse");
    _mapCardNamesToImageNames.put(CardNames.OUT_OF_GAS,"panneDEssence");
    _mapCardNamesToImageNames.put(CardNames.RIGHT_OF_WAY,"prioritaire");
    _mapCardNamesToImageNames.put(CardNames.REPAIRS,"reparation");
    _mapCardNamesToImageNames.put(CardNames.SPARE_TIRE,"roueDeSecours");
  }

  private URL buildImageURL(String name)
  {
    String fullResourcePath=CARDS_IMAGES_PACKAGE+"/"+name+".png";
    URL url=getClass().getClassLoader().getResource(fullResourcePath);
    return url;
  }

  private Image loadImage(String imageName)
  {
    Image ret=null;
    try
    {
      URL url=buildImageURL(imageName);
    	ret=ImageIO.read(url);
    	if (ret!=null)
    	{
    	  _cache.put(imageName, ret);
    	}
    }
    catch(IOException ioe)
    {
      _logger.error("",ioe);
    }
    return ret;
  }

  public Image getImage(String cardName)
  {
    Image ret=null;
    String imageName=_mapCardNamesToImageNames.get(cardName);
    if (imageName!=null)
    {
      ret=_cache.get(imageName);
      if (ret==null)
      {
        ret=loadImage(imageName);
      }
    }
    return ret;
  }
}
