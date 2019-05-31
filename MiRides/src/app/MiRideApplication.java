package app;

import cars.Booking;
import cars.Car;
import cars.SilverService;
import exceptions.InvalidBooking;
import utilities.DateTime;
import utilities.DateUtilities;
import utilities.MiRidesUtilities;

/*
 * Class:			MiRideApplication
 * Description:		The system manager the manages the 
 *              	collection of data. 
 * Author:			Rodney Cocker
 */
public class MiRideApplication
{
	private Car[] cars = new Car[15];
	private Car[] silverService = new Car[15];
	private int itemCount = 0;
	private int silverserviceItemCount = 0;
	private String[] availableCars;

	public MiRideApplication()
	{
		// seedData();
	}

	public String createCar(String id, String make, String model, String driverName, int numPassengers)
	{
		String validId = isValidId(id);
		if (isValidId(id).contains("Error:"))
		{
			return validId;
		}
		if (!checkIfCarExists(id))
		{
			cars[itemCount] = new Car(id, make, model, driverName, numPassengers);
			itemCount++;
			return "New Car added successfully for registion number: " + cars[itemCount - 1].getRegistrationNumber();
		}
		return "Error: Already exists in the system.";
	}

	public String createSilverServiceCar(String id, String make, String model, String driverName, int numPassengers,
			double bookingFee, String[] refreshments)
	{
		String validId = isValidId(id);
		if (isValidId(id).contains("Error:"))
		{
			return validId;
		}
		if (!checkIfCarExists(id))
		{
			silverService[silverserviceItemCount] = new SilverService(id, make, model, driverName, numPassengers,
					bookingFee, refreshments);
			itemCount++;
			return "New Car added successfully for registion number: " + cars[itemCount - 1].getRegistrationNumber();
		}
		return "Error: Already exists in the system.";
	}

	public String[] book(DateTime dateRequired)
	{
		int numberOfAvailableCars = 0;
		// finds number of available cars to determine the size of the array required.
		for (int i = 0; i < cars.length; i++)
		{
			if (cars[i] != null)
			{
				if (!cars[i].isCarBookedOnDate(dateRequired))
				{
					numberOfAvailableCars++;
				}
			}
		}
		if (numberOfAvailableCars == 0)
		{
			String[] result = new String[0];
			return result;
		}
		availableCars = new String[numberOfAvailableCars];
		int availableCarsIndex = 0;
		// Populate available cars with registration numbers
		for (int i = 0; i < cars.length; i++)
		{

			if (cars[i] != null)
			{
				if (!cars[i].isCarBookedOnDate(dateRequired))
				{
					availableCars[availableCarsIndex] = availableCarsIndex + 1 + ". " + cars[i].getRegistrationNumber();
					availableCarsIndex++;
				}
			}
		}
		return availableCars;
	}

	public String book(String firstName, String lastName, DateTime required, int numPassengers,
			String registrationNumber)
	{
		Car car = getCarById(registrationNumber);
		if (car != null)
		{
			if (car.book(firstName, lastName, required, numPassengers))
			{

				String message = "Thank you for your booking. \n" + car.getDriverName() + " will pick you up on "
						+ required.getFormattedDate() + ". \n" + "Your booking reference is: "
						+ car.getBookingID(firstName, lastName, required);
				return message;
			} else
			{
				String message = "Booking could not be completed.";
				return message;
			}
		} else
		{
			return "Car with registration number: " + registrationNumber + " was not found.";
		}
	}

	public String completeBooking(String firstName, String lastName, DateTime dateOfBooking, double kilometers)
	{
		String result = "";

		// Search all cars for bookings on a particular date.
		for (int i = 0; i < cars.length; i++)
		{
			if (cars[i] != null)
			{
				if (cars[i].isCarBookedOnDate(dateOfBooking))
				{
					return cars[i].completeBooking(firstName, lastName, dateOfBooking, kilometers);
				}
//				else
//				{
//					
//				}
//				if(!result.equals("Booking not found"))
//				{
//					return result;
//				}
			}
		}
		return "Booking not found.";
	}

	public String completeBooking(String firstName, String lastName, String registrationNumber, double kilometers)
	{
		String carNotFound = "Car not found";
		Car car = null;
		// Search for car with registration number
		for (int i = 0; i < cars.length; i++)
		{
			if (cars[i] != null)
			{
				if (cars[i].getRegistrationNumber().equals(registrationNumber))
				{
					car = cars[i];
					break;
				}
			}
		}

		if (car == null)
		{
			return carNotFound;
		}
		if (car.getBookingByName(firstName, lastName) != -1)
		{
			return car.completeBooking(firstName, lastName, kilometers);
		}
		return "Error: Booking not found.";
	}

	public boolean getBookingByName(String firstName, String lastName, String registrationNumber)
	{
		String bookingNotFound = "Error: Booking not found";
		Car car = null;
		// Search for car with registration number
		for (int i = 0; i < cars.length; i++)
		{
			if (cars[i] != null)
			{
				if (cars[i].getRegistrationNumber().equals(registrationNumber))
				{
					car = cars[i];
					break;
				}
			}
		}

		if (car == null)
		{
			return false;
		}
		if (car.getBookingByName(firstName, lastName) == -1)
		{
			return false;
		}
		return true;
	}

	public String displaySpecificCar(String regNo)
	{
		for (int i = 0; i < cars.length; i++)
		{
			if (cars[i] != null)
			{
				if (cars[i].getRegistrationNumber().equals(regNo))
				{
					return cars[i].getDetails();
				}
			}
		}
		return "Error: The car could not be located.";
	}

	public boolean seedData()
	{
		for (int i = 0; i < cars.length; i++)
		{
			if (cars[i] != null)
			{
				return false;
			}
		}
		// 2 cars not booked
		Car honda = new Car("SIM194", "Honda", "Accord Euro", "Henry Cavill", 5);
		cars[itemCount] = honda;
		try
		{
			honda.book("Craig", "Cocker", new DateTime(1), 3);
		} catch (InvalidBooking e5)
		{
			// TODO Auto-generated catch block
			e5.printStackTrace();
		}
		itemCount++;

		Car lexus = new Car("LEX666", "Lexus", "M1", "Angela Landsbury", 3);
		cars[itemCount] = lexus;
		try
		{
			lexus.book("Craig", "Cocker", new DateTime(1), 3);
		} catch (InvalidBooking e4)
		{
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		itemCount++;

		// 2 cars booked
		Car bmw = new Car("BMW256", "Mini", "Minor", "Barbara Streisand", 4);
		cars[itemCount] = bmw;
		itemCount++;
		try
		{
			bmw.book("Craig", "Cocker", new DateTime(1), 3);
		} catch (InvalidBooking e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		Car audi = new Car("AUD765", "Mazda", "RX7", "Matt Bomer", 6);
		cars[itemCount] = audi;
		itemCount++;
		try
		{
			audi.book("Rodney", "Cocker", new DateTime(1), 4);
		} catch (InvalidBooking e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// 1 car booked five times (not available)
		Car toyota = new Car("TOY765", "Toyota", "Corola", "Tina Turner", 7);
		cars[itemCount] = toyota;
		itemCount++;
		try
		{
			toyota.book("Rodney", "Cocker", new DateTime(1), 3);
			toyota.book("Craig", "Cocker", new DateTime(2), 7);
			toyota.book("Alan", "Smith", new DateTime(3), 3);
			toyota.book("Carmel", "Brownbill", new DateTime(4), 7);
			toyota.book("Paul", "Scarlett", new DateTime(5), 7);
			toyota.book("Paul", "Scarlett", new DateTime(6), 7);
			toyota.book("Paul", "Scarlett", new DateTime(7), 7);
		} catch (InvalidBooking e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		// 1 car booked five times (not available)
		Car rover = new Car("ROV465", "Honda", "Rover", "Jonathon Ryss Meyers", 7);
		cars[itemCount] = rover;
		itemCount++;
		try
		{
			rover.book("Rodney", "Cocker", new DateTime(1), 3);
		} catch (InvalidBooking e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// rover.completeBooking("Rodney", "Cocker", 75);
		DateTime inTwoDays = new DateTime(2);
		try
		{
			rover.book("Rodney", "Cocker", inTwoDays, 3);
		} catch (InvalidBooking e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rover.completeBooking("Rodney", "Cocker", inTwoDays, 75);
		

		// Two silver service cars that HAVE NOT been booked
		String[] refreshmentA = {"Rice","Tea","Soy sauce"};
		Car bugatti = new SilverService("BUG782", "Bugatti", "E3", "High Roller", 3,3.5, refreshmentA);	
		silverService[silverserviceItemCount] = bugatti;
		silverserviceItemCount++;

		
		return true;
	}

	public String displayAllBookings()
	{
		System.out.println("item count" + itemCount);
		if (itemCount == 0)
		{
			return "No cars have been added to the system.";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Summary of all cars: ");
		sb.append("\n");

		for (int i = 0; i < itemCount; i++)
		{
			sb.append(cars[i].getDetails());
			if (cars[i].getCurrentBooking().equals(""))
			{
			} else
			{
				sb.append("\nCURRENT BOOKINGS");
				sb.append(cars[i].getCurrentBooking());
			}
			if (cars[i].getPastBooking().equals(""))
			{
			} else
			{
				sb.append("\nPAST BOOKINGS");
				sb.append(cars[i].getPastBooking());
			}

		}
		return sb.toString();

	}

	public String displaySilverServiceBookings()
	{
		System.out.println("item count" + silverserviceItemCount);
		if (silverserviceItemCount == 0)
		{
			return "No cars have been added to the system.";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Summary of all cars: ");
		sb.append("\n");

		for (int i = 0; i < silverserviceItemCount; i++)
		{
			sb.append(silverService[i].getDetails());
			if (silverService[i].getCurrentBooking().equals(""))
			{
			} else
			{
				sb.append("\nCURRENT BOOKINGS");
				sb.append(silverService[i].getCurrentBooking());
			}
			if (silverService[i].getPastBooking().equals(""))
			{
			} else
			{
				sb.append("\nPAST BOOKINGS");
				sb.append(silverService[i].getPastBooking());
			}

		}
		return sb.toString();

	}

	public String displayBooking(String id, String seatId)
	{
		Car booking = getCarById(id);
		if (booking == null)
		{
			return "Booking not found";
		}
		return booking.getDetails();
	}

	public String isValidId(String id)
	{
		return MiRidesUtilities.isRegNoValid(id);
	}

	public String isValidPassengerCapacity(int passengerNumber)
	{
		return MiRidesUtilities.isPassengerCapacityValid(passengerNumber);
	}

	public boolean checkIfCarExists(String regNo)
	{
		Car car = null;
		if (regNo.length() != 6)
		{
			return false;
		}
		car = getCarById(regNo);
		if (car == null)
		{
			return false;
		} else
		{
			return true;
		}
	}

	private Car getCarById(String regNo)
	{
		Car car = null;

		for (int i = 0; i < cars.length; i++)
		{
			if (cars[i] != null)
			{
				if (cars[i].getRegistrationNumber().equals(regNo))
				{
					car = cars[i];
					return car;
				}
			}
		}
		return car;
	}

	public void SA(String entertype, DateTime required)
	{
		if (entertype.equals("SD"))
		{
			for (int i = 0; i < cars.length; i++)
			{
				if (cars[i] != null)
				{
					Car car = cars[i];
					car.isCarBookedOnDate(required);
					if (car.isCarBookedOnDate(required) == false)

					{
						System.out.print(car.getDetails());
						if (car.getCurrentBooking().equals(""))
						{

						} else
						{
							System.out.println("\nCURRENT BOOKINGS");
							System.out.println(car.getCurrentBooking());
						}
						if (car.getPastBooking().equals(""))
						{

						} else
						{
							System.out.println("\nPAST BOOKINGS");
							System.out.println(car.getPastBooking());
						}

					}

				} else if (cars[i] == null && i == 0)
				{
					System.out.print("Error - no cars were found on this date.");
					break;
				}

			}
		} else if (entertype.equals("SS"))
		{
			for (int i = 0; i < silverService.length; i++)
			{
				if (silverService[i] != null)
				{
					Car car = silverService[i];
					boolean dateMatch = car.isCarBookedOnDate(required);

					if (dateMatch == false && car.getAvailable() == true)
					{
						System.out.print(car.getDetails());
						if (car.getCurrentBooking().equals(""))
						{

						} else
						{
							System.out.println("\nCURRENT BOOKINGS");
							System.out.println(car.getCurrentBooking());
						}
						if (car.getPastBooking().equals(""))
						{

						} else
						{
							System.out.println("\nPAST BOOKINGS");
							System.out.println(car.getPastBooking());
						}

					}
				} else if (silverService[i] == null && i == 0)
				{
					System.out.print("Error - no cars were found on this date.");
					break;
				}
			}
		}
	}

	public void carsort(String entertype, String sort)
	{
		Car[] aux = new Car[1];

		if (sort.equals("A"))
		{
			if (entertype.equals("SD"))
			{

				for (int j = 0; j < cars.length; j++)
				{
					for (int i = 0; i < cars.length; i++)
					{
						if (cars[i] != null && cars[i + 1] != null)
						{
							if (cars[i].toString().substring(0, 5)
									.compareTo(cars[i + 1].toString().substring(0, 5)) > 0)
							{
								aux[0] = cars[i];
								cars[i] = cars[i + 1];
								cars[i + 1] = aux[0];
							}
						}
					}
				}

			}

			else if (entertype.equals("SS"))
			{
				for (int j = 0; j < silverService.length; j++)
				{
					for (int i = 0; i < silverService.length; i++)
					{
						if (silverService[i] != null && cars[i + 1] != null)
						{
							if (silverService[i].toString().substring(0, 5)
									.compareTo(silverService[i + 1].toString().substring(0, 5)) > 0)
							{
								aux[0] = silverService[i];
								silverService[i] = silverService[i + 1];
								silverService[i + 1] = silverService[0];
							}
						}
					}
				}
			}

		} else if (sort.equals("D"))
		{
			if (entertype.equals("SD"))
			{
				for (int j = 0; j < cars.length; j++)
				{
					for (int i = 0; i < cars.length; i++)
					{
						if (cars[i] != null && cars[i + 1] != null)
						{
							if (cars[i].toString().substring(0, 5)
									.compareTo(cars[i + 1].toString().substring(0, 5)) < 0)
							{
								aux[0] = cars[i];
								cars[i] = cars[i + 1];
								cars[i + 1] = aux[0];
							}
						}
					}
				}
			}

			else if (entertype.equals("SS"))
			{
				for (int j = 0; j < silverService.length; j++)
				{
					for (int i = 0; i < silverService.length; i++)
					{
						if (silverService[i] != null && cars[i + 1] != null)
						{
							if (silverService[i].toString().substring(0, 5)
									.compareTo(silverService[i + 1].toString().substring(0, 5)) < 0)
							{
								aux[0] = silverService[i];
								silverService[i] = silverService[i + 1];
								silverService[i + 1] = aux[0];
							}
						}
					}

				}
			}
		}

	}
}
