import java.io.*;

public class Reader{
	public Reader() throws Exception
	{
		FileReader file = null;
		try {
		file = new FileReader("text.txt");//"C:\\Users\\great_000\\Documents\\School\\4th year\\2nd Sem\\CS130\\Midterm Proj\\CS130Project\\CS130_Midterms\\src\\text.txt");
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
