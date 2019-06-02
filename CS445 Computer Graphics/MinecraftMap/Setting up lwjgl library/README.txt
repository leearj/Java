Follow the step along with the screenshots.

#1a. Copy and paste the src folder and attached png file -- "terrain.png". I will call mine MinecraftMap_demo

#1b. You need to download two jar files: 
	- lwjgl-2.9.3.jar (Not lwjgl3, because the project uses the outdated library that library3 doesn't have anymore)

	- slick-util.jar

	
#2. put slick-util.jar into lwjgl-2.9.3\lwjgl-2.9.3\jar folder 

#3. In the project(elcipse), Go to Build Path - Configure Build Path

#4a. Add External JARs, locate and add lwjgl.jar 

#4b. double click the lwjgl.jar in Eclipse, you see Native library location(None); click Edit.

#4c. set it to where lwjgl.dll file is at. I am using windows, so mine is "...\native\windows". If mac, go macosx!

#5. Add External JARs, add lwjgl_util.jar (No need to do the location setting)

#6. Add External JARs, add slick-util.jar (No need to do the location setting)

#7. Apply and close, and you are all set!


