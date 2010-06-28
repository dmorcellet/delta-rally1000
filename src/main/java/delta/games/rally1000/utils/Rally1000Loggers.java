package delta.games.rally1000.utils;

import org.apache.log4j.Logger;

import delta.common.utils.traces.LoggersRegistry;

/**
 * Management class for all Rally1000 loggers.
 * @author DAM
 */
public abstract class Rally1000Loggers
{
  /**
   * Name of the "RALLY1000" logger.
   */
  public static final String RALLY1000="GAMES.RALLY1000";

  private static final Logger _rally1000Logger=LoggersRegistry.getLogger(RALLY1000);

  /**
   * Get the logger used for Rally1000 (GAMES.RALLY1000).
   * @return the logger used for Rally1000.
   */
  public static Logger getRally1000Logger()
  {
    return _rally1000Logger;
  }
}
