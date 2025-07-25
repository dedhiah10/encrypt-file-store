# encrypt-file-store


class Point {
   public final int xVal, yVal;
   public Point(int[] pt) {
     this(pt[0], pt[1]);
   }
   public Point(int xVal, int yVal) {
     this.xVal = xVal;
     this.yVal = yVal;
   }

   @Override
   public String toString() {
     return "Point{" + xVal +
           ", " + yVal +
           '}';
   }
}

class Line {
   public final Point pt1, pt2;
   public final double slope;
   public static int LineCount = 0;

   Line(Point pt1, Point pt2) {
     Line.LineCount++;
     this.pt1 = pt1;
     this.pt2 = pt2;
     int xDiff = pt2.xVal - pt1.xVal;
     if (xDiff != 0) {
       this.slope = ((double) pt2.yVal - pt1.yVal) / ((double) xDiff);
     } else { this.slope = Double.MAX_VALUE; }
   }

   boolean AreLinesCollinear(Line l1) {
     Line l = new Line(this.pt1, l1.pt1);

     // same Line
     if (this == l1) {return true;}
     // collinear
     if (this.slope == l.slope) {return true;}

     return false;
   }

   @Override
   public String toString() {
     return "Line{" + pt1 +
           ", " + pt2 +
           ", slope=" + slope +
           '}';
   }
}

public int countTrapezoids(int[][] points) {
   // convert int[][] to pint[]
   Point[] ptArr = getPointsFromIntArr(points);

   // group points by slopes
   Map<Double, List<Line>> SlopeToLineMap = getSlopeToLineMap(ptArr);

   return getCountFromSlopeToLineMap(SlopeToLineMap);
}

private Point[] getPointsFromIntArr(int[][] points) {
   int ptLen = points.length;
   Point[] ptArr = new Point[ptLen];

   for (int i = 0; i < ptLen; i++) {
     ptArr[i] = new Point(points[i]);
   }

   return ptArr;
}

private Map<Double, List<Line>> getSlopeToLineMap(Point[] points) {
   int Len = points.length, Count = (Len * (Len - 1)) / 2;
   Map<Double, List<Line>> SlopeToLineMap = new HashMap<>(Count);

   for (int p1 = 0; p1 < Len - 1; p1++) {
     for (int p2 = p1 + 1; p2 < Len; p2++) {
       Line l = new Line(points[p1], points[p2]);
       SlopeToLineMap.computeIfAbsent(
             l.slope,
             k -> new ArrayList<>()
       ).add(l);
     }
   }

   System.out.printf("exp: %d, val: %d,%n%n%n", Count, Line.LineCount);

   return SlopeToLineMap;
}

private int getCountFromSlopeToLineMap(Map<Double, List<Line>> mapSlopeToLines) {
   int ctr = 0;
   for (Map.Entry<Double, List<Line>> e: mapSlopeToLines.entrySet()) {
     Double Slope = e.getKey();
     List<Line> lines = e.getValue();
     int max = lines.size();
     if (max < 2) {break;}
     for (int l1 = 0; l1 < max - 1; l1++) {
       for(int l2 = l1 + 1; l2 < max; l2++) {
         boolean collinear = lines.get(l1).AreLinesCollinear(lines.get(l2));
         System.out.printf("%d-%d :: %b, l1: %s, l2: %s. %n%n%n", l1, l2, collinear, lines.get(l1).toString(), lines.get(l2).toString());
         if(!collinear) {ctr++;}
       }
     }
   }
   return ctr;
}
