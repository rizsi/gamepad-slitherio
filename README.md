# Use Gamepad to control slither.io game

Features:

 * Screen rectangle must be set up (currently in the code)
 * left stick controls the snake by setting the direction directly
 * Button A - run
 * auto turn - not very useful but works
    * Y to capture average turn speed while pressed
    * right stick (y direction) to increase/decrease
    * B to clear
 * Gamepad is read using the libjinput-java library
 * Mouse pointer is controlled using the java.awt.Robot class
 * Tested on Ubuntu 16.04 with Logitech Gamepad F310

## Usage

 * Install libjinput-java: ```$ sudo apt-get install libjinput-java```
 * Import Java project into Eclipse (no binary release yet)
 * Edit the browser rectangle
 * Run program (Run As Java application)
 * Play the game using the controller

