package core;
public class Rocket extends SolidObject {

	// the polygons of the model
	private Polygon3D[] polygons;

	// the moving direction of the rocket
	private int angle;

	private Vector direction;

	// the angle that rocket has turned during a frame
	private int angleDelta;

	// hostile rockets are fired by enemy tanks and will not collide with other
	// enemy tanks
	private boolean isHostile;

	private Polygon3D rocketAura;

	// location of the target
	private Vector targetLocation;
	
	

	// temporary vectors which will be used for vector arithmetic
	private Vector tempVector1 = new Vector(0, 0, 0);

	private Vector tempVector2 = new Vector(0, 0, 0);

	public Rocket(double x, double y, double z, int angle, boolean isHostile) {
		start = new Vector(x, y, z);
		this.angle = angle;

		iDirection = new Vector(1, 0, 0);
		jDirection = new Vector(0, 1, 0);
		kDirection = new Vector(0, 0, 1);

		this.isHostile = isHostile;

		// boundary of this model has a cubic shape (ie l = w)
		modelType = 4;
		makeBoundary(0.01, 0.025, 0.01);

		// create 2D boundary
		boundary2D = new Rectangle2D(x - 0.005, z + 0.005, 0.01, 0.01);

		// adjust orientation of the model
		iDirection.rotate_XZ(angle);
		kDirection.rotate_XZ(angle);

		// find the initial move direction, it will likely to change if the
		// target moves
		direction = new Vector(0, 0, 0.075);
		direction.rotate_XZ(angle);

		lifeSpan = 38;

		// find centre of the model in world coordinate
		findCentre();

		makePolygons();

		Vector[] v = new Vector[] { put(-0.15, -0.05, 0.15),
				put(0.15, -0.05, 0.15), put(0.15, -0.05, -0.15),
				put(-0.15, -0.05, -0.15) };
		rocketAura = new Polygon3D(v, v[0], v[1], v[3], Main.textures[21], 1,
				1, 2);

	}

	// Construct polygons for this model.
	// The polygon data is hard-coded here
	public void makePolygons() {
		Vector[] v;
		polygons = new Polygon3D[17];

		double r = 0.007;
		double theta = Math.PI / 4;

		for (int i = 0; i < 8; i++) {
			v = new Vector[] {
					put(r * Math.cos(i * theta), r * Math.sin(i * theta), -0.03),
					put(r * Math.cos((i + 1) * theta), r
							* Math.sin((i + 1) * theta), -0.03),
					put(r * Math.cos((i + 1) * theta), r
							* Math.sin((i + 1) * theta), 0.03),
					put(r * Math.cos(i * theta), r * Math.sin(i * theta), 0.03) };
			polygons[i] = new Polygon3D(v, v[0], v[1], v[3], Main.textures[25],
					1, 1, 6);
		}

		v = new Vector[8];
		for (int i = 1; i < 9; i++)
			v[8 - i] = put(r * Math.cos(i * theta), r * Math.sin(i * theta),
					-0.03);
		polygons[8] = new Polygon3D(v, v[0], v[1], v[3], Main.textures[16], 1,
				1, 6);
		polygons[8].constantI = true;

		for (int i = 0; i < 8; i++) {
			v = new Vector[] {
					put(r * Math.cos(i * theta), r * Math.sin(i * theta), 0.03),
					put(r * Math.cos((i + 1) * theta), r
							* Math.sin((i + 1) * theta), 0.03),
					put(0, 0, 0.05),

			};
			polygons[9 + i] = new Polygon3D(v, v[0], v[1], v[2],
					Main.textures[26], 1, 1, 6);
		}

	}

	// return the 2D boundary of this model
	public Rectangle2D getBoundary2D() {
		return boundary2D;
	}

	// update the model
	public void update() {
		// rockets fired by enemy/player tanks will most likely within the view
		// plane,
		// so they are considered visible all the time
		visible = true;

		// Within its life span, if the rocket hits some hard object such as a
		// rock or a tank,
		// it explodes.
		lifeSpan--;

		// update rocket aura
		if (rocketAura != null) {
			rocketAura.origin.add(direction);
			rocketAura.bottomEnd.add(direction);
			rocketAura.rightEnd.add(direction);

			for (int i = 0; i < rocketAura.vertex3D.length; i++) {
				rocketAura.vertex3D[i].add(direction);
			}
			rocketAura.update();

			rocketAura.myTexture.Texture = rocketAura.myTexture.lightMapData[1 + (GameData
					.getRandom() % 3) * 2];
			Rasterizer.rasterize(rocketAura);

		}

		// find diretion
		if (!isHostile) {
			// if the rocket is fired by player, then set the target location to
			// the centre of the nearest enemy
			tempVector1.set(PlayerTank.bodyCenter);
			tempVector2.set(0, 0, 0.15);
			tempVector2.rotate_XZ(PlayerTank.turretAngle);

			for (int i = 0; i < 20; i++, tempVector1.add(tempVector2)) {
				targetLocation = ObstacleMap.isOccupied3(tempVector1);
				if (targetLocation != null)
					break;
			}

			if (targetLocation == null) {
				tempVector2.scale(1000);
				tempVector1.add(tempVector2);
				targetLocation = tempVector1.myClone();
			}
		} else {
			// if the rocket is fired by enemy, t hen set the target location to
			// the centre of player tank
			targetLocation = PlayerTank.bodyCenter;
		}

		// find direction based on the target location
		int targetAngle = 90 + (int) (180 * Math
				.atan((centre.z - targetLocation.z)
						/ (centre.x - targetLocation.x)) / Math.PI);
		if (targetLocation.x > centre.x && targetAngle <= 180)
			targetAngle += 180;

		angleDelta = targetAngle - angle;

		if (angleDelta > 180) {
			angleDelta -= 360;
		}
		if (angleDelta < -180) {
			angleDelta += 360;
		}

		int angleDeltaAbs = Math.abs(angleDelta);

		if (angleDeltaAbs > 10) {
			angleDelta = angleDelta / angleDeltaAbs;
			angleDelta *= 10;
		}

		angle += angleDelta;

		angleDelta = (angleDelta + 360) % 360;

		// apply rotation to rocket direction
		direction.rotate_XZ(angleDelta);

		// update bundary2D
		boundary2D.update(direction);

		// check whether the rocket emembeded into other objects.
		int position = (int) (boundary2D.xPos * 4)
				+ (129 - (int) (boundary2D.yPos * 4)) * 80;
		if (ObstacleMap.projectileCollideObstacle2(this, position, isHostile)) {
			lifeSpan = -1;
			// generate explosion
			centre.add(direction);
			Explosion theExplosion = new Explosion(centre.x, centre.y,
					centre.z, 1);
			theExplosion.damage = 10;
			Projectiles.register(theExplosion);
			return;
		}

		// send to draw list
		ModelDrawList.register(this);

		// update centre
		centre.add(direction);

		// find centre in camera coordinate
		tempCentre.set(centre);
		tempCentre.y = -1;
		tempCentre.subtract(Camera.position);
		tempCentre.rotate_XZ(Camera.XZ_angle);
		tempCentre.rotate_YZ(Camera.YZ_angle);

		// update 3D boundary
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++)
				boundary[i].vertex3D[j].add(direction);
			boundary[i].update();
		}

		// update polygons
		for (int i = 0; i < polygons.length; i++) {
			// update polygons in world coordinate
			for (int j = 0; j < polygons[i].vertex3D.length; j++) {
				polygons[i].vertex3D[j].add(direction);
				polygons[i].vertex3D[j].subtract(centre);
				polygons[i].vertex3D[j].rotate_XZ(angleDelta);
				polygons[i].vertex3D[j].add(centre);
			}

			polygons[i].findRealNormal();
			polygons[i].findDiffuse();

			// update polygons into camera coordinate
			polygons[i].update();
		}

		// When its life Span counts
		// down to zero, it will explode and cause damage.
		if (lifeSpan < 0) {
			// generate explosion
			Explosion theExplosion = new Explosion(centre.x, centre.y,
					centre.z, 1);
			theExplosion.damage = 10;
			Projectiles.register(theExplosion);
			return;
		}
		if (Main.timer % 2 == 0) {
			centre.subtract(direction);
			
			Projectiles.register(new RocketTail(centre));
			
			centre.add(direction);
		}
	}

	// draw the model
	public void draw() {
		for (int i = 0; i < polygons.length; i++) {
			polygons[i].draw();
		}
	}
	
	
}
