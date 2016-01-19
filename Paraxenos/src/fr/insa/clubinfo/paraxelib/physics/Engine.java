package fr.insa.clubinfo.paraxelib.physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import android.os.SystemClock;
import android.util.Log;
import fr.insa.clubinfo.paraxelib.physics.actor.MovingActor;
import fr.insa.clubinfo.paraxelib.physics.actor.StaticActor;
import fr.insa.clubinfo.paraxelib.physics.body.Circle;
import fr.insa.clubinfo.paraxelib.physics.body.Polygon;
import fr.insa.clubinfo.paraxelib.physics.body.SetOfBody;
import fr.insa.clubinfo.paraxelib.utils.Edge;
import fr.insa.clubinfo.paraxelib.utils.Vertex;

public class Engine {

	ArrayList<MovingActor> movingActors = new ArrayList<>(100);
	HashMap<MovingActor, Integer> movingActorToIndex = new HashMap<>(100);
	Stack<Integer> freeMovingActorIndexes = new Stack<Integer>();

	ArrayList<StaticActor> staticActors = new ArrayList<>(100);
	HashMap<StaticActor, Integer> staticActorToIndex = new HashMap<>(100);
	Stack<Integer> freeStaticActorIndexes = new Stack<Integer>();

	public void addActor(MovingActor actor) {
		int index;
		if (freeMovingActorIndexes.empty()) {
			index = movingActors.size();
			movingActors.add(actor);
		} else {
			index = freeMovingActorIndexes.pop();
			movingActors.set(index, actor);
		}
		movingActorToIndex.put(actor, index);
	}

	public void addActor(StaticActor actor) {
		int index;
		if (freeStaticActorIndexes.empty()) {
			index = staticActors.size();
			staticActors.add(actor);
		} else {
			index = freeStaticActorIndexes.pop();
			staticActors.set(index, actor);
		}
		staticActorToIndex.put(actor, index);
	}

	public void removeActor(MovingActor actor) {
		Integer index = movingActorToIndex.get(actor);
		if (index == null)
			return;
		// remove from the hash map
		movingActorToIndex.remove(actor);
		// remove from the array
		movingActors.set(index, null);
		// add the free index to the stack
		freeMovingActorIndexes.push(index);
	}

	public void removeActor(StaticActor actor) {
		Integer index = staticActorToIndex.get(actor);
		if (index == null)
			return;
		// remove from the hash map
		staticActorToIndex.remove(actor);
		// remove from the array
		staticActors.set(index, null);
		// add the free index to the stack
		freeStaticActorIndexes.push(index);
	}

	/** Execute the physic engine for a step duration of 'time' millisecond */
	public void step(int time) {
		collideAndMoveActors(time);
	}

	private void collideAndMoveActors(double time) {
		CollisionDetails collisionDetails = new CollisionDetails();
		double elapsedTime = 0.0;/* from 0.0 to 1.0 */

		while (elapsedTime < 1.0) {
			long t = SystemClock.currentThreadTimeMillis();
			collisionDetails.reset();

			// FIXME do it only for the actors being collided ? or maybe not ? I
			// think no, but... maybe
			updateActorsMoving(time);

			// Get the details of the first collision that will occur
			findFirstCollision(collisionDetails);

			double timeBeforeCollision = collisionDetails.timeBeforeCollision;

			// Collision occurs before the end of the time
			if (collisionDetails.collisionOccurs
					&& timeBeforeCollision + elapsedTime < 1.0) {
				// Move the actors until the collision occurs
				updateActorsPosition(timeBeforeCollision);
				// Make the actors collide (change their velocity)
				collide(collisionDetails);
				// Update the velocity of the actor by applying the acceleration
				updateActorsVelocity(timeBeforeCollision);
				// Time progress
				elapsedTime += timeBeforeCollision;
			}
			// No collision occurs
			else {
				double remainingTime = 1.0 - elapsedTime;
				// Move the actors until the collision occurs
				updateActorsPosition(remainingTime);
				// Update the velocity of the actor by applying the acceleration
				updateActorsVelocity(remainingTime);
				// Max time reached
				elapsedTime = 1.0;
			}
			t = SystemClock.currentThreadTimeMillis()-t;
			Log.i("###", ""+t+" ms");
		}
	}

	/** Give the details of the first collision that will occur **/
	private CollisionDetails findFirstCollision(CollisionDetails detailsOut) {
		ArrayList<MovingActor> movingActors = this.movingActors;
		int actorMax = movingActors.size() - 1;
		for (int actorIndex1 = 0; actorIndex1 < actorMax; actorIndex1++) {
			Actor actor1 = movingActors.get(actorIndex1);
			if (actor1 == null)
				continue;

			nearestObstacle(actorIndex1, detailsOut);
		}

		return detailsOut;
	}

	// Keep the same stack for every uses
	private static final Stack<Body> bodiesStack = new Stack<>();

	/** Find the first actor the given moving actor will bounce off **/
	private CollisionDetails nearestObstacle(int movingActorIndex,
			CollisionDetails detailsOut) {
		ArrayList<MovingActor> movingActors = this.movingActors;
		ArrayList<StaticActor> staticActors = this.staticActors;

		MovingActor actor = movingActors.get(movingActorIndex);
		
		int actorCount = movingActors.size();
		for (int actorIndex2 = movingActorIndex + 1; actorIndex2 < actorCount; actorIndex2++) {
			MovingActor actor2 = movingActors.get(actorIndex2);
			if (actor2 == null)
				continue;

			if (Collision.staticCircleCircle(actor.getBody(), actor.getPosition(),
					actor2.getBody(), actor2.getPosition())) {
				// Two actors are already in collision -> no dynamic collision
				continue;
			}
			double timeBeforeCollision = Collision.dateCircleCircle(actor.getBody(),
					actor2.getBody(), Collision.getTmpRelativePositionBA(
							actor.getPosition(), actor2.getPosition()), Collision
							.getTmpRelativeVelocityAB(actor.getMoving(),
									actor2.getMoving()));
			detailsOut.updateIfEarlier(timeBeforeCollision, movingActorIndex,
					actorIndex2);
		}

		actorCount = staticActors.size();
		for (int actorIndex2 = 0; actorIndex2 < actorCount; actorIndex2++) {
			StaticActor actor2 = staticActors.get(actorIndex2);
			if (actor2 == null)
				continue;

			Stack<Body> bodiesStack = Engine.bodiesStack;
			bodiesStack.push(actor2.getBody());

			while (!bodiesStack.empty()) {
				Body b = bodiesStack.pop();
				switch (b.type) {
				case SET: {
					SetOfBody sob = (SetOfBody) b;
					for (int i = sob.getCount(); --i >= 0;)
						bodiesStack.push(sob.getBody(i));
					break;
				}
				case CIRCLE: {
					/*
					 * TODO Circle.position ? Circle c = (Circle) b; double
					 * timeBeforeCollision =
					 * Collision.dateCircleCircle(actor.body, c.position,
					 * Collision.getTmpRelativePositionBA(actor.position,
					 * actor2.position), actor.moving);
					 * detailsOut.updateIfEarlier(timeBeforeCollision,
					 * movingActorIndex, actorIndex2, c);
					 */
					break;
				}
				case POLYGON: {
					Polygon p = (Polygon) b;
					for (int i = p.getEdgeCount(); --i >= 0;) {
						Edge e = p.getEdge(i);
						double timeBeforeCollision = Collision.dateCircleEdge(
								actor.getBody(), actor.getPosition(), actor.getMoving(), e);
						detailsOut.updateIfEarlier(timeBeforeCollision,
								movingActorIndex, actorIndex2, e);
					}
					for (int i = p.getVertexCount(); --i >= 0;) {
						Vertex v = p.getVertex(i);
						double timeBeforeCollision = Collision
								.dateCircleVertex(actor.getBody(), v, Collision
										.getTmpRelativePositionBA(
												actor.getPosition(), v.position),
										actor.getMoving());
						detailsOut.updateIfEarlier(timeBeforeCollision,
								movingActorIndex, actorIndex2, v);
					}
					break;
				}
				}
			}
		}

		return detailsOut;
	}

	private void collide(CollisionDetails collision) {
		if (collision.targetActorIsMoving) {
			MovingActor actor1 = movingActors.get(collision.movingActorIndex);
			MovingActor actor2 = movingActors.get(collision.targetActorIndex);
			Collision.collideCircleCircle(actor1.getMass(), actor2.getMass(),
					actor1.getVelocity(), actor2.getVelocity(), Collision
							.getTmpRelativePositionBA(actor1.getPosition(),
									actor2.getPosition()));
		} else {
			MovingActor actor1 = movingActors.get(collision.movingActorIndex);
			// USELESS? StaticActor actor2 =
			// staticActors.get(collision.targetActorIndex);
			switch (collision.staticTarget.type) {
			case CIRCLE:
				// TODO Collision.collideCircleStaticCircle(actor1.velocity,
				// Collision.getTmpRelativePositionBA(actor1.position,
				// collision.staticTarget.circle.position));
				break;
			case EDGE:
				Collision.collideCircleStaticEdge(actor1.getVelocity(),
						collision.staticTarget.edge);
				break;
			case VERTEX:
				Collision.collideCircleStaticPoint(actor1.getVelocity(), Collision
						.getTmpRelativePositionBA(actor1.getPosition(),
								collision.staticTarget.vertex.position));
				break;
			}
		}
	}

	private void updateActorsPosition(double time) {
		ArrayList<MovingActor> movingActors = this.movingActors;
		for (int a = movingActors.size(); --a >= 0;) {
			MovingActor actor = movingActors.get(a);
			if (actor != null)
				actor.move(time);
		}
	}

	private void updateActorsMoving(double time) {
		ArrayList<MovingActor> movingActors = this.movingActors;
		for (int a = movingActors.size(); --a >= 0;) {
			MovingActor actor = movingActors.get(a);
			if (actor != null)
				actor.updateMovingVector(time);
		}
	}

	private void updateActorsVelocity(double time) {
		ArrayList<MovingActor> movingActors = this.movingActors;
		for (int a = movingActors.size(); --a >= 0;) {
			MovingActor actor = movingActors.get(a);
			if (actor != null)
				actor.addAccelerationToVelocity(time);
		}
	}

	static class CollisionDetails {
		boolean collisionOccurs = false;
		int movingActorIndex;
		int targetActorIndex;
		boolean targetActorIsMoving;
		double timeBeforeCollision = Double.POSITIVE_INFINITY;
		StaticTargetShape staticTarget = new StaticTargetShape();

		protected void reset() {
			collisionOccurs = false;
			timeBeforeCollision = Double.POSITIVE_INFINITY;
		}

		/* FIXME this code is ugly :( */

		protected void updateIfEarlier(double time, int movingActorIndex,
				int targetMovingActorIndex) {
			if (time < timeBeforeCollision) {
				this.movingActorIndex = movingActorIndex;
				this.targetActorIndex = targetMovingActorIndex;
				targetActorIsMoving = true;
				collisionOccurs = true;
				timeBeforeCollision = time;
			}
		}

		protected void updateIfEarlier(double time, int movingActorIndex,
				int targetStaticActorIndex, Circle c) {
			if (time < timeBeforeCollision) {
				this.movingActorIndex = movingActorIndex;
				this.targetActorIndex = targetStaticActorIndex;
				targetActorIsMoving = false;
				staticTarget.set(c);
				collisionOccurs = true;
				timeBeforeCollision = time;
			}
		}

		protected void updateIfEarlier(double time, int movingActorIndex,
				int targetStaticActorIndex, Vertex v) {
			if (time < timeBeforeCollision) {
				this.movingActorIndex = movingActorIndex;
				this.targetActorIndex = targetStaticActorIndex;
				targetActorIsMoving = false;
				staticTarget.set(v);
				collisionOccurs = true;
				timeBeforeCollision = time;
			}
		}

		protected void updateIfEarlier(double time, int movingActorIndex,
				int targetStaticActorIndex, Edge e) {
			if (time < timeBeforeCollision) {
				this.movingActorIndex = movingActorIndex;
				this.targetActorIndex = targetStaticActorIndex;
				targetActorIsMoving = false;
				staticTarget.set(e);
				collisionOccurs = true;
				timeBeforeCollision = time;
			}
		}

		protected static class StaticTargetShape {
			enum Type {
				CIRCLE, VERTEX, EDGE
			};

			Type type;
			Circle circle;
			Vertex vertex;
			Edge edge;

			public void set(Circle c) {
				type = Type.CIRCLE;
				circle = c;
			}

			public void set(Vertex v) {
				type = Type.VERTEX;
				vertex = v;
			}

			public void set(Edge e) {
				type = Type.EDGE;
				edge = e;
			}
		}
	}
}
