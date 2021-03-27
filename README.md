# jSimpleMind
jSimpleMind is a small Java library which is able to parse SimpleMind MindMaps (see https://simplemind.eu/) 

# Where is it?
The home page for the jSimpleMind project can be found in the github project web site (https://github.com/hervegirod/jSimpleMind/).
There you also find information on how to download the latest release as well as all the other information you might need regarding
this project.

# Requirements
A Java 1.8 or later compatible virtual machine for your operating system.

# Licence
The ChangeLicenceTag Library uses a BSD license for the source code.

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

## Getting the Topics:
```java
  SimpleMindDiagram diagram = parser.parse(file);

  Map<Integer, Topic> topics = diagram.getTopics();
```  

