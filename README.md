# 3D_Graphics_Engine

# Preview

![Test Image 1](ScreenCaps/capture.png)

This is a Ray Casting Engine written in java,Ray casting is a rendering technique used in computer graphics and computational geometry. 
It is capable of creating a three-dimensional perspective in a two-dimensional map by mathematically casting a ray to an object and then to the light source, by calculating the distance traveled and the luminance of the ray we can repeat the process for every pixel on the screen and create an image.
This Ray Caster uses this technique and allows you to move within the scene with the mouse and the arrows, this Ray-Caster also implements shadows and reflections, 

# Modularity
you can change the number of bounces, luminance, objects in the scene pretty easily with the code.

# Performance
The Resolution is pretty restrictive and it will run laggy as it is built to run on the CPU and not on the graphics card, it will only run at a couple FPS.
