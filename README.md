TextRazor Official Java SDK
===========================

This is the official Java SDK for the TextRazor Text Analytics API.

TextRazor offers state-of-the-art natural language processing tools through a simple API, allowing you to build semantic technology into your applications in minutes.

Hundreds of applications across a range of verticals rely on TextRazor to extract meaning from unstructured text, with use cases including social media monitoring, enterprise search, recommendation systems and ad targetting.
TextRazor offers automatic Named Entity Recognition, Disambiguation and Linking, Topic and Theme Extraction, Relation Extraction and much more.

Visit https://www.textrazor.com to find out more.

Getting Started
===============

- Download the latest [TextRazor JAR](https://github.com/TextRazor/textrazor-java/blob/master/bin/textrazor-1.0.12.jar).
  
  TextRazor for Java depends on the Jackson JSON library, specifically jackson-core-2.9.8.jar, jackson-annotations-2.9.8.jar, jackson-databind-2.12.7.1.jar.  Ensure that these are on your classpath when running your project.

- Alternatively, if you use Maven you can add a dependency to TextRazor in your pom.xml:

```
<dependency>
  <groupId>com.textrazor</groupId>
  <artifactId>textrazor</artifactId>
  <version>1.0.13</version>
</dependency> 
```

Usage
=====

You'll need a [free API key](https://www.textrazor.com) to add to your requests, see [an example](https://github.com/crayston/textrazor-java/blob/master/test/com/textrazor/TestTextRazor.java) to get going.

Full JavaDoc can be found as part of the package, or you can browse more comprehensive documentation [at the TextRazor site](https://www.textrazor.com/docs/java).

If you have any questions please get in touch at support@textrazor.com
