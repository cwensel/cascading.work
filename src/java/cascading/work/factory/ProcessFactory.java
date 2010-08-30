/*
 * Copyright (c) 2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.cascading.org/
 *
 * This file is part of the Cascading project.
 *
 * Cascading is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cascading is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cascading.  If not, see <http://www.gnu.org/licenses/>.
 */

package cascading.work.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cascading.work.Resource;
import cascading.work.Schema;

/**
 * Class ProcessFactory is an abstract base class for creating process based factories. Where a 'process'
 * has source and sink resources as defined by {@link Schema} instances.
 *
 * @param <P> a 'process' type
 * @param <R> a resource type sub-classing {@link Resource}
 * @see FlowFactory
 */
public abstract class ProcessFactory<P, R extends Resource>
  {
  protected Properties properties;
  Map<String, Schema> sourceSchemas = new HashMap<String, Schema>();
  Map<String, Schema> sinkSchemas = new HashMap<String, Schema>();
  Map<String, List<R>> sourceResources = new HashMap<String, List<R>>();
  Map<String, List<R>> sinkResources = new HashMap<String, List<R>>();

  protected ProcessFactory()
    {
    }

  protected ProcessFactory( Properties properties )
    {
    this.properties = properties;
    }

  public void setProperties( Properties properties )
    {
    this.properties = properties;
    }

  public Properties getProperties()
    {
    return properties;
    }

  /**
   * Method addSourceSchema binds a given {@link Schema} instance to the given 'name'.
   * <p/>
   * Only one Schema may be bound to a source name.
   *
   * @param sourceName
   * @param schema
   */
  protected void setSourceSchema( String sourceName, Schema schema )
    {
    sourceSchemas.put( sourceName, schema );
    }

  protected Schema getSourceSchema( String sourceName )
    {
    return sourceSchemas.get( sourceName );
    }

  /**
   * Method addSinkSchema binds a given {@link Schema} instance to the given 'name'.
   * <p/>
   * Only one Schema may be bound to a sink name.
   *
   * @param sinkName
   * @param schema
   */
  protected void setSinkSchema( String sinkName, Schema schema )
    {
    sinkSchemas.put( sinkName, schema );
    }

  protected Schema getSinkSchema( String sinkName )
    {
    return sinkSchemas.get( sinkName );
    }

  /**
   * Method addSourceResource binds a name to the given resources.
   * <p/>
   * This method may be called repeatedly with the same sourceName, all given
   * resources will be added to the binding.
   * <p/>
   * Any null resource values will be removed.
   *
   * @param sourceName
   * @param resources
   */
  public void addSourceResource( String sourceName, R... resources )
    {
    if( resources == null || resources.length == 0 )
      return;

    List<R> resourceList = getSourceResources( sourceName );

    Collections.addAll( resourceList, resources );

    while( resourceList.contains( null ) )
      resourceList.remove( null );
    }

  /**
   * Method getSourceResources returns a List of resources associated with the given name.
   *
   * @param sourceName
   * @return
   */
  protected List<R> getSourceResources( String sourceName )
    {
    List<R> resourceList = sourceResources.get( sourceName );

    if( resourceList == null )
      {
      resourceList = new ArrayList<R>();
      sourceResources.put( sourceName, resourceList );
      }

    return resourceList;
    }

  /**
   * Method getAllSourceResources returns a Collection of all Resources instances add via
   * {@link #addSourceResource(String, cascading.work.Resource[])}.
   *
   * @return Collection of Resource instances
   */
  public Collection<R> getAllSourceResources()
    {
    Set<R> set = new HashSet<R>();

    for( List<R> resources : sourceResources.values() )
      set.addAll( resources );

    return set;
    }

  /** Method clearSourceResources removes all bindings for all names. */
  protected void clearSourceResources()
    {
    sourceResources.clear();
    }

  /**
   * Method addSinkResource binds a name to the given resources.
   * <p/>
   * This method may be called repeatedly with the same sinkName, all given
   * resources will be added to the binding.
   * <p/>
   * Any null resource values will be removed.
   *
   * @param sinkName
   * @param resources
   */
  public void addSinkResource( String sinkName, R... resources )
    {
    if( resources == null || resources.length == 0 )
      return;

    List<R> resourceList = getSinkResources( sinkName );

    Collections.addAll( resourceList, resources );

    while( resourceList.contains( null ) )
      resourceList.remove( null );
    }

  /**
   * Method getSinkResources returns a List of resources associated with the given name.
   *
   * @param sinkName
   * @return
   */
  protected List<R> getSinkResources( String sinkName )
    {
    List<R> resourceList = sinkResources.get( sinkName );

    if( resourceList == null )
      {
      resourceList = new ArrayList<R>();
      sinkResources.put( sinkName, resourceList );
      }

    return resourceList;
    }

  /**
   * Method getAllSinkResources returns a Collection of all Resources instances add via
   * {@link #addSinkResource(String, cascading.work.Resource[])}.
   *
   * @return Collection of Resource instances
   */
  public Collection<R> getAllSinkResources()
    {
    Set<R> set = new HashSet<R>();

    for( List<R> resources : sinkResources.values() )
      set.addAll( resources );

    return set;
    }

  /** Method clearSinkResources removes all bindings for all names. */
  protected void clearSinkResources()
    {
    sinkResources.clear();
    }

  protected Collection<String> getSourceNames()
    {
    return sourceResources.keySet();
    }

  protected Collection<String> getSinkNames()
    {
    return sinkResources.keySet();
    }

  /**
   * Method create returns a new instance of the type this factory creates.
   *
   * @return
   */
  public abstract P create();
  }
