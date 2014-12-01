/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.marketdata;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZonedDateTime;

import com.google.common.collect.ImmutableSet;
import com.opengamma.timeseries.date.DateTimeSeries;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.result.FailureStatus;
import com.opengamma.util.result.Result;
import com.opengamma.util.time.LocalDateRange;

/**
 * Market data bundle that doesn't contain any data but records requests it receives for data.
 * <p>
 * All of the data lookup methods always return a failure result. Requests are recorded if the data
 * isn't available in the bundle of supplied data passed to the constructor.
 *
 * TODO make this an environment? that won't really work
 * TODO move to the engine package and make package-private
 */
public final class GatheringMarketDataBundle implements MarketDataBundle {

  private static final Result<?> FAILURE = Result.failure(FailureStatus.PENDING_DATA,
                                                          "Gathering requirements - no data available");

  private final MarketDataTime _time;
  private final MarketDataBundle _suppliedData;
  private final Set<SingleValueRequirement> _requirements;
  private final Set<TimeSeriesRequirement> _timeSeriesRequirements;

  private GatheringMarketDataBundle(MarketDataTime time, MarketDataBundle suppliedData) {
    this(time,
         suppliedData,
         Collections.newSetFromMap(new ConcurrentHashMap<SingleValueRequirement, Boolean>()),
         Collections.newSetFromMap(new ConcurrentHashMap<TimeSeriesRequirement, Boolean>()));
  }

  private GatheringMarketDataBundle(MarketDataTime time,
                                    MarketDataBundle suppliedData,
                                    Set<SingleValueRequirement> requirements,
                                    Set<TimeSeriesRequirement> timeSeriesRequirements) {
    _time = ArgumentChecker.notNull(time, "time");
    _suppliedData = ArgumentChecker.notNull(suppliedData, "suppliedData");
    _requirements = requirements;
    _timeSeriesRequirements = timeSeriesRequirements;
  }

  public static GatheringMarketDataBundle create(ZonedDateTime time, MarketDataBundle suppliedData) {
    return new GatheringMarketDataBundle(MarketDataTime.of(time), suppliedData);
  }

  public static GatheringMarketDataBundle create(LocalDate date, MarketDataBundle suppliedData) {
    return new GatheringMarketDataBundle(MarketDataTime.of(date), suppliedData);
  }

  public static GatheringMarketDataBundle create(MarketDataBundle suppliedData) {
    return new GatheringMarketDataBundle(MarketDataTime.VALUATION_TIME, suppliedData);
  }

  // TODO should probably store dataType somewhere. in the requirement? seems sensible
  @Override
  public <T> Result<T> get(MarketDataId<?> id, Class<T> dataType) {
    // only gather requirements for data that hasn't been supplied
    if (_suppliedData.get(id, dataType).isSuccess()) {
      return failure();
    }
    SingleValueRequirement requirement = SingleValueRequirement.of(id, _time);
    _requirements.add(requirement);
    return failure();
  }

  @Override
  public <T> Result<DateTimeSeries<LocalDate, T>> get(MarketDataId<?> id, Class<T> dataType, LocalDateRange dateRange) {
    // only gather requirements for data that hasn't been supplied
    if (_suppliedData.get(id, dataType, dateRange).isSuccess()) {
      return failure();
    }
    TimeSeriesRequirement requirement = TimeSeriesRequirement.of(id, dateRange);
    _timeSeriesRequirements.add(requirement);
    return failure();
  }

  @Override
  public MarketDataBundle withTime(ZonedDateTime time) {
    return new GatheringMarketDataBundle(MarketDataTime.of(time),
                                           _suppliedData,
                                           _requirements,
                                           _timeSeriesRequirements);
  }

  @Override
  public MarketDataBundle withDate(LocalDate date) {
    return new GatheringMarketDataBundle(MarketDataTime.of(date),
                                           _suppliedData,
                                           _requirements,
                                           _timeSeriesRequirements);
  }

  @SuppressWarnings("unchecked") // this is safe, the type is never used in a failure result
  private static <T> Result<T> failure() {
    return (Result<T>) FAILURE;
  }

  // TODO everything seems to want a set<ExternalIdBundle>
  public Set<SingleValueRequirement> getRequirements() {
    return ImmutableSet.copyOf(_requirements);
  }

  public Set<TimeSeriesRequirement> getTimeSeriesRequirements() {
    return ImmutableSet.copyOf(_timeSeriesRequirements);
  }
}
