# Lights-Out-Game

Created for CS1410 By Oliver Yu

This is a java implementation of the classic game called Lights Out. The goal is to turn off all the lights. Once you click on a light to turn it on or off, the lights to the top, bottom, left and right are also switched from their states. 

## How to play the game

Download the executable jar file located in Lights-Out-Game/LightsOut.jar

Once a game is won or user decides to start with a new randomly generated board, click New Game.

To setup your a custom board simply click on Enter Manual Mode and create your own board. There are no rules for this mode, that means when you click on one square, the squares around it will not change unless you click on them. Click Exit Manual Mode to play the custom game.

## Running Tests

A JUnit test class is incluced in the source code under src/LightsOutModelTests. Currently, the tests validate whether or not the methods that make up the "brains" of the game located in LightsOutModel work properly.

Two helper methods are used to assist in testing the methods: 
The reset method resets the board to a blank (all lights on) state after the user wins a game without starting a new game. 
The blankWin method turns off all the lights from a blank state.

constructorTest tests whether or exceptions are thrown when a user tries to creat a board of invalid length (0 columns and/or rows).
testLightSwitch tests the lightSwtich and the getOccupant methods
testMove tests lightSwitch, getOccupant, move and getWins methods. Uses the helper methods mentioned above

## Built With

Java


## Authors:

Oliver Yu
Assignment from Joe Zachary for CS 1410
