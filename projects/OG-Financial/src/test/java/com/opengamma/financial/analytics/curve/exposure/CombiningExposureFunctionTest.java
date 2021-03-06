/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.curve.exposure;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.opengamma.core.id.ExternalSchemes;
import com.opengamma.core.position.Trade;
import com.opengamma.core.security.SecuritySource;
import com.opengamma.financial.security.bond.CorporateBondSecurity;
import com.opengamma.financial.security.bond.GovernmentBondSecurity;
import com.opengamma.financial.security.bond.MunicipalBondSecurity;
import com.opengamma.financial.security.capfloor.CapFloorCMSSpreadSecurity;
import com.opengamma.financial.security.capfloor.CapFloorSecurity;
import com.opengamma.financial.security.cash.CashSecurity;
import com.opengamma.financial.security.cashflow.CashFlowSecurity;
import com.opengamma.financial.security.cds.CreditDefaultSwapIndexSecurity;
import com.opengamma.financial.security.cds.LegacyFixedRecoveryCDSSecurity;
import com.opengamma.financial.security.cds.LegacyRecoveryLockCDSSecurity;
import com.opengamma.financial.security.cds.LegacyVanillaCDSSecurity;
import com.opengamma.financial.security.cds.StandardFixedRecoveryCDSSecurity;
import com.opengamma.financial.security.cds.StandardRecoveryLockCDSSecurity;
import com.opengamma.financial.security.cds.StandardVanillaCDSSecurity;
import com.opengamma.financial.security.deposit.ContinuousZeroDepositSecurity;
import com.opengamma.financial.security.deposit.PeriodicZeroDepositSecurity;
import com.opengamma.financial.security.deposit.SimpleZeroDepositSecurity;
import com.opengamma.financial.security.equity.EquitySecurity;
import com.opengamma.financial.security.equity.EquityVarianceSwapSecurity;
import com.opengamma.financial.security.forward.AgricultureForwardSecurity;
import com.opengamma.financial.security.forward.EnergyForwardSecurity;
import com.opengamma.financial.security.forward.MetalForwardSecurity;
import com.opengamma.financial.security.fra.FRASecurity;
import com.opengamma.financial.security.future.AgricultureFutureSecurity;
import com.opengamma.financial.security.future.BondFutureSecurity;
import com.opengamma.financial.security.future.DeliverableSwapFutureSecurity;
import com.opengamma.financial.security.future.EnergyFutureSecurity;
import com.opengamma.financial.security.future.EquityFutureSecurity;
import com.opengamma.financial.security.future.EquityIndexDividendFutureSecurity;
import com.opengamma.financial.security.future.FXFutureSecurity;
import com.opengamma.financial.security.future.FederalFundsFutureSecurity;
import com.opengamma.financial.security.future.IndexFutureSecurity;
import com.opengamma.financial.security.future.InterestRateFutureSecurity;
import com.opengamma.financial.security.future.MetalFutureSecurity;
import com.opengamma.financial.security.future.StockFutureSecurity;
import com.opengamma.financial.security.fx.FXForwardSecurity;
import com.opengamma.financial.security.fx.FXVolatilitySwapSecurity;
import com.opengamma.financial.security.fx.NonDeliverableFXForwardSecurity;
import com.opengamma.financial.security.option.BondFutureOptionSecurity;
import com.opengamma.financial.security.option.CommodityFutureOptionSecurity;
import com.opengamma.financial.security.option.CreditDefaultSwapOptionSecurity;
import com.opengamma.financial.security.option.EquityBarrierOptionSecurity;
import com.opengamma.financial.security.option.EquityIndexDividendFutureOptionSecurity;
import com.opengamma.financial.security.option.EquityIndexFutureOptionSecurity;
import com.opengamma.financial.security.option.EquityIndexOptionSecurity;
import com.opengamma.financial.security.option.EquityOptionSecurity;
import com.opengamma.financial.security.option.FXBarrierOptionSecurity;
import com.opengamma.financial.security.option.FXDigitalOptionSecurity;
import com.opengamma.financial.security.option.FXOptionSecurity;
import com.opengamma.financial.security.option.FxFutureOptionSecurity;
import com.opengamma.financial.security.option.IRFutureOptionSecurity;
import com.opengamma.financial.security.option.NonDeliverableFXDigitalOptionSecurity;
import com.opengamma.financial.security.option.NonDeliverableFXOptionSecurity;
import com.opengamma.financial.security.option.SwaptionSecurity;
import com.opengamma.financial.security.swap.ForwardSwapSecurity;
import com.opengamma.financial.security.swap.SwapSecurity;
import com.opengamma.financial.security.swap.YearOnYearInflationSwapSecurity;
import com.opengamma.financial.security.swap.ZeroCouponInflationSwapSecurity;
import com.opengamma.id.ExternalId;
import com.opengamma.util.test.TestGroup;

/**
 * Unit test for CombinedExposureFunction.
 */
@Test(groups = TestGroup.UNIT)
public class CombiningExposureFunctionTest {
  private static final ExposureFunction EXPOSURE_FUNCTION = new CombiningExposureFunction(new UnderlyingExposureFunction(ExposureFunctionTestHelper.getSecuritySource(null)));
  
  @Test
  public void testAgriculturalFutureSecurity() {
    final AgricultureFutureSecurity future = ExposureFunctionTestHelper.getAgricultureFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testBondFutureOptionSecurity() {
    final BondFutureSecurity future = ExposureFunctionTestHelper.getBondFutureSecurity();
    final SecuritySource securitySource = ExposureFunctionTestHelper.getSecuritySource(future);
    final ExposureFunction exposureFunction = new UnderlyingExposureFunction(securitySource);
    final BondFutureOptionSecurity security = ExposureFunctionTestHelper.getBondFutureOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = exposureFunction.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testBondFutureSecurity() {
    final BondFutureSecurity future = ExposureFunctionTestHelper.getBondFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testCapFloorCMSSpreadSecurity() {
    final CapFloorCMSSpreadSecurity security = ExposureFunctionTestHelper.getCapFloorCMSSpreadSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertTrue(ids.get(0).equals(ExternalSchemes.syntheticSecurityId("USD 10y Swap" + ExposureFunction.SEPARATOR +
                                                                         "USD 15y Swap")) ||
                   ids.get(0).equals(ExternalSchemes.syntheticSecurityId("USD 15y Swap" + ExposureFunction.SEPARATOR +
                                                                             "USD 10y Swap")));
  }

  @Test
  public void testCapFloorSecurity() {
    final CapFloorSecurity security = ExposureFunctionTestHelper.getCapFloorSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("USD 6m Libor"), ids.get(0));
  }

  @Test
  public void testCashFlowSecurity() {
    final CashFlowSecurity security = ExposureFunctionTestHelper.getCashFlowSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testCashSecurity() {
    final CashSecurity cash = ExposureFunctionTestHelper.getCashSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(cash);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testContinuousZeroDepositSecurity() {
    final ContinuousZeroDepositSecurity security = ExposureFunctionTestHelper.getContinuousZeroDepositSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testCorporateBondSecurity() {
    final CorporateBondSecurity security = ExposureFunctionTestHelper.getCorporateBondSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEnergyFutureOptionSecurity() {
    final EnergyFutureSecurity future = ExposureFunctionTestHelper.getEnergyFutureSecurity();
    final SecuritySource securitySource = ExposureFunctionTestHelper.getSecuritySource(future);
    final ExposureFunction exposureFunction = new UnderlyingExposureFunction(securitySource);
    final CommodityFutureOptionSecurity security = ExposureFunctionTestHelper.getEnergyFutureOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = exposureFunction.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEnergyFutureSecurity() {
    final EnergyFutureSecurity future = ExposureFunctionTestHelper.getEnergyFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEquityBarrierOptionSecurity() {
    final EquityBarrierOptionSecurity security = ExposureFunctionTestHelper.getEquityBarrierOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEquityFutureSecurity() {
    final EquityFutureSecurity future = ExposureFunctionTestHelper.getEquityFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEquityIndexDividendFutureSecurity() {
    final SecuritySource securitySource = ExposureFunctionTestHelper.getSecuritySource(null);
    final EquityIndexDividendFutureSecurity future = ExposureFunctionTestHelper.getEquityIndexDividendFutureSecurity();
    final ExposureFunction exposureFunction = new UnderlyingExposureFunction(securitySource);
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = exposureFunction.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEquitySecurity() {
    final EquitySecurity security = ExposureFunctionTestHelper.getEquitySecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testFRASecurity() {
    final FRASecurity fra = ExposureFunctionTestHelper.getFRASecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(fra);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(fra.getUnderlyingId(), ids.get(0));
  }

  @Test
  public void testFXFutureOptionSecurity() {
    final FXFutureSecurity future = ExposureFunctionTestHelper.getFXFutureSecurity();
    final SecuritySource securitySource = ExposureFunctionTestHelper.getSecuritySource(future);
    final ExposureFunction exposureFunction = new UnderlyingExposureFunction(securitySource);
    final FxFutureOptionSecurity security = ExposureFunctionTestHelper.getFXFutureOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = exposureFunction.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testFXFutureSecurity() {
    final FXFutureSecurity future = ExposureFunctionTestHelper.getFXFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testIndexFutureSecurity() {
    final IndexFutureSecurity future = ExposureFunctionTestHelper.getIndexFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testInterestRateFutureSecurity() {
    final InterestRateFutureSecurity future = ExposureFunctionTestHelper.getInterestRateFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("USD 3m Libor"), ids.get(0));
  }

  @Test
  public void testFederalFundsFutureSecurity() {
    final FederalFundsFutureSecurity future = ExposureFunctionTestHelper.getFederalFundsFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("Fed Funds"), ids.get(0));
  }

  @Test
  public void testMetalFutureSecurity() {
    final MetalFutureSecurity future = ExposureFunctionTestHelper.getMetalFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testStockFutureSecurity() {
    final StockFutureSecurity future = ExposureFunctionTestHelper.getStockFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(future);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testAgricultureForwardSecurity() {
    final AgricultureForwardSecurity security = ExposureFunctionTestHelper.getAgricultureForwardSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testCreditDefaultSwapIndexSecurity() {
    final CreditDefaultSwapIndexSecurity security = ExposureFunctionTestHelper.getCreditDefaultSwapIndexSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testCreditDefaultSwapOptionSecurity() {
    final CreditDefaultSwapOptionSecurity security = ExposureFunctionTestHelper.getCreditDefaultSwapOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testDeliverableSwapSecurity() {
    final DeliverableSwapFutureSecurity security = ExposureFunctionTestHelper.getDeliverableSwapFutureSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEnergyForwardSecurity() {
    final EnergyForwardSecurity security = ExposureFunctionTestHelper.getEnergyForwardSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEquityIndexDividendFutureOptionSecurity() {
    final EquityIndexDividendFutureOptionSecurity security = ExposureFunctionTestHelper.getEquityIndexDividendFutureOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEquityIndexFutureOptionSecurity() {
    final EquityIndexFutureOptionSecurity security = ExposureFunctionTestHelper.getEquityIndexFutureOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEquityIndexOptionSecurity() {
    final EquityIndexOptionSecurity security = ExposureFunctionTestHelper.getEquityIndexOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEquityOptionSecurity() {
    final EquityOptionSecurity security = ExposureFunctionTestHelper.getEquityOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testEquityVarianceSwapSecurity() {
    final EquityVarianceSwapSecurity security = ExposureFunctionTestHelper.getEquityVarianceSwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testFixedFloatSwapSecurity() {
    SwapSecurity security = ExposureFunctionTestHelper.getPayFixedFloatSwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("3m Euribor"), ids.get(0));
    security = ExposureFunctionTestHelper.getReceiveFixedFloatSwapSecurity();
    trade = ExposureFunctionTestHelper.getTrade(security);
    ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("3m Euribor"), ids.get(0));
  }

  @Test
  public void testFloatFloatSwapSecurity() {
    final SwapSecurity security = ExposureFunctionTestHelper.getFloatFloatSwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("6m Euribor" + ExposureFunction.SEPARATOR + "3m Euribor"),
                 ids.get(0));
  }

  @Test
  public void testForwardFixedFloatSwapSecurity() {
    ForwardSwapSecurity security = ExposureFunctionTestHelper.getPayForwardFixedFloatSwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("3m Euribor"), ids.get(0));
    security = ExposureFunctionTestHelper.getReceiveForwardFixedFloatSwapSecurity();
    trade = ExposureFunctionTestHelper.getTrade(security);
    ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("3m Euribor"), ids.get(0));
  }

  @Test
  public void testForwardFloatFloatSwapSecurity() {
    final ForwardSwapSecurity security = ExposureFunctionTestHelper.getForwardFloatFloatSwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("6m Euribor" + ExposureFunction.SEPARATOR + "3m Euribor"),
                 ids.get(0));
  }

  @Test
  public void testForwardXCcySwapSecurity() {
    final ForwardSwapSecurity security = ExposureFunctionTestHelper.getForwardXCcySwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertTrue(ids.get(0).equals(ExternalSchemes.syntheticSecurityId("3m USD Libor" + ExposureFunction.SEPARATOR +
                                                                         "6m Euribor")) ||
        ids.get(0).equals(ExternalSchemes.syntheticSecurityId("6m Euribor" + ExposureFunction.SEPARATOR +
                                                                  "3m USD Libor")));
  }

  @Test
  public void testFXBarrierOptionSecurity() {
    final FXBarrierOptionSecurity security = ExposureFunctionTestHelper.getFXBarrierOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testFXDigitalOptionSecurity() {
    final FXDigitalOptionSecurity security = ExposureFunctionTestHelper.getFXDigitalOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testFXForwardSecurity() {
    final FXForwardSecurity security = ExposureFunctionTestHelper.getFXForwardSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testFXOptionSecurity() {
    final FXOptionSecurity security = ExposureFunctionTestHelper.getFXOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testGovernmentBondSecurity() {
    final GovernmentBondSecurity security = ExposureFunctionTestHelper.getGovernmentBondSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testInterestRateFutureOptionSecurity() {
    final IRFutureOptionSecurity security = ExposureFunctionTestHelper.getInterestRateFutureOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testLegacyFixedRecoveryCDSSecurity() {
    final LegacyFixedRecoveryCDSSecurity security = ExposureFunctionTestHelper.getLegacyFixedRecoveryCDSSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testLegacyRecoveryLockCDSSecurity() {
    final LegacyRecoveryLockCDSSecurity security = ExposureFunctionTestHelper.getLegacyRecoveryLockCDSSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testLegacyVanillaCDSSecurity() {
    final LegacyVanillaCDSSecurity security = ExposureFunctionTestHelper.getLegacyVanillaCDSSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testMetalForwardSecurity() {
    final MetalForwardSecurity security = ExposureFunctionTestHelper.getMetalForwardSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testMunicipalBondSecurity() {
    final MunicipalBondSecurity security = ExposureFunctionTestHelper.getMunicipalBondSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testNonDeliverableFXDigitalOptionSecurity() {
    final NonDeliverableFXDigitalOptionSecurity security = ExposureFunctionTestHelper.getNonDeliverableFXDigitalOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testNonDeliverableFXForwardSecurity() {
    final NonDeliverableFXForwardSecurity security = ExposureFunctionTestHelper.getNonDeliverableFXForwardSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testNonDeliverableFXOptionSecurity() {
    final NonDeliverableFXOptionSecurity security = ExposureFunctionTestHelper.getNonDeliverableFXOptionSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testPeriodicZeroDepositSecurity() {
    final PeriodicZeroDepositSecurity security = ExposureFunctionTestHelper.getPeriodicZeroDepositSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testSimpleZeroDepositSecurity() {
    final SimpleZeroDepositSecurity security = ExposureFunctionTestHelper.getSimpleZeroDepositSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testStandardFixedRecoveryCDSSecurity() {
    final StandardFixedRecoveryCDSSecurity security = ExposureFunctionTestHelper.getStandardFixedRecoveryCDSSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testStandardRecoveryLockCDSSecurity() {
    final StandardRecoveryLockCDSSecurity security = ExposureFunctionTestHelper.getStandardRecoveryLockCDSSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testStandardVanillaCDSSecurity() {
    final StandardVanillaCDSSecurity security = ExposureFunctionTestHelper.getStandardVanillaCDSSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }

  @Test
  public void testSwaptionSecurity() {
    SwapSecurity underlying = ExposureFunctionTestHelper.getPayFixedFloatSwapSecurity();
    SwaptionSecurity security = ExposureFunctionTestHelper.getPaySwaptionSecurity();
    UnderlyingExposureFunction underlyingExposureFunction = new UnderlyingExposureFunction(ExposureFunctionTestHelper.getSecuritySource(underlying));
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = underlyingExposureFunction.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("3m Euribor"), ids.get(0));
    underlying = ExposureFunctionTestHelper.getReceiveFixedFloatSwapSecurity();
    security = ExposureFunctionTestHelper.getPaySwaptionSecurity();
    trade = ExposureFunctionTestHelper.getTrade(security);
    ids = underlyingExposureFunction.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("3m Euribor"), ids.get(0));
  }

  @Test
  public void testXCcySwapSecurity() {
    final SwapSecurity security = ExposureFunctionTestHelper.getXCcySwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("3m USD Libor" + ExposureFunction.SEPARATOR + "3m Euribor"),
                 ids.get(0));
  }

  @Test
  public void testPayYoYInflationSwapSecurity() {
    final YearOnYearInflationSwapSecurity security = ExposureFunctionTestHelper.getPayYoYInflationSwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("CPI"), ids.get(0));
  }

  @Test
  public void testReceiveYoYInflationSwapSecurity() {
    final YearOnYearInflationSwapSecurity security = ExposureFunctionTestHelper.getPayYoYInflationSwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("CPI"), ids.get(0));
  }

  @Test
  public void testPayZeroCouponInflationSwapSecurity() {
    final ZeroCouponInflationSwapSecurity security = ExposureFunctionTestHelper.getPayZeroCouponInflationSwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("CPI"), ids.get(0));
  }

  @Test
  public void testReceiveZeroCouponInflationSwapSecurity() {
    final ZeroCouponInflationSwapSecurity security = ExposureFunctionTestHelper.getReceiveZeroCouponInflationSwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertEquals(1, ids.size());
    assertEquals(ExternalSchemes.syntheticSecurityId("CPI"), ids.get(0));
  }

  @Test
  public void testFXVolatilitySwapSecurity() {
    final FXVolatilitySwapSecurity security = ExposureFunctionTestHelper.getFXVolatilitySwapSecurity();
    Trade trade = ExposureFunctionTestHelper.getTrade(security);
    List<ExternalId> ids = EXPOSURE_FUNCTION.getIds(trade);
    assertNull(ids);
  }
}
