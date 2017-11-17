package gift.of.silence.core;

public class Vector2D {

    protected static final Double EPSILON = 0.0000001;
    protected static final Double EPSILON_SQR = EPSILON * EPSILON;

    public Double x;
    public Double y;

    public Vector2D(Object... args) {
        switch (args.length) {
            case 0:
                x = 0D;
                y = 0D;
                break;
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    this.x = that.x;
                    this.y = that.y;
                    break;
                }
            case 2:
                if (args[0] instanceof Number && args[1] instanceof Number) {
                    x = ((Number) args[0]).doubleValue();
                    y = ((Number) args[1]).doubleValue();
                    break;
                }
            default:
                throw new RuntimeException();
        }
    }

    public Vector2D set(Object... args) {
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    this.x = that.x;
                    this.y = that.y;
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    x = (Double) args[0];
                    y = (Double) args[1];
                }
                break;
            default:
                throw new RuntimeException();
        }
        return this;
    }

    public Vector2D add(Object... args) {
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    this.x += that.x;
                    this.y += that.y;
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    x += (Double) args[0];
                    y += (Double) args[1];
                }
                break;
            default:
                throw new RuntimeException();
        }
        return this;
    }

    public Vector2D subtract(Object... args) {
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    this.x -= that.x;
                    this.y -= that.y;
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    x -= (Double) args[0];
                    y -= (Double) args[1];
                }
                break;
            default:
                throw new RuntimeException();
        }
        return this;
    }

    public Vector2D multiply(Object... args) {
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    this.x *= that.x;
                    this.y *= that.y;
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    x *= (Double) args[0];
                    y *= (Double) args[1];
                }
                break;
            default:
                throw new RuntimeException();
        }
        return this;
    }

    public Vector2D divide(Object... args) {
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    this.x /= that.x;
                    this.y /= that.y;
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    x /= (Double) args[0];
                    y /= (Double) args[1];
                }
                break;
            default:
                throw new RuntimeException();
        }
        return this;
    }

    public Vector2D zero() {
        x = 0D;
        y = 0D;
        return this;
    }

    public Vector2D scale(Double scale) {
        return multiply(scale, scale);
    }

    public Double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Double lengthSqr() {
        return x * x + y * y;
    }

    public Vector2D length(Double length) {
        if (isZero()) {
            // FIXME 
        } else {
            return scale(length / length());
        }
    }

    public Vector2D normalise() {
        return length(1D);
    }

    public Double distance(Object... args) {
        Double xd = 0D, yd = 0D;
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    xd = this.x - that.x;
                    yd = this.y - that.y;
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    xd = x - (Double) args[0];
                    yd = y - (Double) args[1];
                }
                break;
            default:
                throw new RuntimeException();
        }
        return Math.sqrt(xd * xd + yd * yd);
    }

    public Double distanceSqr(Object... args) {
        Double xd = 0D, yd = 0D;
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    xd = this.x - that.x;
                    yd = this.y - that.y;
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    xd = x - (Double) args[0];
                    yd = y - (Double) args[1];
                }
                break;
            default:
                throw new RuntimeException();
        }
        return xd * xd + yd * yd;
    }

    public Boolean equals(Object... args) {
        Boolean equal = Boolean.TRUE;
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    equal = equal && this.x == that.x;
                    equal = equal && this.y == that.y;
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    equal = equal && x == (Double) args[0];
                    equal = equal && y == (Double) args[1];
                }
                break;
            default:
                throw new RuntimeException();
        }
        return equal;
    }

    public Boolean isNormalized() {
        return Math.abs(length() - 1) < EPSILON;
    }

    public Boolean isZero() {
        return Math.abs(x) < EPSILON && Math.abs(y) < EPSILON;
    }

    public Boolean isNear(Object... args) {
        return distanceSqr(args) < EPSILON_SQR;
    }

    public Boolean isWithin(Double epsilon, Object... args) {
        return distanceSqr(args) < epsilon * epsilon;
    }

    public Boolean isValid() {
        return !Double.isNaN(x) && !Double.isNaN(y) && Double.isFinite(x) && Double.isFinite(y);
    }

    public Double dot(Object... args) {
        Double dot = Double.NaN;
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    dot = this.x * that.x + this.y * that.y;
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    dot = x * (Double) args[0] + y * (Double) args[1];
                }
                break;
            default:
                throw new RuntimeException();
        }
        return dot;
    }

    public Double cross(Object... args) {
        Double cross = Double.NaN;
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    cross = this.x * that.y - this.y * that.x;
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    cross = x * (Double) args[1] - y * (Double) args[0];
                }
                break;
            default:
                throw new RuntimeException();
        }
        return cross;
    }

    public Boolean isNormalTo(Object... args) {
        return dot(args) < EPSILON;
    }

    public Double angle() {
        Double ang = Math.atan2(y, x);
        if (ang < 0) {
            ang += Math.PI + Math.PI;
        }
        return ang;
    }

    public Vector2D angle(Double radians) {
        final Double origLength = length();
        x = origLength * Math.cos(radians);
        y = origLength * Math.sin(radians);
        return this;
    }

    public Double angleBetween(Object... args) {
        Double angle = Double.NaN;
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    angle = that.angle() - this.angle();
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    angle = new Vector2D((Double) args[0], (Double) args[1]).angle() - this.angle();
                }
                break;
            default:
                throw new RuntimeException();
        }

        if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        } else if (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }

        return angle;
    }

    public Vector2D rotate(Double radians) {
        final Double s = Math.sin(radians);
        final Double c = Math.cos(radians);

        Double newX = x * c - y * s;
        Double newY = x * s + y * c;
        x = newX;
        y = newY;

        return this;
    }

    public Vector2D normalRight() {
        return new Vector2D(-y, x);
    }

    public Vector2D normalLeft() {
        return new Vector2D(y, -x);
    }

    public Vector2D negate() {
        x = -x;
        y = -y;

        return this;
    }

    public Vector2D lerp(Double t, Object... args) {
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    x = x + t * (that.x - x);
                    y = y + t * (that.y - y);
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    x = x + t * ((Double) args[0] - x);
                    y = y + t * ((Double) args[1] - y);

                }
                break;
            default:
                throw new RuntimeException();
        }
        return this;
    }

    public Vector2D slerp(Double t, Object... args) {
        final Double cosTheta = dot(args);
        final Double theta = Math.acos(cosTheta);
        final Double sinTheta = Math.sin(theta);
        final Double w1 = Math.sin((1 - t) * theta) / sinTheta;
        final Double w2 = Math.sin(t * theta) / sinTheta;

        final Vector2D to = new Vector2D();
        to.set(args);

        scale(w1).add(to.scale(w2));
        return this;
    }

    public Vector2D reflect(Object... args) {
        Double d;
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    d = 2 * (x * that.x + y * that.y);
                    subtract(d * that.x, d * that.y);
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    d = 2 * (x * (Double) args[0] + y * (Double) args[1]);
                    subtract(d * (Double) args[0], d * (Double) args[1]);

                }
                break;
            default:
                throw new RuntimeException();
        }
        return this;
    }

    public Vector2D project(Object... args) {
        Double scalar = Double.NaN;
        Vector2D projVector = null;
        switch (args.length) {
            case 1:
                if (args[0] instanceof Vector2D) {
                    Vector2D that = (Vector2D) args[0];
                    projVector = that;
                    scalar = dot(projVector) / projVector.lengthSqr();
                }
                break;
            case 2:
                if (args[0] instanceof Double && args[1] instanceof Double) {
                    projVector = new Vector2D(args[0], args[1]);
                    scalar = dot(projVector) / projVector.lengthSqr();
                }
                break;
            default:
                throw new RuntimeException();
        }

        set(projVector);
        scale(scalar);
        return this;
    }

    public Vector2D offsetPolar(Double radius, Double angle) {
        x += radius * Math.cos(angle);
        y += radius * Math.sin(angle);
        return this;
    }

    @Override
    public Vector2D clone() {
        return new Vector2D(this);
    }

    public static void swap(Vector2D a, Vector2D b) {
        final Vector2D tmp = a.clone();
        a.set(b);
        b.set(tmp);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
