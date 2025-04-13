//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/*
  In this class I am going to be building a 3d engine.
  This main file is going to be where everything is going to be run
  In this file, I am going to be creating
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


// Class used to create a Vertex
// is simply a structure to store our three coordinates (X, Y and Z)
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

class   Matrix3{
    double[] values;
    Matrix3(double[] values){
        this.values =values;
    }
    Matrix3 multiply(Matrix3 other) {
        double[] results = new double[9];
        for (int row = 0; row<3; row++){
            for(int col =0; col<3; col++){
                for( int i = 0; i <3; i++){
                    results[row * 3 + col] += this.values[row*3 +i] * other.values[i*3+col];
                }
            }
        }
        return new Matrix3(results);
    }

    Vertex transform(Vertex in){
        return new Vertex(
                in.x * values[0] + in.y * values[3] + in.z * values[6],
                in.x * values[1] + in.y * values[4] + in.z * values[7],
                in.x * values[2] + in.y * values[5] + in.z * values[8]
        );
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
        JPanel renderPanel = new JPanel(){
            public void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0,0, getWidth(),getHeight());

                double heading = Math.toRadians(headingSlider.getValue());
                Matrix3 headingTransform = new Matrix3(new double[] {
                        Math.cos(heading), 0, Math.sin(heading),
                        0, 1, 0,
                        -Math.sin(heading), 0, Math.cos(heading)
                });
                double pitch = Math.toRadians(pitchSlider.getValue());
                Matrix3 pitchTransform = new Matrix3(new double[] {
                        1, 0, 0,
                        0, Math.cos(pitch), Math.sin(pitch),
                        0, -Math.sin(pitch), Math.cos(pitch)
                });
                Matrix3 transform = headingTransform.multiply(pitchTransform);

                BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

                double[] zBuffer = new double[img.getWidth() * img.getHeight()];
                for( int q = 0; q < zBuffer.length; q++){
                    zBuffer[q] = Double.NEGATIVE_INFINITY;
                }


                //All the render will happen here

                //Displaying the triangle above as wireframe
                for(Triangle t: tris){
                    Vertex v1 = transform.transform(t.v1);
                    Vertex v2 = transform.transform(t.v2);
                    Vertex v3 = transform.transform(t.v3);

                    // Since we are not using Graphics2D anymore
                    // we have to do translate manually

                    v1.x += (double) getWidth() / 2;
                    v1.y += (double) getHeight() / 2;
                    v2.x += (double) getWidth() / 2;
                    v2.y += (double) getHeight() / 2;
                    v3.x += (double) getWidth() / 2;
                    v3.y += (double) getHeight() / 2;

                    //compute rectangular bounds for triangle
                    int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
                    int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));

                    int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
                    int maxY = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

                    double trianglArea = (v1.y -v3.y) * (v2.x -v3.x) + (v2.y -v3.y) * (v3.x -v1.x);

                    for( int y = minY; y <= maxY; y++){
                        for(int x = minX; x <= maxX; x++){

                            double b1 = ((y -v3.y) * (v2.x -v3.x) + (v2.y -v3.y) * (v3.x -x)) / trianglArea;

                            double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / trianglArea;

                            double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y) * (v2.x - x)) / trianglArea;

                            if( b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1){
                                img.setRGB(x, y, t.color.getRGB());
                            }


                            double depth = b1* v1.z + b2 * v2.z + b3 * v3.z;
                            int zIndex = y *img.getWidth() + x;
                            if (zBuffer[zIndex] < depth) {
                                img.setRGB(x, y, t.color.getRGB());
                                zBuffer[zIndex] = depth;
                            }

                        }




                    }

                }

                g2.drawImage(img, 0, 0, null);



            }
        };
        pane.add(renderPanel, BorderLayout.CENTER);
        headingSlider.addChangeListener(e -> renderPanel.repaint());
        pitchSlider.addChangeListener(e -> renderPanel.repaint());

        frame.setSize(400, 400);
        frame.setVisible(true);


    }


}