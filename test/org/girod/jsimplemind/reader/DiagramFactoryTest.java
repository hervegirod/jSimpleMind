/*
Copyright (C) 2021 Herve Girod

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies,
either expressed or implied, of the FreeBSD Project.

Alternatively if you have any questions about this project, you can visit
the project website at the project page on https://github.com/hervegirod/jSimpleMind
 */
package org.girod.jsimplemind.reader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.net.URL;
import org.girod.jsimplemind.model.SimpleMindDiagram;
import org.girod.jsimplemind.model.SimpleMindDiagramTest;
import org.xml.sax.SAXException;

/**
 *
 * @since 0.2
 */
public class DiagramFactoryTest {

   public DiagramFactoryTest() {
   }

   @BeforeClass
   public static void setUpClass() {
   }

   @AfterClass
   public static void tearDownClass() {
   }

   @Before
   public void setUp() {
   }

   @After
   public void tearDown() {
   }
   /**
    * Test of createDiagram method, of class DiagramFactory.
    */
   @Test
   public void testCreateDiagram() throws IOException, SAXException {
      System.out.println("DiagramFactoryTest : testCreateDiagram");
      URL url = SimpleMindDiagramTest.class.getResource("mindmap.xml");
      SimpleMindParser parser = new SimpleMindParser();
      parser.setDiagramFactory(new MyDiagramFactory());
      SimpleMindDiagram diagram = parser.parse(url);
      assertNotNull("Diagram must not be null", diagram);
      String title = diagram.getTitle();
      assertNotNull("title must not be null", diagram);
      assertEquals("title", "The Diagram", title);

      assertTrue("Diagram must be a MyDiagram", diagram instanceof MyDiagram);
   }

   public class MyDiagramFactory implements DiagramFactory {
      @Override
      public SimpleMindDiagram createDiagram() {
         return new MyDiagram();
      }
   }

   public class MyDiagram extends SimpleMindDiagram {
      public MyDiagram() {
         super();
      }
   }
}
