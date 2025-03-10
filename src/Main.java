//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/***
 * In this class I am going to be building a 3d engine.
 * This main file is going to be where everything is going to be run
 * In this file, I am going to be creating
 * ***/

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;


// Class used to create a Vertex
//Vertex is simply a structure to store our three coordinates (X, Y and Z)
class Vertex {
    double x;
    double y;
    double z;

     Vertex(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

// Creating a triangle in 3d space using 3 Vertex object, defined above
// triangle binds together three vertices and stores its color
class Triangle{
    Vertex v1;
    Vertex v2;
    Vertex v3;
    Color color;

    Triangle(Vertex v1, Vertex v2, Vertex v3, Color color){
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
    }
}

public class Main {


    public static void main(String[] args){

        // creating 4 triangles and adding them to the list to be displayed
        List<Triangle> tris = new ArrayList<>();
        tris.add(new Triangle(new Vertex(100, 100, 100),
                new Vertex(-100, -100, 100),
                new Vertex(-100, 100, -100),
                Color.WHITE));
        tris.add(new Triangle(new Vertex(100, 100, 100),
                new Vertex(-100, -100, 100),
                new Vertex(100, -100, -100),
                Color.RED));
        tris.add(new Triangle(new Vertex(-100, 100, -100),
                new Vertex(100, -100, -100),
                new Vertex(100, 100, 100),
                Color.GREEN));
        tris.add(new Triangle(new Vertex(-100, 100, -100),
                new Vertex(100, -100, -100),
                new Vertex(-100, -100, 100),
                Color.BLUE));

        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        //Slider to control horizontal rotation
        JSlider headingSlider = new JSlider(0,360,180);
        pane.add(headingSlider, BorderLayout.SOUTH);

        //Slider to control vertical rotation
        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL,-90,90,0);
        pane.add(pitchSlider, BorderLayout.EAST);

        //panel to display render results
        JPanel renderPancel = new JPanel(){
            public void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0,0, getWidth(),getHeight());

                //All the render will happen here

                //Displaying the triangle above as wireframe
                g2.translate(getWidth()/2, getHeight()/2);
                g2.setColor(Color.WHITE);
                for (Triangle t : tris){
                    Path2D path = new Path2D.Double();
                    path.moveTo(t.v1.x, t.v1.y);
                    path.lineTo(t.v2.x, t.v2.y);
                    path.lineTo(t.v3.x, t.v3.y);
                    path.closePath();
                    g2.draw(path);
                }

            }
        };
        pane.add(renderPancel, BorderLayout.CENTER);

        frame.setSize(400, 400);
        frame.setVisible(true);


    }


}