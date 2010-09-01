/*
 * Copyright (c) 2007-2009 Concurrent, Inc. All Rights Reserved.
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
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import cascading.cascade.Cascade;
import cascading.cascade.CascadeConnector;
import cascading.flow.Flow;
import cascading.work.TapResource;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class CascadeFactory<R extends TapResource> extends Factory<Cascade>
  {
  private static final Logger LOG = LoggerFactory.getLogger( CascadeFactory.class );

  private final String name;

  private SimpleDirectedGraph<R, ProcessFactoryHolder> resourceGraph = null;
  private List<ProcessFactory> processFactories = new ArrayList<ProcessFactory>();

  public static class ProcessFactoryHolder
    {
    ProcessFactory processFactory;

    public ProcessFactoryHolder()
      {
      }

    private ProcessFactoryHolder( ProcessFactory processFactory )
      {
      this.processFactory = processFactory;
      }
    }

  public CascadeFactory( Properties properties, String name )
    {
    super( properties );
    this.name = name;
    }

  public String getName()
    {
    return name;
    }

  public void addAllProcessFactories( Collection<ProcessFactory<?, R>> processFactories )
    {
    for( ProcessFactory<?, R> processFactory : processFactories )
      addProcessFactory( processFactory );
    }

  public void addProcessFactory( ProcessFactory<?, R> processFactory )
    {
    if( processFactories.contains( processFactory ) )
      throw new IllegalStateException( "may not add identical process factories, received: " + processFactory );

    processFactories.add( processFactory );
    }

  protected Collection<R> getResourcesWith( String identifier )
    {
    initResourceGraph();

    Set<R> resources = new HashSet<R>();

    for( R resource : resourceGraph.vertexSet() )
      {
      if( resource.getIdentifier().equals( identifier ) )
        resources.add( resource );
      }

    return resources;
    }

  protected Collection<ProcessFactory> getSourceDependenciesOn( R sourceResource )
    {
    initResourceGraph();

    Set<ProcessFactory> factories = new HashSet<ProcessFactory>();
    Set<ProcessFactoryHolder> outgoing = resourceGraph.outgoingEdgesOf( sourceResource );

    for( ProcessFactoryHolder processFactoryHolder : outgoing )
      factories.add( processFactoryHolder.processFactory );

    return factories;
    }

  protected void initResourceGraph()
    {
    if( resourceGraph == null )
      rebuildResourceGraph();
    }

  protected void rebuildResourceGraph()
    {
    resourceGraph = new SimpleDirectedGraph<R, ProcessFactoryHolder>( ProcessFactoryHolder.class );

    for( ProcessFactory processFactory : processFactories )
      insertProcessFactory( processFactory );
    }

  private void insertProcessFactory( ProcessFactory<?, R> processFactory )
    {
    for( R resource : processFactory.getAllSourceResources() )
      resourceGraph.addVertex( resource );

    for( R resource : processFactory.getAllSinkResources() )
      resourceGraph.addVertex( resource );

    for( R incoming : processFactory.getAllSourceResources() )
      {
      for( R outgoing : processFactory.getAllSinkResources() )
        resourceGraph.addEdge( incoming, outgoing, new ProcessFactoryHolder( processFactory ) );
      }
    }

  protected CascadeConnector getCascadeConnector()
    {
    return new CascadeConnector( getProperties() );
    }

  @Override
  public Cascade create()
    {
    List<Flow> flows = new ArrayList<Flow>();

    for( ProcessFactory processFactory : processFactories )
      {
      Object o = processFactory.create();

      if( o == null )
        new IllegalStateException( "factory returned null: " + processFactory );

      if( o instanceof Flow )
        flows.add( (Flow) o );
      else if( o instanceof Cascade )
        flows.addAll( ( (Cascade) o ).getFlows() );
      else
        throw new IllegalStateException( "process type not supported: " + o.getClass().getName() + ", returned by: " + processFactory );
      }

    if( flows.isEmpty() )
      throw new IllegalStateException( "now flows were created from the given process factories" );

    return getCascadeConnector().connect( name, flows );
    }

  }
