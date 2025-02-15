package com.ivhar;

public abstract class Player {
    private final char mark;
    protected Player(char mark) {
        this.mark = mark;
    }
    public abstract void makeTurn(Field field);

    public final char getMark() {
        return mark;
    }
}