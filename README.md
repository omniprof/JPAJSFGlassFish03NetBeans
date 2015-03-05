# JPAJSFGlassFish03NetBeans
NetBeans version of demo of JPA, JSF and Arquillian using GlassFish

Sample code that demonstrates usage of JPA and Arquillian

The story behind the creation of this project can be found at http://www.omniprogrammer.com/?p=431

The original version of this demo code was created in 2014 for a course in JSF. Students created web commerce sites using JSF and JDBC. The server was TomEE and DBMS was MySQL. The IDE was Eclipse.

This year, 2015, I changed the curriculum to use JSF 2.2 because my students wanted to use HTML 5. Unfortunately it proved too complex to upgrade TomEE to JSF 2.2 and an official version was not due for another 6 months. Therefore I moved to GlassFish.

Eclipse had been used for 10 years in this course an as the technology and I became more sophisticated it became a burden. I spent as much time solving Eclipse problems as I did with solving coding problems. This year I switched to NetBeans. The switch was not without its own issues but they are far fewer than what I experienced with Eclipse.

I introduced Arquillian into the curriculum in 2014 and Bartosz Majsak provided invaluable assitance (he showed me how) in getting the TomEE setup working and again this year with the GlassFish setup. Following the recomendation for best practices I chose to do my testing on a remote server. The server was running on the same system as the IDE.

While everything worked there was a problem with the length of time a test took. On average it took 100 seconds before NetBeans displayed the result of the test. The actual test was completed in seconds but as much of 70 seconds was spent waiting for the result. I switched to an embedded GlassFish server and testing dropped to less than 20 seconds.

While searching for a solution I came across the occasional suggestion that NetBeans was at fault and that I should use Eclipse. My blog post demonstrated that the testing time was the same on both platforms.

My work is done on a Windows 8.1 PC with 16 gig RAM, i7-4770 CPU, and with a Kingston SSD. The student computers are Windows 8.1, first gen i7 with 8 gig of RAM and HDD. On an Yosemite OSX Apple MacBook Pro, early 2011, i5, 8 gig RAM and SSD that I use in lectures the test on a remote server takes just around 35 seconds and around 30 seconds for embedded.

C:\Users\Ken>java -version java version "1.8.0_31" Java(TM) SE Runtime Environment (build 1.8.0_31-b13) Java HotSpot(TM) 64-Bit Server VM (build 25.31-b07, mixed mode)

I guess we can blame Microsoft?
