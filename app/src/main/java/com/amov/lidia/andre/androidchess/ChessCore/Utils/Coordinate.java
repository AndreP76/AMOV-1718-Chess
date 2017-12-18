package com.amov.lidia.andre.androidchess.ChessCore.Utils;

import java.util.Scanner;

public class Coordinate extends Point{
    char Column;

    public Coordinate(char column, int line) {
        super(line,column-'A');
        Column = column;
    }

    public char getColumn() {
        return Column;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Coordinate) {
            Coordinate c = (Coordinate) o;
            return c.getColumn() == this.getColumn() && c.getLine() == this.getLine();
        } else
            return o instanceof Point && super.equals(o);
    }

    public static Coordinate parse(String command) {
        int Line;
        char col;

        try {
            Scanner Sin = new Scanner(command);
            col = Sin.next().charAt(0);
            Line = Sin.nextInt();
            return new Coordinate(col, Line);
        }catch (RuntimeException RE){
            return null;
        }
    }
}
