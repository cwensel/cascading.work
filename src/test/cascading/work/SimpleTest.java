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

import cascading.flow.Flow;
import junit.framework.TestCase;

/**
 *
 */
public class SimpleTest extends TestCase
  {

  public void testSchema()
    {
    PersonSchema schema = new PersonSchema();

    assertNotNull( schema.getTapFor( new ConversionResource( "foo", Protocol.HDFS, Format.TSV ) ) );
    assertNotNull( schema.getTapFor( new ConversionResource( "foo", Protocol.HTTP, Format.JSON ) ) );
    }

  public void testFactory()
    {
    CSVToTSVFactory factory = new CSVToTSVFactory( "convert", new PersonSchema() );

    factory.setSource( Protocol.HDFS, "some/path" );
    factory.setSink( Protocol.HTTP, "http://some/place" );

    Flow flow = factory.create();

    assertEquals( 1, flow.getSourcesCollection().size() );
    assertEquals( 1, flow.getSinksCollection().size() );

    assertEquals( "some/path", flow.getSources().get( "convert" ).getIdentifier() );
    assertEquals( "http://some/place", flow.getSinks().get( "convert" ).getIdentifier() );
    }


  }
