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

package cascading.work;

import java.util.Properties;

import cascading.flow.Flow;
import cascading.pipe.Pipe;
import cascading.work.factory.FlowFactory;

/**
 * A mock FlowFactory that creates a working Flow for conversion of data between
 * two formats and across multiple protocols.
 */
public class CopyFactory extends FlowFactory
  {
  private String name;

  public CopyFactory( String name )
    {
    this( null, name );
    }

  public CopyFactory( Properties properties, String name )
    {
    super( properties );
    this.name = name;

    setSourceSchema( name, new CopySchema() );
    setSinkSchema( name, new CopySchema() );
    }

  public void addSourceResource( TapResource resource )
    {
    addSourceResource( name, resource );
    }

  public void addSinkResource( TapResource resource )
    {
    addSinkResource( name, resource );
    }

  @Override
  public Flow create()
    {
    Pipe pipe = new Pipe( name ); // this forces pipe-lining between the source and sink

    return createFlowFrom( name, pipe );
    }
  }
