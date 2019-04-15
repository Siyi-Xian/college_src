import java.util.Arrays;

public class Matrix {
	private double[][] matrix;
	private final int NUMROW;
	private final int NUMCOL;

	// Constructor
	// Initialize NUMROW, NUMCOL, and matrix
	public Matrix(double[][] m) {
		this.NUMROW = m.length;
		this.NUMCOL = m[0].length;
		this.matrix = m;
	}

	// Returns a string value of the matrix.
	public String toString() {
		String str_matrix;
		str_matrix = (NUMROW + "") + "x" + (NUMCOL + "") + " matrix\n";

		for (int i = 0; i < NUMROW; i++) {
			str_matrix += "[";
			for (int j = 0; j < NUMCOL - 1; j++)
				str_matrix += (matrix[i][j] + "") + ", ";
			str_matrix += (matrix[i][NUMCOL - 1] + "") + "]\n";
		}

		return str_matrix;
	}

	// Returns a new matrix which is the m row.
	public Matrix getRowMatrix(int m) {
		double[][] matrix_row_d = new double[1][NUMCOL];
		matrix_row_d[0] = matrix[m];

		Matrix matrix_row = new Matrix(matrix_row_d);
		System.out.println(matrix_row.toString());

		return matrix_row;
	}

	// Returns a new matrix which is the m column.
	public Matrix getColMatrix(int m) {
		double[][] matrix_col_d = new double[1][NUMROW];
		for (int i = 0; i < NUMROW; i++)
			matrix_col_d[0][i] = matrix[i][0];

		Matrix matrix_col = new Matrix(matrix_col_d);
		System.out.println(matrix_col.toString());

		return matrix_col;
	}

	// Transpose the given matrix
	public Matrix transpose() {
		double[][] matrix_trans_d = new double[NUMCOL][NUMROW];
		for (int i = 0; i < NUMCOL; i++)
			for (int j = 0; j < NUMROW; j++)
				matrix_trans_d[i][j] = matrix[j][i];

		Matrix matrix_trans = new Matrix(matrix_trans_d);
		return matrix_trans;
	}

	// The upperDiagonal() should return a new matrix where all values above the
	// main diagonal are 0.
	public Matrix upperDiagonal() {
		double[][] matrix_upper_d = new double[NUMROW][NUMCOL];
		for (int i = 0; i < NUMROW; i++)
			for (int j = 0; j < NUMCOL; j++)
				if (i >= j)
					matrix_upper_d[i][j] = matrix[i][j];
				else
					matrix_upper_d[i][j] = 0;

		Matrix matrix_upper = new Matrix(matrix_upper_d);
		return matrix_upper;
	}

	// The lowerDiagonal() should return a new matrix where all values below the
	// main diagonal are 0.
	public Matrix lowerDiagonal() {
		double[][] matrix_lower_d = new double[NUMROW][NUMCOL];
		for (int i = 0; i < NUMROW; i++)
			for (int j = 0; j < NUMCOL; j++)
				if (i <= j)
					matrix_lower_d[i][j] = matrix[i][j];
				else
					matrix_lower_d[i][j] = 0;

		Matrix matrix_lower = new Matrix(matrix_lower_d);
		return matrix_lower;
	}

	// A diagonal matrix is a matrix were the all values are 0 except the main
	// diagonal.
	public Matrix diagonalMatrix() {
		double[][] matrix_diag_d = matrix;
		Matrix matrix_diag = new Matrix(matrix_diag_d);

		matrix_diag = matrix_diag.lowerDiagonal();
		matrix_diag = matrix_diag.upperDiagonal();

		return matrix_diag;
	}

	// Test client
	public static void main(String[] args) {
		double[][] temp_matrix = new double[][] { { 3, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 1, 1 }, { 1, 1, 1, 1 } };

		/*Matrix mat = new Matrix(temp_matrix);

		Matrix mat_row = mat.getRowMatrix(0);

		Matrix mat_col = mat.getColMatrix(0);

		Matrix mat_trans = mat.transpose();
		System.out.println(mat_trans.toString());

		Matrix mat_upper = mat.upperDiagonal();
		System.out.println(mat_upper.toString());

		Matrix mat_lower = mat.lowerDiagonal();
		System.out.println(mat_lower.toString());

		Matrix mat_diag = mat.diagonalMatrix();
		System.out.println(mat_diag.toString());*/

		double[][] temp_matrix1 = new double[][] { { 3, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 1, 1 }, { 1, 1, 1, 1 } };
		
		System.out.println(Arrays.deepEquals(temp_matrix, temp_matrix1));
	}

}