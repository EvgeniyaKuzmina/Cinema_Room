package cinema;
import java.util.Scanner;

public class Cinema {
    private int numberOfRows;
    private int numberOfSeats;
    private int bookingRow;
    private int bookingSeat;
    final int seatNumberOne = 1;
    int priceOfTicket;
    int chooseMenu;
    boolean stop = true;
    boolean stop1 = true;
    boolean next = true;
    String[][] seating;
    int totalPrice;
    private int count;
    private int totalSold;
    float percentageOfBuyTickets;





    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getBookingRow() {
        return bookingRow;
    }

    public void setBookingRow(int bookingRow) {
        this.bookingRow = bookingRow;
    }

    public int getBookingSeat() {
        return bookingSeat;
    }

    public void setBookingSeat(int bookingSeat) {
        this.bookingSeat = bookingSeat;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        count++;
        this.count = count;
    }
    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold() {
        this.totalSold += priceOfTicket();


    }

    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        Scanner sc = new Scanner(System.in);
        do {

            System.out.println("Enter the number of rows:");
            String input = sc.nextLine();
            cinema.setNumberOfRows(cinema.checkNumber(input));
        } while(cinema.next);
        do {
            System.out.println("Enter the number of seats in each row:");
            String input = sc.nextLine();
            cinema.setNumberOfSeats(cinema.checkNumber(input));
        } while(cinema.next);

        cinema.chooseMenu();

    }

    void printRoom(String[][] seating) {
        System.out.println();
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 0; i < getNumberOfSeats(); i++) {
            System.out.print(i + seatNumberOne + " ");
        }
        System.out.println();
        for (int i = 0; i < getNumberOfRows(); i++) {
            System.out.print(i + seatNumberOne + " ");
            for (int j = 0; j < getNumberOfSeats(); j++) {
                if (seating[i][j].equals("B") ||  (i == getBookingRow() - 1 && j == getBookingSeat() - 1)) {
                    seating[i][j] = "B";
                }
                System.out.print(seating[i][j] + " ");
            }
            System.out.println();
        }
    }

    void checkAvailable(String[][] seating) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(" ");
            System.out.println("Enter a row number:");
            String input = sc.nextLine();
            setBookingRow(checkNumber(input));
        } while(next);
        do {
            System.out.println("Enter a seat number in that row:");
            String input = sc.nextLine();
            setBookingSeat(checkNumber(input));
        } while(next);

        if (getBookingRow() - 1 >= getNumberOfRows() || getBookingSeat() - 1 >= getNumberOfSeats()
                || getBookingRow() - 1  < 0 || getBookingSeat() - 1 < 0) {
            System.out.println("Wrong input!");
            stop1 = true;
        } else if (!"B".equals(seating[getBookingRow() - 1 ][getBookingSeat() - 1])) {
            seating[getBookingRow() - 1][getBookingSeat() - 1] = "B";
            setCount(count);
            setTotalSold();
            stop1 = false;
        } else {
            System.out.println("That ticket has already been purchased!");
            stop1 = true;
        }
    }

    int checkNumber (String input) {
        int result = 0;

        if (input.matches("\\d\\d") || input.matches("\\d") || input.matches("\\d\\d\\d")) {
            result = Integer.parseInt(input);
            next = false;
        } else {
            System.out.println("Not a number. Try again.");
            next = true;
        }

        return result;
    }

    int priceOfTicket() {
        if (getNumberOfRows() * getNumberOfSeats() <= 60) {
            priceOfTicket = 10;
        } else {
            int frontRows = getNumberOfRows() / 2;
            if (getBookingRow() <= frontRows) {
                priceOfTicket = 10;
            } else {
                priceOfTicket = 8;
            }
        }
        return priceOfTicket;
    }

     int totalPriceOfAllTickets() {
         int priceFrontRows = 0;
         int priceBackRows = 0;
         if (getNumberOfRows() * getNumberOfSeats() <= 60) {
            priceOfTicket = 10;
            totalPrice = getNumberOfRows() * getNumberOfSeats() * priceOfTicket;
         } else {
            int frontRows = getNumberOfRows() / 2;
            int backRows = getNumberOfRows() - frontRows;
            for (int i = 0; i < getNumberOfRows(); i++) {
                if (i < frontRows) {
                    priceOfTicket = 10;
                    priceFrontRows = getNumberOfSeats() * frontRows * priceOfTicket;
                } else {
                    priceOfTicket = 8;
                    priceBackRows = getNumberOfSeats() * backRows * priceOfTicket;
                }
            }
            totalPrice = priceFrontRows + priceBackRows;
        }
        return totalPrice;
    }

    float percentageOfBuyTickets() {
        percentageOfBuyTickets = getCount() * 100 / (float)(getNumberOfRows() * getNumberOfSeats());
        return percentageOfBuyTickets;
    }
    
    void chooseMenu() {
        Scanner sc = new Scanner(System.in);
        seating = new String[getNumberOfRows()][getNumberOfSeats()];

        for (int i = 0; i < getNumberOfRows(); i++) {
            for (int j = 0; j < getNumberOfSeats(); j++) {
                seating[i][j] = "S";
            }
        }
        do {
            System.out.println(" ");
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            chooseMenu = sc.nextInt();

            switch (chooseMenu) {
                case 1:
                    printRoom(seating);
                    break;
                case 2:
                    do {
                        checkAvailable(seating);
                    } while (stop1);
                    System.out.println("Ticket price: $" + priceOfTicket());
                    break;
                case 3:
                    System.out.println(" ");
                    System.out.println("Number of purchased tickets: " + getCount());
                    System.out.printf("Percentage: %.2f%%\n", percentageOfBuyTickets());
                    System.out.println("Current income: $" + getTotalSold());
                    System.out.println("Total income: $" + totalPriceOfAllTickets());
                    break;
                case 0:
                    stop = false;
                    break;
                default:
                    System.out.println("The value is less than zero or greater than two");
            }
        } while (stop);
    }


}
