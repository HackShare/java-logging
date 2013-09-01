java-logging
============

Suite of Java Logger plugins:

* `ClassnameFilter` – log messages are filtered by the originating class name.
* `CustomFormatter` – format of printed log messages is specified at runtime, including pruning of stack traces.


Installation
============

It is sufficient to add this library to the `runtime` classpath.

Releases are in Maven Central:

```xml
<dependency>
  <groupId>com.github.fommil</groupId>
  <artifactId>java-logging</artifactId>
  <version>1.1</version>
  <scope>runtime</scope>
</dependency>
```

Snapshots are distributed on Sonatype's Snapshot Repository:

```xml
<dependency>
  <groupId>com.github.fommil</groupId>
  <artifactId>java-logging</artifactId>
  <version>1.1-SNAPSHOT</version>
  <scope>runtime</scope>
</dependency>
```

If the above fails, ensure you have the following in your `pom.xml`:

```xml
    <repositories>
        <repository>
            <id>sonatype-snapshots</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
```


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

# this is the default, not .level (because JUL uses it magically elsewhere)
com.github.fommil.logging.ClassnameFilter.level = WARNING

# Note that ':' (colon) characters must be escaped in .level properties
#EventStream(akka\://MySystem/user/actor).level = WARNING
#com.domain.mypackage.MyClass.level = ALL
```

Donations
=========

Please consider supporting the maintenance of this open source project with a donation:

[![Donate via Paypal](https://www.paypal.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=B2HW5ATB8C3QW&lc=GB&item_name=java-logging&currency_code=GBP&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted)

Licence
=======

Copyright (C) 2012 Samuel Halliday

This library is free software; you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published
by the Free Software Foundation; either version 3 of the License, or
(at your option) any later version.

This library is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this library; if not, see http://www.gnu.org/licenses/

Contributing
============

Contributors are encouraged to fork this repository and issue pull
requests. Contributors implicitly agree to assign an unrestricted licence
to Sam Halliday, but retain the copyright of their code (this means
we both have the freedom to update the licence for those contributions).

