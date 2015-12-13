import java.util.Scanner;

/**
 * The program has numerous functions related to Hill Substitution Ciphers.
 * 
 * @author 		Vladi Veselinov
 * @version		0.2
 */
public class HillCiphers 
{
	public static void main(String[] args) 
	{
		Scanner keyboard = new Scanner(System.in);
		int option;
		int[][] matrixMessage = new int[][]{{-1},{-1},{-1}};
		int columns = 0;
		boolean messageEntered = false;
		do
		{
			System.out.println("=====       Main Menu       =====");
			System.out.println("1) Find the mod 26 of a number");
			System.out.println("2) Save a string message into a matrix");
			System.out.println("3) Print saved message");
			System.out.println("0) Exit this program");
			System.out.print("Select an option: ");
			option = keyboard.nextInt();
			if(option == 1) 	// Find the mod 26 of a number
			{
				System.out.print("Enter the number: ");
				int number = keyboard.nextInt();
				System.out.println(number + " = " + toMod26(number) + "(mod 26)");
			}
			if(option == 2)		// Save a string message into a matrix
			{
				System.out.print("Enter the message: ");
				String message = keyboard.next();
				message = message.toUpperCase();
				boolean messageOK = true;
				for(int i=0;i<message.length();i++)
					if(letterToNumber(message.charAt(i)) == -1)
						messageOK = false;
				if(!messageOK)
				{
					System.out.println("Error: the message has an invalid character!");
					continue;
				}
				matrixMessage = toMatrix(message);
				messageEntered = true;
				columns = matrixMessage[0].length;
				for(int i=0;i<3;i++)
				{
					for(int j=0;j<columns;j++)
						System.out.print(matrixMessage[i][j]+"\t");
					System.out.print("\n");
				}
			}
			else if(option == 3)	// Print saved message
			{
				if(!messageEntered)
				{
					System.out.println("Error: there is no saved message!");
					continue;
				}
				String message = "";
				for(int j=0;j<columns;j++)
				{
					for(int i=0;i<3;i++)
					{
						char letter = numberToLetter(matrixMessage[i][j]);
						message = message + Character.toString(letter);
					}
				}
				System.out.println(message);
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
	public static int[][] toMatrix(String message)
	{
		message = message.toUpperCase();
		int length = message.length();
		if(length == 0)
		{
			int[][] nullMatrix = new int[][]{{-1},{-1},{-1}};
			return nullMatrix;
		}
		int columns = length/3;
		if(length%3 != 0)
			columns++;
		int[][] matrix = new int[3][columns];
		for(int i = 0;i<columns;i++)
		{
			if(i == (columns - 1) && (length%3 != 0))
			{
				matrix[0][i] = letterToNumber(message.charAt(3*i));
				if(length%3 == 2)
					matrix[1][i] = letterToNumber(message.charAt(3*i+1));
				else
					matrix[1][i] = letterToNumber('X');
				matrix[2][i] = letterToNumber('X');
				break;
			}
			matrix[0][i] = letterToNumber(message.charAt(3*i));
			matrix[1][i] = letterToNumber(message.charAt(3*i+1));
			matrix[2][i] = letterToNumber(message.charAt(3*i+2));
		}
		return matrix;
	}
	public static int letterToNumber(char letter)
	{
		switch(letter)
		{
		case 'A': return 0;
		case 'B': return 1;
		case 'C': return 2;
		case 'D': return 3;
		case 'E': return 4;
		case 'F': return 5;
		case 'G': return 6;
		case 'H': return 7;
		case 'I': return 8;
		case 'J': return 9;
		case 'K': return 10;
		case 'L': return 11;
		case 'M': return 12;
		case 'N': return 13;
		case 'O': return 14;
		case 'P': return 15;
		case 'Q': return 16;
		case 'R': return 17;
		case 'S': return 18;
		case 'T': return 19;
		case 'U': return 20;
		case 'V': return 21;
		case 'W': return 22;
		case 'X': return 23;
		case 'Y': return 24;
		case 'Z': return 25;
		default: return -1;
		}
	}
	public static char numberToLetter(int number)
	{
		switch(number)
		{
		case 0: return 'A';
		case 1: return 'B';
		case 2: return 'C';
		case 3: return 'D';
		case 4: return 'E';
		case 5: return 'F';
		case 6: return 'G';
		case 7: return 'H';
		case 8: return 'I';
		case 9: return 'J';
		case 10: return 'K';
		case 11: return 'L';
		case 12: return 'M';
		case 13: return 'N';
		case 14: return 'O';
		case 15: return 'P';
		case 16: return 'Q';
		case 17: return 'R';
		case 18: return 'S';
		case 19: return 'T';
		case 20: return 'U';
		case 21: return 'V';
		case 22: return 'W';
		case 23: return 'X';
		case 24: return 'Y';
		case 25: return 'Z';
		default: return '?';
		}
	}
}
