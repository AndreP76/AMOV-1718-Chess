package com.amov.lidia.andre.androidchess.ChessCore.Utils;

public class DirectionUtils {
    public static final Direction START_DIRECTION = Direction.NORTH;
    public static final Direction END_DIRECTION = Direction.NORTHWEST;
    public static Point DirectionToVector(Direction d){
        switch (d){
            case NORTHEAST: { return new Point(-1,1);}
            case NORTHWEST: { return new Point(-1,-1);}
            case SOUTHWEST: { return new Point(1,-1);}
            case SOUTHEAST: { return new Point(1,1);}
            case EAST: { return new Point(0,1);}
            case WEST: { return new Point(0,-1);}
            case NORTH: { return new Point(1,0);}
            case SOUTH: { return new Point(-1,0);}
            default:{return new Point(0,0);}
        }
    }

    public static Direction NextDir(Direction i) {
        switch (i){
            case NORTHEAST: { return Direction.EAST;}
            case NORTHWEST: { return Direction.NORTH;}
            case SOUTHWEST: { return Direction.WEST;}
            case SOUTHEAST: { return Direction.SOUTH;}
            case EAST: { return Direction.SOUTHEAST;}
            case WEST: { return Direction.NORTHWEST;}
            case NORTH: { return Direction.NORTHEAST;}
            case SOUTH: { return Direction.SOUTHWEST;}
            default:{return null;}
        }
    }

    public static Direction IndexToDir(int i) {
        return Direction.values()[i % Direction.values().length];
    }
}
