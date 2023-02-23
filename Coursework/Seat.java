
public class Seat {
	String seatNum;
	String seatClass;
	boolean isWindow;
	boolean isAisle;
	boolean isTable;
	float seatPrice;
	String eMail;
	//user likeness is a value that determines how many of the search criteria is met when booking the seat.This value is not saved back into the file and is reset shortly after use
	int userLikeness;
	public Seat(String seatNum, String seatClass, boolean isWindow, boolean isAisle, boolean isTable,
			float seatPrice, String eMail, int userLikeness) {
		this.seatNum = seatNum;
		this.seatClass = seatClass;
		this.isWindow = isWindow;
		this.isAisle = isAisle;
		this.isTable = isTable;
		this.seatPrice = seatPrice;
		this.eMail = eMail;
		this.userLikeness = userLikeness;
}
	public String toString() {
		//data from the seat is converted into a convenient string, along with user likeness to show how many of the conditions are actually met
		String result = seatNum + " "+ seatClass + " By the Window: " + isWindow + ", by the aisle: " + isAisle + ", has a table: " + isTable + ". Price: " + seatPrice +"£. User requirements met: " + userLikeness + "/5.";
		return result;
	}
	public void reserve(String eMail) {
		//eMail value is set to users email
		this.eMail = eMail;
	}
	public void cancel() {
		//email is set back to free
		this.eMail = "free";
	}
	public void addLikeness() {
		//if the seat meets one of the conditions, this function is called to increase the likeness
		this.userLikeness ++;
	}
	public void resetLikeness() {
		//after searching likeness is reset to 0 to avoid it going over 5 and affecting future searches
		this.userLikeness = 0;
	}
}