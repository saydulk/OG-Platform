/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.credit.isdastandardmodel;

import org.threeten.bp.LocalDate;

import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCounts;
import com.opengamma.util.ArgumentChecker;

/**
 * This represents one payment period on the premium leg of a CDS
 */
public class CDSCoupon {
  private static final DayCount ACT_365 = DayCounts.ACT_365;
  private static final DayCount ACT_360 = DayCounts.ACT_360;
  private static final boolean PROTECTION_FROM_START = true;

  private final double _effStart;
  private final double _effEnd;
  private final double _paymentTime;
  private final double _yearFrac;
  private final double _ycRatio;

  /**
   * Make a set of CDSCoupon used by {@link CDSAnalytic} given a trade date and the schedule of the accrual periods 
   * @param tradeDate The trade date 
   * @param leg schedule of the accrual periods
   * @param protectionFromStartOfDay If true the protection is from the start of day and the effective accrual start and end dates are one day less. The exception is the final 
   * accrual end date which has one day added (if  protectionFromStartOfDay = true) in ISDAPremiumLegSchedule to compensate for this, so the  accrual end date is just the CDS maturity.
   * The effect of having protectionFromStartOfDay = true is to add an extra day of protection. 
   * @param accrualDCC The day count used to compute accrual periods 
   * @param curveDCC  Day count used on curve (NOTE ISDA uses ACT/365 (fixed) and it is not recommended to change this)
   * @see {@link CDSAnalytic}
   * @return A set of CDSCoupon
   */
  public static CDSCoupon[] makeCoupons(final LocalDate tradeDate, final ISDAPremiumLegSchedule leg, final boolean protectionFromStartOfDay, final DayCount accrualDCC, final DayCount curveDCC) {
    ArgumentChecker.notNull(leg, "leg");
    final int n = leg.getNumPayments();
    final CDSCoupon[] res = new CDSCoupon[n];
    for (int i = 0; i < n; i++) {
      final LocalDate[] dates = leg.getAccPaymentDateTriplet(i);
      res[i] = new CDSCoupon(tradeDate, dates[0], dates[1], dates[2], protectionFromStartOfDay, accrualDCC, curveDCC);
    }
    return res;
  }

  /**
   * Make a set of CDSCoupon used by {@link CDSAnalytic} given a trade date and a set of {@link CDSCouponDes}
   * @param tradeDate The trade date 
   * @param couponsDes Description of CDS accrual periods with LocalDate 
   * @param protectionFromStartOfDay If true the protection is from the start of day and the effective accrual start and end dates are one day less. The exception is the final 
   * accrual end date which should have one day added (if  protectionFromStartOfDay = true) in the final CDSCouponDes to compensate for this, so the  accrual end date is just the
   *  CDS maturity. The effect of having protectionFromStartOfDay = true is to add an extra day of protection. 
   * @param curveDCC Day count used on curve (NOTE ISDA uses ACT/365 (fixed) and it is not recommended to change this)
   * @return A set of CDSCoupon
   */
  public static CDSCoupon[] makeCoupons(final LocalDate tradeDate, final CDSCouponDes[] couponsDes, final boolean protectionFromStartOfDay, final DayCount curveDCC) {
    ArgumentChecker.noNulls(couponsDes, "couponsDes");
    final int n = couponsDes.length;
    ArgumentChecker.isTrue(couponsDes[n - 1].getPaymentDate().isAfter(tradeDate), "all coupons have expired");
    int count = 0;
    while (tradeDate.isAfter(couponsDes[count].getPaymentDate())) {
      count++;
    }
    final int nCoupons = n - count;
    final CDSCoupon[] coupons = new CDSCoupon[nCoupons];
    for (int i = 0; i < nCoupons; i++) {
      coupons[i] = new CDSCoupon(tradeDate, couponsDes[i + count], protectionFromStartOfDay, curveDCC);
    }
    return coupons;
  }

  /**
   * Turn a date based description of a CDS accrual period ({@link CDSCouponDes}) into an analytic description ({@link CDSCoupon}). This has protection from  start of day  
   * and uses ACT/360 for the accrual day count. 
   * @param tradeDate The trade date 
   * @param coupon A date based description of a CDS accrual period 
   */
  public CDSCoupon(final LocalDate tradeDate, final CDSCouponDes coupon) {
    this(tradeDate, coupon, PROTECTION_FROM_START, ACT_360);
  }

  /**
  * Turn a date based description of a CDS accrual period ({@link CDSCouponDes}) into an analytic description ({@link CDSCoupon}). This has protection from  start of day  
   * and uses ACT/360 for the accrual day count. 
   * @param tradeDate The trade date 
   * @param coupon A date based description of a CDS accrual period 
   * @param curveDCC Day count used on curve (NOTE ISDA uses ACT/365 (fixed) and it is not recommended to change this)
   */
  public CDSCoupon(final LocalDate tradeDate, final CDSCouponDes coupon, final DayCount curveDCC) {
    this(tradeDate, coupon, PROTECTION_FROM_START, curveDCC);
  }

  /**
  * Turn a date based description of a CDS accrual period ({@link CDSCouponDes}) into an analytic description ({@link CDSCoupon}). 
  * This uses ACT/360 for the accrual day count. 
   * @param tradeDate The trade date 
   * @param coupon A date based description of a CDS accrual period 
   * @param protectionFromStartOfDay If true the protection is from the start of day and the effective accrual start and end dates are one day less. The exception is the final 
   * accrual end date which should have one day added (if  protectionFromStartOfDay = true) in the final CDSCouponDes to compensate for this, so the  accrual end date is just the
   *  CDS maturity. The effect of having protectionFromStartOfDay = true is to add an extra day of protection. 
   */
  public CDSCoupon(final LocalDate tradeDate, final CDSCouponDes coupon, final boolean protectionFromStartOfDay) {
    this(tradeDate, coupon, protectionFromStartOfDay, ACT_360);
  }

  /**
    * Turn a date based description of a CDS accrual period ({@link CDSCouponDes}) into an analytic description ({@link CDSCoupon}). 
  * This uses ACT/360 for the accrual day count. 
   * @param tradeDate The trade date 
   * @param coupon A date based description of a CDS accrual period 
   * @param protectionFromStartOfDay If true the protection is from the start of day and the effective accrual start and end dates are one day less. The exception is the final 
   * accrual end date which should have one day added (if  protectionFromStartOfDay = true) in the final CDSCouponDes to compensate for this, so the  accrual end date is just the
   *  CDS maturity. The effect of having protectionFromStartOfDay = true is to add an extra day of protection. 
   * @param curveDCC Day count used on curve (NOTE ISDA uses ACT/365 (fixed) and it is not recommended to change this)
   */
  public CDSCoupon(final LocalDate tradeDate, final CDSCouponDes coupon, final boolean protectionFromStartOfDay, final DayCount curveDCC) {
    ArgumentChecker.notNull(coupon, "coupon");
    ArgumentChecker.notNull(curveDCC, "curveDCC");
    ArgumentChecker.isFalse(tradeDate.isAfter(coupon.getPaymentDate()), "coupon payment is in the past");

    final LocalDate effStart = protectionFromStartOfDay ? coupon.getAccStart().minusDays(1) : coupon.getAccStart();
    final LocalDate effEnd = protectionFromStartOfDay ? coupon.getAccEnd().minusDays(1) : coupon.getAccEnd();

    _effStart = effStart.isBefore(tradeDate) ? -curveDCC.getDayCountFraction(effStart, tradeDate) : curveDCC.getDayCountFraction(tradeDate, effStart);
    _effEnd = curveDCC.getDayCountFraction(tradeDate, effEnd);
    _paymentTime = curveDCC.getDayCountFraction(tradeDate, coupon.getPaymentDate());
    _yearFrac = coupon.getYearFrac();
    _ycRatio = _yearFrac / curveDCC.getDayCountFraction(coupon.getAccStart(), coupon.getAccEnd());
  }

  /**
   * Setup a analytic description (i.e. involving only doubles) of a single CDS premium payment period seen from a particular trade
   * date. Protection is taken from start of day; ACT/360 is used for the accrual DCC and ACT/365F for the curve DCC  
   * @param tradeDate The trade date (this is the base date that discount factors and survival probabilities are measured from)
   * @param premiumDateTriplet The three dates: start and end of the accrual period and the payment time 
   */
  public CDSCoupon(final LocalDate tradeDate, final LocalDate... premiumDateTriplet) {
    this(toDoubles(tradeDate, PROTECTION_FROM_START, ACT_360, ACT_365, premiumDateTriplet));
  }

  /**
   *
   * Setup a analytic description (i.e. involving only doubles) of a single CDS premium payment period seen from a particular trade
   * date.  ACT/360 is used for the accrual DCC and ACT/365F for the curve DCC  
   * @param tradeDate The trade date (this is the base date that discount factors and survival probabilities are measured from)
   * @param accStart The start of the accrual period 
   * @param accEnd The end of the accrual period 
   * @param paymentDate The date of the premium payment 
   * @param protectionFromStartOfDay true if protection is from the start of day (true for standard CDS) 
   */
  public CDSCoupon(final LocalDate tradeDate, final LocalDate accStart, final LocalDate accEnd, final LocalDate paymentDate, final boolean protectionFromStartOfDay) {
    this(toDoubles(tradeDate, protectionFromStartOfDay, ACT_360, ACT_365, accStart, accEnd, paymentDate));
  }

  /**
   * Setup a analytic description (i.e. involving only doubles) of a single CDS premium payment period seen from a particular trade
   * date. 
   * @param tradeDate The trade date (this is the base date that discount factors and survival probabilities are measured from)
   * @param premiumDateTriplet  The three dates: start and end of the accrual period and the payment time 
   * @param protectionFromStartOfDay true if protection is from the start of day (true for standard CDS) 
   * @param accrualDCC The day-count-convention used for calculation the accrual period (ACT/360 for standard CDS) 
   * @param curveDCC The day-count-convention used for converting dates to time intervals along curves - this should be ACT/365F 
   */
  public CDSCoupon(final LocalDate tradeDate, final LocalDate[] premiumDateTriplet, final boolean protectionFromStartOfDay, final DayCount accrualDCC, final DayCount curveDCC) {
    this(toDoubles(tradeDate, protectionFromStartOfDay, accrualDCC, curveDCC, premiumDateTriplet));
  }

  /**
   * Setup a analytic description (i.e. involving only doubles) of a single CDS premium payment period seen from a particular trade
   * date. 
   * @param tradeDate The trade date (this is the base date that discount factors and survival probabilities are measured from)
   * @param accStart The start of the accrual period 
   * @param accEnd The end of the accrual period 
   * @param paymentDate The date of the premium payment 
   * @param protectionFromStartOfDay true if protection is from the start of day (true for standard CDS) 
   * @param accrualDCC The day-count-convention used for calculation the accrual period (ACT/360 for standard CDS) 
   * @param curveDCC The day-count-convention used for converting dates to time intervals along curves - this should be ACT/365F 
   */
  public CDSCoupon(final LocalDate tradeDate, final LocalDate accStart, final LocalDate accEnd, final LocalDate paymentDate, final boolean protectionFromStartOfDay, final DayCount accrualDCC,
      final DayCount curveDCC) {
    this(toDoubles(tradeDate, protectionFromStartOfDay, accrualDCC, curveDCC, accStart, accEnd, paymentDate));
  }

  private CDSCoupon(final double... data) {
    _effStart = data[0];
    _effEnd = data[1];
    _paymentTime = data[2];
    _yearFrac = data[3];
    _ycRatio = data[4];
  }

  @SuppressWarnings("unused")
  private CDSCoupon(final CDSCoupon other) {
    ArgumentChecker.notNull(other, "other");
    _paymentTime = other._paymentTime;
    _yearFrac = other._yearFrac;
    _effStart = other._effStart;
    _effEnd = other._effEnd;
    _ycRatio = other._ycRatio;
  }

  private static double[] toDoubles(final LocalDate tradeDate, final boolean protectionFromStartOfDay, final DayCount accrualDCC, final DayCount curveDCC, final LocalDate... premDates) {
    ArgumentChecker.notNull(tradeDate, "tradeDate");
    ArgumentChecker.noNulls(premDates, "premDates");
    ArgumentChecker.isTrue(3 == premDates.length, "premDates must be length 3");
    ArgumentChecker.notNull(accrualDCC, "accrualDCC");
    ArgumentChecker.notNull(curveDCC, "curveDCC");
    final LocalDate accStart = premDates[0];
    final LocalDate accEnd = premDates[1];
    final LocalDate paymentDate = premDates[2];
    ArgumentChecker.isTrue(accEnd.isAfter(accStart), "require accEnd after accStart");
    //  ArgumentChecker.isTrue(paymentDate.isAfter(accStart), "require paymentDate after accStart");
    ArgumentChecker.isFalse(tradeDate.isAfter(paymentDate), "coupon payment is in the past");

    final LocalDate effStart = protectionFromStartOfDay ? accStart.minusDays(1) : accStart;
    final LocalDate effEnd = protectionFromStartOfDay ? accEnd.minusDays(1) : accEnd;

    final double[] res = new double[5];
    res[0] = effStart.isBefore(tradeDate) ? -curveDCC.getDayCountFraction(effStart, tradeDate) : curveDCC.getDayCountFraction(tradeDate, effStart);
    res[1] = curveDCC.getDayCountFraction(tradeDate, effEnd);
    res[2] = curveDCC.getDayCountFraction(tradeDate, paymentDate);
    res[3] = accrualDCC.getDayCountFraction(accStart, accEnd);
    res[4] = res[3] / curveDCC.getDayCountFraction(accStart, accEnd);
    return res;
  }

  /**
   * Gets the paymentTime.
   * @return the paymentTime
   */
  public double getPaymentTime() {
    return _paymentTime;
  }

  /**
   * Gets the yearFrac.
   * @return the yearFrac
   */
  public double getYearFrac() {
    return _yearFrac;
  }

  /**
   * Gets the effStart.
   * @return the effStart
   */
  public double getEffStart() {
    return _effStart;
  }

  /**
   * Gets the effEnd.
   * @return the effEnd
   */
  public double getEffEnd() {
    return _effEnd;
  }

  /**
   * Gets the ratio of the accrual period year fraction calculated using the accrual DCC to that calculated using the curve DCC. This is used in accrual on default calculations
   * @return the year fraction ratio
   */
  public double getYFRatio() {
    return _ycRatio;
  }

  /**
   * Produce a coupon with payments and accrual start/end offset by a given amount. For example if an offset of 0.5 was applied to a coupon with 
   * effStart, effEnd and payment time of 0, 0.25 and 0.25,  the new coupon would have 0.5, 0.75, 0.75 (effStart, effEnd, payment time)
   * @param offset amount of offset (in years)
   * @return offset coupon 
   */
  public CDSCoupon withOffset(final double offset) {
    return new CDSCoupon(_effStart + offset, _effEnd + offset, _paymentTime + offset, _yearFrac, _ycRatio);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(_effEnd);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(_effStart);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(_paymentTime);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(_ycRatio);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(_yearFrac);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CDSCoupon other = (CDSCoupon) obj;
    if (Double.doubleToLongBits(_effEnd) != Double.doubleToLongBits(other._effEnd)) {
      return false;
    }
    if (Double.doubleToLongBits(_effStart) != Double.doubleToLongBits(other._effStart)) {
      return false;
    }
    if (Double.doubleToLongBits(_paymentTime) != Double.doubleToLongBits(other._paymentTime)) {
      return false;
    }
    if (Double.doubleToLongBits(_ycRatio) != Double.doubleToLongBits(other._ycRatio)) {
      return false;
    }
    if (Double.doubleToLongBits(_yearFrac) != Double.doubleToLongBits(other._yearFrac)) {
      return false;
    }
    return true;
  }

}
