# Mondrian Art Generator

Generates a pseudo-random artwork in the style of Piet Mondrian using recursive rectangle subdivision in Java.

<img src="basic.png" width=300/> <img src="extension.png" width=300/>

## How it works
The canvas is recursively split into smaller rectangles, vertically or horizontally, until a minimum size is reached. Regions are then filled with either a random color from a fixed palette (basic mode) or a color derived from the region's position on the canvas (extension mode). Borders are handled by offsetting fill boundaries rather than drawing lines.

## Concepts demonstrated
- Recursion with multiple base cases
- 2D array manipulation
- Divide-and-conquer spatial decomposition
- Java AWT `Color` and `Graphics` rendering

## Requirements
Java 11+

> If you're currently enrolled in a course using this assignment, please don't copy this work.
