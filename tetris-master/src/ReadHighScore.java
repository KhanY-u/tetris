import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadHighScore {
	
	static File file = new File("highscore.txt");
	static Scanner scan ;
	static int score;
	
	public static int getScore() { ;
	
	try {
		scan = new Scanner(file);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	while(scan.hasNextLine())
	{
		score = scan.nextInt();
	}
	return score;
	}
}



