package TortoiseHareRace;

public class Hare extends TAndH{
	
	/**
	 * move hare
	 * abstract from TAndH class
	 */
	@Override
	void move() {
		// TODO Auto-generated method stub
		int action = super.getR().nextInt(100);
		
		if (action < 20)
			super.setP(getP());
		else if (action < 40)
			super.setP(getP() + 8);
		else if (action < 50)
			if (checkStartPoint(10))
				super.setP(getP() - 10);
			else
				move();
		else if (action < 75)
			super.setP(getP() + 1);
		else
			if (checkStartPoint(1))
				super.setP(getP() - 1);
			else
				move();
	}
	
	/**
	 * check if hare is out of border
	 * @param p : squares to move back
	 * @return true for in border, false for out of border
	 */
	private boolean checkStartPoint(int p) {
		return !((getP() - p < 0) || 
				((getP() > 160) && (getP() - p < 160)) || 
				((getP() > 320) && (getP() - p < 320)) || 
				((getP() > 480) && (getP() - p < 480)));
	}
}
