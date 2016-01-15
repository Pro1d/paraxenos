package fr.insa.clubinfo.paraxenos.math;

public class Vector {
	public double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Vector copy) {
		this.x = copy.x;
		this.y = copy.y;
	}

	public Vector add(Vector v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}

	public Vector add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector sub(Vector v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}

	public Vector sub(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector mul(double k) {
		this.x *= k;
		this.y *= k;
		return this;
	}

	public Vector normalize() {
		if (x != 0.0 || y != 0.0) {
			double n = (double) Math.hypot(x, y);
			x /= n;
			y /= n;
		}
		return this;
	}

	public double dot(Vector v2) {
		return x * v2.x + y * v2.y;
	}

	public double cross(Vector v2) {
		return x * v2.y - y * v2.x;
	}

	public double norm2() {
		return x * x + y * y;
	}

	public double norm() {
		return Math.hypot(x, y);
	}

	public Vector set(Vector v) {
		this.x = v.x;
		this.y = v.y;
		return this;
	}

	public Vector set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public boolean isNull() {
		return x == 0.0 && y == 0.0;
	}

	public Vector rotate90() {
		double ly = this.y;
		this.y = this.x;
		this.x = -ly;
		return this;
	}

	public boolean equals(Vector v) {
		return v.x == x && v.y == y;
	}

	public static Vector add_(Vector v1, Vector v2) {
		Vector v = new Vector(v1);
		return v.add(v2);
	}

	public static Vector sub_(Vector v1, Vector v2) {
		Vector v = new Vector(v1);
		return v.sub(v2);
	}

	public static Vector mul_(Vector v1, double k) {
		Vector v = new Vector(v1);
		return v.mul(k);
	}
}
