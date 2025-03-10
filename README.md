# 3D Engine in Java

This project is a basic **3D engine** implemented in Java. It demonstrates the rendering of 3D triangles in a 2D environment by leveraging the `Swing` library for graphics. The engine includes functionalities for wireframe rendering and interactive rotation using sliders.

## Overview

The main file (`Main.java`) serves as the entry point for the application. It initializes and displays a collection of triangles in 3D space, projects them onto a 2D plane, and renders them as wireframes. 

### Key Components

1. **Vertex Class**:
   - Represents a 3D point with `x`, `y`, and `z` coordinates.
   - Used as the building block for defining 3D triangles.

2. **Triangle Class**:
   - Encapsulates three vertices to represent a triangular facet.
   - Includes a `Color` property to assign colors to individual triangles.

3. **Main Class**:
   - Defines a list of triangles and initializes their properties (vertices and colors).
   - Sets up a graphical user interface (GUI) using `JFrame`, including:
     - **Horizontal rotation slider** for controlling heading rotation.
     - **Vertical rotation slider** for controlling pitch rotation.
     - A rendering panel for displaying the triangles as wireframes.

### Functionality

- **Wireframe Rendering**: Triangles are drawn as simple outlines in a 2D plane using the `Graphics2D` library.
- **Interactive Controls**:
  - A **horizontal slider** to adjust the heading angle (rotation along the Y-axis).
  - A **vertical slider** to adjust the pitch angle (rotation along the X-axis).

## Prerequisites

- **Java Development Kit (JDK)**: Ensure you have JDK 8 or higher installed.
- **Java AWT and Swing Libraries**: These are included in the standard JDK.

## Running the Program

1. Clone the repository or download the `Main.java` file.
2. Open the file in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
3. Compile and run the `Main` class.

### Run Command (if using the command line):
```bash
javac Main.java
java Main
