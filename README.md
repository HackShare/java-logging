java-logging
============

Custom Java Logger adapters and formatters:

* `ClassnameFilter` – log messages are filtered by the originating class name.
* `CustomFormatter` – format of printed log messages is specified at runtime, including pruning of stack traces.


Installation
============

TODO: via Maven


Activation
==========

To use these custom adapters, you must create
a `logging.properties` file and pass it to the `java` binary at startup. e.g.

```
java -Djava.util.logging.config.file=logging.properties
```

with a `logging.properties` such as:

```
handlers = java.util.logging.ConsoleHandler
java.util.logging.ConsoleHandler.level = ALL
java.util.logging.ConsoleHandler.filter = com.github.fommil.logging.ClassnameFilter
java.util.logging.ConsoleHandler.formatter = com.github.fommil.logging.CustomFormatter
com.github.fommil.logging.CustomFormatter.format = %L: %m [%c] (%n) %e %E %S
com.github.fommil.logging.CustomFormatter.stackExclude = \
  org.jetbrains. com.intellij java. sun. com.sun

.level = WARNING

# Note that ':' (colon) characters must be escaped in .level properties
#EventStream(akka\://MySystem/user/actor).level = WARNING
#com.domain.mypackage.MyClass.level = ALL
```
