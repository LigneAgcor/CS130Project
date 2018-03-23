import java.io.*;
import java.util.*;

public class Reader
{
	public ArrayList<String> tokens = new ArrayList<String>();
	public ArrayList<String> lexemes = new ArrayList<String>();
	String allinLine = "";
	public Reader(String path) throws Exception
	{
		FileReader file = null;
		try 
		{
			file = new FileReader(path);
		}
		catch(FileNotFoundException ae)
		{
			System.out.println("File not Found");
		}
		BufferedReader br = new BufferedReader(file);
		
		String currentLine;
		
		while ((currentLine = br.readLine()) != null)
		{
			allinLine += currentLine;
			allinLine += " ";
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
		while (a.length > i)
		{
			//System.out.println(a[i]);
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
									//tokens.add("COMMENT");
									//addString(start, i+j+2,a);
									i+=j+3;
									j+=a.length;
									done = true;
								}
							}
							if (!done)
							{
								if(j+i >= a.length-1 ) 
								{
									i += j+1;
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
							if((a[i+j] == '>' || compareSpecial(a[i+j]) )  && foundLetter)
							{
								tokens.add("TAGINDENT");
								addString(start, i+j-1,a);
								i+=j;
								j+=a.length;
								done = true;
							}
							if (!done)
							{
								
								if(j+i >= a.length-1 || compareSpecial(a[i+j])) 
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
				// ------------ LTHAN
				if (!worked)
				{
					tokens.add("LTHAN");
					lexemes.add("<");
					worked = true;
					i++;
				}
			}
			// ----------- GTHAN
			if (i < a.length && !worked)
			{
				if (a[i] == '>')
				{
					tokens.add("GTHAN");
					lexemes.add(">");
					worked = true;
					i++;
				}
			}
			// ------------ COLON
			if (i < a.length && !worked)
			{
				if (a[i] == ':')
				{
					tokens.add("COLON");
					lexemes.add(":");
					worked = true;
					i++;
				}
			}
			// ---------- MULT, EXP
			if (i < a.length && !worked)
			{
				
				if (a[i] == '*')
				{
					worked = true;
					boolean done = false;
					if (i+1 < a.length)
					{
						if (a[i+1] == '*')
						{
							tokens.add("EXP");
							lexemes.add("**");
							i+=2;
							done = true;
						}
					}
					if (!done)
					{
						tokens.add("MULT");
						lexemes.add("*");
						i++;
					}
				}
			}
			// ------------ PLUS
			if (i < a.length && !worked)
			{
				if (a[i] == '+')
				{
					tokens.add("PLUS");
					lexemes.add("+");
					worked = true;
					i++;
				}
			}
			// ------------ MINUS
			if (i < a.length && !worked)
			{
				if (a[i] == '-')
				{
					tokens.add("MINUS");
					lexemes.add("-");
					worked = true;
					i++;
				}
			}
			// ------------ DIVIDE
			if (i < a.length && !worked)
			{
				if (a[i] == '/')
				{
					tokens.add("DIVIDE");
					lexemes.add("/");
					worked = true;
					i++;
				}
			}
			// -------------- MODULO
			if (i < a.length && !worked)
			{
				if (a[i] == '%')
				{
					tokens.add("MODULO");
					lexemes.add("%");
					worked = true;
					i++;
				}
			}
			// -------------- LPAREN
			if (i < a.length && !worked)
			{
				if (a[i] == '(')
				{
					tokens.add("LPAREN");
					lexemes.add("(");
					worked = true;
					i++;
				}
			}
			// ------------- RPAREN
			if (i < a.length && !worked)
			{
				if (a[i] == ')')
				{
					tokens.add("RPAREN");
					lexemes.add(")");
					worked = true;
					i++;
				}
			}
			// ------------- EQUALS
			if (i < a.length && !worked)
			{
				if (a[i] == '=')
				{
					tokens.add("EQUALS");
					lexemes.add("=");
					worked = true;
					i++;
				}
			}
			// ------------ COMMA
			if (i < a.length && !worked)
			{
				if (a[i] == ',')
				{
					tokens.add("COMMA");
					lexemes.add(",");
					worked = true;
					i++;
				}
			}
			// ------------ PERIOD
			if (i < a.length && !worked)
			{
				if (a[i] == '.')
				{
					tokens.add("PERIOD");
					lexemes.add(".");
					worked = true;
					i++;
				}
			}
			// ------------ DQUOTE
			if (i < a.length && !worked)
			{
				if (a[i] == '\"')
				{
					tokens.add("DQUOTE");
					lexemes.add("\"");
					worked = true;
					i++;
				}
			}
			// ------------ QUOTE
			if (i < a.length && !worked)
			{
				if (a[i] == '\'')
				{
					tokens.add("QUOTE");
					lexemes.add("\'");
					worked = true;
					i++;
				}
			}
			// ------------ NUMBERS
			if (i < a.length && !worked)
			{
				if (compareNumber(a[i]))
				{
					worked = true;
					int start = i;
					boolean done = false;
					boolean decimal = false;
					boolean open = true;
					boolean exponent = false;
					boolean expdeci = false;
					boolean expneg = false;
					for (int j = 1; i+j < a.length; j++)
					{
						if(a[j+i] == '.'  && !decimal && !exponent)
						{
							decimal = true;
							open = true;
						}
						else if(a[j+i] == '.'  && decimal && !exponent)
						{
							if(open)
							{
								tokens.add("ERROR");
								lexemes.add("***lexical error: badly formed number");
								tokens.add("NUMBER");
								addString(start, i+j,a);
								i+=j+1;
								j+=a.length;
								done = true;
								break;
							}
							else
							{
								tokens.add("NUMBER");
								addString(start, i+j-1,a);
								i+=j;
								j+=a.length;
								done = true;
								break;
							}
						}
						
						if((a[j+i] == 'e' || a[j+i] == 'E') && !exponent)
						{
							exponent = true;
							open = true;
						}
						else if((a[j+i] == 'e' || a[j+i] == 'E') && exponent)
						{
							if(open)
							{
								tokens.add("ERROR");
								lexemes.add("***lexical error: badly formed number");
								tokens.add("NUMBER");
								addString(start, i+j,a);
								i+=j+1;
								j+=a.length;
								done = true;
								break;
							}
							else
							{
								tokens.add("NUMBER");
								addString(start, i+j-1,a);
								i+=j;
								j+=a.length;
								done = true;
								break;
							}
						}
						
						if(a[j+i] == '-' && exponent && !expneg)
						{
							expneg = true;
							open = true;
						}
						else if(a[j+i] == '-' && expneg)
						{
							if(open)
							{
								tokens.add("ERROR");
								lexemes.add("***lexical error: badly formed number");
								tokens.add("NUMBER");
								addString(start, i+j,a);
								i+=j;
								j+=a.length;
								done = true;
								break;
							}
							else
							{
								tokens.add("NUMBER");
								addString(start, i+j-1,a);
								i+=j;
								j+=a.length;
								done = true;
								break;
							}
						}
						
						if(!compareNumber(a[j+i]) && a[j+i] != '.' && (a[j+i] != 'e' && a[j+i] != 'E') && !(a[j+i] == '-' && exponent))
						{
							if(open)
							{
								tokens.add("ERROR");
								lexemes.add("***lexical error: badly formed number");
								tokens.add("NUMBER");
								addString(start, i+j,a);
								i+=j+1;
								j+=a.length;
								done = true;
								break;
							}
							else
							{
								tokens.add("NUMBER");
								addString(start, i+j-1,a);
								i+=j;
								j+=a.length;
								done = true;
								break;
							}
						}
						
						if(compareNumber(a[j+i]))
						{
							if(open)
								open = false;
						}
						if (!done && j+i+1 >= a.length)
						{
							tokens.add("NUMBER");
							addString(start, i+j,a);
							i+=j+1;
							j+=a.length;
							done = true;
						}
					}
				}
			}
			// ------------ IDENT
			if (i < a.length && !worked)
			{
				if (compareLetter(a[i]))
				{
					worked = true;
					int start = i;
					boolean done = false;
					for (int j = 1; j+i < a.length; j++)
					{
						if(!compareLetter(a[j+i]))
						{
							tokens.add("IDENT");
							addString(start, i+j-1,a);
							i+=j;
							j+=a.length;
							done = true;
						}
						if (!done && j+i+1 >= a.length)
						{
							tokens.add("IDENT");
							addString(start, i+j,a);
							i+=j+1;
							j+=a.length;
						}
					}
				}
			}
			//illegal characters
			if (i < a.length && !worked)
			{
				if (a[i] == '$' || a[i] == '!' || a[i] == '&')
				{
					tokens.add("ERROR");
					lexemes.add("***lexical error: illegal character (" + a[i] + ")");
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
		if (ascii == 34 ||
				ascii == 37 ||
				(ascii >= 39 && ascii <= 46) ||
				ascii == 58 ||
				ascii == 59 ||
				b == ' ' ||
				ascii == 60 ||
				ascii == 62 ||
				b == '!' ||
				b == '&' ||
				b == '$')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean compareLetter(char b)
	{
		int ascii = (int) b;
		//33-46
		if ((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean compareNumber(char n)
	{
		int num = (int) n;
		if ((num >= 48 && num <= 57))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void printTest() throws Exception
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
	
	public void commentTest() throws Exception
	{
		String s = "<!-- im a valid comment -->  <!--im invalid:((";
		read(s.toCharArray());
		printTest();
	}
	
	public void tagTest() throws Exception
	{
		String s = "< asd><></";
		read(s.toCharArray());
		printTest();
	}
	
	public void basicTest() throws Exception
	{
		String s = "+/:<\'\"()/+**-->=,.*%";
		read(s.toCharArray());
		printTest();
	}
	public void illegalTest() throws Exception
	{
		String s = "+/$:<\'\"()!+**->=,.&*%";
		read(s.toCharArray());
		printTest();
	}
	public void letterTest() throws Exception
	{
		String s = "< acsd hello world hehe </<";
		read(s.toCharArray());
		printTest();
	}
	public void numberTest() throws Exception
	{
		String s = "123e-10.5asdq";
		read(s.toCharArray());
		printTest();
	}
	public void allTest() throws Exception
	{
		String s = allinLine;
		read(s.toCharArray());
		printTest();
	}
	public static void main(String args[]) throws Exception
	{
		Reader a = new Reader(args[0]);
		a.allTest();
		/*
		System.out.println(a.lexemes.size());
		System.out.println(a.tokens.size());
		for(int i = 0; i < a.lexemes.size(); i++)
		{
			System.out.println(a.lexemes.get(i));
		}
		*/
	}
}
