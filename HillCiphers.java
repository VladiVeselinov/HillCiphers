import java.util.Scanner;

/**
 * The program has numerous functions related to Hill Substitution Ciphers.
 * 
 * @author 		Vladi Veselinov
 * @version		0.1
 */
public class HillCiphers 
{
	public static void main(String[] args) 
	{
		Scanner keyboard = new Scanner(System.in);
		int option;
		do
		{
			System.out.println("=====       Main Menu       =====");
			System.out.println("1) Find the mod 26 of a number");
			System.out.println("0) Exit this program");
			System.out.print("Select an option: ");
			option = keyboard.nextInt();
			if(option == 1)
			{
				System.out.print("Enter the number: ");
				int number = keyboard.nextInt();
				System.out.println(number + " = " + toMod26(number) + "(mod 26)");
			}
			else if(option == 0)
				System.out.println("Good bye!");
			else
				System.out.println("Invalid option!");
		}
		while(option != 0);
		keyboard.close();
	}
	public static int toMod26(int number)
	{
		int remainder = number%26;
		if(remainder >= 0)
			return remainder;
		else
			return remainder+26;
	}
}
