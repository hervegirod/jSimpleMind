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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a SimpleMind diagram.
 *
 * @since 0.1
 */
public class SimpleMindDiagram {
   private String title = null;
   public Map<Integer, Topic> topics = new HashMap<>();
   public Map<String, Map<Integer, Topic>> topicsByName = new HashMap<>();

   public SimpleMindDiagram() {
   }

   /**
    * Set the diagram title.
    *
    * @param title the title
    */
   public void setTitle(String title) {
      this.title = title;
   }

   /**
    * Return the diagram title.
    *
    * @return the title
    */
   public String getTitle() {
      return title;
   }

   /**
    * Add a Topic.
    *
    * @param topic the Topic
    */
   public void addTopic(Topic topic) {
      topics.put(topic.getID(), topic);

      String text = topic.getText();
      Map<Integer, Topic> _topics;
      if (topicsByName.containsKey(text)) {
         _topics = topicsByName.get(text);
      } else {
         _topics = new HashMap<>();
         topicsByName.put(text, _topics);
      }
      _topics.put(topic.getID(), topic);
   }

   /**
    * Return true if the diagram has a topic for a specified id.
    *
    * @param id the id
    * @return true if the diagram has a topic for the specified id
    */
   public boolean hasTopic(int id) {
      return topics.containsKey(id);
   }

   /**
    * Return the topic for a specified id.
    *
    * @param id the id
    * @return the topic
    */
   public Topic getTopic(int id) {
      return topics.get(id);
   }

   /**
    * Return the map of topics.
    *
    * @return the topics
    */
   public Map<Integer, Topic> getTopics() {
      return topics;
   }

   /**
    * Return the map of topics sorted by their associated text. not that topics with no text will be set as having
    * an empty text.
    *
    * @return the topics
    */
   public Map<String, Map<Integer, Topic>> getTopicsByName() {
      return topicsByName;
   }

   /**
    * Return the map of topics for a specified text.
    *
    * @param text the text
    * @return the topics
    */
   public Map<Integer, Topic> getTopics(String text) {
      return topicsByName.get(text);
   }

   /**
    * Return true if there are topics for a specified text.
    *
    * @param text the text
    * @return true if there are topics for the specified text
    */
   public boolean hasTopics(String text) {
      return topicsByName.containsKey(text);
   }
}
