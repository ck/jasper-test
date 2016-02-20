# Jasper Test #

This is stripped down version of Immutant's feature-demo to test
JasperReports inside Wildfly.

## In WildFly

[WildFly](http://wildfly.org) is installed by downloading and
unpacking an archive. For our purposes, we'll drop it in the project
directory. For a list of available versions, see
<http://wildfly.org/downloads/>

    VERSION=10.0.0.Final

    # Install WildFly
    wget http://download.jboss.org/wildfly/$VERSION/wildfly-$VERSION.zip
    unzip wildfly-$VERSION.zip

    # Create the war file and deploy it to WildFly
    lein immutant war -o wildfly-$VERSION

    # Fire up WildFly
    wildfly-$VERSION/bin/standalone.sh -c standalone-full.xml

Note the web examples will be deployed with a context path of `/demo`
on WildFly so go to <http://localhost:8080/demo/> to see the web
examples. Alternatively, to mount the app at the root context,
<http://localhost:8080/>, rename the war file beneath
`wildfly-$VERSION/standalone/deployments/` to `ROOT.war`.
