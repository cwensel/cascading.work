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

package cascading.work.tap;

import java.io.IOException;

import cascading.scheme.Scheme;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.TupleEntryCollector;
import cascading.tuple.TupleEntryIterator;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;

/** A mock Tap for testing purposes. */
public class HTTPTap extends Tap
  {
  String url;

  public HTTPTap( Scheme scheme, String url, SinkMode sinkMode )
    {
    super( scheme, sinkMode );
    this.url = url;
    }

  @Override
  public Path getPath()
    {
    return new Path( url );
    }

  @Override
  public long getPathModified( JobConf conf ) throws IOException
    {
    return 0;
    }

  @Override
  public boolean pathExists( JobConf conf ) throws IOException
    {
    return true;
    }

  @Override
  public boolean deletePath( JobConf conf ) throws IOException
    {
    return true;
    }

  @Override
  public boolean makeDirs( JobConf conf ) throws IOException
    {
    return true;
    }

  @Override
  public TupleEntryCollector openForWrite( JobConf conf ) throws IOException
    {
    return null;
    }

  @Override
  public TupleEntryIterator openForRead( JobConf conf ) throws IOException
    {
    return null;
    }
  }
