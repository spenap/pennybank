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

