public class AStar {
    public int _height = 100, _width = 150;
    public static double alpha = 10 ,l = 5;
    public int[][] map;
    Point start, target;
    MinHeap mh = new MinHeap();



    public AStar(int _m, int _n, double _l, double _alpha) {
        _width = _m;
        _height = _n;
        l = _l;
        alpha = _alpha;
        map = new int[_width][_height];
        for (int i = 0; i < _width; i++)
            for (int j = 0; j < _height; j++)
                map[i][j] = 0;
    }

    public void Start() {
        for (int i = 0; i < _width; i++)
            for (int j = 0; j < _height; j++) {
                if (i == 0 || i == _width - 1 || j == 0 || j == _height - 1)
                    map[i][j] = 1;
            }
    }

    public void CreateObstacle(Point p1, Point p2) {
        if (p1.x > _width && p1.y > _height && p2.x > _width && p2.y > _height)
            return;
        Gui.obstacles.add(p1);
        Gui.obstacles.add(p2);
        if (Math.abs(p1.x - p2.x) >= Math.abs(p1.y - p2.y)) {
            int max = p1.x >= p2.x ? p1.x : p2.x;
            int min = p1.x <= p2.x ? p1.x : p2.x;
            for (int i = min; i <= max; i++) {
                double t = (double)(i-p1.x)/(double)(p2.x - p1.x);
                int j = (int) Math.round ((1-t)* p1.y + t * p2.y);
               // int j = (int) (m * (i - p1.x) + p1.y);//(int) (((float)(i - p1.x)/m) + p1.y);
                map[i][j] = 1;
                map[i][j + 1] = 1;
            }

        } else {
            int max = p1.y >= p2.y ? p1.y : p2.y;
            int min = p1.y <= p2.y ? p1.y : p2.y;
            for (int j = min; j <= max; j++) {
                double t = (double)(j-p1.y)/(double)(p2.y - p1.y);
                int i = (int) Math.round ((1-t)* p1.x + t * p2.x);
                //int i = (int) (((float) (j - p1.y) / m) + p1.x);
                map[i][j] = 1;
                map[i][j + 1] = 1;
            }
        }

    }

    public void CreateStartPoint(Point pt) {
        map[pt.x][pt.y] = 2;
        start = pt;
        Gui.start = pt;
    }

    public void CreateTargetPoint(Point pt) {
        map[pt.x][pt.y] = 3;
        target = pt;
        Gui.target = pt;
    }

    public void FanTail(Vertex pre, Vertex center, int div) {

        Vertex next = new Vertex(new Point(center.pt.x + (center.pt.x - pre.pt.x), center.pt.y + (center.pt.y - pre.pt.y)), 0, 0, center);
        next.h = Distance(next.pt, target);
        next.cost = center.cost + l;

        if (next.pt.x >= 0 && next.pt.x < _width && next.pt.y >= 0 && next.pt.y < _height) {
            double step = alpha / (double) div;
            double sum = 0;
            while (sum < alpha) {
                double rad = Math.toRadians(sum);

                Point pt = new Point((int)Math.round ((next.pt.x - center.pt.x) * Math.cos(rad) - ((next.pt.y - center.pt.y) * Math.sin(rad)) + center.pt.x),
                        (int)Math.round ((next.pt.x - center.pt.x) * Math.sin(rad) + ((next.pt.y - center.pt.y) * Math.cos(rad)) + center.pt.y));

                if (pt.x >= 0 && pt.x < _width && pt.y >= 0 && pt.y < _height)
                    if (!InterSect(center.pt, pt)) {
                        map[pt.x][pt.y] = 5;
                        mh.insert(new Vertex(pt, Distance(pt, target), Distance(center.pt,pt) + center.cost, center));
                    }
                sum += step;
            }
            step = alpha / (double) div;
            sum = 0;
            while (sum < alpha) {
                double rad = Math.toRadians(-sum);
                Point pt = new Point((int)Math.round ((next.pt.x - center.pt.x) * Math.cos(rad) - ((next.pt.y - center.pt.y) * Math.sin(rad)) + center.pt.x),
                        (int)Math.round ((next.pt.x - center.pt.x) * Math.sin(rad) + ((next.pt.y - center.pt.y) * Math.cos(rad)) + center.pt.y));
                if (pt.x >= 0 && pt.x < _width && pt.y >= 0 && pt.y < _height)
                    if (!InterSect(center.pt, pt)) {
                        map[pt.x][pt.y] = 5;
                        mh.insert(new Vertex(pt, Distance(pt, target), Distance(center.pt,pt) + center.cost, center));
                    }
                sum += step;
            }
        }
    }

    public void FirstFanTail(Vertex start) {
        if (start.pt.x >= 0 && start.pt.x < _width && start.pt.y >= 0 && start.pt.y < _height) {
            double step = 1;
            double sum = 0;
            while (sum < 359) {
                double rad = Math.toRadians(sum);
                Point pt = new Point((int) (start.pt.x + l * Math.cos(rad)), (int) (start.pt.y + l * Math.sin(rad)));
                if (pt.x >= 0 && pt.x < _width && pt.y >= 0 && pt.y < _height)
                    if (!InterSect(start.pt, pt)) {
                        map[pt.x][pt.y] = 5;
                        mh.insert(new Vertex(pt, Distance(pt, target), Distance(start.pt,pt), start));
                    }
                sum += step;
            }
        }
    }

    public double Distance(Point p1, Point p2) {
        if(p1 != null && p2 !=null)
        return Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y)));
        else return 0;
    }

    public boolean InterSect(Point p1, Point p2) {
        if (Math.abs(p1.x - p2.x) >= Math.abs(p1.y - p2.y)) {
            int max = p1.x >= p2.x ? p1.x : p2.x;
            int min = p1.x <= p2.x ? p1.x : p2.x;
            for (int i = min; i <= max; i++) {
                double t = (double)(i-p1.x)/(double)(p2.x - p1.x);
                int j = (int) Math.round ((1-t)* p1.y + t * p2.y);
                if (i < _width && i >= 0 && j < _height && j >= 0 && j + 1 < _height)
                    if (map[i][j] == 1 || map[i][j + 1] == 1)
                        return true;
            }

        } else {
            int max = p1.y >= p2.y ? p1.y : p2.y;
            int min = p1.y <= p2.y ? p1.y : p2.y;
            for (int j = min; j <= max; j++) {
                double t = (double)(j-p1.y)/(double)(p2.y - p1.y);
                int i = (int) Math.round ((1-t)* p1.x + t * p2.x);
                //int i = (int) (((float) (j - p1.y) / m) + p1.x);
                if (i < _width && i >= 0 && j < _height && j >= 0 && j + 1 < _height)
                    if (map[i][j] == 1 || map[i][j + 1] == 1)
                        return true;
            }
        }
        return false;
    }

    int count =0;
    public void RunAlgorithm() {
        Vertex vt0 = new Vertex(start, Distance(start, target), 0, null);
        FirstFanTail(vt0);
        Gui s = new Gui();

        //boolean flag = true;
        while (!mh.isEmpty()) {
            Vertex vt1 = mh.extractMin();

            mh.ProcessedVertices.add(vt1);
            if(vt1.h < l ){
                FindPath(vt1);
                System.out.println("\n Total Path Length is: " + (vt1.cost+vt1.h));
                break;
            }
            FanTail(vt1.pre, vt1, 3);
            //mh.ProcessedVertices.add(vt1);
            count++;
        }
    }

    public void FindPath(Vertex vtx) {
        while (vtx != null) {
            Gui.path.add(vtx);
            vtx = vtx.pre;
        }
        map[start.x][start.y] = 2;
    }

}
