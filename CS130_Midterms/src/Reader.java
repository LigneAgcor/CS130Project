import java.io.*;

public class Reader{
	public Reader() throws Exception
	{
		FileReader file = null;
		FileWriter output = null;
		try {
		file = new FileReader("src\\text files\\text.txt");//"C:\\Users\\great_000\\Documents\\School\\4th year\\2nd Sem\\CS130\\Midterm Proj\\CS130Project\\CS130_Midterms\\src\\text.txt");
		output = new FileWriter("src\\text files\\output.txt");
		}
		catch(FileNotFoundException ae){
			System.out.println("File not Found");
		}
		BufferedReader br = new BufferedReader(file);
		BufferedWriter bw = new BufferedWriter(output);
		
		
		String currentLine;
		String concatenated = "";
		boolean startnewLex = true;
		
		while ((currentLine = br.readLine()) != null)
		{
			System.out.println(currentLine);
			concatenated += currentLine;
			concatenated += "\n";
		}
		
		System.out.println(concatenated);
		
	}
	
	
	public static void main(String args[]) throws Exception
	{
		Reader test = new Reader();
		
	}
	
	
}
