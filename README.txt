Step 1 Prepare Database Tier
For the data access tire, this step prepares the table and the sample data by following step.
1. Install MySQL Server 5.5.
2. Set the user name and password.
3. Login to MySQL.
4. Execute sql script to create the database and sample data.

Step 2 Prepare Middle Tier (Servlet and REST Connection Server)
1. Import BarcodePaymentServer project into eclipse.
2. in connection manager set connection parameters for MySQL(the ones chosen in step 1).
3. Include all provided jars to your classpath.
4. Compile and run on tomcat 7.0.8 within eclipse.

-Alternative-
1. Import BarcodePaymentServer into an editor of your choice.
2. in connection manager set connection parameters for MySQL(the ones chosen in step 1).
3. Include all provided jars to your classpath.
3. From command line/terminal compile and deploy project to webapps of tomcat server.
4. Manually start tomcat server.

Step 3 Prepare Front Tier (Mobile Client)
1. Download TouchPay.apk
2. Install .apk on android device else install on Emulator.
3. If testing on device also install Zxing BarcodeScanner .apk provided in the client package.
4. Install ScanTest.apk on another device.(SanTest is mini merchant android app to accept payments)
3. Finally run the application.

PS: If using emulator the application should work. else if using an Android Device, import Mobile Client project in your editor and change URL hostnames to match you server IP Address. Ex. http://10.0.2.2:8080 for testing on emulator and http://dynamic_TomcatServer_ip:8080 for testing from device. 
