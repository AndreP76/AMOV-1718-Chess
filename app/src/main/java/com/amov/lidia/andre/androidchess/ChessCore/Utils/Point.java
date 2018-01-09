package com.amov.lidia.andre.androidchess.ChessCore.Utils;

import java.io.Serializable;

public class Point implements Serializable {
    private int Col;
    private int Line;

    public Point(int l, int c) {
        Col = c;
        Line = l;
    }

    public void SumPoint(Point increment) {
        this.setCol(this.getCol() + increment.getCol());
        this.setLine(this.getLine() + increment.getLine());
    }

    public double distanceToTarget(Point target) {
        return Math.sqrt(Math.pow(this.getCol() - target.getCol(), 2) + Math.pow(this.getLine() - target.getLine(), 2));
    }

    public int getCol() {
        return Col;
    }

    public void setCol(int col) {
        Col = col;
    }

    public int getLine() {
        return Line;
    }

    public void setLine(int line) {
        Line = line;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point) {
            Point p = (Point) (o);
            return p.getCol() == this.getCol() && p.getLine() == this.getLine();
        } else return false;
    }

    public Point sum(Point point) {
        return new Point(this.getLine() + point.getLine(), this.getCol() + point.getCol());
    }

    @Override
    public String toString() {
        return "(" + this.getLine() + "," + this.getCol() + ")";
    }

    public String getColString() {
        return Character.toString((char) ('A' + Col));
    }
}
