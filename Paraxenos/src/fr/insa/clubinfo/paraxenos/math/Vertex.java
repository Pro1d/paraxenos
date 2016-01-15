package fr.insa.clubinfo.paraxenos.math;

public class Vertex {
	public Vector position;
	private Vector collisionLimitStart;
	private Vector collisionLimitEnd;
	private boolean reversedLimit;

	public Vertex(double x, double y) {
		position = new Vector(x, y);
	}

	public Vertex(Vector p) {
		position = new Vector(p);
	}

	public Vertex(Vector p, Vector limitStart, Vector limitEnd) {
		position = new Vector(p);

		// oriented angle : (n1, n2)
		if (limitStart.cross(limitEnd) < 0) {
			collisionLimitStart = new Vector(limitEnd);
			collisionLimitEnd = new Vector(limitStart);
			reversedLimit = true;
		} else {
			collisionLimitStart = new Vector(limitStart);
			collisionLimitEnd = new Vector(limitEnd);
			reversedLimit = false;
		}
	}

	/**
	 * Returns true if the vector 'dir' is inside the oriented angle
	 * (collisionLimitStart,collisionLimitEnd)
	 * 
	 * @param dir
	 *            from this vertex to the circle's center
	 * @return
	 */
	public boolean directionInCollisionRange(Vector dir) {
		boolean onLeftOfStart = collisionLimitStart.cross(dir) >= 0;
		boolean onRightOfEnd = collisionLimitEnd.cross(dir) <= 0;
		return (onLeftOfStart && onRightOfEnd) ^ reversedLimit;
	}
}
