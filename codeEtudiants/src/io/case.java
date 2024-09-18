package io;
import NatureTerrain;

class Case
{
    private int line;
    private int column;
    private NatureTerrain natureTerrain;

    public Case(int line, int column, NatureTerrain natureTerrain)
    {
        this.line = line;
        this.column = column;
        this.natureTerrain = natureTerrain;
    }

    public int getLine()
    {
        return this.line;
    }
    public int getColumne()
    {
        return this.column;
    }
    public NatureTerrain getNature()
    {
        return this.natureTerrain;
    }
}