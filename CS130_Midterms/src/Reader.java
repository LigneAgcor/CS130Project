import java.io.*;
import java.util.*;
import javax.script.*;

public class Reader
{
	public ArrayList<String> tags = new ArrayList<String>();
	public ArrayList<String> output = new ArrayList<String>();
	public ArrayList<String> tokens = new ArrayList<String>();
	public ArrayList<String> lexemes = new ArrayList<String>();
	String allinLine = "";
	public Reader(String input) throws Exception
	{
			FileReader file = null;
			try
			{
				file = new FileReader(input);
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
				//allinLine += " ";
			}
		
		//System.out.println(allinLine);
		
	}
	
	public Reader()
	{
		allinLine = "This is for Unit Testing";
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
			//read < for COMMENT, TAGIDENT, LTHAN
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
								tokens.add("TAGIDENT");
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
						if (i+2 < a.length)
						{
							
							if (a[i+1] == '*' && a[i+2] == '*')
							{
								tokens.add("KLEENE");
								lexemes.add("***");
								i+=3;
								done = true;
							}
						}
						if (!done && a[i+1] == '*')
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
			// ------------ LBRACKET
			if (i < a.length && !worked)
			{
				if (a[i] == '[')
				{
					tokens.add("LBRACKET");
					lexemes.add("[");
					worked = true;
					i++;
				}
			}
			// ------------ RBRACKET
			if (i < a.length && !worked)
			{
				if (a[i] == ']')
				{
					tokens.add("RBRACKET");
					lexemes.add("]");
					worked = true;
					i++;
				}
			}
			// ------------ SCOLON
			if (i < a.length && !worked)
			{
				if (a[i] == ';')
				{
					tokens.add("SCOLON");
					lexemes.add(";");
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
					boolean open = false;
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
				b == '$' ||
				b == ';')
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


int count = 0;
	
	public String parseTokens(ArrayList<String> tokens, ArrayList<String> lexemes)
	{
		String out = "";
		while(count < tokens.size())
		{
			String tkn = tokens.get(count);
			//String lxm = lexemes.get(ctr);
			if(tkn.equals("TAGIDENT"))
			{
				while(!tokens.get(count).equals("GTHAN"))
				{
					count++;
					tkn = tokens.get(count);
				}
				out += tagOpen();
			}
			else if(lexemes.get(count).equals("tr"))
			{
				
				out += "\n";
			}
			else if(lexemes.get(count).equals("td") && !lexemes.get(count+3).equals("tr"))
			{
				
				out += ",";
			}
			if(tkn.equals("ERROR"))
			{
				out += "ERROR FOUND" + lexemes.get(count);
				break;
			}
			count++;
		}
		
		return out;
	}
	
	public String parseTokens()
	{
		return parseTokens(tokens, lexemes);
	}
	
	String tagOpen()
	{
		String concatenated = "";
		while(!tokens.get(count).equals("ENDTAGHEAD"))
		{
			if(tokens.get(count).equals("ERROR"))
			{
				concatenated = "ERROR FOUND" + lexemes.get(count);
				break;
			}
			if(tokens.get(count).equals("TAGIDENT"))
			{
				while(!tokens.get(count).equals("GTHAN"))
				{
					count++;
				}
				count++;
				concatenated += tagOpen();
			}
			else
			{
				concatenated += parse();
			}
		}
		return concatenated;
	}

String parse()
	{
		String concatenated = "";
		boolean calculate = false;
		boolean bracketed = false;
		while(!tokens.get(count).equals("ENDTAGHEAD"))
		{
				if(tokens.get(count).equals("ERROR"))
				{
					concatenated = "ERROR FOUND" + lexemes.get(count);
					break;
				}
				if(tokens.get(count).equals("LBRACKET"))
				{
					bracketed = true;
				}
				if(tokens.get(count).equals("IDENT"))
				{
					concatenated += lexemes.get(count);
				}
				if(tokens.get(count).equals("EQUALS"))
				{
					calculate = true;
					count++;
					concatenated += calculate(bracketed);
				}
				if(tokens.get(count).equals("NUMBER"))
				{
					concatenated += lexemes.get(count);
				}
				if(tokens.get(count).equals("RBRACKET"))
				{
					bracketed = false;
				}
			if(!calculate)
				count++;
		}
		return concatenated;
	}

String calculate(boolean bracketed)
{
	ScriptEngineManager mgr = new ScriptEngineManager();
	ScriptEngine engine = mgr.getEngineByName("JavaScript");
	String expression = "";
	float result = 0;
	while(!tokens.get(count).equals("ENDTAGHEAD"))
	{
		if(tokens.get(count).equals("ERROR"))
		{
			expression = "ERROR FOUND" + lexemes.get(count);
			break;
		}
		
		boolean exp = false;
		if(tokens.get(count+1).equals("EXP"))
		{
			expression += ("Math.pow(" + lexemes.get(count) + "," + lexemes.get(count+2) + ")");
			count += 3;
			exp = true;
		}
		if(!tokens.get(count).equals("RBRACKET") && !exp)
			expression += lexemes.get(count);
		if(!exp)
			count++;
	}
	try {
		result = Float.parseFloat(engine.eval(expression).toString());
	} catch (Exception e) {}
	
	if(!bracketed)
		return "" + result;
	else
		return expression;
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
	/*
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
		String s = "123.+2";
		read(s.toCharArray());
		printTest();
	}*/

	public void allTest() throws Exception
	{
		String s = allinLine;
		read(s.toCharArray());
		//printTest();
		outputToCSV(parseTokens());
	}
	
	public void outputToCSV(String write)
	{
		try
		{
			FileWriter writer = new FileWriter("src\\text files\\test.csv");
			//System.out.println(write);
			writer.append(write);
			writer.flush();
			writer.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String args[]) throws Exception
	{
		Reader a = new Reader("src\\text files\\text.txt");
		a.allTest();
	}
}
