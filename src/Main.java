public class Main {
    public static void main(String[] args) {
        //Width of plane
        int width = 700;
        //Height of plane
        int height = 700 ;
        // alpha is maximum turning angle
        double alpha = 30;
        // l is minimum leg length
        double l = 30.0;

        Gui._width = width;
        Gui._height = height;
        AStar as = new AStar(width,height,l,alpha);

        as.Start();
        // Create an obstacle
        as.CreateObstacle(new Point(371,221), new Point(286,321));
        // Set start point
        as.CreateStartPoint(new Point(61,68));
        // Set target point
        as.CreateTargetPoint(new Point(620,486));

        long startTime = System.nanoTime();
        as.RunAlgorithm();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Total Time is: " + (double)totalTime / 1000000.0 + " milisecond");
        Gui s = new Gui();
        s.setVisible(true);
    }
}
