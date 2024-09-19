package io;
import enumerator.*;

public class Case
{
    private int line;
    private int column;
    private TypeLand typeLand;

    public Case(int line, int column, TypeLand typeLand)
    {
        this.line = line;
        this.column = column;
        this.typeLand = typeLand;
    }

    public int getLine()
    {
        return this.line;
    }
    public int getColumne()
    {
        return this.column;
    }
    public TypeLand getNature()
    {
        return this.typeLand;
    }
}