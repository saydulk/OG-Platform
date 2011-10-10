/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.model.volatility.surface;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

import org.testng.annotations.Test;

import com.opengamma.math.interpolation.GridInterpolator2D;
import com.opengamma.math.interpolation.LinearInterpolator1D;
import com.opengamma.math.surface.ConstantDoublesSurface;
import com.opengamma.math.surface.InterpolatedDoublesSurface;
import com.opengamma.math.surface.SurfaceShiftFunctionFactory;

/**
 * 
 */
public class VolatilitySurfaceTest {
  private static final LinearInterpolator1D LINEAR = new LinearInterpolator1D();
  private static final GridInterpolator2D INTERPOLATOR = new GridInterpolator2D(LINEAR, LINEAR);
  private static final InterpolatedDoublesSurface SURFACE = InterpolatedDoublesSurface.from(new double[] {0, 1, 2, 0, 1, 2, 0, 1, 2}, new double[] {0, 0, 0, 1, 1, 1, 2, 2, 2}, new double[] {4, 5, 6,
      4, 5, 6, 4, 5, 6}, INTERPOLATOR, "S");
  private static final VolatilitySurface VOL = new VolatilitySurface(SURFACE);

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testNull() {
    new VolatilitySurface(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testNullPair() {
    VOL.getVolatility(null);
  }

  @Test
  public void testGetters() {
    assertEquals(VOL.getSurface(), SURFACE);
  }

  @Test
  public void testHashCodeAndEquals() {
    VolatilitySurface other = new VolatilitySurface(SURFACE);
    assertEquals(other, VOL);
    assertEquals(other.hashCode(), VOL.hashCode());
    other = new VolatilitySurface(ConstantDoublesSurface.from(0.3));
    assertFalse(other.equals(VOL));
  }

  @Test
  public void testBuilders() {
    VolatilitySurface other = VOL.withParallelShift(0);
    assertFalse(other.equals(VOL));
    other = VOL.withParallelShift(2);
    InterpolatedDoublesSurface underlying = (InterpolatedDoublesSurface) SurfaceShiftFunctionFactory.getShiftedSurface(SURFACE, 2);
    assertEquals(underlying.getClass(), other.getSurface().getClass());
    assertEquals(((InterpolatedDoublesSurface) other.getSurface()).getInterpolator(), underlying.getInterpolator());
    assertArrayEquals(other.getSurface().getXData(), underlying.getXData());
    assertArrayEquals(other.getSurface().getYData(), underlying.getYData());
    assertArrayEquals(other.getSurface().getZData(), underlying.getZData());
    other = VOL.withSingleShift(0, 1, 0);
    assertFalse(other.equals(VOL));
    other = VOL.withSingleShift(0, 1, 2);
    underlying = (InterpolatedDoublesSurface) SurfaceShiftFunctionFactory.getShiftedSurface(SURFACE, 0, 1, 2);
    assertEquals(underlying.getClass(), other.getSurface().getClass());
    assertEquals(((InterpolatedDoublesSurface) other.getSurface()).getInterpolator(), underlying.getInterpolator());
    assertArrayEquals(other.getSurface().getXData(), underlying.getXData());
    assertArrayEquals(other.getSurface().getYData(), underlying.getYData());
    assertArrayEquals(other.getSurface().getZData(), underlying.getZData());
    other = VOL.withMultipleShifts(new double[] {0, 1}, new double[] {0, 1}, new double[] {0, 0});
    assertFalse(other.equals(VOL));
    other = VOL.withMultipleShifts(new double[] {0, 1}, new double[] {0, 1}, new double[] {0.9, 0.8});
    underlying = (InterpolatedDoublesSurface) SurfaceShiftFunctionFactory.getShiftedSurface(SURFACE, new double[] {0, 1}, new double[] {0, 1}, new double[] {0.9, 0.8});
    assertEquals(underlying.getClass(), other.getSurface().getClass());
    assertEquals(((InterpolatedDoublesSurface) other.getSurface()).getInterpolator(), underlying.getInterpolator());
    assertArrayEquals(other.getSurface().getXData(), underlying.getXData());
    assertArrayEquals(other.getSurface().getYData(), underlying.getYData());
    assertArrayEquals(other.getSurface().getZData(), underlying.getZData());
  }
}
