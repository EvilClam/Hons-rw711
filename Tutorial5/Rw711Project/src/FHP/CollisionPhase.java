package FHP;

import cellularAutomaton.Cell;
import cellularAutomaton.Phase;

public class CollisionPhase implements Phase<Partical[]> {

	boolean hasCahnged = false;
	@Override
	public Partical[] executePhase(Cell<Partical[]>[] neighbours,
			Cell<Partical[]> current) {
		Partical[] tmp = ((FHPCell) current).getValue();
		Partical[] returnNew = new Partical[tmp.length];
		int alive = 0;
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i] != null) {
				returnNew[i] = tmp[i].clone();
				alive++;
			}
		}

		int first = (int) (Math.random() * 2);

		// head on collision
		if (returnNew[0] != null && returnNew[2] != null
				&& returnNew[4] != null && alive == 3) {

			// Three way collision
		} else if (returnNew[1] != null && returnNew[3] != null
				&& returnNew[5] != null && alive == 3) {

		}

		return returnNew;
	}

	/**
	 * 
	 * @param returnNew
	 * @param alive
	 */
	public void case1(Partical[] returnNew, int alive) {
		if (hasCahnged) {
			return;
		}
		
		int first = (int) (Math.random() * 2);
		if (returnNew[0] != null && returnNew[3] != null && alive == 2) {
			if (first == 0) {
				returnNew[5] = returnNew[0];
				returnNew[2] = returnNew[3];
			} else {
				returnNew[1] = returnNew[0];
				returnNew[4] = returnNew[3];
			}
			returnNew[0] = null;
			returnNew[3] = null;
			hasCahnged = true;
			// head on collision
		} else if (returnNew[1] != null && returnNew[4] != null && alive == 2) {
			if (first == 0) {
				returnNew[0] = returnNew[1];
				returnNew[3] = returnNew[4];
			} else {
				returnNew[2] = returnNew[1];
				returnNew[5] = returnNew[4];
			}
			returnNew[1] = null;
			returnNew[4] = null;
			hasCahnged = true;
			// head on collision
		} else if (returnNew[2] != null && returnNew[5] != null && alive == 2) {
			if (first == 0) {
				returnNew[3] = returnNew[2];
				returnNew[0] = returnNew[5];
			} else {
				returnNew[1] = returnNew[2];
				returnNew[4] = returnNew[5];
			}
			returnNew[2] = null;
			returnNew[5] = null;
			hasCahnged = true;
		}
		
	}

	/**
	 * 
	 * @param returnNew
	 * @param alive
	 */
	public void case2(Partical[] returnNew, int alive) {
		if (hasCahnged) {
			return;
		}
		// Three way collision
		if (returnNew[0] != null && returnNew[2] != null
				&& returnNew[4] != null && alive == 3) {
			returnNew[3] = returnNew[0];
			returnNew[5] = returnNew[2];
			returnNew[1] = returnNew[4];
			returnNew[0] = null;
			returnNew[2] = null;
			returnNew[4] = null;
			hasCahnged = true;
			// Three way collision
		} else if (returnNew[1] != null && returnNew[3] != null
				&& returnNew[5] != null && alive == 3) {
			returnNew[0] = returnNew[3];
			returnNew[2] = returnNew[5];
			returnNew[4] = returnNew[1];
			returnNew[1] = null;
			returnNew[3] = null;
			returnNew[5] = null;
			hasCahnged = true;
		}
	}

	/**
	 * 
	 * @param returnNew
	 * @param alive
	 */
	public void case3(Partical[] returnNew, int alive) {
		if (hasCahnged) {
			return;
		}
		int[] offset = {2,4};
		int first = (int) (Math.random() * 2);
		Partical[] tmp = new Partical[returnNew.length];
		if (returnNew[0] != null && returnNew[3] != null && returnNew[1] != null && returnNew[4] != null && alive == 4) {
			tmp[(0 + offset[first])%6] = returnNew[0];
			tmp[(3 + offset[first])%6] = returnNew[3];
			tmp[(1 + offset[first])%6] = returnNew[1];
			tmp[(4 + offset[first])%6] = returnNew[4];
			hasCahnged = true;
		} else if (returnNew[2] != null && returnNew[5] != null && returnNew[1] != null && returnNew[4] != null && alive == 4) {
			tmp[(2 + offset[first])%6] = returnNew[3];
			tmp[(5 + offset[first])%6] = returnNew[5];
			tmp[(1 + offset[first])%6] = returnNew[1];
			tmp[(4 + offset[first])%6] = returnNew[4];
			hasCahnged = true;
		} else if (returnNew[0] != null && returnNew[3] != null && returnNew[2] != null && returnNew[5] != null && alive == 4) {
			tmp[(0 + offset[first])%6] = returnNew[0];
			tmp[(3 + offset[first])%6] = returnNew[3];
			tmp[(2 + offset[first])%6] = returnNew[2];
			tmp[(5 + offset[first])%6] = returnNew[5];
			hasCahnged = true;
		}
		for (int i = 0 ; i < returnNew.length && hasCahnged; i++) { 
			returnNew[i] = tmp[i];
		}
		
	}

	/**
	 * 
	 * @param returnNew
	 * @param alive
	 */
	public void case4(Partical[] returnNew, int alive) {
		if (hasCahnged) {
			return;
		}
		int direction1 = 0;
		int direction2 = 0;
		int directionOpen1 = 0;
		int directionOpen2 = 0;
		boolean hasAlone = false;

		if (alive == 3) {
			
			//Find pair
			for (int i = 0 ; i < 3 ; i++) {
				if (returnNew[i] != null && returnNew[i + 3] != null) {
					if (direction1 == 0 && direction2 == 0) {
						direction1 = i;
						direction2 = i + 3;
					}
				} else if (returnNew[i] == null && returnNew[i + 3] == null) {
					if (directionOpen1 == 0 && directionOpen2 == 0) {
						directionOpen1 = i;
						directionOpen2 = i + 3;
					}
				}
			}
			
			if ((direction1 == 0 && direction2 == 0) || (directionOpen1 == 0 && directionOpen2 == 0)) {
				return;
			}
			
			//Find single
			for (int i = 0 ; i < 6 ; i++) {
				if (returnNew[i] != null && returnNew[(i + 3)%6] == null) {
					if (!hasAlone) {
						hasAlone = true;
					} else {
						return;
					}		
				}
			}
			
			if (!hasAlone) {
				return;
			}
			
			hasCahnged = true;
			returnNew[directionOpen1] = returnNew[direction1];
			returnNew[directionOpen2] = returnNew[direction2];
			returnNew[direction1] = null;
			returnNew[direction2] = null;
		}
	}

	/**
	 * 
	 * @param returnNew
	 * @param alive
	 */
	public void case5a(Partical[] returnNew, int alive) {
		if (hasCahnged) {
			return;
		}
		int hasAlone = 7;
		for (int i = 0 ; i < 6 ; i++) {
			if (returnNew[i] != null && returnNew[(i + 3)%6] == null && returnNew[i].getVelocity() == 0 && returnNew[(i + 1) % 6] != null) {
				if (hasAlone == 7) {
					hasAlone = i;
				} else {
					return;
				}		
			}
		}
		
		if (hasAlone == 7) {
			return;
		}
		
		returnNew[hasAlone].
		
	}
	
	/**
	 * 
	 * @param returnNew
	 * @param alive
	 */
	public void case5b(Partical[] returnNew, int alive) {
		if (hasCahnged) {
			return;
		}
		int hasAlone = 7;
		for (int i = 0 ; i < 6 ; i++) {
			if (returnNew[i] != null && returnNew[(i + 3)%6] == null && returnNew[i].getVelocity() == 0 ) {
				if (hasAlone == 7) {
					hasAlone = i;
				} else {
					hasCahnged = false;
					return;
				}		
			}
		}
		
	}

}
