package scanner;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Realiza las pruebas al analizador sintactico.
 *
 * @author Mario
 * @version 1.0
 * @since 18/03/15
 */
public class ParserUnitTest
{
    @Test
    public void testVarWithVar()
    {
        Parser parser = new Parser();
        parser.parse(Lexer.getTokens("<var1>::=<var2>;."));
        assertTrue(parser.getOutput().equals("<var1><var2>::=\n"));
    }

    @Test
    public void testVarWithTerm()
    {
        Parser parser = new Parser();
        parser.parse(Lexer.getTokens("<var1>::='terml';."));
        assertTrue(parser.getOutput().equals("<var1>'terml'::=\n"));
    }

    @Test
    public void testGroup()
    {
        Parser parser = new Parser();
        parser.parse(Lexer.getTokens("<var1>::={[(<var2> & 'terml')]};."));
        assertTrue(parser.getOutput().equals("<var1><var2>&'terml')]}::=\n"));
    }

    @Test
    public void testOrOp()
    {
        Parser parser = new Parser();
        parser.parse(Lexer.getTokens("<var1>::=<var2>|<var3>;."));
        String expected = parser.getOutput();
        assertTrue(expected.equals("<var1><var2><var3>|::=\n"));
    }
}
