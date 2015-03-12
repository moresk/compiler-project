package scanner;

/**
 * Clase principal del analizador.
 *
 * Semantica del lenguaje:
 * <ul>
 *     <li>prog - conj</li>
 *     <li>conj - conj | prod</li>
 *     <li>prod - var DEF expr;</li>
 *     <li>expr - expr ALT term | term</li>
 *     <li>term - term & fact | fact</li>
 *     <li>fact - {expr} | [expr] | prim</li>
 *     <li>prim - (expr) | var | term</li>
 * </ul>
 *
 * @author Mario
 * @version 1.0
 * @since 11/03/15
 */
public class Scanner
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        for (Token token : Lexer.getTokens("a1b&|{}[]+-2::=c\';."))
        {
            System.out.println(token.toString());
        }
    }
}
