public class Vertex {
    public Point pt;
    public double f, h, cost;
    public Vertex pre;

    public Vertex(Point _pt, double _h, double _cost,Vertex _pre) {
        pt = _pt;
        h = _h;
        cost = _cost;
        f = _h + _cost;
        pre = _pre;
    }
}
