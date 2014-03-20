package FHP;

public class Partical {
	/**
	 * The velocity of the current partical.
	 */
	private short velocity ;
	
	/**
	 * The mass of the current partical.
	 */
	private float mass;
	
	/**
	 * Initializes velocity to 5 m/s and mass to 1.
	 */
	public Partical() {
		velocity = 5;
		mass = 1f;
	}
	
	/**
	 * Initializes the velocity of the current partical to the given values.
	 * @param velo Velocity of the new partical. 
	 * @param mas Mass of the new partical.
	 */
	public Partical(short velo, float mas) {
		velocity = velo;
		mass = mas;
	}
	
	/**
	 * Returns the velocity of the partical.
	 * @return Velocity of the partical.
	 */
	public short getVelocity() {
		return velocity;
	}
	
	/**
	 * Returns the mass of the current partical.
	 * @return The mass of the current partical.
	 */
	public float getMass() {
		return mass;
	}
	
	@Override
	public Partical clone() {
		return new Partical(getVelocity(), getMass());
	}
}
