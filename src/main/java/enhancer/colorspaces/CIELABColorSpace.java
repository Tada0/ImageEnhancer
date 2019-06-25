package enhancer.colorspaces;

class CIELABColorSpace {

    private double l;
    private double a;
    private double b;

    CIELABColorSpace(double l, double a, double b){
        this.l = l;
        this.a = a;
        this.b = b;
    }

    double getL() {
        return l;
    }

    double getA() {
        return a;
    }

    double getB() {
        return b;
    }
}
