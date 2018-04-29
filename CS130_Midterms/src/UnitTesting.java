import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class UnitTesting {
	
	String sample = "<!-- a comment -->\r\n" + 
			"<table>\r\n" + 
			"<tr><td> product </td> <td> price </td> </tr>\r\n" + 
			"<tr> <td> pencil </td> <td> 5.55 </td>    </tr>\r\n" + 
			"<tr> <td> paper </td> <td> = 1 + 2 * 3 </td> </tr>\r\n" + 
			"</table>";
	
	Reader rabbit = new Reader();
	
	@Test
	public void parseTest()
	{
		rabbit.read(sample.toCharArray());
		assertEquals("Success!", "product,price\n"
				+ "pencil,5.55\n"
				+ "paper,7.0\n", rabbit.parseTokens());
	}
	
	@Test
	public void analyzerTest() throws Exception
	{
		String test = "<!--comment--> \n"
				+ "+ - = ( ) * 5.1";
		rabbit.read(test.toCharArray());
		ArrayList<String> lexemes = new ArrayList<String>() {{
		    add("+");
		    add("-");
		    add("=");
		    add("(");
		    add(")");
		    add("*");
		    add("5.1");
		}};
		ArrayList<String> tokens = new ArrayList<String>() {{
		    add("PLUS");
		    add("MINUS");
		    add("EQUALS");
		    add("LPAREN");
		    add("RPAREN");
		    add("MULT");
		    add("NUMBER");
		}};
		assertEquals(lexemes , rabbit.lexemes);
		assertEquals(tokens , rabbit.tokens);
	}

}
