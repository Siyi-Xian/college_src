////////////////////////////////////////////////////////////////////////////////////
//
//  H212 Fall 16
//  Homework 3 
//  
//  Due: 9/24/16 11:59 PM
//
//  Author  Siyi Xian    siyixian
//  Last Edited:  22 February 2017 
//               
//////////////////////////////////////////////////////////////////////////////////
/*
* once all the bugs in the program are fixed the program should generate output similar to this:
* Student0 91
* Student8 83
* Student17 79
* Student3 76
* Student5 76
* Student12 75
* Student4 74
* Student7 72
* Student11 71
* Student6 62
* Student1 59
* Student2 54
* Student13 45
* Student18 41
* Student16 38
* Student9 36
* Student10 24
* Student15 18
* Student14 6
* Student19 1
*/
public class Student {

	public String username;
	public int score;

	public static Student[] students = new Student[20]; // We need to declare
														// this static variety
														// after defining it

	// Call the constructor to create a new student object
	public Student(String _username) {
		username = _username;
	}

	// sort students by score in descending order
	public static void sort() {

		// Stores number of switches
		int switches = 1;

		// sort the array
		while (switches <= students.length) { // We need to give this loop a
												// limited and right run times

			for (int i = 0; i < students.length - 1; i++) {
				// if score of ith student is less than score of i+1th student
				// switch
				if (students[i].score < students[i + 1].score) {

					// swap students in array
					Student temp; // When we are swap two variety,
					temp = students[i]; // we need to set a new object to
										// temporally store one of the value.
					students[i] = students[i + 1];
					students[i + 1] = temp;
					// increment switches
				}
			}
			switches++; // Variety which to control run time of this loop
		}
	}

	// Allocates the student array with a username and a randomly generated
	// score
	public static void allocateStudentArray() {

		String username = "Student";

		// for i less than length of student array
		for (int i = 0; i < students.length; i++) {

			// populate student array
			int score = (int) (Math.random() * 100);// generate a random number
													// between 0-100 // We need
													// to convert this value to
													// integer after times 100,
			students[i] = new Student(username + i);// initialize with username
													// // or the value will
													// always be 0

			// assign score
			students[i].score = score;

		}
	}

	public static void main(String args[]) {

		allocateStudentArray();
		sort();

		// print output
		for (int i = 0; i < students.length; i++) {
			System.out.println(students[i].username + " " + students[i].score);
		}

	}

}
