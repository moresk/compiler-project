package scanner;

/**
 * Clase principal del analizador.
 *
 * @author Mario
 * @version 1.0
 * @since 11/03/15
 */
public class Scanner
{
    /**
     * Analiza la expresion de entrada.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Parser parser = new Parser();
        parser.parse
        (   Lexer.getTokens
            (
"<Entero>::= { { ['+'|'-'] & <Variable1> } & (['+'|'-']) & {<Variable2>} } & { ['+'|'-'] & <Variable3> } ;"
            )
        );

        System.out.println(parser.getOutput());
    }
}
