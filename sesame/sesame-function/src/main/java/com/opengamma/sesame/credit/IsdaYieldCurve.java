/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.sesame.credit;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.analytics.financial.credit.isdastandardmodel.ISDACompliantYieldCurve;
import com.opengamma.financial.analytics.isda.credit.YieldCurveData;

/**
 * Represents a yield curve for use in pricing on the ISDA model.
 * As well as holding the calibrated curve, the curve data used
 * as input to calibration is also captured. This is useful when
 * access to term structure and/or base market data is required.
 */
@BeanDefinition
public final class IsdaYieldCurve implements ImmutableBean {

  /**
   * The data used to calibrated this curve, i.e. term structure,
   * market data, conventions, etc. If the IsdaYieldCurve is created with a
   * curve from the multicurve bundle, the YieldCurveData can be null
   */
  @PropertyDefinition
  private final YieldCurveData _curveData;
  
  /**
   * The calibrated yield curve.
   */
  @PropertyDefinition(validate = "notNull")
  private final ISDACompliantYieldCurve _calibratedCurve;
  
  
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code IsdaYieldCurve}.
   * @return the meta-bean, not null
   */
  public static IsdaYieldCurve.Meta meta() {
    return IsdaYieldCurve.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(IsdaYieldCurve.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static IsdaYieldCurve.Builder builder() {
    return new IsdaYieldCurve.Builder();
  }

  private IsdaYieldCurve(
      YieldCurveData curveData,
      ISDACompliantYieldCurve calibratedCurve) {
    JodaBeanUtils.notNull(calibratedCurve, "calibratedCurve");
    this._curveData = curveData;
    this._calibratedCurve = calibratedCurve;
  }

  @Override
  public IsdaYieldCurve.Meta metaBean() {
    return IsdaYieldCurve.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the data used to calibrated this curve, i.e. term structure,
   * market data, conventions, etc. If the IsdaYieldCurve is created with a
   * curve from the multicurve bundle, the YieldCurveData can be null
   * @return the value of the property
   */
  public YieldCurveData getCurveData() {
    return _curveData;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the calibrated yield curve.
   * @return the value of the property, not null
   */
  public ISDACompliantYieldCurve getCalibratedCurve() {
    return _calibratedCurve;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      IsdaYieldCurve other = (IsdaYieldCurve) obj;
      return JodaBeanUtils.equal(getCurveData(), other.getCurveData()) &&
          JodaBeanUtils.equal(getCalibratedCurve(), other.getCalibratedCurve());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getCurveData());
    hash = hash * 31 + JodaBeanUtils.hashCode(getCalibratedCurve());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("IsdaYieldCurve{");
    buf.append("curveData").append('=').append(getCurveData()).append(',').append(' ');
    buf.append("calibratedCurve").append('=').append(JodaBeanUtils.toString(getCalibratedCurve()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code IsdaYieldCurve}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code curveData} property.
     */
    private final MetaProperty<YieldCurveData> _curveData = DirectMetaProperty.ofImmutable(
        this, "curveData", IsdaYieldCurve.class, YieldCurveData.class);
    /**
     * The meta-property for the {@code calibratedCurve} property.
     */
    private final MetaProperty<ISDACompliantYieldCurve> _calibratedCurve = DirectMetaProperty.ofImmutable(
        this, "calibratedCurve", IsdaYieldCurve.class, ISDACompliantYieldCurve.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "curveData",
        "calibratedCurve");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 770856249:  // curveData
          return _curveData;
        case -1314959246:  // calibratedCurve
          return _calibratedCurve;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public IsdaYieldCurve.Builder builder() {
      return new IsdaYieldCurve.Builder();
    }

    @Override
    public Class<? extends IsdaYieldCurve> beanType() {
      return IsdaYieldCurve.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code curveData} property.
     * @return the meta-property, not null
     */
    public MetaProperty<YieldCurveData> curveData() {
      return _curveData;
    }

    /**
     * The meta-property for the {@code calibratedCurve} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ISDACompliantYieldCurve> calibratedCurve() {
      return _calibratedCurve;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 770856249:  // curveData
          return ((IsdaYieldCurve) bean).getCurveData();
        case -1314959246:  // calibratedCurve
          return ((IsdaYieldCurve) bean).getCalibratedCurve();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code IsdaYieldCurve}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<IsdaYieldCurve> {

    private YieldCurveData _curveData;
    private ISDACompliantYieldCurve _calibratedCurve;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(IsdaYieldCurve beanToCopy) {
      this._curveData = beanToCopy.getCurveData();
      this._calibratedCurve = beanToCopy.getCalibratedCurve();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 770856249:  // curveData
          return _curveData;
        case -1314959246:  // calibratedCurve
          return _calibratedCurve;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 770856249:  // curveData
          this._curveData = (YieldCurveData) newValue;
          break;
        case -1314959246:  // calibratedCurve
          this._calibratedCurve = (ISDACompliantYieldCurve) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public IsdaYieldCurve build() {
      return new IsdaYieldCurve(
          _curveData,
          _calibratedCurve);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code curveData} property in the builder.
     * @param curveData  the new value
     * @return this, for chaining, not null
     */
    public Builder curveData(YieldCurveData curveData) {
      this._curveData = curveData;
      return this;
    }

    /**
     * Sets the {@code calibratedCurve} property in the builder.
     * @param calibratedCurve  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder calibratedCurve(ISDACompliantYieldCurve calibratedCurve) {
      JodaBeanUtils.notNull(calibratedCurve, "calibratedCurve");
      this._calibratedCurve = calibratedCurve;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("IsdaYieldCurve.Builder{");
      buf.append("curveData").append('=').append(JodaBeanUtils.toString(_curveData)).append(',').append(' ');
      buf.append("calibratedCurve").append('=').append(JodaBeanUtils.toString(_calibratedCurve));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
