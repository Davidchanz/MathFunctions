package com.example.mathfunctions;

import com.FXChemiEngine.engine.Shape;
import com.FXChemiEngine.engine.ShapeObject;
import com.FXChemiEngine.engine.shape.Dot;
import com.FXChemiEngine.math.UnityMath.Vector2;
import com.FXChemiEngine.util.Color;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Function extends Shape {
    private double a=0.0, b=0.0, c=0.0, px=1.0, py=1.0, ay=0.0;
    private int minX, maxX;
    private double dx;
    private String expression;
    private double zoomFactor = 1.0;

    public void setExpression(String expression){
        this.expression = expression;
    }

    public void reset(){
        this.vertices.clear();
        this.center = new Vector2(0.0F, 0.0F);
        this.position = new Vector2(0,0);
        a=0.0; b=0.0; c=0.0; px=1.0; py=1.0; ay=0.0;
    }

    public boolean parseExpression(){
        try {
            reset();
            String sings = parseSigns();
            Scanner scanner = new Scanner(expression);
            scanner.useDelimiter(Pattern.compile("[=*/+-]"));
            //int count = 0;
            while (scanner.hasNext()) {
                String ex = scanner.next();
                //System.out.println(ex);
                if (ex.length() == 0) {
                    continue;
                }

                double sing = 1.0;
                if (sings.length() != 0) {
                    if (sings.charAt(0) == '-') {
                        sing = -1.0;
                    }
                    if (sings.length() == 1)
                        sings = "";
                    else
                        sings = sings.substring(1);
                }

                //count++;

                if (ex.contains("y^")) {
                    //y^2
                    py = Double.parseDouble(ex.substring(ex.indexOf("y^") + 2));
                    if (ex.substring(0, ex.indexOf("y^")).length() != 0)
                        ay = sing * Double.parseDouble(ex.substring(0, ex.indexOf("y^")));
                    else
                        ay = sing * 1.0;
                } else if (ex.contains("x^")) {
                    //x^2
                    px = Double.parseDouble(ex.substring(ex.indexOf("x^") + 2));
                    if (ex.substring(0, ex.indexOf("x^")).length() != 0)
                        a = sing * Double.parseDouble(ex.substring(0, ex.indexOf("x^")));
                    else
                        a = sing * 1.0;
                } else if (ex.contains("y")) {
                    //2y
                    if (ex.substring(0, ex.indexOf("y")).length() != 0)
                        ay = sing * Double.parseDouble(ex.substring(0, ex.indexOf("y")));
                    else
                        ay = sing * 1.0;
                } else if (ex.contains("x")) {
                    //2x
                    if (ex.substring(0, ex.indexOf("x")).length() != 0)
                        b = sing * Double.parseDouble(ex.substring(0, ex.indexOf("x")));
                    else
                        b = sing * 1.0;
                } else {
                    //5
                    c = Double.parseDouble(ex);
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private String parseSigns(){
        String sings = "";
        Scanner scanner = new Scanner(expression);
        scanner.useDelimiter(Pattern.compile("[^=*/+-]"));
        while (scanner.hasNext()) {
            String ex = scanner.next();
            sings += ex;
        }
        return sings;
    }

    public void gen(){
        this.vertices.clear();
        for(double x = minX; x < maxX; x+=dx){
            double y = Math.pow(a * Math.pow(x, px) + b * x + c, 1.0/py) / ay;
            add(new Vector2((float) (zoomFactor*x), (float) (zoomFactor*y)));
        }
        if(py % 2.0 == 0.0)
            for(double x = minX; x < maxX; x+=dx){
                double y = Math.pow(a * Math.pow(x, px) + b * x + c, 1.0/py) / ay;
                add(new Vector2((float) (zoomFactor*x), (float) (zoomFactor*-y)));
            }
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public void setDx(double dx) {
        if(dx > 0 && dx > 0.01)
            this.dx = dx;
    }

    public double getDx() {
        return dx;
    }

    public Function(Color c) {
        super(c);
        this.setStrokeColor(c);
        this.center = new Vector2(0.0F, 0.0F);
        this.position = new Vector2(0,0);
    }

    public void add(Vector2 dot){
        this.vertices.add(dot);
    }

    @Override
    public void paint() {
        ArrayList<Vector2> dots = this.getVertices(this.vertices);
        if (dots != null) {
            for(int i = 0; i < dots.size()-1; ++i) {
                ArrayList<Vector2> tmp = new ArrayList<>(Arrays.stream(new Vector2[]{dots.get(i), dots.get(i + 1)}).toList());
                this.drawStroke(tmp);
            }
            //this.drawStroke(dots);
        }
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
        if(this.zoomFactor < 1)
            this.zoomFactor = 1;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }
}
