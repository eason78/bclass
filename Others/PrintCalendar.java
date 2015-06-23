// print the calendar of a month
import java.util.Scanner;

public class PrintCalendar {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		// prompt the user to enter the year
		System.out.print("Enter full year (eg. 2001): ");
		int year = input.nextInt();

		// prompt the user to enter month
		System.out.print("Enter the number of month(between 1 and 12): ");
		int month = input.nextInt();

		// print the calendar of required month
		printMonth(year, month);
	}

	public static void printMonth(int year, int month) {
		printMonthTitle(year, month);
		printMonthBody(year, month);
	}

	public static void printMonthTitle(int year, int month) {
		System.out.println("	" + getMonthName(month) + " " + year);
		System.out.println(" ===========================");
		System.out.println(" Sun Mon Tue Wed Thu Fri Sat");
	}

	public static String getMonthName(int month) {
		String monthName = " ";
		switch(month) {
			case 1: monthName = "January"; break;
			case 2: monthName = "February"; break;
			case 3: monthName = "March"; break;
			case 4: monthName = "April"; break;
			case 5: monthName = "May"; break;
			case 6: monthName = "Jane"; break;
			case 7: monthName = "July"; break;
			case 8: monthName = "August"; break;
			case 9: monthName = "September"; break;
			case 10: monthName = "October"; break;
			case 11: monthName = "November"; break;
			case 12: monthName = "December"; break;
		}
		return monthName;
	}

	public static void printMonthBody(int year, int month) {
		int startDay = getStartDay(year, month);
		int numberOfDaysInMonth = getNumberOfDaysInMonth(year, month);
		
		for (int i = 0; i < startDay; i++) {
			System.out.print("    ");
		}

		for (int i = 1; i <= numberOfDaysInMonth; i++) {
			System.out.printf("%4d", i);
			if ((i + startDay) % 7 == 0) {
				System.out.println();
			}
		}
		System.out.println();
	}

	public static int getStartDay(int year, int month) {
		final int START_DAY_FOR_JAN_1_1800 = 3;
		// get total number of days from 1/1/1800 to month/1/year
		int totalNumberOfDays = getTotalNumberOfDays(year, month);
		// return the start day for the required month of the year
		return (totalNumberOfDays + START_DAY_FOR_JAN_1_1800) % 7;
	}

	public static int getTotalNumberOfDays(int year, int month) {
		int total = 0;
		// get the total number of days from 1/1/1800 to 1/1/year
		for (int i = 1800; i < year; i++) {
			if (isLeapYear(i)) {
				total += 366;
			} else {
				total += 365;
			}
		}

		for (int i = 1; i < month; i++) {
			total += getNumberOfDaysInMonth(year, i);
		}

		return total;
	}

	public static int getNumberOfDaysInMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 ||
			month == 8 || month == 10 || month == 12) {
			return 31;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		} else if (month == 2) {
			return isLeapYear(year) ? 29 : 28;
		} else{
			// if month is invalid
			return 0;
		}
	}

	public static boolean isLeapYear(int year) {
		return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
	}
}