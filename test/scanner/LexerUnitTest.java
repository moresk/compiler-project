package scanner;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Realiza las pruebas al analizador lexico.
 *
 * @author Mario
 * @version 1.0
 * @since 18/03/15
 */
public class LexerUnitTest
{
    @Test
    public void testAllTokens()
    {
        ArrayList<Token> expected = new ArrayList<Token>()
        {{
            add(new Token(Token.Type.EOF, "."));
            add(new Token(Token.Type.END, ";"));
            add(new Token(Token.Type.AND, "&"));
            add(new Token(Token.Type.OR, "|"));
            add(new Token(Token.Type.RBC, "}"));
            add(new Token(Token.Type.LBC, "{"));
            add(new Token(Token.Type.RBK, "]"));
            add(new Token(Token.Type.LBK, "["));
            add(new Token(Token.Type.RPS, ")"));
            add(new Token(Token.Type.LPS, "("));
            add(new Token(Token.Type.DEF, "::="));
            add(new Token(Token.Type.TERML, "\'term\'"));
            add(new Token(Token.Type.VAR, "<var>"));

        }};

        ArrayList<Token> actual =
            Lexer.getTokens(".;&|}{][)(::=\'term\'<var>");

        for (int i = 0; i < actual.size(); i++)
        {
            assertEquals
            (   expected.get(i).getType(),
                actual.get(i).getType()
            );
        }
    }

    @Test
    public void testGroup()
    {
        Token var = Lexer.getTokens("<v1r1>").get(0);
        assertEquals(var.getData(), "<v1r1>");
        assertEquals(var.getType(), Token.Type.VAR);

        Token term =
            Lexer.getTokens("\'1t!@#$%^&*()-+=::=\\erm2\'").get(0);
        assertEquals(term.getData(), "\'1t!@#$%^&*()-+=::=\\erm2\'");
        assertEquals(term.getType(), Token.Type.TERML);
    }

    @Test
    public void testIncGroup()
    {
        try
        {
            Lexer.getTokens("<a");
            assertTrue(false);
        }
        catch (Error e)
        {
            try
            {
                Lexer.getTokens("\'b");
                assertTrue(false);
            }
            catch (Error ee)
            {
                assertTrue(true);
            }
        }
        catch (Exception ex)
        {
            assertTrue(false);
        }
    }

    @Test
    public void testDef()
    {
        try
        {
            Lexer.getTokens("::");
            assertTrue(false);
        }
        catch (Error e)
        {
            try
            {
                Lexer.getTokens(":=:");
                assertTrue(false);
            }
            catch (Error ee)
            {
                assertTrue(true);
            }
        }
        catch (Exception ex)
        {
            assertTrue(false);
        }
    }
}
