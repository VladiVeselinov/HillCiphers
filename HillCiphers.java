import java.util.Scanner;

/**
 * The program has numerous functions related to Hill Substitution Ciphers.
 * 
 * @author 		Vladi Veselinov
 * @version		0.3
 */
public class HillCiphers 
{
	public static void main(String[] args) 
	{
		Scanner keyboard = new Scanner(System.in);
		
		int[][] messageMatrix = new int[][]{{-1},{-1},{-1}};
		int columns = 0;
		boolean messageEntered = false;
		
		int[][] key = new int[3][3];
		boolean keyEntered = false;
		
		int option;
		System.out.println("Select 8 to view a list of options");
		do
		{
			System.out.print("Select an option: ");
			option = keyboard.nextInt();
			// Find the mod 26 of a number
			if(option == 1)
			{
				System.out.print("Enter the number: ");
				int number = keyboard.nextInt();
				System.out.println(number + " = " + mod(number,26) + "(mod 26)");
			}
			// Save a string message into a matrix
			else if(option == 2)
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
				messageMatrix = toMatrix(message);
				messageEntered = true;
				columns = messageMatrix[0].length;
				for(int i=0;i<3;i++)
				{
					for(int j=0;j<columns;j++)
						System.out.print(messageMatrix[i][j]+"\t");
					System.out.print("\n");
				}
			}
			// Print saved message
			else if(option == 3)
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
						char letter = numberToLetter(messageMatrix[i][j]);
						message = message + Character.toString(letter);
					}
				}
				System.out.println(message);
			}
			// Enter a key matrix
			else if(option == 4)
			{
				System.out.println("The key must be a 3 by 3 matrix."); 
				System.out.print("Enter the first row: ");
				key[0][0] = keyboard.nextInt();
				key[0][1] = keyboard.nextInt();
				key[0][2] = keyboard.nextInt();
				System.out.print("Enter the second row: ");
				key[1][0] = keyboard.nextInt();
				key[1][1] = keyboard.nextInt();
				key[1][2] = keyboard.nextInt();
				System.out.print("Enter the third row: ");
				key[2][0] = keyboard.nextInt();
				key[2][1] = keyboard.nextInt();
				key[2][2] = keyboard.nextInt();
				keyEntered = true;
				System.out.println("Key matrix saved.");
			}
			// Crypt using the key
			else if(option == 5)
			{
				if(!messageEntered)
				{
					System.out.println("Error: there is no saved message!");
					continue;
				}
				if(!keyEntered)
				{
					System.out.println("Error: the key hasn't been entered yet!");
					continue;
				}
				messageMatrix = crypt(key, messageMatrix);
				for(int i=0;i<3;i++)
				{
					for(int j=0;j<columns;j++)
						System.out.print(messageMatrix[i][j]+"\t");
					System.out.print("\n");
				}
			}
			// Determine whether the key is invertible
			else if(option == 6)
			{
				if(!keyEntered)
				{
					System.out.println("Error: the key hasn't been entered yet!");
					continue;
				}
				int det = det3(key);
				System.out.println("The determinant of the key is " + det);
				System.out.println(det + " = " + mod(det,13) + "(mod 13)");
				System.out.println(det + " = " + mod(det,2) + "(mod 2)");
				if(mod(det,13) == 0 || mod(det,2) == 0)
					System.out.println("The key is not invertible modulo 26");
				else
					System.out.println("The key is invertible modulo 26");
			}
			else if(option == 7)
			{
				if(!keyEntered)
				{
					System.out.println("Error: the key hasn't been entered yet!");
					continue;
				}
				int det = det3(key);
				if(mod(det,13) == 0 || mod(det,2) == 0)
				{
					System.out.println("Error: the key is not invertible modulo 26!");
					continue;
				}
				key = invert(key,true);
				for(int i=0;i<3;i++)
				{
					for(int j=0;j<3;j++)
						System.out.print(key[i][j]+"\t");
					System.out.print("\n");
				}
			}
			// View list of options
			else if(option == 8)
			{
				System.out.println("1) Find the mod 26 of a number");
				System.out.println("2) Save a string message into a matrix");
				System.out.println("3) Print the saved message");
				System.out.println("4) Enter a key matrix");
				System.out.println("5) Code/Decode the message using the key matrix");
				System.out.println("6) Determine whether the key is invertible");
				System.out.println("7) Invert the key");
				System.out.println("8) View this list of options");
				System.out.println("0) Exit this program");
			}
			else if(option == 0)
				System.out.println("Good bye!");
			else
				System.out.println("Invalid option!");
		}
		while(option != 0);
		keyboard.close();
	}
	public static int mod(int number,int mod)
	{
		int remainder = number%mod;
		if(remainder >= 0)
			return remainder;
		else
			return remainder+mod;
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
	public static int[][] crypt(int[][] key, int[][] m)
	{
		int columns = m[0].length;
		if(columns == 0)
		{
			int[][] nullMatrix = new int[][]{{-1},{-1},{-1}};
			return nullMatrix;
		}
		int[][] result = new int[3][columns];
		for(int i = 0; i < columns; i++)
		{
			result[0][i] = key[0][0] * m[0][i] + key[0][1] * m[1][i] + key[0][2] * m[2][i];
			result[0][i] = mod(result[0][i],26);
			result[1][i] = key[1][0] * m[0][i] + key[1][1] * m[1][i] + key[1][2] * m[2][i];
			result[1][i] = mod(result[1][i],26);
			result[2][i] = key[2][0] * m[0][i] + key[2][1] * m[1][i] + key[2][2] * m[2][i];
			result[2][i] = mod(result[2][i],26);
		}
		return result;
	}
	// Calculates the determinant of a 3 by 3 matrix only
	public static int det3(int[][] m)
	{
		int det = m[0][0] * ((m[1][1] * m[2][2]) - (m[1][2] * m[2][1]));
		det -= m[1][0] * ((m[0][1] * m[2][2]) - (m[0][2] * m[2][1]));
		det += m[2][0] * ((m[0][1] * m[1][2]) - (m[0][2] * m[1][1]));
		return det;
	}
	// Finds the modulo 26 inverse of a 3 by 3 matrix only
	public static int[][] invert(int[][] m, boolean verbose)
	{
		int det = det3(m);
		if(det == 0)
		{
			int[][] nullMatrix = new int[][]{{-1},{-1},{-1}};
			return nullMatrix;
		}
		if(verbose)
			System.out.println("Determinant = " + det);
		det = mod(det,26);
		if(verbose)
			System.out.println("Determinant = " + det + "(mod 26)");
		int detInv = inv26(det);
		if(verbose)
			System.out.println("Determinant inverse(mod 26) = " + det);
		int[][] inv = new int[3][3];
		inv[0][0] = m[1][1] * m[2][2] - m[1][2] * m[2][1];
		inv[0][1] = m[0][2] * m[2][1] - m[0][1] * m[2][2];
		inv[0][2] = m[0][1] * m[1][2] - m[0][2] * m[1][1];
		inv[1][0] = m[1][2] * m[2][0] - m[1][0] * m[2][2];
		inv[1][1] = m[0][0] * m[2][2] - m[0][2] * m[2][0];
		inv[1][2] = m[0][2] * m[1][0] - m[0][0] * m[1][2];
		inv[2][0] = m[1][0] * m[2][1] - m[1][1] * m[2][0];
		inv[2][1] = m[0][1] * m[2][0] - m[0][0] * m[2][1];
		inv[2][2] = m[0][0] * m[1][1] - m[0][1] * m[1][0];
		if(verbose)
		{
			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++)
					System.out.print(inv[i][j]+"\t");
				System.out.print("\n");
			}
		}
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				inv[i][j] = mod(inv[i][j] * detInv,26);
		return inv;		
	}
	// Finds the mod 26 inverse of a number
	public static int inv26(int n)
	{
		n = mod(n,26);
		switch(n)
		{
		case 1: return 1;
		case 3: return 9;
		case 5: return 21;
		case 7: return 15;
		case 9: return 3;
		case 11: return 19;
		case 15: return 7;
		case 17: return 23;
		case 19: return 11;
		case 21: return 5;
		case 23: return 17;
		case 25: return 25;
		default: return -1;
		}
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
