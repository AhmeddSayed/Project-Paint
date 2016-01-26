package ppaint;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class DrawingBoard extends JFrame
{

    public Shape r = null;

    public static void main(String[] args)
    {

        new DrawingBoard();
    }

    public DrawingBoard()
    {

        this.setSize(700, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new PaintSurface(), BorderLayout.CENTER);
        this.setVisible(true);
    }

    private class PaintSurface extends JComponent
    {

        public int shapeID = 4;
        ArrayList<Shape> shapes = new ArrayList<Shape>();

        Point startDrag, endDrag;

        public PaintSurface()
        {
            this.addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    startDrag = new Point(e.getX(), e.getY());
                    endDrag = startDrag;
                    repaint();
                }

                public void mouseReleased(MouseEvent e)
                {

                    drawShape r = new drawShape(startDrag, endDrag, shapeID);
                    shapes.add(r.getShape());
                    startDrag = null;
                    endDrag = null;
                    repaint();
                }
            });

            this.addMouseMotionListener(new MouseMotionAdapter()
            {
                public void mouseDragged(MouseEvent e)
                {
                    endDrag = new Point(e.getX(), e.getY());
                    drawShape r = new drawShape(startDrag, startDrag, shapeID);
                    repaint();
                }
            });
        }

        private void paintBackground(Graphics2D g2)
        {
            g2.setPaint(Color.LIGHT_GRAY);
            for (int i = 0; i < getSize().width; i += 10)
            {
                Shape line = new Line2D.Float(i, 0, i, getSize().height);
                g2.draw(line);
            }

            for (int i = 0; i < getSize().height; i += 10)
            {
                Shape line = new Line2D.Float(0, i, getSize().width, i);
                g2.draw(line);
            }

        }

        public void paint(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintBackground(g2);
            Color[] colors =
            {
                Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED, Color.BLUE, Color.PINK
            };
            int colorIndex = 0;

            g2.setStroke(new BasicStroke(2));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

            for (Shape s : shapes)
            {
                g2.setPaint(Color.BLACK);
                g2.draw(s);
                g2.setPaint(colors[(colorIndex++) % 6]);
                g2.fill(s);
            }
            if (startDrag != null && endDrag != null)
            {
                g2.setPaint(Color.black);
                Shape r = makeShape(startDrag, endDrag, shapeID);
                g2.draw(r);
            }
        }

        private Shape makeShape(Point x1, Point x2, int ID)
        {
            drawShape shape = new drawShape(x1, x2, ID);
            return shape.getShape();
        }

    }
}
