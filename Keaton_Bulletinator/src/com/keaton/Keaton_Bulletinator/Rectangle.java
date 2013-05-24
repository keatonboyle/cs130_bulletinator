package com.keaton.Keaton_Bulletinator;

public class Rectangle<T>
{
   public Rectangle(T n, T e, T s, T w)
   {
      this.north = n;
      this.east = e;
      this.south = s;
      this.west = w;
   }

   public T north;
   public T east;
   public T south;
   public T west;
}
