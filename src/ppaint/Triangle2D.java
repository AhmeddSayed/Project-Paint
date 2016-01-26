import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import static java.lang.Math.sqrt;

public class drawShape
{

    public Shape drawShape(Point startDrag, Point endDrag, int shapeID)
    {
        switch (shapeID)
        {

            case 1: //Line
                r = new Line2D.Double(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                break;

            case 2: //oval shape
                r = new Ellipse2D.Double(Math.min(startDrag.x, endDrag.x), Math.min(startDrag.y, endDrag.y), Math.abs(startDrag.x - endDrag.x), Math.abs(startDrag.y - endDrag.y));
                break;

            case 3: //Circle
                r = new Ellipse2D.Double(Math.min(startDrag.x, endDrag.x), Math.min(startDrag.y, endDrag.y), Math.abs(startDrag.x - endDrag.x), Math.abs(startDrag.x - endDrag.x));
                break;

            case 4://Rectangle shape
                r = new Rectangle2D.Float(Math.min(startDrag.x, endDrag.x), Math.min(startDrag.y, endDrag.y), Math.abs(startDrag.x - endDrag.x), Math.abs(startDrag.y - endDrag.y));
                break;

            case 5: //Triangle

                final GeneralPath p0 = new GeneralPath();
                p0.moveTo(startDrag.x, startDrag.y);
                p0.lineTo(Math.max(endDrag.x, startDrag.x) - (Math.abs(endDrag.y - startDrag.y) / sqrt(3)), endDrag.y);
                p0.lineTo(Math.max(endDrag.x, startDrag.x) + (Math.abs(endDrag.y - startDrag.y) / sqrt(3)), endDrag.y);
                p0.closePath();
                r = p0;
                break;

            default:
                break;
        }
    }
}
