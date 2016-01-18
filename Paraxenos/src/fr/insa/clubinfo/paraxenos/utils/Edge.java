package fr.insa.clubinfo.paraxenos.utils;

public class Edge {
	public Vector vertexStart;
	public Vector vertexEnd;
	public Vector tangent;
	public Vector normal;

	public Edge(double x1, double y1, double x2, double y2) {
		vertexStart = new Vector(x1, y1);
		vertexEnd = new Vector(x2, y2);
		tangent = Vector.sub_(vertexEnd, vertexStart).normalize();
		normal = new Vector(tangent).rotate90();
	}

	public Edge(Vector v1, Vector v2) {
		vertexStart = new Vector(v1);
		vertexEnd = new Vector(v2);
		tangent = Vector.sub_(v2, v1).normalize();
		normal = new Vector(tangent).rotate90();
	}
}
