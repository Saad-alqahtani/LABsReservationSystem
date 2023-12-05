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
		
		 load(); // Load previous data
		    Person p; LocalDate l = null; int t; Room r; 
		    boolean st; Scanner sc = new Scanner(System.in); String s;
	

		    String timeStamp = new SimpleDateFormat("yyyy-MM-dd | HH:mm:ss").format(Calendar.getInstance().getTime());

		    while(true) {
		        System.out.println("\n\n\nWelcome to KKU LAB Management System");
		        System.out.println(timeStamp);
		        System.out.println("=================================================");
		        
			System.out.println("1. Make a reservation + New User");
			System.out.println("2. Update a reservation");
			System.out.println("3. Print all reservations on screen");
			System.out.println("4. Print reservations to File");
			System.out.println("5. Save and Exit");
			System.out.println("6. Remove Reservation");
			System.out.println("7. Create New User");
			System.out.println("8. Print all users on screen");
			System.out.println("9. Remove User");
	     //   System.out.println("10. Find Reservations by User");

			System.out.print("\nPlease Enter a number:");
			 s = sc.next();
		        switch(s) {
		        case "1":
		        	System.out.println("Enter person name, id and age (press Enter after each):");
		            String name = sc.next();
		            String id = sc.next();
		            int age;

		            // Loop specifically for age input
		            while (true) {
		                try {
		                 //   System.out.println("Enter age:");
		                    age = sc.nextInt();

		                    // Check if age is between 25 and 60
		                    if (age >= 25 && age <= 60) {
		                        break; // Exit the loop if age is valid
		                    } else {
		                        System.out.println("Invalid age. Age must be between 25 and 60. Please re-enter age.");
		                    }
		                } catch (InputMismatchException e) {
		                    System.out.println("Invalid input. Please enter a numeric age.");
		                    sc.next(); // Clear the buffer
		                }
		            }

				    		        
		           p = new Person(name, id, age);		            
		          people.addPerson(p);
		            
		            System.out.println("Enter room name:");
		            r = new LabRoom(sc.next());

		            while (true) {
		                System.out.println("Enter Date in yyyy-mm-dd:");
		                String dateString = sc.next();

		                try {
		                    l = LocalDate.parse(dateString);

		                    // Check if the year is 2024
		                    if (l.getYear() == 2024) {
		                        break; // Exit the loop if the year is 2024
		                    } else {
		                        System.out.println("Please enter a date in the year 2024.");
		                    }
		                } catch (DateTimeParseException e) {
		                    System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format.");
		                }
		            }

		            

		         // Hour input handling
		            while (true) {
		                System.out.println("At what Clock 1-24 (Only 1 hour can be booked)?");
		                try {
		                    t = sc.nextInt();
		                    if (t >= 1 && t <= 24) {
		                        break; // Exit the hour input loop
		                    } else {
		                        System.out.println("Invalid hour. Please enter a number between 1 and 24.");
		                    }
		                } catch (InputMismatchException e) {
		                    System.out.println("Invalid input. Please enter a number.");
		                    sc.next(); // Clear the invalid input
		                }
		            }

		       //     st = reservations.add(new ReserveBlock(p, l, t, r));
					st=this.reserveBlock(new ReserveBlock(p,l,t,r));

		            //reservations
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
	            System.out.println("Saving...");
	            save();
	            System.out.println("Thank you.");
	            sc.close();
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
			    System.out.println("Enter person name, id and age(press Enter after each):");
			    String name2 = sc.next();
			    String id2 = sc.next();
			    int age2;

			    // Loop specifically for age input
			    while (true) {
			    //    System.out.println("Enter age:");
			        age2 = sc.nextInt();

			        // Check if age is between 25 and 60
			        if (age2 >= 25 && age2 <= 60) {
			            break; // Exit the loop if age is valid
			        } else {
			            System.out.println("Invalid age. Age must be between 25 and 60. Please re-enter age.");
			        }
			    }

			    people.addPerson(name2, id2, age2);
			    break;



			    
				//System.out.println("Enter person name, id, age (press Enter after each):");
			//	people.addPerson(sc.next(), sc.next(), sc.nextInt());
			//	break;
				
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
	public static void main(String args[]) {
		//reservations.add(new ReserveBlock(new Person("Ahmad","0",0),LocalDate.parse("2023-12-12"),12,new LabRoom("18S")));
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
				return false;
		}
		return true;
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
	/*public boolean loadDataFromFile() {
		reservations.clear();
		ObjectInputStream o;
			try {
				o = new ObjectInputStream(new FileInputStream(SESSIONS_FILE));
				reservations=(List<ReserveBlock>) o.readObject();
				System.out.println("Finished Loading data.");
				System.out.println(reservations.size()+" session(s) imported.");
				o.close();
				return true;
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found to load!");
			} catch (IOException e) {
				System.out.println("Could not load from file!");
				e.printStackTrace();
			} catch(Exception e) {
				System.out.println("Unknow error in load file!");
				e.printStackTrace();
			}
		return false;
	}*/
	
	public boolean loadDataFromFile() {
	    reservations.clear();
	    File file = new File(SESSIONS_FILE);
	    if (!file.exists()) {
	        System.out.println("File Not Found to load. No sessions loaded.");
	        return false;
	    }
	    try (ObjectInputStream o = new ObjectInputStream(new FileInputStream(file))) {
	        reservations = (List<ReserveBlock>) o.readObject();
	        System.out.println("Finished loading data.");
	        System.out.println(reservations.size() + " session(s) imported.");
	        return true;
	    } catch (FileNotFoundException e) {
	        System.out.println("Data file not found.");
	    } catch (IOException e) {
	        System.out.println("Could not load from file!");
	    } catch (ClassNotFoundException e) {
	        System.out.println("Class not found while reading from file.");
	    } catch (Exception e) {
	        System.out.println("Unknow error in load file! " + e.getMessage());
	    }
	    return false;
	}
	
	

	public boolean saveDataToFile() {
	    if (KkuSystem.reservations.isEmpty()) {
	        System.out.println("Nothing to save!");
	        return true;
	    } else {
	        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(SESSIONS_FILE))) {
	            o.writeObject(reservations);
	            // Optional: Implement saving for PEOPLE_FILE if needed
	            // ObjectOutputStream o2 = new ObjectOutputStream(new FileOutputStream(PEOPLE_FILE));
	            // o2.writeObject(people);
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
		return false;
	}
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
	        System.out.println("Some files were not found and will be created.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	//9. Implements at least one method from OptionalReserveTasks inside KkuSystem class
	// Implementations of methods from OptionalReserveTasks interface
    @Override
    public List<ReserveBlock> findReservedBlocksBy(Person p) {
        List<ReserveBlock> results = new ArrayList<>();
        for (ReserveBlock rb : reservations) {
            if (rb.getBy().equals(p)) {
                results.add(rb);
            }
        }
        return results;
    }

    @Override
    public List<ReserveBlock> findReservedBlockAt(int clock) {
        List<ReserveBlock> results = new ArrayList<>();
        for (ReserveBlock rb : reservations) {
            if (rb.getClock() == clock) {
                results.add(rb);
            }
        }
        return results;
    }

    @Override
    public List<ReserveBlock> findReservedBlockAt(LocalDate date) {
        List<ReserveBlock> results = new ArrayList<>();
        for (ReserveBlock rb : reservations) {
            if (rb.getDate().equals(date)) {
                results.add(rb);
            }
        }
        return results;
    }

    @Override
    public List<ReserveBlock> findReservedBlockAt(LocalDate date, int clock) {
        List<ReserveBlock> results = new ArrayList<>();
        for (ReserveBlock rb : reservations) {
            if (rb.getDate().equals(date) && rb.getClock() == clock) {
                results.add(rb);
            }
        }
        return results;
    }

    @Override
    public List<Person> findPeopleReservedBlock(ReserveBlock rb) {
        List<Person> peopleList = new ArrayList<>();
        for (ReserveBlock reservation : reservations) {
            if (reservation.equals(rb)) {
                peopleList.add(reservation.getBy());
            }
        }
        return peopleList;
    }

    @Override
    public List<Integer> findHoursForReservedBlock(ReserveBlock rb, LocalDate date) {
        List<Integer> hours = new ArrayList<>();
        for (ReserveBlock reservation : reservations) {
            if (reservation.equals(rb) && reservation.getDate().equals(date)) {
                hours.add(reservation.getClock());
            }
        }
        return hours;
    }

    @Override
    public List<LocalDate> findDatesForReservedBlock(ReserveBlock rb) {
        List<LocalDate> dates = new ArrayList<>();
        for (ReserveBlock reservation : reservations) {
            if (reservation.equals(rb)) {
                dates.add(reservation.getDate());
            }
        }
        return dates;
    }

}