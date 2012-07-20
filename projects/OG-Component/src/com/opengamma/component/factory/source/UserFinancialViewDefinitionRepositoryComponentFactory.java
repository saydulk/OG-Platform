/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.component.factory.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.component.ComponentInfo;
import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import com.opengamma.component.factory.ComponentInfoAttributes;
import com.opengamma.engine.view.AggregatingViewDefinitionRepository;
import com.opengamma.engine.view.ViewDefinitionRepository;
import com.opengamma.financial.view.ConfigDbViewDefinitionRepository;
import com.opengamma.financial.view.ManageableViewDefinitionRepository;
import com.opengamma.financial.view.memory.InMemoryViewDefinitionRepository;
import com.opengamma.financial.view.rest.RemoteViewDefinitionRepository;
import com.opengamma.master.config.ConfigMaster;

/**
 * Component factory providing the {@code ViewDefinitionRepository}.
 */
@BeanDefinition
public class UserFinancialViewDefinitionRepositoryComponentFactory extends AbstractComponentFactory {

  /**
   * The classifier that the factory should publish under.
   */
  @PropertyDefinition(validate = "notNull")
  private String _classifier;
  /**
   * The classifier that the factory should publish under (underlying master).
   */
  @PropertyDefinition
  private String _underlyingClassifier;
  /**
   * The config master (underlying master).
   */
  @PropertyDefinition(validate = "notNull")
  private ConfigMaster _underlyingConfigMaster;
  /**
   * The classifier that the factory should publish under (user master).
   */
  @PropertyDefinition
  private String _userClassifier;

  //-------------------------------------------------------------------------
  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) {
    ViewDefinitionRepository source = initUnderlying(repo, configuration);
    
    // add user level if requested
    ViewDefinitionRepository userSource = initUser(repo, configuration);
    if (userSource != null) {
      Collection<ViewDefinitionRepository> coll = new ArrayList<ViewDefinitionRepository>();
      coll.add(source);
      coll.add(userSource);
      source = new AggregatingViewDefinitionRepository(coll);
    }
    
    // register
    ComponentInfo info = new ComponentInfo(ViewDefinitionRepository.class, getClassifier());
    info.addAttribute(ComponentInfoAttributes.LEVEL, 2);
    info.addAttribute(ComponentInfoAttributes.REMOTE_CLIENT_JAVA, RemoteViewDefinitionRepository.class);
    repo.registerComponent(info, source);
  }

  protected ViewDefinitionRepository initUnderlying(ComponentRepository repo, LinkedHashMap<String, String> configuration) {
    ViewDefinitionRepository source = new ConfigDbViewDefinitionRepository(getUnderlyingConfigMaster());
    if (getUnderlyingClassifier() != null) {
      ComponentInfo info = new ComponentInfo(ViewDefinitionRepository.class, getUnderlyingClassifier());
      info.addAttribute(ComponentInfoAttributes.LEVEL, 1);
      info.addAttribute(ComponentInfoAttributes.REMOTE_CLIENT_JAVA, RemoteViewDefinitionRepository.class);
      repo.registerComponent(info, source);
    }
    return source;
  }

  protected ViewDefinitionRepository initUser(ComponentRepository repo, LinkedHashMap<String, String> configuration) {
    if (getUserClassifier() == null) {
      return null;
    }
    ManageableViewDefinitionRepository source = new InMemoryViewDefinitionRepository();
    ComponentInfo info = new ComponentInfo(ManageableViewDefinitionRepository.class, getUserClassifier());
    info.addAttribute(ComponentInfoAttributes.LEVEL, 1);
    info.addAttribute(ComponentInfoAttributes.REMOTE_CLIENT_JAVA, RemoteViewDefinitionRepository.class);
    repo.registerComponent(info, source);
    return source;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code UserFinancialViewDefinitionRepositoryComponentFactory}.
   * @return the meta-bean, not null
   */
  public static UserFinancialViewDefinitionRepositoryComponentFactory.Meta meta() {
    return UserFinancialViewDefinitionRepositoryComponentFactory.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(UserFinancialViewDefinitionRepositoryComponentFactory.Meta.INSTANCE);
  }

  @Override
  public UserFinancialViewDefinitionRepositoryComponentFactory.Meta metaBean() {
    return UserFinancialViewDefinitionRepositoryComponentFactory.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -281470431:  // classifier
        return getClassifier();
      case 1705602398:  // underlyingClassifier
        return getUnderlyingClassifier();
      case -1673062335:  // underlyingConfigMaster
        return getUnderlyingConfigMaster();
      case 473030732:  // userClassifier
        return getUserClassifier();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -281470431:  // classifier
        setClassifier((String) newValue);
        return;
      case 1705602398:  // underlyingClassifier
        setUnderlyingClassifier((String) newValue);
        return;
      case -1673062335:  // underlyingConfigMaster
        setUnderlyingConfigMaster((ConfigMaster) newValue);
        return;
      case 473030732:  // userClassifier
        setUserClassifier((String) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_classifier, "classifier");
    JodaBeanUtils.notNull(_underlyingConfigMaster, "underlyingConfigMaster");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      UserFinancialViewDefinitionRepositoryComponentFactory other = (UserFinancialViewDefinitionRepositoryComponentFactory) obj;
      return JodaBeanUtils.equal(getClassifier(), other.getClassifier()) &&
          JodaBeanUtils.equal(getUnderlyingClassifier(), other.getUnderlyingClassifier()) &&
          JodaBeanUtils.equal(getUnderlyingConfigMaster(), other.getUnderlyingConfigMaster()) &&
          JodaBeanUtils.equal(getUserClassifier(), other.getUserClassifier()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getClassifier());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUnderlyingClassifier());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUnderlyingConfigMaster());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUserClassifier());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the classifier that the factory should publish under.
   * @return the value of the property, not null
   */
  public String getClassifier() {
    return _classifier;
  }

  /**
   * Sets the classifier that the factory should publish under.
   * @param classifier  the new value of the property, not null
   */
  public void setClassifier(String classifier) {
    JodaBeanUtils.notNull(classifier, "classifier");
    this._classifier = classifier;
  }

  /**
   * Gets the the {@code classifier} property.
   * @return the property, not null
   */
  public final Property<String> classifier() {
    return metaBean().classifier().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the classifier that the factory should publish under (underlying master).
   * @return the value of the property
   */
  public String getUnderlyingClassifier() {
    return _underlyingClassifier;
  }

  /**
   * Sets the classifier that the factory should publish under (underlying master).
   * @param underlyingClassifier  the new value of the property
   */
  public void setUnderlyingClassifier(String underlyingClassifier) {
    this._underlyingClassifier = underlyingClassifier;
  }

  /**
   * Gets the the {@code underlyingClassifier} property.
   * @return the property, not null
   */
  public final Property<String> underlyingClassifier() {
    return metaBean().underlyingClassifier().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the config master (underlying master).
   * @return the value of the property, not null
   */
  public ConfigMaster getUnderlyingConfigMaster() {
    return _underlyingConfigMaster;
  }

  /**
   * Sets the config master (underlying master).
   * @param underlyingConfigMaster  the new value of the property, not null
   */
  public void setUnderlyingConfigMaster(ConfigMaster underlyingConfigMaster) {
    JodaBeanUtils.notNull(underlyingConfigMaster, "underlyingConfigMaster");
    this._underlyingConfigMaster = underlyingConfigMaster;
  }

  /**
   * Gets the the {@code underlyingConfigMaster} property.
   * @return the property, not null
   */
  public final Property<ConfigMaster> underlyingConfigMaster() {
    return metaBean().underlyingConfigMaster().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the classifier that the factory should publish under (user master).
   * @return the value of the property
   */
  public String getUserClassifier() {
    return _userClassifier;
  }

  /**
   * Sets the classifier that the factory should publish under (user master).
   * @param userClassifier  the new value of the property
   */
  public void setUserClassifier(String userClassifier) {
    this._userClassifier = userClassifier;
  }

  /**
   * Gets the the {@code userClassifier} property.
   * @return the property, not null
   */
  public final Property<String> userClassifier() {
    return metaBean().userClassifier().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code UserFinancialViewDefinitionRepositoryComponentFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code classifier} property.
     */
    private final MetaProperty<String> _classifier = DirectMetaProperty.ofReadWrite(
        this, "classifier", UserFinancialViewDefinitionRepositoryComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code underlyingClassifier} property.
     */
    private final MetaProperty<String> _underlyingClassifier = DirectMetaProperty.ofReadWrite(
        this, "underlyingClassifier", UserFinancialViewDefinitionRepositoryComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code underlyingConfigMaster} property.
     */
    private final MetaProperty<ConfigMaster> _underlyingConfigMaster = DirectMetaProperty.ofReadWrite(
        this, "underlyingConfigMaster", UserFinancialViewDefinitionRepositoryComponentFactory.class, ConfigMaster.class);
    /**
     * The meta-property for the {@code userClassifier} property.
     */
    private final MetaProperty<String> _userClassifier = DirectMetaProperty.ofReadWrite(
        this, "userClassifier", UserFinancialViewDefinitionRepositoryComponentFactory.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "classifier",
        "underlyingClassifier",
        "underlyingConfigMaster",
        "userClassifier");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return _classifier;
        case 1705602398:  // underlyingClassifier
          return _underlyingClassifier;
        case -1673062335:  // underlyingConfigMaster
          return _underlyingConfigMaster;
        case 473030732:  // userClassifier
          return _userClassifier;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends UserFinancialViewDefinitionRepositoryComponentFactory> builder() {
      return new DirectBeanBuilder<UserFinancialViewDefinitionRepositoryComponentFactory>(new UserFinancialViewDefinitionRepositoryComponentFactory());
    }

    @Override
    public Class<? extends UserFinancialViewDefinitionRepositoryComponentFactory> beanType() {
      return UserFinancialViewDefinitionRepositoryComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code classifier} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> classifier() {
      return _classifier;
    }

    /**
     * The meta-property for the {@code underlyingClassifier} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> underlyingClassifier() {
      return _underlyingClassifier;
    }

    /**
     * The meta-property for the {@code underlyingConfigMaster} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ConfigMaster> underlyingConfigMaster() {
      return _underlyingConfigMaster;
    }

    /**
     * The meta-property for the {@code userClassifier} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> userClassifier() {
      return _userClassifier;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
