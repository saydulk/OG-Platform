/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.volatility.cube;

import java.util.ArrayList;
import java.util.Collection;

import com.opengamma.id.VersionCorrection;
import com.opengamma.util.money.Currency;

/**
 * Aggregates an ordered set of sources into a single source.
 */
public class AggregatingVolatilityCubeDefinitionSource implements VolatilityCubeDefinitionSource {

  private final Collection<VolatilityCubeDefinitionSource> _sources;

  public AggregatingVolatilityCubeDefinitionSource(final Collection<VolatilityCubeDefinitionSource> sources) {
    _sources = new ArrayList<VolatilityCubeDefinitionSource>(sources);
  }

  @Override
  public VolatilityCubeDefinition getDefinition(final String name, final String instrumentType) {
    for (VolatilityCubeDefinitionSource source : _sources) {
      VolatilityCubeDefinition definition = source.getDefinition(name, name);
      if (definition != null) {
        return definition;
      }
    }
    return null;
  }

  @Override
  public VolatilityCubeDefinition getDefinition(final String name, final String instrumentType, VersionCorrection versionCorrection) {
    for (VolatilityCubeDefinitionSource source : _sources) {
      VolatilityCubeDefinition definition = source.getDefinition(name, instrumentType, versionCorrection);
      if (definition != null) {
        return definition;
      }
    }
    return null;
  }
}
