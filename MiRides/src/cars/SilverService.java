package cars;

class SilverService extends Car
{

	private double bookingFee;
	private String[] refreshments;
	
	// Tracking bookings
	private Booking[] currentBookings;
	private Booking[] pastBookings;
	private int bookingSpotAvailable = 0;
	private double tripFee = 0;

	// Constants
	private final double MIN_SS_FEE = 3;

	public SilverService(String regNo, String make, String model, String driverName, int passengerCapacity,
			double bookingFee, String[] refreshments)
	{
		super(regNo, make, model, driverName, passengerCapacity);
		// TODO Auto-generated constructor stub
		
	}

	public double getBookingFee()
	{
		return bookingFee;
	}

	public void setBookingFee(double bookingFee)
	{
		boolean validSSFee = bookingFee >= MIN_SS_FEE;

		if (validSSFee)
		{
			this.bookingFee = bookingFee;
		} else
		{
			this.bookingFee = MIN_SS_FEE;
		}

	}

	public void setRefreshments(String[] refreshments)
	{
		this.refreshments = refreshments;
	}
	
	private String completeBooking(int bookingIndex, double kilometers)
	{
		tripFee = 0;
		Booking booking = currentBookings[bookingIndex];
		// Remove booking from current bookings array.
		currentBookings[bookingIndex] = null;
		bookingSpotAvailable = bookingIndex;

		// call complete booking on Booking object
		// double kilometersTravelled = Math.random()* 100;
		double fee = kilometers * (this.bookingFee * 0.3);
		tripFee += fee;
		booking.completeBooking(kilometers, fee, this.bookingFee);
		// add booking to past bookings
		for (int i = 0; i < pastBookings.length; i++)
		{
			if (pastBookings[i] == null)
			{
				pastBookings[i] = booking;
				break;
			}
		}
		String result = String.format("Thank you for riding with MiRide.\nWe hope you enjoyed your trip.\n$"
				+ "%.2f has been deducted from your account.", tripFee);
		tripFee = 0;
		return result;
	}
	

}