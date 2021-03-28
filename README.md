# jSimpleMind
jSimpleMind is a small Java library which is able to parse SimpleMind MindMaps (see https://simplemind.eu/) 

# Where is it?
The home page for the jSimpleMind project can be found in the github project web site (https://github.com/hervegirod/jSimpleMind/).
There you also find information on how to download the latest release as well as all the other information you might need regarding
this project.

# Requirements
A Java 1.8 or later compatible virtual machine for your operating system.

# Licence
The jSimpleMind Library uses a BSD license for the source code.

# History
## Version 0.1
 - Creation of the library

## Version 0.2
 - Handle the Topics text and stroke color
 - Add the capability to create diagrams with special properties
 - Add the capability to create custom Topics

# Usage
## Parsing a SimpleMind file
To parse a SimpleMind xml file:
```java
  SimpleMindParser parser = new SimpleMindParser();
  SimpleMindDiagram diagram = parser.parse(file);
```

To parse a SimpleMind smmx file (this is a zip file containing the xml file):
```java
  InputStream stream = ReaderUtilities.getSimpleMindStream(file);
  SimpleMindParser parser = new SimpleMindParser();
  SimpleMindDiagram diagram = parser.parse(stream);
```

## Getting the Topics
```java
  SimpleMindDiagram diagram = parser.parse(file);

  Map<Integer, Topic> topics = diagram.getTopics();
```  

## Handling special properties on Topics
The `NoteParser` class allow to parse the note content of a Topic and eventually to add special properties 
on the Topic. 

To install a `NoteParser` on the parser, just do:
```java
  SimpleMindParser parser = new SimpleMindParser();
  parser.setNoteParser(<my NoteParser>);
```

The Topic class support the concept of properties which allows the `NoteParser` to add specific properties depending on 
the parsing of the note content.

## Creating custom diagrams
The `DiagramFactory` class allows to create custom diagrams or Topics. 

To install a `DiagramFactory` on the parser, just do:
```java
  SimpleMindParser parser = new SimpleMindParser();
  parser.setDiagramFactory(<my DiagramFactory>);
```

It is possible to combine the `DiagramFactory` with the `NoteParser` to create special Topics with additional methods rather
than use the default Topic class.
