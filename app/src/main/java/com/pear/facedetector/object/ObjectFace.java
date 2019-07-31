package com.pear.facedetector.object;

import com.pear.facedetector.Kind;

public class ObjectFace {
    private String name;
    private float x;
    private float y;
    private Kind kind;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ObjectFace(String name, float x, float y, Kind kind) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.kind = kind;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }
}
