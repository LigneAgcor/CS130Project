import java.io.*;

public class Reader{
	public Reader() throws Exception
	{
		FileReader file = null;
		try {
		file = new FileReader("text.txt");
		}
		catch(FileNotFoundException ae){
			System.out.println("File not Found");
		}
		BufferedReader br = new BufferedReader(file);
		
		String currentLine;
		
		while ((currentLine = br.readLine()) != null)
		{
			System.out.println(currentLine);
		}
		
	}
	
	public static void main(String args[]) throws Exception
	{
		Reader test = new Reader();
		
		System.out.println("Run testing");
	}
	
	
}
