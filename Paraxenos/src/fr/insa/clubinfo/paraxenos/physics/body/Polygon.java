package fr.insa.clubinfo.paraxenos.physics.body;

import java.util.ArrayList;

import fr.insa.clubinfo.paraxenos.math.Edge;
import fr.insa.clubinfo.paraxenos.math.Vector;
import fr.insa.clubinfo.paraxenos.math.Vertex;
import fr.insa.clubinfo.paraxenos.physics.Body;

public class Polygon extends Body {

	ArrayList<Edge> edges = new ArrayList<>();
	ArrayList<Vertex> vertex = new ArrayList<>();

	public Polygon() {
		super(Type.POLYGON);
	}

	public void addPath(double[] points, boolean loop) {
		if (points == null || points.length < 2)
			return;

		int nbPoints = points.length / 2;

		if (nbPoints == 1) {
			vertex.add(new Vertex(points[0], points[1]));
		} else {
			Edge[] path = new Edge[loop ? nbPoints : nbPoints - 1];

			// Add edges
			for (int i = 0; i < path.length; i++) {
				int j = (i + 1) % nbPoints;
				path[i] = new Edge(points[i * 2 + 0], points[i * 2 + 1],
						points[j * 2 + 0], points[j * 2 + 1]);
				edges.add(path[i]);
			}

			// Add vertex in the corners if required, defined a normal
			for (int i = 0; i < (loop ? path.length : path.length - 1); i++) {
				int j = (i + 1) % path.length;
				double cross = path[i].tangent.cross(path[j].tangent);
				double dot = path[i].tangent.dot(path[j].tangent);
				if (cross < 0 || (cross == 0 && dot < 0))
					vertex.add(new Vertex(path[i].vertexEnd, path[j].normal,
							path[i].normal));
			}

			// Ends of the path if no loop
			if (!loop) {
				Edge first = path[0];
				vertex.add(new Vertex(first.vertexStart, first.normal, Vector
						.mul_(first.tangent, -1)));
				Edge last = path[path.length - 1];
				vertex.add(new Vertex(last.vertexEnd, last.tangent, last.normal));
			}
		}
	}

	public void addEdge(Vector v1, Vector v2) {
		edges.add(new Edge(v1, v2));
	}

	public void addVertex(Vector v) {
		vertex.add(new Vertex(v));
	}

	public int getEdgeCount() {
		return edges.size();
	}

	public int getVertexCount() {
		return vertex.size();
	}

	public Edge getEdge(int index) {
		return edges.get(index);
	}

	public Vertex getVertex(int index) {
		return vertex.get(index);
	}
}
