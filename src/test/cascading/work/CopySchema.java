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

import cascading.scheme.TextLine;
import cascading.tuple.Fields;

/**
 * A mock Schema that represents a 'person' type.
 * <p/>
 * Note the constant fields which can be used inside a Cascading application.
 */
public class CopySchema extends Schema<Protocol, Format>
  {
  protected CopySchema()
    {
    super( Protocol.HDFS );

    addSchemeFor( Format.TSV, new TextLine( new Fields( "line" ), new Fields( "line" ) ) );
    addSchemeFor( Format.CSV, new TextLine( new Fields( "line" ), new Fields( "line" ) ) );
    addSchemeFor( Format.JSON, new TextLine( new Fields( "line" ), new Fields( "line" ) ) );
    }
  }
