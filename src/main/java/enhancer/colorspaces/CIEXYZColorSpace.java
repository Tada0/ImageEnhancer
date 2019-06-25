package enhancer.colorspaces;

class CIEXYZColorSpace {
    private double x;
    private double y;
    private double z;

    CIEXYZColorSpace(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    double getZ() {
        return z;
    }
}