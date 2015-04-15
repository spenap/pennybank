# Introduction #

To install Pennybank it is necessary to build it up from the sources. However, the installation is almost straight-forward, having to install [MySQL](http://www.mysql.com/), [Maven](http://maven.apache.org/), [macwidgets](http://code.google.com/p/macwidgets/) and [jta](http://java.sun.com/products/jta) and call the database init scripts

# Details #

The installation is reduced to 5 steps
  * Installing and configuring MySQL
  * Installing Maven
  * Installing Maven manual dependencies
    * JTA: This library has a non-free (as in Freedom) licence, which doesn't allow its redistribution. It does, however, allow is personal use.
    * Macwidgets: This libraries are in a very active state, so it is possible to find them added to the Maven repositories.
  * Automatic database initialization, using one Maven task
  * Compiling and building the application