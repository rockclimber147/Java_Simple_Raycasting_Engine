# Java_Simple_Raycasting_Engine

## Description
I made this raycasting engine using Java Swing. I wanted to have an interactable demo that could be used to explore the concepts used to generate raycasted 2.5D graphics. The bottom left panel shows a top down view of the player. The top panel shows what the player 'sees', based on their position and settings in the bttom right panel.

## Usage
Run the main method to get started.

### Settings Panel
Raycount slider changes the amount of rays that are cast from the player


FOV slider changes the minimum and maximum angles the rays are cast at, relative to the player


Scale factor slider scales the heights of the rendered rectangles on the top view


Color factor slider changes the rate at which rendered rectangles turn dark with increasing distance



### Top Down View
Keyboard input moves player relative to the direction they are facing:
W - forward


S - backward


A - strafe left


D - strafe right


Q - rotate left


E - rotate right


Rays are drawn as yellow according to the slider configuration, example (rayCount 50, FOV 90):

![image](https://github.com/rockclimber147/Java_Simple_Raycasting_Engine/assets/98131303/cd664a12-96de-49e5-891a-86ae46b3568f)

### Player 'view'
Rectangles are drawn from left to right, each corresponding to a ray emitted by the player and render based on the color of the target, length of the ray, and scale sliders.

All together, it gives the illusion of travelling in 3d space!

![_2024-01-11 22-39-34](https://github.com/rockclimber147/Java_Simple_Raycasting_Engine/assets/98131303/20611a0d-7c93-4144-99a3-f538d0ee1cb3)
