package de.monx.aoc19.daily_tasks.t18;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.monx.aoc19.helper.Vec2;
import de.monx.aoc19.helper.Vec3;
import lombok.Data;

@Data
public class Attempt {
	final static Vec2[] _DIRS_ADDER = { //
			new Vec2(1, 1), // down right
			new Vec2(-1, 1), // up right
			new Vec2(-1, -1), // up left
			new Vec2(1, -1) // down left
	};
	private int steps = 0;
	private Vec3 position = null;
	private Set<Character> keysCollected = new HashSet<>();
	private Vec2[] positions = new Vec2[4];

	public Attempt(Vec3 position) {
		this.position = position;
	}

	public Attempt(Vec3 position, Set<Character> keysCollected) {
		this.position = position;
		this.keysCollected = keysCollected;
	}

	public Attempt(Vec2[] positions) {
		this.positions = positions;
	}

	public Attempt(Vec2[] positions, Set<Character> keysCollected, int steps) {
		this.positions = positions;
		this.keysCollected = keysCollected;
		this.steps = steps;
	}
	
	public void collectKeyP2(char c, Vec3 v, int idx) {
		keysCollected.add(c);
		positions[idx] = v.toVec2();
		steps = v.z;
	}
	
	public static Attempt initP2(Vec3 position) {
		Vec2[] positions = new Vec2[4];
		for (int i = 0; i < positions.length; i++) {
			positions[i] = position.toVec2().add(_DIRS_ADDER[i]);
		}
		return new Attempt(positions);
	}

	@Override
	public Attempt clone() {
		if(position != null) {
			return new Attempt(position.clone(), new HashSet<>(keysCollected));
		} else {
			return new Attempt(positions.clone(), new HashSet<>(keysCollected), steps);
		}
	}

	public void addKey(char key) {
		keysCollected.add(key);
	}

	public String keysStr() {
		char[] arr = new char[keysCollected.size()];
		int i = 0;
		for (char c : keysCollected) {
			arr[i++] = c;
		}
		Arrays.sort(arr);
		if (position != null) {
			return new String(arr) + position.toVec2().hashCode();
		} else {
			String str = "";
			for (Vec2 v : positions) {
				str += v.hashCode() + ",";
			}
			return new String(arr) + str;
		}
	}

	int getStepCount() {
		if(position != null) {
			return position.z;
		} else {
			return steps;
		}
	}

	public boolean hasKey(char c) {
		return keysCollected.contains(c);
	}

	public boolean isGateOpen(char c) {
		return keysCollected.contains(T18.getKeyOfGate(c));
	}

	// -1 if not comparable
	// 0 if equal
	// 1 if this is better
	// 2 if other is better
	public int compare(Attempt other) {
		if(position != null) {
			return compareP1(other);
		} else {
			return compareP2(other);
		}
	}

	private int compareP2(Attempt other) {
		
		if (arePositionsEqual(other)) {
			if (other.getSteps() < steps) {// other is faster
				if (other.keysCollected.size() >= keysCollected.size() // other has more or the same amount of keys
						&& this.isSubSetOf(other.getKeysCollected())) { // and other contains all keys this has
					return 2; // other is more efficient
				} // else default -> can't be compared = -1
			} else if (other.position.z > position.z) {// this is faster
				if (other.keysCollected.size() <= keysCollected.size() // has more or the same amount of keys
						&& other.isSubSetOf(keysCollected)) { // and has all keys, the other has
					return 1; // then this is more efficient
				} // else default -> can't be compared = -1
			} else { // if both are equally fast
				if (keysCollected.size() == other.getKeysCollected().size() && other.isSubSetOf(keysCollected)) {
					return 0; // both are equal
				}
			}
		}
		return -1;
	}
	
	boolean arePositionsEqual(Attempt other) {
		for(int i = 0; i < positions.length; i++) {
			if(!other.getPositions()[i].equals(positions[i])) {
				return false;
			}
		}
		return true;
	}
	
	private int compareP1(Attempt other) {
		if (position.toVec2().equals(other.position.toVec2())) {
			if (other.position.z < position.z) {// other is faster
				if (other.keysCollected.size() >= keysCollected.size() // other has more or the same amount of keys
						&& this.isSubSetOf(other.getKeysCollected())) { // and other contains all keys this has
					return 2; // other is more efficient
				} // else default -> can't be compared = -1
			} else if (other.position.z > position.z) {// this is faster
				if (other.keysCollected.size() <= keysCollected.size() // has more or the same amount of keys
						&& other.isSubSetOf(keysCollected)) { // and has all keys, the other has
					return 1; // then this is more efficient
				} // else default -> can't be compared = -1
			} else { // if both are equally fast
				if (keysCollected.size() == other.getKeysCollected().size() && other.isSubSetOf(keysCollected)) {
					return 0; // both are equal
				}
//				if (other.isSubSetOf(keysCollected)) {
//					return 2; // other collected more keys, therefore other is better
//				} else if (isSubSetOf(other.getKeysCollected())) {
//					return 1; // this is better
//				}
			}
		}
		return -1;
	}

	public boolean isSubSetOf(Set<Character> keys) {
		for (char c : keysCollected) {
			if (!keys.contains(c)) {
				return false;
			}
		}
		return true;
	}

}
