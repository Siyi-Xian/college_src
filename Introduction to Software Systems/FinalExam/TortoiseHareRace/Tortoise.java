package TortoiseHareRace;

public class Tortoise extends TAndH{
	
	/**
	 * move tortoise
	 * abstract from TAndH class
	 */
	@Override
	void move() {
		// TODO Auto-generated method stub
		int action = super.getR().nextInt(100);
		
		if (action < 45)
			super.setP(getP() + 3);
		else if (action < 70)
			if (checkStartPoint(6))
				super.setP(getP() - 6);
			else
				move();
		else
			super.setP(getP() + 1);
	}
	
	/**
	 * check if tortoise is out of border
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
