/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
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

/**
 *
 * @author Ahmed
 */
public class PaintSurface extends JComponent
{
    int x,y;
    int h,w;

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

                drawShape r = new drawShape(startDrag, endDrag, NewJFrame.shapeID);
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
                drawShape r = new drawShape(startDrag, startDrag, NewJFrame.shapeID);
                repaint();
            }
        });
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, w, h);
        g.setColor(Color.BLACK);
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
            Shape r = makeShape(startDrag, endDrag, NewJFrame.shapeID);
            g2.draw(r);
        }
    }

    private Shape makeShape(Point x1, Point x2, int ID)
    {
        drawShape shape = new drawShape(x1, x2, ID);
        return shape.getShape();
    }

}


