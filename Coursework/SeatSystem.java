
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;

public class SeatSystem {
	// An array of seats is created to store the seat data from the file
	private static final Seat[] seats = new Seat[18];
	// Scanner for input is added
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		// application starts by loading seats from the file into local variables
		loadSeats();
		// Menu is build using a switch and only quits when user types in 'Q'
		String choice = "";
		do {
			System.out.println("\n-- PICK ACTION --");
			System.out.println("1 - Reserve a new seat");
			System.out.println("2 - Cancel seat");
			System.out.println("3 - View Booked Seats");
			System.out.println("Q - Quit");
			System.out.print("Pick : ");
			choice = input.next().toUpperCase();
			switch (choice) {
			case "1": {
				seatSelection();
				break;
			}
			case "2": {
				seatCancellation();
				break;
			}
			case "3": {
				seatView();
				break;
			}
			}
		} while (!choice.equals("Q"));
		// upon exit, file is saved and input scanner closed
		saveFile();
		input.close();
	}

	public static void seatSelection() {
		// necessary variables are assigned at the beginning
		String choice = "";
		boolean validAnswer = false;
		float userPrice = 0;
		int i = 0;
		System.out.println("\n-- WELCOME TO OUR NEW SEAT RESERVING SYSTEM --");
		System.out.println(
				"This new system has been designed to help you find the perfect seat for you, simply answer a few questions!");
		// this section is looped so the user can choose to answer the questions again
		// without returning to Main Menu
		do {
			validAnswer = false;
			System.out.println(
					"Do you wish for your seat to be Standard, or First Class?\n1 - Standard\n2 - First Class");
			// User is given a choice between two options, this part will loop until a valid
			// answer is given
			while (!validAnswer) {
				choice = input.next().toUpperCase();
				switch (choice) {
				case "1": {
					for (i = 0; i < seats.length; i++) {
						if (seats[i].seatClass.equals("STD")) {
							// Value likeness determines the amount of conditions that has been met by the
							// seat to match the user's needs
							seats[i].addLikeness();
						}
					}
					// When a valid answer is given, loop will end and user moves on to the next
					// question
					validAnswer = true;
					break;
				}
				case "2": {
					for (i = 0; i < seats.length; i++) {
						if (seats[i].seatClass.equals("1ST"))
							seats[i].userLikeness++;
					}
					validAnswer = true;
					break;
				}
				}
			}
			System.out.println("Do you wish for your seat to be by a window?\n1 - Yes\n2 - No");
			// boolean valid answer is reset back to false for the next question
			// Not much is different from the previous section, therefore no further
			// comments are needed for following sections
			validAnswer = false;
			while (!validAnswer) {
				choice = input.next().toUpperCase();
				switch (choice) {
				case "1": {
					for (i = 0; i < seats.length; i++) {
						if (seats[i].isWindow) {
							seats[i].addLikeness();
						}
					}
					validAnswer = true;
					break;
				}
				case "2": {
					for (i = 0; i < seats.length; i++) {
						if (!seats[i].isWindow)
							seats[i].userLikeness++;
					}
					validAnswer = true;
					break;
				}

				}
			}
			System.out.println("Do you wish for your seat to be by the aisle?\n1 - Yes\n2 - No");
			validAnswer = false;
			while (!validAnswer) {
				choice = input.next().toUpperCase();
				switch (choice) {
				case "1": {
					for (i = 0; i < seats.length; i++) {
						if (seats[i].isAisle) {
							seats[i].addLikeness();
						}
					}
					validAnswer = true;
					break;
				}
				case "2": {
					for (i = 0; i < seats.length; i++) {
						if (!seats[i].isAisle)
							seats[i].userLikeness++;
					}
					validAnswer = true;
					break;
				}
				}
			}
			System.out.println("Do you wish for your seat to have a table?\n1 - Yes\n2 - No");
			validAnswer = false;
			while (!validAnswer) {
				choice = input.next().toUpperCase();
				switch (choice) {
				case "1": {
					for (i = 0; i < seats.length; i++) {
						if (seats[i].isTable) {
							seats[i].addLikeness();
						}
					}
					validAnswer = true;
					break;
				}
				case "2": {
					for (i = 0; i < seats.length; i++) {
						if (!seats[i].isTable)
							seats[i].userLikeness++;
					}
					validAnswer = true;
					break;
				}
				}
			}
			// The system will ask the user about their maximum preferred price, and display
			// seats with price lesser or equal to that provided
			System.out.println("What is your preferred max price?\nEnter Price:\n");
			validAnswer = false;
			while (!validAnswer) {
				choice = input.next().toUpperCase();
				// The system will determine if the answer provided by the user can be safely
				// converted to float
				try {
					Float.parseFloat(choice);
					validAnswer = true;
				} catch (NumberFormatException e) {
					System.out.println("Please enter a valid number!\n");
				}
			}
			// user will only move to this part after providing a float value
			userPrice = Float.parseFloat(choice);
			for (i = 0; i < seats.length; i++) {
				if (seats[i].seatPrice <= userPrice) {
					seats[i].addLikeness();
				}
			}
			// Now we check if any of the seats meets all the conditions set by user
			boolean perfectMatch = false;
			for (i = 0; i < seats.length; i++) {
				// 5 is the maximum amount of user likeness points that the seat could score by
				// meeting all conditions
				if (seats[i].userLikeness == 5 && seats[i].eMail.equals("free")) {
					if (!perfectMatch) {
						System.out.println("Congratulations, looks like we have found a perfect match!");
						perfectMatch = true;
					}
					System.out.println(seats[i].toString());
				}
			}
			if (!perfectMatch) {
				int threshold = 4;
				boolean found = false;
				System.out.println(
						"Sorry, we couldn't find any seat that meets your requirements! Here are a few seats that that partially match your requirements:");
				while (!found) {
					// if perfect match has not been found, system will check if any seats meet 4 of
					// 5 possible conditions. If that is not the case, it will check for 3 out of 5
					// and so on, decreasing the threshold value by one for each unsuccessful
					// attempt
					for (i = 0; i < seats.length; i++) {
						if (seats[i].userLikeness == threshold && seats[i].eMail.equals("free")) {
							found = true;
							System.out.println(seats[i].toString());
						}
					}
					threshold--;
				}
			}
			// likeness will reset as soon as it is no longer needed
			for (i = 0; i < seats.length; i++) {
				seats[i].resetLikeness();
			}
			// user will be given a choice to reserve a seat
			System.out.println(
					"Do you wish to reserve one of the displayed seats?\n1 - Yes\n2 - Search Again\nQ - Return to Main Manu");
			validAnswer = false;
			// loop will continue until a valid answer is given, or 'Q' is chosen to quit
			while (!validAnswer && !choice.equals("Q")) {
				// this input is for deciding if user wants to reserve a seat or not
				choice = input.next().toUpperCase();
				switch (choice) {
				case "1": {
					// if option 1 is chosen, user needs to type in the number of the seat they wish
					// to reserve
					System.out.println("Please type in the number of the seat that you wish to reserve:");
					boolean validChoice = false;
					while (!validChoice) {
						String userSeatChoice = input.next().toUpperCase();
						for (i = 0; i < seats.length; i++) {
							if (seats[i].seatNum.equals(userSeatChoice)) {
								// since user can input any seat they wish, even outside of the displayed ones,
								// the system once again checks if the mail for chosen seat is free, since
								// occupied seats would not be displayed earlier
								if (seats[i].eMail.equals("free")) {
									System.out.println(
											"Please enter the eMail address for which you wish to make the reservation:");
									// seat will be reserved for the mail given by the user
									String userMail = input.next();
									seats[i].reserve(userMail);
									// file is saved immediately after reservation, there is no need to manually
									// close the application
									saveFile();
									System.out.println("Seat has been reserved succesfully!");
									// both conditions are set to exit the loop completely and return to main menu
									validChoice = true;
									choice = "Q";
								} else {
									System.out.println(
											"Sorry, this seat is already occupied! Please select a different seat.");
								}
							}
						}
					}

					validAnswer = true;
					break;
				}
				case "2": {
					// if user chooses to select a seat again, loop will return to the first
					// question
					validAnswer = true;
					break;
				}
				}
			}
		} while (!choice.equals("Q"));
	}

	public static void seatCancellation() {
		String userMail = "";
		String choice = "";
		int i = 0;
		boolean mailBooked = false;
		// to protect other user's data, first the user must enter their E-Mail and will
		// only be allowed to see seats that have been booked for this mail before
		System.out.println("Please enter your E-Mail to display the list of your booked seats:");
		do {
			userMail = input.next();
			// this loop both checks if any seats are booked on the mail address and
			// displays the numbers of all seats booked on this address
			for (i = 0; i < seats.length; i++) {
				if (seats[i].eMail.equals(userMail)) {
					mailBooked = true;
					System.out.println(seats[i].seatNum);

				}
			}
			boolean validAnswer = false;
			if (mailBooked) {
				System.out.println("Above is the list of the seats that are currently booked on your E-Mail address.");
				while (!validAnswer) {
					System.out.println(
							"Please type in the number of the seat that you wish to cancel, or 'Q' to return to Main Menu");
					choice = input.next().toUpperCase();
					for (i = 0; i < seats.length; i++) {
						// system checks if the seat has the same number as the one provided by the user
						// and if the seat is actually reserved for the E-Mail address of the user that
						// is trying to cancel it
						if (seats[i].eMail.equals(userMail) && seats[i].seatNum.equals(choice)) {
							validAnswer = true;
							seats[i].cancel();
							// file is saved
							saveFile();
							System.out.println("Seat cancelled successfully!");
						}
					}
					if (choice.equals("Q")) {
						validAnswer = true;

					}
					if (!validAnswer) {
						System.out.println("There is no seat with that number reserved for this E-Mail address!");

					}

				}
			} else {
				// if there is no seats reserved for the address, user can try again or return
				System.out.println(
						"It appears there are no seats reserved for this E-Mail address. Do you wish to try again?/n1 - Try Again/n2 - Return to Main menu");
				while (!validAnswer) {
					choice = input.next().toUpperCase();
					switch (choice) {
					case "1": {
						validAnswer = true;
						break;
					}
					case "2": {

						validAnswer = true;
						choice = "Q";
						break;
					}
					}
				}
			}

		} while (!choice.equals("Q"));
	}

	public static void seatView() {
		String choice = "";
		int i = 0;

		// this function is a modified version of seatCancellation with it's canceling
		// components removed, so it only displays seats based on the input E-Mail
		do {
			System.out.println(
					"Please enter your E-Mail to display the list of your booked seats, or 'Q' to return to Main Menu");
			choice = input.next();

			for (i = 0; i < seats.length; i++) {
				if (seats[i].eMail.equals(choice)) {
					System.out.println(seats[i].seatNum);

				}
			}
			// since i'm using same input for quitting and E-Mail, the string is changed
			// into upper case only once it is determined that the user is trying to quit
			if (choice.equals("q"))
				choice.toUpperCase();
		} while (!choice.equals("Q"));
	}

	public static void saveFile() {
		try {
			// file writer is created to write the data
			FileWriter myWriter = new FileWriter("seats.txt");
			int i = 0;
			for (i = 0; i < seats.length; i++) {
				// all the data is saved to the file in the same structure as it was loaded
				myWriter.write(
						seats[i].seatNum + " " + seats[i].seatClass + " " + seats[i].isWindow + " " + seats[i].isAisle
								+ " " + seats[i].isTable + " " + seats[i].seatPrice + " " + seats[i].eMail + "\n");
			}
			// file writer is closed
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	private static void loadSeats() throws FileNotFoundException {
		Scanner read = new Scanner(new FileReader("seats.txt"));
		int index = 0;
		// reader reads all the data and assigns values to local variables in the seat
		// class
		while (read.hasNext()) {
			String seatNumTemp = read.next();
			String seatClassTemp = read.next();
			boolean isWindowTemp = read.nextBoolean();
			boolean isAisleTemp = read.nextBoolean();
			boolean isTableTemp = read.nextBoolean();
			float seatPriceTemp = read.nextFloat();
			String eMailTemp = read.next();

			seats[index] = new Seat(seatNumTemp, seatClassTemp, isWindowTemp, isAisleTemp, isTableTemp, seatPriceTemp,
					eMailTemp, 0);
			index++;
		}
		// read is closed
		read.close();
	}
}
