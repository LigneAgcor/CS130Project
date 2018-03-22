import java.io.*;
import java.util.*;

public class Reader
{
	public ArrayList<String> tokens = new ArrayList<String>();
	public ArrayList<String> lexemes = new ArrayList<String>();
	public Reader() throws Exception
	{
		FileReader file = null;
		try 
		{
			file = new FileReader("src\\text files\\text.txt");
		}
		catch(FileNotFoundException ae)
		{
			System.out.println("File not Found");
		}
		BufferedReader br = new BufferedReader(file);
		
		String currentLine;
		
		while ((currentLine = br.readLine()) != null)
		{
			System.out.println(currentLine);
		}
		
	}
	
	public void read(char[] a)
	{	
		int i = 0;
		int counter = 0;
		String savedstate = "";
		while (a.length > i+1)
		{
			//COMMENT, TAGIDENT, LTHAN
			if (a[i] == '<' || savedstate.equals("<"))
			{
				savedstate = "<";
				//COMMENT
				// if '<!--' is read
				if (i+6 < a.length)
				{
					if (a[i+1] == '!' && a[i+2] == '-' && a[i+3] == '-' || savedstate.equals("<"))
					{
						for (int j = 4; j+i < a.length; j++)
						{
							if(i+j+2 < a.length)
							{
								if(a[i+j] == '-' && a[i+j+1] == '-' && a[i+j+2] == '>')
								{
									i+=j;
								}
							}
							if (i+j < a.length)
							{
								System.out.println("im called");
								if(j+i >= a.length-1 ) 
								{
									i += j;
									j+=a.length;
									tokens.add("ERROR");
									lexemes.add("**lexical error: unexpected end of file");
								}
							}
						}
						
						/*
						
						System.out.println(counter);
						counter += 3;
						//if the end comment is read
						if(a[i+counter+1] == '-' && a[i+counter+2] == '-' && a[i+counter+3] == '>')
						{
							i+=counter;
							counter = 0;
						}
						//if comment is not yet done
						else
						{
	
							System.out.println(counter);
							System.out.println("im called");
							if(counter+i < a.length-1 ) 
							{
								counter++;
							}
							else 
							{
								i += counter;
								counter = 0;
								tokens.add("ERROR");
								lexemes.add("**lexical error: unexpected end of file");
							}
						}
						*/
					}
				}
				else if (a[i+counter+1] == '/')
				{
					
				}
			}
		}
	}
	public boolean compareSpecial(char b)
	{
		if (b == '+' || b == '-' || b == '*' || b == '%' || b == '(' || b == ')' || b == ':' || b == ',' || b == '.' || b == '"')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void printTest()
	{
		for (int i = 0; i < tokens.size(); i++)
		{
			if (tokens.get(i).equals("ERROR"))
			{
				System.out.println(lexemes.get(i));
			}
			else
			{
				System.out.println(tokens.get(i) + "\t" + lexemes.get(i));
			}
		}
	}
	
	public void commentTest()
	{
		String s = "<!-- im a comment --> <!-- im an invalid comment";
		read(s.toCharArray());
		printTest();
	}
	
	public static void main(String args[]) throws Exception
	{
		Reader a = new Reader();
		a.commentTest();
	}
}
