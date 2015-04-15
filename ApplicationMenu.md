# **Atención: aplicación migrada a Swing. Página mantenida como referencia** #

La aplicación se realizará en Swing, debido a su mayor facilidad de uso en la plataforma Netbeans, y a la posibilidad de realizar programación visual.

# Introducción #

Para integrar una aplicación SWT con el entorno en Mac OS X, es necesario hacer que los menús se comporten como el resto de las aplicaciones nativas.

Existe bastante información al respecto en Internet, siendo el proceso conocido como _macificar_ ( _macifying_) una aplicación.

Una parte importante del proceso es el menú de aplicación. En OS X, las aplicaciones colocan su menú en la parte superior de la pantalla. El primer item del menú siempre contendrá el nombre del programa, una acción que permitirá consultar _acerca del_ mismo, y modificar las preferencias.

# Detalles #

Mientras que SWT ya posiciona correctamente el menú por sí mismo, colocándolo en la parte superior como hacen las demás aplicaciones nativas, no gestiona de manera igual de transparente ese primer item principal. Para poder solucionar eso hay que hacer uso de la implementación concreta de SWT para OS X, Carbon.

Los siguientes enlaces dan información sobre como solucionar este problema.

### Enlaces ###

http://www.informit.com/articles/article.aspx?p=132546&seqNum=6

http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.swt.snippets/src/org/eclipse/swt/snippets/Snippet178.java?view=markup

http://www.cafeaulait.org/slides/eclipseworld2005/macifying/Macifying_SWT.html

http://www.simidude.com/blog/2008/macify-a-swt-application-in-a-cross-platform-way/

http://www.azureuswiki.com/index.php/PreferencesAndAbout