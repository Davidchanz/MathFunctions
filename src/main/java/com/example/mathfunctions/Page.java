package com.example.mathfunctions;

import com.FXChemiEngine.engine.ShapeObject;
import com.FXChemiEngine.engine.shape.Line;
import com.FXChemiEngine.math.UnityMath.Vector2;
import com.FXChemiEngine.util.Color;

public class Page extends ShapeObject {
    private double zoomFactor = 1.0F;
    public Page(){
        super("Hash", 0);
    }

    public void update(int w, int h){
        this.body.clear();
        for(double x = -w; x < w; x+=zoomFactor*20.0)
            add(new Line(new Vector2((float) x, -h), new Vector2((float)x, h), new Vector2(0,0), Color.BLACK));
        for(double y = -h; y < h; y+=zoomFactor*20.0)
            add(new Line(new Vector2(-w, (float)y), new Vector2(w, (float)y), new Vector2(0,0), Color.BLACK));
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
        if(this.zoomFactor < 1)
            this.zoomFactor = 1;
    }
}
