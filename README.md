TextRazor Official Java SDK
===========================

This is the official Java SDK for the TextRazor Text Analytics API.

TextRazor offers state-of-the-art natural language processing tools through a simple API, allowing you to build semantic technology into your applications in minutes.

Hundreds of applications across a range of verticals rely on TextRazor to extract meaning from unstructured text, with use cases including social media monitoring, enterprise search, recommendation systems and ad targetting.
TextRazor offers automatic Named Entity Recognition, Disambiguation and Linking, Topic and Theme Extraction, Relation Extraction and much more.

Visit https://www.textrazor.com to find out more.

Getting Started
===============

Requires Java 8 or later. Add the dependency to your Maven pom.xml:

```xml
<dependency>
  <groupId>com.textrazor</groupId>
  <artifactId>textrazor</artifactId>
  <version>1.0.15</version>
</dependency> 
```

Maven includes the required Jackson dependencies automatically. Version 1.0.15 uses the Jackson 2.21.7 BOM, which selects jackson-core 2.21.7, jackson-annotations 2.21, and jackson-databind 2.21.7.

For manual installation, download the JAR from [Maven Central](https://central.sonatype.com/artifact/com.textrazor/textrazor/1.0.15) and include those Jackson JARs on your classpath.

Usage
=====

You'll need a [free API key](https://www.textrazor.com). Set it in the `TEXTRAZOR_API_KEY` environment variable, then analyze text:

```java
import com.textrazor.TextRazor;
import com.textrazor.annotations.AnalyzedText;
import com.textrazor.annotations.Entity;

TextRazor client = new TextRazor(System.getenv("TEXTRAZOR_API_KEY"));
client.addExtractor("entities");

AnalyzedText result = client.analyze("Apple is based in Cupertino, California.");
for (Entity entity : result.getResponse().getEntities()) {
    System.out.println(entity.getEntityId());
}
```

Call this code from a method that handles or declares `NetworkException` and `AnalysisException`.

Full JavaDoc can be found as part of the package, or you can browse more comprehensive documentation [at the TextRazor site](https://www.textrazor.com/docs/java).

If you have any questions please get in touch at support@textrazor.com
