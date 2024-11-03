package JGamePackage.JGame.Types.PointObjects;

import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.lib.CustomError.CustomError;
import JGamePackage.lib.Utils.ExtendedMath;

public class Vector2 extends BasePoint {

    private static final CustomError ErrorAccessIndexOutOfBounds = new CustomError("Index %s out of bounds for Vector2 index access. Valid indices are "+Constants.Vector2Axis.X+" and "+Constants.Vector2Axis.Y+".", CustomError.ERROR, "Vector2");

    /**Shorthand for {@code Vector2(0,0)}.*/
    public static final Vector2 zero = new Vector2(0, 0);
    /**Shorthand for {@code Vector2(.5,.5)} */
    public static final Vector2 half = new Vector2(.5, .5);
    /**Shorthand for {@code Vector2(1,1)} */
    public static final Vector2 one = new Vector2(1, 1);
    /**Shorthand for {@code Vector2(-1,0)}.*/
    public static final Vector2 left = new Vector2(-1, 0);
    /**Shorthand for {@code Vector2(0,1)}. */
    public static final Vector2 up = new Vector2(0, -1);
    /**Shorthand for {@code Vector2(1,0)}. */
    public static final Vector2 right = new Vector2(1, 0);
    /**Shorthand for {@code Vector2(0,-1)}.*/
    public static final Vector2 down = new Vector2(0, 1);


    /**The X component of this Vector2.
     * 
     */
    public final double X;
    /**The Y component of this Vector2.
     * 
     */
    public final double Y;

    /**Creates a new Vector2 with the specified X and Y components.
     * 
     * @param x
     * @param y
     */
    public Vector2(double x, double y){
        X = x;
        Y = y;
    }

    /**Creates a new Vector2 and sets the Vector2's X and Y components equal to the {@code n} parameter.
     * 
     * @param n : The number to set the X and Y coordinates to
     */
    public Vector2(double n){
        X = n;
        Y = n;
    }



    public Vector2 add(Vector2 other){
        return new Vector2(X+other.X, Y+other.Y);
    }

    public Vector2 add(double n){
        return new Vector2(X+n, Y+n);
    }

    public Vector2 add(double x, double y){
        return new Vector2(X+x, Y+y);
    }


    public Vector2 subtract(Vector2 other){
        return new Vector2(X-other.X, Y-other.Y);
    }

    public Vector2 subtract(double n){
        return new Vector2(X-n, Y-n);
    }

    public Vector2 subtract(double x, double y) {
        return new Vector2(X-x, Y-y);
    }


    public Vector2 multiply(Vector2 other){
        return new Vector2(X*other.X, Y*other.Y);
    }

    public Vector2 multiply(double n) {
        return new Vector2(X*n, Y*n);
    }
    
    public Vector2 multiply(double x, double y) {
        return new Vector2(X*x, Y*y);
    }


    public Vector2 divide(Vector2 other) {
        return new Vector2(X/other.X, Y/other.Y);
    }

    public Vector2 divide(double n) {
        return new Vector2(X/n, Y/n);
    }

    public Vector2 divide(double x, double y) {
        return new Vector2(X/x, Y/y);
    }



    public Vector2 abs() {
        return new Vector2(Math.abs(X), Math.abs(Y));
    }

    public Vector2 negative() {
        return new Vector2(-X, -Y);
    }

    public Vector2 normalized() {
        double x = this.X;
        double y = this.Y;

        double mag = magnitude();
        if (mag > 1E-05){
            x /= mag;
            y /= mag;
            
        } else {
            y = 0;
            x = 0;
        }
        
        return new Vector2(x, y);
    }



    public boolean isZero(){
        return X==0 && Y==0;
    }

    public double magnitude() {
        return Math.sqrt(X*X + Y*Y);
    }

    public Vector2 lerp(Vector2 goal, double t) {
        double newX = ExtendedMath.lerp(X, goal.X, t);
        double newY = ExtendedMath.lerp(Y, goal.Y, t);
        return new Vector2(newX, newY);
    }



    /**Returns a Vector2 with its X and Y components shifted so that the new Vector2's
     * origin is at the {@code origin} parameter.
     * 
     * @param origin
     * @return
     */
    public Vector2 toLocalSpace(Vector2 origin) {
        return origin.add(this);
    }



    public double getAxisFromIndex(int index) {
        if (index == Constants.Vector2Axis.X ) return X;
        if (index == Constants.Vector2Axis.Y) return Y;
        ErrorAccessIndexOutOfBounds.Throw(new String[] {index+""});
        return 0; //will never reach this, just to satisfy compiler
    }



    @Override
    public String toString(){
        return "("+X+", "+Y+")";
    }

    @Override
    public Vector2 clone(){
        return new Vector2(X, Y);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;

        if (!(obj instanceof Vector2)) return false;

        Vector2 other = (Vector2) obj;

        return X==other.X && Y == other.Y;
    }
}