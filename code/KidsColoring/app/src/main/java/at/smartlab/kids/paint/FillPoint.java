package at.smartlab.kids.paint;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by wolfgang on 11.10.15.
 */
public class FillPoint {

    private Point p;

    private Paint paint;

    public FillPoint(Point p, Paint paint) {
        this.p = p;
        this.paint = paint;
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
