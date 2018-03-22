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
	
	public void addString(int start, int end, char[] a)
	{
		String s = "";
		 for (int i = start; i <= end; i++)
		{
			s += a[i]; 
		}
		 lexemes.add(s);
	}
	
	public void read(char[] a)
	{	
		int i = 0;
		int counter = 0;
		while (a.length > i+1)
		{
			//COMMENT, TAGIDENT, LTHAN
			if (a[i] == '<')
			{
				//COMMENT
				// if '<!--' is read
				if (i+3 < a.length)
				{
					if (a[i+1] == '!' && a[i+2] == '-' && a[i+3] == '-')
					{
						boolean done = false; 
						int start = i;
						for (int j = 4; j+i < a.length; j++)
						{
							//System.out.println(a[i+j] + "" +  a[i+j+1] + "" + a[i+j+2]);
							if(i+j+2 < a.length)
							{
								if(a[i+j] == '-' && a[i+j+1] == '-' && a[i+j+2] == '>')
								{
									tokens.add("COMMENT");
									addString(start, i+j+2,a);
									i+=j;
									j+=a.length;
									done = true;
								}
							}
							if (i+j < a.length && !done)
							{
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
			i++;
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
		String s = "<!-- im a valid comment -->  <!--im invalid:((";
		read(s.toCharArray());
		printTest();
	}
	
	public static void main(String args[]) throws Exception
	{
		Reader a = new Reader();
		a.commentTest();
	}
}
