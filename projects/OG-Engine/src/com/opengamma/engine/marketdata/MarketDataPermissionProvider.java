/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.engine.marketdata;

import java.util.Set;

import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.livedata.UserPrincipal;
import com.opengamma.util.PublicSPI;

/**
 * Used to query permissions on market data.
 */
@PublicSPI
public interface MarketDataPermissionProvider {

  /**
   * Checks whether has permission to view market data and returns the requirements
   * @param user
   * @param requirements
   * @return
   */
  Set<ValueRequirement> checkMarketDataPermissions(UserPrincipal user, Set<ValueRequirement> requirements);
  
}
