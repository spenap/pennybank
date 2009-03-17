ICON
------
Credit for the Piggybank picture and icon derived should be given to http://www.flickr.com/photos/annia316/

http://www.flickr.com/photos/annia316/201999076/


SWT ( NOT NEEDED )
------------------
Bug report asking for SWT support for maven, and a good explanation to get it working 

https://bugs.eclipse.org/bugs/show_bug.cgi?id=199302

JTA
----

mvn install:install-file -Dfile=PATH_TO_JTA/jta.jar \
-DgroupId=javax.transaction -DartifactId=jta -Dversion=1.0.1B -Dpackaging=jar 

Mac WIDGETS
------

URL: http://code.google.com/p/macwidgets/

mvn install:install-file -Dfile=PATH_TO_MACWIDGETS/mac_widgets-0.9.4/forms-1.2.1.jar \
-DgroupId=com.jgoodies -DartifactId=forms -Dversion=1.2.1 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=PATH_TO_MACWIDGETS/mac_widgets-0.9.4/mac_widgets.jar \
-DgroupId=com.jgoodies -DartifactId=mac_widgets -Dversion=0.9.4 -Dpackaging=jar -DgeneratePom=true

MySQL
------
       
   - Create a directory where MySQL will store its databases. For example:
     /home/user/.MySQLData.
   - Create $HOME/.my.cnf file with a similar content to the following:
   
     [mysqld]
     datadir=/home/user/.MySQLData
    
     Change the value of "datadir" to the directory created previously.
   
   - cd /usr/local/mysql 
   - scripts/mysql_install_db
   
     This will create "mysql" and "test" databases in the directory specified
     by the previous "datadir" option.
         
Create a database
-----------------                       

- Start the MySQL server.

- Create a database with name "pennybank" and other with name "pennybank_test"

  mysqladmin -u root create pennybank

  mysqladmin -u root create pennybank_test

- Create a user with name "pennybank" and password "pennybank", and allow her/him to
  access from local host.

  mysql -u root

  GRANT ALL PRIVILEGES ON pennybank.* to pennybank@localhost IDENTIFIED BY 'pennybank';
  GRANT ALL PRIVILEGES ON pennybank_test.* to pennybank@localhost IDENTIFIED BY 'pennybank';
  
- Try to access to "pennybank" database as "pennybank" user with password "pennybank".
   
  mysql -u pennybank --password=pennybank pennybank
   
- Shutdown the MySQL server.

  mysqladmin -u root shutdown