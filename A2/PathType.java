/*
 *  ============================================================================================
 *  enum which defines the type of paths in A1
 *  YOUR UPI: zzou832
 *  ============================================================================================
 */
import java.util.*;
enum PathType { BOUNCE, BOUNDARY;
	private static final Random rand = new Random(30);
	private static final int SIZE = values().length;
	/** returns the path type
	*	@param index	the index value */
	public static final PathType getPathType(int index) { return values()[index]; }
	/** returns the next path type  */
	public PathType next() {
		return values()[(ordinal() + 1) % values().length];
	}
	/** returns a random path type  */
	public static PathType getRandomPathType()  {
	    return values()[rand.nextInt(SIZE)];
  }
}

enum Direction { DOWN,RIGHT,UP,LEFT;
	private static final int SIZE = values().length;
	/** returns the next direction  */
	public Direction next() {
		return values()[(ordinal() + 1) % values().length];
	}
}