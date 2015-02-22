cd ..
mvn --projects increase-web --also-make install 
mvn --projects increase-appengine appengine:update

