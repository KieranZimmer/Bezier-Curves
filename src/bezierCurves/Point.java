package bezierCurves;
//Kieran Zimmer
//February 29th 2012
//Circle Circle Dot Dot (Group B)

import static java.lang.Math.*;  //static import math

public class Point {
  double x,y;  //(x,y) values
  public Point(int x, int y) { //make point at (x,y)
    this.x = x;
    this.y = y;
  }
  public Point(double x, double y) { //make point at (x,y)
    this.x = x;
    this.y = y;
  }
  public Point(Point p) {
    x = p.x;
    y = p.y;
  }
  public double distance(Point xy) {  //distance between 2 points
    return sqrt(pow(x - xy.x,2.0) + pow(y - xy.y,2.0));  //uses pythagorean theorem
  }
  public double distance(double x1,double y1) {  //distance between 2 points
    return sqrt(pow(x - x1,2.0) + pow(y - y1,2.0));  //uses pythagorean theorem
  }
  public void move(int x,int y) {  //moves the point
    this.x = x;
    this.y = y;
  }
  public String toString() {
    return "(" + (int)x + ", " + (int)(1080 - y) + ")";
  }
}