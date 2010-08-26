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
public class CSVToTSVFactory extends FlowFactory
  {
  private String name;

  public CSVToTSVFactory( String name, Schema schema )
    {
    this( null, name, schema );
    }

  public CSVToTSVFactory( Properties properties, String name, Schema schema )
    {
    super( properties );
    this.name = name;

    setSourceSchema( name, schema );
    setSinkSchema( name, schema );
    }

  public void setSource( String path )
    {
    setSource( (Protocol) getSourceSchema( name ).getDefaultProtocol(), path );
    }

  public void setSource( Protocol protocol, String path )
    {
    addSourceResource( name, new ConversionResource( path, protocol, Format.CSV ) );
    }

  public void setSink( String path )
    {
    setSource( (Protocol) getSinkSchema( name ).getDefaultProtocol(), path );
    }

  public void setSink( Protocol protocol, String path )
    {
    addSinkResource( name, new ConversionResource( path, protocol, Format.TSV ) );
    }

  @Override
  public Flow create()
    {
    Pipe pipe = new Pipe( name ); // this forces pipe-lining between the source and sink

    return createFlowFrom( name, pipe );
    }
  }
