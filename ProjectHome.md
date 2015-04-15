# Summary #
Pennybank is an personal accounting application. It aims to allow to keep track of the most common financial operations: incomes, expenses and transfers, so making it easier to keep your economy controlled.

# Installation #

Currently, Pennybank is in a pre-alpha stage, not ready for real usage. However, it can be easily built using [this instructions](InstallingPennyBank.md).

The development progress can be tracked from here, but also in the [PennyBank blog](http://www.simonpena.com/blog/category/proyectos/pennybank).

# Technology #

See [Technology Used](TechnologyUsed.md)

This application is developed using [Java](http://www.sun.com/java), with [Hibernate](http://www.hibernate.org) as object-relational mapper and [Swing](http://java.sun.com/j2se/1.5.0/docs/guide/swing/index.html/) as the front-end. Unit tests are made using [JUnit](http://www.junit.org), and [Maven](http://maven.apache.org) is used as the software project management tool.

## Current Status ##

[MySQL](http://www.mysql.com) is being used as the database system.

The only non-free piece of software used is the [JTA](http://java.sun.com/javaee/technologies/jta/index.jsp) library, which does not allow its redistribution. Until a free replacement is found, this application can not be distributed in binary form. However, building from the sources is easy, as Maven manages the dependencies itself, explaining the user how to install the missing libraries

The target Operative System is currently Mac OS X, but as the application is written in Java, it should be easy to make it run well in other platforms.

## Future improvements ##

Right now steps are being made in order to switch from MySQL to [H2 Database](http://www.h2database.com/), an embedded database which should avoid the deployment of a full database system. While [SQLite](http://www.sqlite.org/) was the first option, it wasn't supported by Hibernate (although there's a [project](http://code.google.com/p/hibernate-sqlite/) with that objective.

The next thing to improve is the look & feel in other platforms, as right now it's too OSX focused.