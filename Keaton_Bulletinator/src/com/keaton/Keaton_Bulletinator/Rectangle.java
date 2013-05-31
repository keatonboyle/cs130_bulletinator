package com.keaton.Keaton_Bulletinator;

public class Rectangle
{
   public Rectangle(double n, double e, double s, double w)
   {
      this.north = n;
      this.east = e;
      this.south = s;
      this.west = w;
   }

   public boolean isOutside(double ns, double ew)
   {
      return ((ns > north) || (ns < south) || (ew > east) || (ew < west));
   }

   public double north;
   public double east;
   public double south;
   public double west;
}
