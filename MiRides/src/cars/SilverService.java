package cars;

import utilities.DateTime;
import utilities.DateUtilities;

class SilverService extends Car
{


	private String[] refreshments;

	// Tracking bookings
	private Booking[] currentBookings;
	private Booking[] pastBookings;
	private int bookingSpotAvailable = 0;
	private double tripFee = 0;

	// Constants
	private final double MIN_SS_FEE = 3;
	private final double RATE = 0.4;

	public SilverService(String regNo, String make, String model, String driverName, int passengerCapacity,
			double bookingFee, String[] refreshments)
	{
		super(regNo, make, model, driverName, passengerCapacity);
		
		

	}

	public double getBookingFee()
	{
		return STANDARD_BOOKING_FEE;
	}

	public void setBookingFee(double bookingFee)
	{
		boolean validSSFee = bookingFee >= MIN_SS_FEE;

		if (validSSFee)
		{
			this.STANDARD_BOOKING_FEE = bookingFee;
		} else
		{
			this.STANDARD_BOOKING_FEE = MIN_SS_FEE;
		}

	}

	public void setRefreshments(String[] refreshments)
	{
		this.refreshments = refreshments;
	}
	
	public boolean book(String firstName, String lastName, DateTime required, int numPassengers)
	{
		boolean booked = false;
		// Does car have five bookings
		available = bookingAvailable();
		boolean dateAvailable = notCurrentlyBookedOnDate(required);
		// Date is within range, not in past and within the next week
		boolean dateValid = dateIsValid(required);
		// Number of passengers does not exceed the passenger capacity and is not zero.
		boolean validPassengerNumber = numberOfPassengersIsValid(numPassengers);

		// Booking is permissible
		if (available && dateAvailable && dateValid && validPassengerNumber)
		{
			tripFee = STANDARD_BOOKING_FEE;
			Booking booking = new Booking(firstName, lastName, required, numPassengers, this);
			currentBookings[bookingSpotAvailable] = booking;
			bookingSpotAvailable++;
			booked = true;
		}
		return booked;
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
		double fee = kilometers * (STANDARD_BOOKING_FEE * RATE);
		tripFee += fee;
		booking.completeBooking(kilometers, fee, STANDARD_BOOKING_FEE);
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

	//Checks that the date is not in the past or more than 7 days in the future.
	private boolean dateIsValid(DateTime date)
	{
		return DateUtilities.dateIsNotInPast(date) && DateUtilities.dateIsNotMoreThan3Days(date);
	}
	
	public String getDetails()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(getRecordMarker());
		sb.append(String.format("%-15s %s\n", "Reg No:", regNo));
		sb.append(String.format("%-15s %s\n", "Make & Model:", make + " " + model));

		sb.append(String.format("%-15s %s\n", "Driver Name:", driverName));
		sb.append(String.format("%-15s %s\n", "Capacity:", passengerCapacity));
		sb.append(String.format("%-15s %s\n", "Standard Fee:", STANDARD_BOOKING_FEE));
		
		sb.append(String.format("%-15s %s\n", " "));
		sb.append(String.format("%-15s %s\n", "Refreshments Available"));
		sb.append(String.format("%-15s %s\n", "Item 1", refreshments[0]));
		sb.append(String.format("%-15s %s\n", "Item 2", refreshments[1]));
		sb.append(String.format("%-15s %s\n", "Item 3", refreshments[2]));

		if (bookingAvailable())
		{
			sb.append(String.format("%-15s %s\n", "Available:", "YES"));
		} else
		{
			sb.append(String.format("%-15s %s\n", "Available:", "NO"));
		}

		return sb.toString();
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(regNo + ":" + make + ":" + model);
		if (driverName != null)
		{
			sb.append(":" + driverName);
		}
		sb.append(":" + passengerCapacity);
		if (bookingAvailable())
		{
			sb.append(":" + "YES");
		} else
		{
			sb.append(":" + "NO");
		}
		sb.append(":" + STANDARD_BOOKING_FEE);
		sb.append(":" + "Item 1" + refreshments[0]);
		sb.append(":" + "Item 2" + refreshments[1]);
		sb.append(":" + "Item 3" + refreshments[2]);

		return sb.toString();
	}
	

}