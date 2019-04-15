import java.util.Scanner;

public class ZIPCode {
	private final int[] weight;
	private final int[][] encodeScheme;
	private int[] numZIP;
	private int[][] barCodeZIP;

	/**
	 * Constructor
	 */
	public ZIPCode() {
		this.weight = new int[] { 7, 4, 2, 1, 0 };
		this.encodeScheme = new int[][] { { 1, 1, 0, 0, 0 }, { 0, 0, 0, 1, 1 }, { 0, 0, 1, 0, 1 }, { 0, 0, 1, 1, 0 },
				{ 0, 1, 0, 0, 1 }, { 0, 1, 0, 1, 0 }, { 0, 1, 1, 0, 0 }, { 1, 0, 0, 0, 1 }, { 1, 0, 0, 1, 0 },
				{ 1, 0, 1, 0, 0 } };
		this.numZIP = new int[6];
		this.barCodeZIP = new int[6][5];
	}

	/**
	 * Ask user to Convert ZIP Code to bar code or opposite conversion
	 * 
	 * @return True for convert ZIP to bar code, False for opposite conversion.
	 */
	private boolean getMethod() {
		Scanner scan = new Scanner(System.in);

		System.out.println("Do you want to encode ZIP code to bar code or not?");
		if (scan.next().equals("Yes"))
			return true;
		else
			return false;
	}

	/**
	 * Ask user to type a ZIP Code and convert it to store as an integer array.
	 * Add a check digit at the last position of the integer array.
	 * 
	 * @return ZIP Code and Check digit in a integer array.
	 */
	private int[] getNumZIP() {
		Scanner scan = new Scanner(System.in);

		System.out.print("Please enter your five digit ZIP code: ");
		int temp = scan.nextInt();

		int[] tempZIP = new int[6];
		tempZIP[5] = 0;
		for (int i = 4; i >= 0; i--) {
			tempZIP[i] = temp % 10;
			temp /= 10;
			tempZIP[5] += tempZIP[i];
		}
		tempZIP[5] = 10 - tempZIP[5] % 10;

		return tempZIP;
	}

	/**
	 * Convert a given number to a bar code
	 * 
	 * @param num
	 *            : the number which need to be convert to bar code.
	 * @return a bar code as a String
	 */
	private String getCode(int num) {
		String temp = "";

		for (int i = 0; i < 5; i++)
			if (encodeScheme[num][i] == 1)
				temp += "|";
			else
				temp += ":";

		return temp;
	}

	/**
	 * Convert a ZIP Code which need to be typed be user to a bar code with
	 * check digit
	 */
	public void numToCode() {
		numZIP = getNumZIP();

		System.out.print("|");
		for (int i = 0; i < 6; i++)
			System.out.print(getCode(numZIP[i]));
		System.out.println("|");
	}

	/**
	 * Ask user to type in a bar code. Then convert it to 2D integer array.
	 * 
	 * @return a bar code as a 2D integer
	 */
	private boolean getBarCode() {
		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter your ZIP code as bar code:");
		String temp = scan.next();
		boolean status = true;
		int index = 1;
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 5; j++)
				switch (temp.charAt(index++)) {
				case '|':
					barCodeZIP[i][j] = 1;
					break;
				case ':':
					barCodeZIP[i][j] = 0;
					break;
				default: {
					System.out.println("There is an input format error at index " + (index + "") + ".");
					status = false;
				}
				}
		return status;
	}

	/**
	 * Convert the a bar code to number. If it is mismatch, return -1.
	 * 
	 * @param c
	 *            : the array of numbers that need to be decode
	 * @return the number which has already been decode.
	 */
	private int getCodeNum(int[] c) {
		int num = 0, numOfOne = 0;

		for (int i = 0; i < 5; i++) {
			if (c[i] == 1)
				numOfOne++;
			num += c[i] * weight[i];
		}
		if (num == 11)
			num = 0;

		if (num > 10 || numOfOne != 2)
			num = -1;

		return num;
	}

	/**
	 * Convert ZIP code in integer array to String
	 * 
	 * @param c
	 *            : a array of integer shows the ZIP code.
	 * @return ZIP Code as String
	 */
	private String getZIP(int[] c) {
		int temp = 0;
		for (int i = 0; i < 5; i++)
			temp = temp * 10 + c[i];
		return (temp + "");
	}

	/**
	 * Convert a bar code which need to be typed by user.
	 */
	public void codeToNum() {
		int[] zip = new int[6];

		if (!getBarCode())
			return;

		for (int i = 0; i < 6; i++) {
			zip[i] = getCodeNum(barCodeZIP[i]);
			if (zip[i] == -1) {
				if (i < 5)
					System.out.println("There is a mismatch at Zip code index " + (i + "") + ".");
				else
					System.out.println("There is a mismatch at check digit.");
				return;
			}
		}

		if ((zip[0] + zip[1] + zip[2] + zip[3] + zip[4] + zip[5]) % 10 == 0)
			System.out.println(getZIP(zip));
		else
			System.out.println("The check digit cannot match the Zip Code.");
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ZIPCode zip = new ZIPCode();
		if (zip.getMethod())
			zip.numToCode();
		else
			zip.codeToNum();
	}
}
