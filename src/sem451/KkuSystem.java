package sem451;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.io.*;
import java.text.SimpleDateFormat;

public class KkuSystem implements FileNames, ReserveTasks, OptionalReserveTasks{
	

	static List<ReserveBlock> reservations = new ArrayList<>();
	static People people = new People();
	
	
	public void showCLIMenu() {
		
		load(); //load previous data
		Person p; LocalDate l; int t; Room r; boolean st; Scanner sc = new Scanner(System.in); String s;
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd | HH:mm:ss").format(Calendar.getInstance().getTime());
		while(true) {
			System.out.println("\n\n\nWelcome to KKU LAB Managament System");
			System.out.println(timeStamp);
			System.out.println("=================================================");
			System.out.println("1. Make a reservation + New User");
			System.out.println("2. Update a reservation");
			System.out.println("3. Print all reservations on screen");
			System.out.println("4. Print reservations to File");
			System.out.println("5. Exit");
			System.out.println("6. Remove Reservation");
			System.out.println("7. Create New User");
			System.out.println("8. Print all users on screen");
			System.out.println("9. Remove User");
			System.out.print("\nPlease Enter a number:");
			s = sc.next();
			switch(s) {
			case "1":
		        System.out.println("Enter person name, ID, and age (press Enter after each):");
		        // Clearing the buffer before reading the name
		        if (sc.hasNextLine()) {
		            sc.nextLine(); // This line will consume the leftover newline from the previous input
		        }
		        // Reading name
			   // System.out.println("Enter name:");
		        String name = sc.nextLine();

		        // Reading ID
		       //System.out.println("Enter ID:");
		        String id = sc.nextLine();

		        // Reading and validating age
		       // System.out.println("Enter age:");
		        int age = readValidAge(sc);

		        p = new Person(name, id, age);
		        people.addPerson(p);

				
				System.out.println("Enter room name:");
				r = new LabRoom(sc.next());
				System.out.println("Enter Date in yyyy-mm-dd:");				
			//	l=LocalDate.parse(sc.next());
			    l = readValidDate(sc);
				System.out.println("At what Clock 1-24 (Only 1 hour can be booked)?");
			    t = readValidHour(sc);
				//t=sc.nextInt();
				st=this.reserveBlock(new ReserveBlock(p,l,t,r));
				break;
				
				
			case "2":
				System.out.println("Not working. Please remove and Add again.");
				break;
				
				
			case "3":
				this.printReservedBlocks(reservations);
				break;
				
				
			case "4":
				this.exportToFile2();
				break;
				
			case "5":
				sc.close();
				System.out.println("Saving...");
				save();
				System.out.println("Thank you.");
				System.exit(0);
				
			case "6":
				p = new Person("test","test",0);
				System.out.println("Enter room name:");
				r = new LabRoom(sc.next());
				System.out.println("Enter Date in yyyy-mm-dd:");
				l=LocalDate.parse(sc.next());
				System.out.println("At what Clock 1-24 (Only 1 hour can be booked)?");
				t=sc.nextInt();
				st=this.removeBlock(new ReserveBlock(null,l,t,r));
				break;
			
			case "7":
				
				  System.out.println("Enter person name, ID, and age (press Enter after each):");
			        // Clearing the buffer before reading the name
			        if (sc.hasNextLine()) {
			            sc.nextLine(); // This line will consume the leftover newline from the previous input
			        }
			        // Reading name
				   // System.out.println("Enter name:");
			        String NewName = sc.nextLine();

			        // Reading ID
			       //System.out.println("Enter ID:");
			        String NewId = sc.nextLine();

			        // Reading and validating age
			       // System.out.println("Enter age:");
			        int NewAge = readValidAge(sc);

				people.addPerson(NewName, NewId, NewAge);
				break;
				
			case "8":
				people.printPeople();
				break;
				
			case "9":
				System.out.println("Enter user id to remove:");
				people.removePerson(sc.next());
				break;
			
			default:
				System.err.println("Wrong choice!\n");
				
			}
		}
		
	}

	public int readValidAge(Scanner sc) {
	      while (true) {
	           // System.out.print("Enter age: ");
	            try {
	                return Integer.parseInt(sc.nextLine());
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid age, Enter an integer for age.");
	            }
	        }
	    }

	public LocalDate readValidDate(Scanner sc) {
	    while (true) {
	      //  System.out.print("Enter Date in yyyy-mm-dd: ");
	        String dateString = sc.next();
	        try {
	            return LocalDate.parse(dateString); // Return the parsed date if valid
	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid date. Enter the date such as yyyy-mm-dd.");
	        }
	    }
	}

	public int readValidHour(Scanner sc) {
	    int hour;
	    while (true) {
	      //  System.out.print("At what Clock 1-24 (Only 1 hour can be booked)? ");
	        if (sc.hasNextInt()) {
	            hour = sc.nextInt(); // Read the hour input

	            // Check if the hour is within the valid range
	            if (hour >= 1 && hour <= 24) {
	                return hour; // Return the valid hour
	            } else {
	                System.out.println("Enter again a number between 1 and 24.");
	            }
	        } else {
	            System.out.println("Enter again a number between 1 and 24.");
	            sc.next(); // Consume the invalid token to avoid an infinite loop
	        }
	    }
	}


	public static void main(String args[]) {
		reservations.add(new ReserveBlock(new Person("Ahmad","0",0),LocalDate.parse("2023-12-12"),12,new LabRoom("18S")));
		//String s = sc.next();
		//LocalDate l = LocalDate.parse(s);
		//LocalTime t = LocalTime.parse(s);
		//System.out.println(t);
		new KkuSystem().showCLIMenu();
	}

	@Override
	/**
	  * Check person is not blocked
	  *	Check block's date, time, & room is not in the list
	  * then add it
	  **/
	public boolean reserveBlock(ReserveBlock rb) {
		if(this.checkExist(rb, reservations))
		{
			System.out.println("Sorry, booked in "+rb.getDate()+" at "+rb.getClock()+"!");
			return false;
		}
		else
		{
			reservations.add(rb);
			System.out.println("Done, Room "+rb.getRoom().getName()+" booked in "+rb.getDate()+" at "+rb.getClock()+".");
			return false;
		}
	}

	/**
	 * Search all list elements if there is any blocked rooms
	 * similar to the parameter return true.
	 * @param rb block you want to add
	 * @param rooms list of blocked rooms
	 * @return true if it finds a match in the list
	 */
	public boolean checkExist(ReserveBlock rb, List<ReserveBlock> rooms) {
		for(ReserveBlock room: rooms) {
			if(room.equals(rb))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean updateBlock(ReserveBlock rb) {
		if(rb.getBy().isBlocked())
		{
			System.out.println("User cannot make a reservation!");
			return false;
		}
		else if(!this.checkExist(rb, reservations))
		{
			System.out.println("Sorry, no one has booked it in "+rb.getDate()+" at "+rb.getClock()+"!");
			System.out.println("Making a new reservation ...");
			return reserveBlock(rb);
		}
		else
		{
			removeBlock(rb);
			if(reserveBlock(rb))
			{
				System.out.println("Updated.");
				return true;
			}
			
			else 
			{
				System.out.println("Failed to update!");
				return false;
			}
		}
	}

	@Override
	public boolean removeBlock(ReserveBlock rb) {
		int in = -1;
		if(reservations.isEmpty()) {
			System.out.println("List is Empty!");
			return false;
		}
		for(int i =0; i<reservations.size();i++)
				{
					if(rb.getDate().equals(reservations.get(i).getDate()))
					{
						if(rb.getClock()==reservations.get(i).getClock()) 
						{
							if(rb.getRoom().getName().equalsIgnoreCase(reservations.get(i).getRoom().getName()))
							{
								in = i;
								break;
							}
						}
					}
				}
		if(in!=-1)
			{
				System.out.println("Removed "+reservations.get(in));
				reservations.remove(in);
				return true;
			}
		System.out.println("Sorry, could not find the block to remove!");
		return false;
	}

	@Override
	public void removeAllEndedBlocks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printReservedBlocks(List<ReserveBlock> blocks) {
		if(blocks.isEmpty())
			System.out.println("No reservations have been made!");
		else
			for(ReserveBlock block:blocks) {
				System.out.println(block);
			}
		
	}

	@Override
	
	public boolean loadDataFromFile() {
		reservations.clear();
		ObjectInputStream o2;
		File sessionsFile = new File(SESSIONS_FILE);
		if (!sessionsFile.exists()) {
			System.out.println("File Not Found to load!--> Sessions");
			return false;
		}
		try (  ObjectInputStream o = new ObjectInputStream(new FileInputStream(sessionsFile))) {
			reservations = (List<ReserveBlock>) o.readObject();
			System.out.println("Finished loading data.");
			System.out.println(reservations.size() + " session(s) imported.");
			return true;
		} catch (IOException e) {
			System.out.println("Could not load from file!");
		} catch (Exception e) {
			System.out.println("Unknow error in load file!");
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean saveDataToFile() {
		if(KkuSystem.reservations.isEmpty())
		{
			System.out.println("Nothing to save!");
			return true;
		}
		else {
			ObjectOutputStream o;
			try {
				o = new ObjectOutputStream(new FileOutputStream(SESSIONS_FILE));
				o.writeObject(reservations);
				o.close();
				
				return true;
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found to save!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//export data to text file
	//it uses PrintWriter and always override!
	public boolean exportToFile2() {
		if(KkuSystem.reservations.isEmpty())
		{
			System.out.println("Nothing to export!");
			return true;
		}
		else {
			try {
				PrintWriter o = new PrintWriter(PRINT_FILE);
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd | HH:mm:ss").format(Calendar.getInstance().getTime());
				o.println("\n================="+timeStamp+"==================");
				for(ReserveBlock bl: reservations)
				{
					o.println(bl);
				}
				o.close();
				System.out.println("Finished exporting.");
				return true;
			} catch (FileNotFoundException e) {
				System.out.println("FileNot Found to export!");
				e.printStackTrace();
			}
		}
		return false;
	}

	
	//this method save users and sessions into external file
	public void save() {
		try {
			this.saveDataToFile();
			people.saveDataToFile();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//this method load users and sessions from external file to their collections
	public void load() {
		try {
			this.loadDataFromFile();
			people.loadDataFromFile();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//9//
	    @Override
	    public List<LocalDate> findDatesForReservedBlock(ReserveBlock targetBlock) {
	        List<LocalDate> matchingDates = new ArrayList<>();
	        for (ReserveBlock currentReservation : reservations) {
	            // Check if the current reservation matches the target reservation block
	            if (currentReservation.equals(targetBlock)) {
	                // If it matches, add the date of the current reservation to the list of matching dates
	                matchingDates.add(currentReservation.getDate());
	            }
	        }
	        // Return the list of dates 
	        return matchingDates;
	    }


}
