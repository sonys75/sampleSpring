cd /
cd Java
dir

javac InstallCert.java

java -cp ./ InstallCert ttocorps.com

 

echo 1 | keytool -exportcert -keystore jssecacerts -storepass changeit -file output.cert -alias ttocorps.com-1

echo 1 
