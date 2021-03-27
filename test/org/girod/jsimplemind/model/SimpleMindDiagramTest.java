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
package org.girod.jsimplemind.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import org.girod.jsimplemind.reader.SimpleMindParser;
import org.xml.sax.SAXException;

/**
 *
 * @since 0.1
 */
public class SimpleMindDiagramTest {
   private static SimpleMindDiagram diagram = null;

   public SimpleMindDiagramTest() {
   }

   @BeforeClass
   public static void setUpClass() throws IOException, SAXException {
      URL url = SimpleMindDiagramTest.class.getResource("mindmap.xml");
      SimpleMindParser parser = new SimpleMindParser();
      diagram = parser.parse(url);
      assertNotNull("Diagram must not be null", diagram);
      String title = diagram.getTitle();
      assertNotNull("title must not be null", diagram);
      assertEquals("title", "The Diagram", title);
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
    * Test of getTitle method, of class SimpleMindDiagram.
    */
   @Test
   public void testDiagramContent() {
      System.out.println("SimpleMindDiagramTest : testDiagramContent");
      assertNotNull("Diagram", diagram);
      assertEquals("Title", "The Diagram", diagram.getTitle());
   }

   /**
    * Test of Topics.
    */
   @Test
   public void testDiagramTopics() {
      System.out.println("SimpleMindDiagramTest : testDiagramTopics");
      assertNotNull("Diagram", diagram);
      Map<Integer, Topic> topics = diagram.getTopics();
      assertEquals("Topics", 11, topics.size());

      Topic topic = topics.get(10);
      assertNotNull("Topic 10", topic);
      assertEquals("Topic 10", 10, topic.getID());
      assertEquals("Topic 10", "spatiotemporal region", topic.getText());
      assertEquals("Topic 10", "", topic.getNote());

      topic = topics.get(8);
      assertNotNull("Topic 8", topic);
      assertEquals("Topic 8", 8, topic.getID());
      assertEquals("Topic 8", "Occurrent", topic.getText());
      assertEquals("Topic 8", "The Occurrent", topic.getNote());
      Topic parent = topic.getParent();
      assertNotNull("Topic 8 parent", parent);
      assertEquals("Topic 8 parent", 0, parent.getID());
      assertTrue("Topic 8 parent", topic.hasParent());

      topic = topics.get(0);
      assertFalse("Topic 0 parent", topic.hasParent());
   }

   /**
    * Test of Topics.
    */
   @Test
   public void testDiagramTopicsByName() {
      System.out.println("SimpleMindDiagramTest : testDiagramTopicsByName");
      assertNotNull("Diagram", diagram);
      Map<String, Map<Integer, Topic>> topics = diagram.getTopicsByName();
      assertEquals("Topics", 11, topics.size());
   }
}
