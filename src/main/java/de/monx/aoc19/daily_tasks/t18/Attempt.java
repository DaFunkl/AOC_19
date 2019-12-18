package de.monx.aoc19.daily_tasks.t18;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.monx.aoc19.helper.Vec3;
import lombok.Data;

@Data
public class Attempt {
	private Vec3 position = new Vec3();
	private Set<Character> keysCollected = new HashSet<>();

	public Attempt(Vec3 position) {
		this.position = position;
	}

	public Attempt(Vec3 position, Set<Character> keysCollected) {
		this.position = position;
		this.keysCollected = keysCollected;
	}

	@Override
	public Attempt clone() {
		return new Attempt(position.clone(), new HashSet<>(keysCollected));
	}

	public void addKey(char key) {
		keysCollected.add(key);
	}
	
	public String keysStr() {
		char[] arr = new char[keysCollected.size()];
		int i = 0;
		for(char c : keysCollected) {
			arr[i++] = c;
		}
		Arrays.sort(arr);
		return new String(arr)+position.toVec2().hashCode();
	}
	
//	public void setNextPostAndCollectKey(Vec3 nextPos, char key) {
//		position = nextPos;
//		keysCollected.add(key);
//	}

	int getStepCount() {
		return position.z;
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
