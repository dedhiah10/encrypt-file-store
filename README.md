# encrypt-file-store


	/*
	public int gcd(int a, int b) {
		while(b != 0) {
			int tmp = a % b;
			a = b;
			b = tmp;
		}
		return a;
	}

	public String hash(int a, int b) {
		int g = gcd(Math.abs(a), Math.abs(b));
		if(g == 0) {
			return "0/0";
		}

		int num = a / g;
		int den = b / g;
		return (num * den < 0 && den != 0 ? "-" : "") + Math.abs(num) + "/" + Math.abs(den);
	}

	public int countTrapezoids(int[][] points) {
		// 1. Trapezium contains two pairs of opposite sides
		// 2. Atleast one pair should be parallel
		// 3. A Trapezium will be counted twice if it has two parallel pairs
		// 4. So parallelogram will be counted twice.
		// 5. So, Result = Trapeziums - Parallelograms
		// 6. Intercept = y1 - m * x1 = (y1 * (x2 - x1) - (y2 - y1) * x) / (x2 - x1)


		int trapeziums_parallelograms = 0;
		HashMap<String, Integer> parallel_lines = new HashMap<>();
		HashMap<String, Integer> collinear_lines = new HashMap<>();

		int n = points.length;

		for (int i = 0; i < n; i++) {
			int[] p2 = points[i];
			for (int j = 0; j < i; j++) {
				int[] p1 = points[j];

				String slope = p1[0] != p2[0] ? hash(p2[1] - p1[1], p2[0] - p1[0]) : "infinity";
				String intercept = p1[0] != p2[0] ? hash(p1[1] * (p2[0] - p1[0]) - (p2[1] - p1[1]) * p1[0], p2[0] - p1[0]) : p1[0] + "";
				String h = slope + "," + intercept;

				int seen_parallel_lines = parallel_lines.getOrDefault(slope, 0);
				int seen_collinear_lines = collinear_lines.getOrDefault(h, 0);

				trapeziums_parallelograms += seen_parallel_lines - seen_collinear_lines;

				parallel_lines.put(slope, parallel_lines.getOrDefault(slope, 0) + 1);
				collinear_lines.put(h, collinear_lines.getOrDefault(h, 0) + 1);
			}
		}

		int parallelograms = 0;
		HashMap<String, Integer> parallel_lines_dist = new HashMap<>();
		HashMap<String, Integer> collinear_lines_dist = new HashMap<>();

		for (int i = 0; i < n; i++) {
			int[] p2 = points[i];
			for (int j = 0; j < i; j++) {
				int[] p1 = points[j];

				String slope = p1[0] != p2[0] ? hash(p2[1] - p1[1], p2[0] - p1[0]) : "infinity";
				String intercept = p1[0] != p2[0] ? hash(p1[1] * (p2[0] - p1[0]) - (p2[1] - p1[1]) * p1[0], p2[0] - p1[0]) : p1[0] + "";
				int dist = (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);

				String h1 = slope + "," + dist;
				String h2 = slope + "," + intercept + "," + dist;

				int seen_parallel_lines_dist = parallel_lines_dist.getOrDefault(h1, 0);
				int seen_collinear_lines_dist = collinear_lines_dist.getOrDefault(h2, 0);

				parallelograms += seen_parallel_lines_dist - seen_collinear_lines_dist;

				parallel_lines_dist.put(h1, parallel_lines_dist.getOrDefault(h1, 0) + 1);
				collinear_lines_dist.put(h2, collinear_lines_dist.getOrDefault(h2, 0) + 1);

			}
		}

		// Again in above loop each parallelogram will be read twice

		return trapeziums_parallelograms - parallelograms / 2;
	}
}
	 */


//	class Trapezoid {
//		Set<Point> trapezoid = new HashSet<>();
//		trape
//	}


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
			return "Pt{" + xVal +
					  ", " + yVal +
					  '}';
		}
	}

	class Line {
//		public final Point pt1, pt2;
		public final int[] p1, p2;
		public final double slope;
		public final double intercept;
		public final int length;
//		public static int LineCount = 0;

//		Line(Point pt1, Point pt2) {
//			Line.LineCount++;
//			this.pt1 = pt1;
//			this.pt2 = pt2;
//			int xDiff = pt2.xVal - pt1.xVal;
//			int yDiff = pt2.yVal - pt1.yVal;
//			if (yDiff == 0) {
//				this.slope = 0;
//			} else if (xDiff != 0) {
//				this.slope = (((double) yDiff) / ((double) xDiff));
//			} else { this.slope = Double.MAX_VALUE; }
//
//			this.length = (xDiff * xDiff) + (yDiff * yDiff);
//		}


		Line(int[] pt1, int[] pt2) {
//			Line.LineCount++;
			this.p1 = pt1;
			this.p2 = pt2;
			int xDiff = pt2[0] - pt1[0];
			int yDiff = pt2[1] - pt1[1];
			if (yDiff == 0) {
				this.slope = 0;
				this.intercept = pt1[0];
			} else if (xDiff != 0) {
				this.slope = (((double) yDiff) / ((double) xDiff));
				this.intercept = pt1[1] - this.slope * pt1[0];
			} else {
				this.slope = Double.POSITIVE_INFINITY;
				this.intercept = Double.POSITIVE_INFINITY;
			}
			this.length = (xDiff * xDiff) + (yDiff * yDiff);
		}

		boolean AreLinesCollinear(Line l1) {
			// same Line
			if (
		  		this.slope == l1.slope &&
				this.intercept == l1.intercept
			) {return true;}

//			System.out.println(l.slope);
//			System.out.println(this.slope);

			return false;
		}

		boolean DoLinesFormParalelloGram(Line l) {
			return this.length == l.length;
		}

		@Override
		public String toString() {
			return "Ln{" + Arrays.toString(p1) +
					  ", " + Arrays.toString(p2) +
					  ", slp=" + slope +
					  ", len=" + length +
					  '}';
		}
	}

	public int countTrapezoids(int[][] points) {
		// convert int[][] to pint[]
//		Point[] ptArr = getPointsFromIntArr(points);

		// group points by slopes
		Map<Double, List<Line>> SlopeToLineMap = getSlopeToLineMap(points);

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

	private Map<Double, List<Line>> getSlopeToLineMap(int[][] points) {
		int Len = points.length, Count = (Len * (Len - 1)) / 2;
		Map<Double, List<Line>> SlopeToLineMap = new HashMap<>(Count);

		for (int p1 = 0; p1 < Len - 1; p1++) {
			for (int p2 = p1 + 1; p2 < Len; p2++) {
//				System.out.printf("%d, %d%n", p1, p2);
				Line l = new Line(points[p1], points[p2]);
				SlopeToLineMap.computeIfAbsent(
						  l.slope,
						  k -> new ArrayList<Line>()
				).add(l);
			}
		}

//		System.out.printf("exp: %d, val: %d,%n%n%n", Count, Line.LineCount);

		return SlopeToLineMap;
	}

	private int getCountFromSlopeToLineMap(Map<Double, List<Line>> mapSlopeToLines) {
		int ctr = 0, paralellograms = 0;
		for (Map.Entry<Double, List<Line>> e: mapSlopeToLines.entrySet()) {
			Double Slope = e.getKey();
			List<Line> lines = e.getValue();
			int max = lines.size();
			if (max < 1) {break;}
			for (int l1 = 0; l1 < max - 1; l1++) {
				for(int l2 = l1 + 1; l2 < max; l2++) {
					boolean collinear = lines.get(l1).AreLinesCollinear(lines.get(l2));
					boolean paralellogram = !collinear && (lines.get(l1).DoLinesFormParalelloGram(lines.get(l2)));
//					if (!collinear) { System.out.printf("%d-%d,%ncollinear: %b, paralellogram: %b,%n l1: %s,%n l2: %s. %n%n%n", l1, l2, collinear, paralellogram, lines.get(l1).toString(), lines.get(l2).toString()); }
					if(!collinear) {ctr++;}
					if(paralellogram) {paralellograms++;}
				}
			}
		}

//		System.out.printf("%d - %d%n", ctr, paralellograms);

		int temp = (ctr - (paralellograms / 2));
		return Math.max(temp, 0);
	}
