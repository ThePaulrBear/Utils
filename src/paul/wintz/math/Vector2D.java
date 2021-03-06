package paul.wintz.math;

import static java.lang.Math.*;

//TODO: convert to immutable.
@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class Vector2D implements Cloneable {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D fromCartesian(double x, double y) {
        return new Vector2D(x, y);
    }

    public static Vector2D fromPolar(double direction, double magnitude) {
        return new Vector2D(magnitude * cos(direction), magnitude * sin(direction));
    }

    public Vector2D() {
        this(0, 0);
    }

    public static Vector2D origin() {
        return new Vector2D(0, 0);
    }

    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2D set(Vector2D v) {
        x = v.x;
        y = v.y;
        return this;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double magnitude() {
        return hypot(x, y);
    }

    public double direction() {
        return atan2(y, x);
    }

    /**
     * Adds the Cartesian coordinates to the vector
     */
    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2D add(Vector2D v) {
        this.set(x + v.x, y + v.y);
        return this;
    }

    public Vector2D subtract(Vector2D v) {
        this.set(x - v.x, y - v.y);
        return this;
    }

    public Vector2D scale(double d) {
        set(d * x, d * y);
        return this;
    }

    /**
     * Adds a vector of length radius and direction theta to the current vector
     * @return this vector after addition
     */
    public Vector2D addPolar(double radius, double theta) {
        this.set(x + radius * cos(theta), y + radius * sin(theta));
        return this;
    }

    public Vector2D rotate(double angle) {
        final double radius = magnitude();
        final double direction = direction() + angle;

        this.set(
                radius * cos(direction),
                radius * sin(direction)
                );

        return this;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    @Override
    public String toString() {
        return String.format("<%f, %f>", x, y);
    }

    /**
     * Return the angle between to vectors in a clockwise direction from
     * the first vector to the second. The result is a value between 0.0
     * and 2*PI.
     */
    public static double angleBetween(final Vector2D from, final Vector2D to) {

        final double crossProduct = crossProduct(from, to);
        final double normalizedCrossProduct = constrainToUnitSegment(crossProduct / (from.magnitude() * to.magnitude()));

        final double dotProduct = dotProduct(from, to);

        if(crossProduct >= 0 && dotProduct >= 0)
            return asin(normalizedCrossProduct);
        else if(dotProduct < 0)
            return PI - asin(normalizedCrossProduct);

        return 2 * PI + asin(normalizedCrossProduct);

    }

    private static double constrainToUnitSegment(double normalizedCrossProduct) {
        if(normalizedCrossProduct > 1.0)
            return 1.0;
        if(normalizedCrossProduct < -1.0)
            return -1.0;
        return normalizedCrossProduct;
    }

    /**
     * Return a new vector that is the difference between the given vectors.
     * @return a - b
     */
    public static Vector2D diff(Vector2D a, Vector2D b) {
        return new Vector2D(a.x - b.x, a.y - b.y);
    }

    /**
     * Return a new vector that is the sum of the given vectors.
     * @return a + b
     */
    public static Vector2D sum(Vector2D a, Vector2D b) {
        return new Vector2D(a.x + b.x, a.y + b.y);
    }

    public static double crossProduct(Vector2D u, Vector2D v) {
        return u.x * v.y - u.y * v.x;
    }

    public static double dotProduct(Vector2D u, Vector2D v) {
        return u.x * v.x + u.y * v.y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vector2D) {
            Vector2D that = (Vector2D) obj;

            return this.x == that.x && this.y == that.y;
        }

        return false;


    }


}