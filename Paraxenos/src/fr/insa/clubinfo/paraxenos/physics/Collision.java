package fr.insa.clubinfo.paraxenos.physics;

import fr.insa.clubinfo.paraxenos.math.Edge;
import fr.insa.clubinfo.paraxenos.math.Vector;
import fr.insa.clubinfo.paraxenos.math.Vertex;
import fr.insa.clubinfo.paraxenos.physics.body.Circle;

public class Collision {
	public static boolean staticCircleCircle(Circle a, Vector posA, Circle b,
			Vector posB) {
		double r = a.radius + b.radius;
		double dx = posA.x - posB.x;
		double dy = posA.y - posB.y;

		return dx * dx + dy * dy < r * r;
	}

	private static final Vector velArelB = new Vector(0, 0);

	public static Vector getTmpRelativeVelocityAB(Vector velA, Vector velB) {
		return velArelB.set(velA).sub(velB);
	}

	private static final Vector posBrelA = new Vector(0, 0);

	public static Vector getTmpRelativePositionBA(Vector posA, Vector posB) {
		return posBrelA.set(posB).sub(posA);
	}

	public static double dateCircleCircle(Circle a, Circle b, Vector posBrelA,
			Vector velArelB) {
		double dot = posBrelA.dot(velArelB);
		if (dot <= 0) // No relative moving or wrong direction
			return Double.POSITIVE_INFINITY;

		double radius = a.radius + b.radius;
		double norm2V = velArelB.norm2();
		double radius2 = radius * radius;

		// déplacement forcément trop court : |pbrela| > radius + |varelb|
		// <-> |pbrela|² > radius² + 2*radius*|varelb| + |varelb|²
		// !!ERROR!! <-> |pbrela|² > radius² + |varelb|² // 2*radius*|varelb| > 0
		// if(pbrela.norm2() > radius2 + norm2V)
		// return Double.POSITIVE_INFINITY;

		double cross = posBrelA.cross(velArelB);
		double underroot = radius2 * norm2V - cross * cross;
		if (underroot < 0) // |cross|/normV = |sin(v->p)*|p|| > radius -> wrong
							// direction
			return Double.POSITIVE_INFINITY;

		double dvrel = (dot - Math.sqrt(underroot));
		double k = dvrel / norm2V;

		return k;
	}

	public static double dateCircleVertex(Circle a, Vertex b, Vector posBrelA,
			Vector velArelB) {
		double dot = posBrelA.dot(velArelB);
		if (dot <= 0) // No relative moving or wrong direction
			return Double.POSITIVE_INFINITY;

		double norm2V = velArelB.norm2();
		double radius2 = a.radius * a.radius;

		// déplacement forcément trop court : |pbrela| > radius + |varelb|
		// <-> |pbrela|² > radius² + 2*radius*|varelb| + |varelb|²
		// <-> |pbrela|² > radius² + |varelb|² // 2*radius*|varelb| > 0
		// if(pbrela.norm2() > radius2 + norm2V)
		// return Double.POSITIVE_INFINITY;

		double cross = posBrelA.cross(velArelB);
		double underroot = radius2 * norm2V - cross * cross;
		if (underroot < 0) // |cross|/normV = |sin(v->p)*|p|| > radius -> wrong
							// direction
			return Double.POSITIVE_INFINITY;

		double dvrel = (dot - Math.sqrt(underroot));
		double k = dvrel / norm2V;

		Vector posArelBAfterMoving = velArelB.mul(k).sub(posBrelA);
		if (!b.directionInCollisionRange(posArelBAfterMoving))
			return Double.POSITIVE_INFINITY;

		return k;
	}

	private static final Vector AC = new Vector(0, 0), ACv = new Vector(0, 0),
			BC = new Vector(0, 0);

	public static double dateCircleEdge(Circle a, Vector posA, Vector velA,
			Edge b) {
		double VcrossAB = velA.cross(b.tangent);
		if (VcrossAB <= 0) // No moving or wrong direction
			return Double.POSITIVE_INFINITY;

		AC.set(posA.x - (b.vertexStart.x + b.normal.x * a.radius), posA.y
				- (b.vertexStart.y + b.normal.y * a.radius));
		// TODO test de précision avec B à la place de A

		if (b.tangent.cross(AC) < 0) // From the wrong side of the edge
			return Double.POSITIVE_INFINITY;

		ACv.set(AC).add(velA);

		if (b.tangent.cross(ACv) > 0) // edge not reached
			return Double.POSITIVE_INFINITY;

		BC.set(posA.x - (b.vertexEnd.x + b.normal.x * a.radius), posA.y
				- (b.vertexEnd.y + b.normal.y * a.radius));

		if (velA.cross(AC) * velA.cross(BC) > 0) // Limit to the segment (not a
													// straight line)
			return Double.POSITIVE_INFINITY;

		double ACdotN = AC.dot(b.normal);
		double VdotN = -velA.dot(b.normal);
		double k = ACdotN / AC.norm() / VdotN * velA.norm();
		return k;
	}

	private static final Vector N = new Vector(0, 0);

	public static void collideCircleCircle(double massA, double massB,
			Vector velA, Vector velB, Vector posBrelA) {
		N.set(posBrelA).normalize();// direction A->B
		double velAnc = N.dot(velA);
		double velBnc = N.dot(velB);
		double invM = 1.0 / (massA + massB);
		// V't + V'n = V - N*dot + N*coef = V + (coef-dot)*N

		double coefA = (velAnc * (massA - massB) + 2 * massB * velBnc) * invM
				- velAnc;
		velA.add(N.x * coefA, N.y * coefA);
		double coefB = (velBnc * (massB - massA) + 2 * massA * velAnc) * invM
				- velBnc;
		velB.add(N.x * coefB, N.y * coefB);
		// v1' = (v1*(m1-m2)+2*m2*v2) / (m1 + m2)
		// v2' = (v2*(m2-m1)+2*m1*v1) / (m1 + m2)
	}

	public static void collideCircleStaticCircle(Vector velA, Vector posBrelA) {
		double velAnc = posBrelA.dot(velA);
		double coefA = -2 * velAnc / posBrelA.norm2();

		velA.add(posBrelA.x * coefA, posBrelA.y * coefA);
	}

	public static void collideCircleStaticPoint(Vector velA, Vector posBrelA) {
		double velAnc = posBrelA.dot(velA);
		double coefA = -2 * velAnc / posBrelA.norm2();

		velA.add(posBrelA.x * coefA, posBrelA.y * coefA);
	}

	public static void collideCircleStaticEdge(Vector velA, Edge b) {
		double coef = -2.0 * b.normal.dot(velA);
		velA.add(b.normal.x * coef, b.normal.y * coef);
	}
}
