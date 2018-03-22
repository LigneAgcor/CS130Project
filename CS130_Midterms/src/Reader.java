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
			//System.out.println(currentLine);
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
		boolean worked = false;
		while (a.length > i+1)
		{
			//read <        for COMMENT, TAGIDENT, LTHAN
			if (a[i] == '<')
			{
				//COMMENT
				// if '<!--' is read
				if (i+3 < a.length)
				{
					if (a[i+1] == '!' && a[i+2] == '-' && a[i+3] == '-')
					{
						worked = true;
						boolean done = false; 
						int start = i;
						for (int j = 4; j+i < a.length; j++)
						{
							if(i+j+2 < a.length)
							{
								if(a[i+j] == '-' && a[i+j+1] == '-' && a[i+j+2] == '>')
								{
									//for testing comment detection
									tokens.add("COMMENT");
									addString(start, i+j+2,a);
									i+=j;
									j+=a.length;
									done = true;
								}
							}
							if (!done)
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
					}
				}
				//ENDTAGHEAD
				if (i+1 < a.length && !worked)
				{
					if (a[i+1] == '/')
					{
						worked = true;
						tokens.add("ENDTAGHEAD");
						lexemes.add("</");
						i+=2;
					}
				}
				//TAGINDENT
				if (i+1 < a.length && !worked)
				{
					if (!compareSpecial(a[i+1]))
					{
						boolean done = false;
						worked = true;
						int start = i;
						boolean foundLetter = false; 
						for (int j = 1; j+i < a.length; j++)
						{
							if(a[i+j] == '>' && foundLetter)
							{
								tokens.add("TAGINDENT");
								addString(start, i+j-1,a);
								i+=j;
								j+=a.length;
								done = true;
							}
							if (!done)
							{
								
								if(j+i >= a.length-1 ) 
								{
									j+=a.length;
									worked = false;
								}
								else
								{
									foundLetter = true;
								}
							}
						}
					}
				}
				if (!worked)
				{
					tokens.add("LTHAN");
					lexemes.add("<");
					worked = true;
					i++;
				}
			}
			if (i < a.length)
			{
				if (a[i] == '>')
				{
					tokens.add("GTHAN");
					lexemes.add(">");
					worked = true;
					i++;
				}
			}
			if (!worked)
			{
				i++;
			}
			else
			{
				worked = false;
			}
		}
	}
	public boolean compareSpecial(char b)
	{
		int ascii = (int) b;
		//33-46
		if (ascii == 34 || ascii == 37 || (ascii >= 39 && ascii <= 46) || ascii == 58 || ascii == 59)
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
	
	public void tagTest()
	{
		String s = "<asd><></";
		read(s.toCharArray());
		printTest();
	}
	
	public static void main(String args[]) throws Exception
	{
		Reader a = new Reader();
		a.tagTest();
	}
}
