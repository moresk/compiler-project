package scanner;

import java.util.ArrayList;

/**
 * Analizador sintactico.
 *
 * Analiza y verifica que el grupo de tokens sea valido
 * en base a la siguiente semantica del lenguaje.
 *
 * <ul>
 *     <li>prog - conj .</li>
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
 * @since 16/03/15
 */
public class Parser
{
    private ArrayList<Token> tokens;

    private int next = 0;
    private Token currentToken;

    private String output = "";

    /**
     * Obtiene la salida del analizador.
     *
     * @return La salida del analizador.
     */
    public String getOutput()
    {
        return output;
    }

    /**
     * Inicia el analisis.
     *
     * @param tokens Grupo de tokens a analizar.
     */
    public void parse(ArrayList<Token> tokens)
    {
        this.tokens = tokens;
        currentToken = tokens.get(next++);
        prog();
    }

    /**
     * Verifica que el programa este formado de la siguiente forma.
     *
     * <ul>
     *      <li>conj.</li>
     * </ul>
     */
    private void prog()
    {
        conj();

        if (currentToken.getType() != Token.Type.EOF)
        {
            throw new Error
            (   String.format
                (   "Error de sintaxis. Se tiene: %s ~~~ Se esperaba: %s",
                    currentToken.getData(), (char) Token.Type.EOF
                )
            );
        }
    }

    /**
     * Verifica que el conjunto este formado por una o mas producciones.
     */
    private void conj()
    {
        prod();

        while (currentToken.getType() == Token.Type.END)
        {
            output = String.format("%s\n", output);

            currentToken = tokens.get(next++);
            if (currentToken.getType() == Token.Type.EOF)
            {
                break;
            }

            prod();
        }
    }

    /**
     * Verifica que la produccion este formada de la siguiente forma.
     *
     * <ul>
     *      <li>var::=expr;</li>
     * </ul>
     */
    private void prod()
    {
        if (currentToken.getType() == Token.Type.VAR)
        {
            output = String.format("%s%s", output, currentToken.getData());
        }
        else
        {
            throw new Error
            (   "Error de sintaxis. "
              + "Toda produccion debe de iniciar con una variable."
            );
        }

        currentToken = tokens.get(next++);
        if (currentToken.getType() == Token.Type.DEF)
        {
            currentToken = tokens.get(next++);
            expr();

            output = String.format("%s%s", output, "::=");
        }
        else
        {
            throw new Error("Error de sintaxis. Se esperaba: ::=");
        }
    }

    /**
     * Verifica que la exprecion este formada por alguna de las sig formas.
     *
     * <ul>
     *      <li>expr | term</li>
     *      <li>term</li>
     * </ul>
     */
    private void expr()
    {
        term();

        while (currentToken.getType() == Token.Type.OR)
        {
            currentToken = tokens.get(next++);
            expr();

            output = String.format("%s%s", output, (char) Token.Type.OR);
        }
    }

    /**
     * Verifica que el termino este formado por alguna de las sig formas.
     *
     * <ul>
     *      <li>term & fact</li>
     *      <li>fact</li>
     * </ul>
     */
    private void term()
    {
        fact();

        currentToken = tokens.get(next++);
        while (currentToken.getType() == Token.Type.AND)
        {
            output = String.format("%s%s", output, (char) Token.Type.AND);

            currentToken = tokens.get(next++);
            term();
        }
    }

    /**
     * Verifica que el factor este formado por alguna de las sig formas.
     *
     * <ul>
     *      <li>{expr}</li>
     *      <li>[expr]</li>
     *      <li>prim</li>
     * </ul>
     */
    private void fact()
    {
        if (currentToken.getType() == Token.Type.LBC)
        {
            currentToken = tokens.get(next++);
            expr();

            if (currentToken.getType() == Token.Type.RBC)
            {
                output = String.format("%s%s", output, currentToken.getData());
            }
            else
            {
                throw new Error
                (   String.format
                    (   "Error de sintaxis. Se tiene: %s ~~~ Se esperaba: %s",
                        currentToken.getData(), (char) Token.Type.RBC
                    )
                );
            }
        }
        else if (currentToken.getType() == Token.Type.LBK)
        {
            currentToken = tokens.get(next++);
            expr();

            if (currentToken.getType() == Token.Type.RBK)
            {
                output = String.format("%s%s", output, currentToken.getData());
            }
            else
            {
                throw new Error
                (   String.format
                    (   "Error de sintaxis. Se tiene: %s ~~~ Se esperaba: %s",
                        currentToken.getData(), (char) Token.Type.RBK
                    )
                );
            }
        }
        else
        {
            prim();
        }
    }

    /**
     * Verifica que el primario este formado por alguna de las sig formas.
     *
     * <ul>
     *      <li>(expr)</li>
     *      <li>var</li>
     *      <li>terml</li>
     * </ul>
     */
    private void prim()
    {
        if (currentToken.getType() == Token.Type.LPS)
        {
            currentToken = tokens.get(next++);
            expr();

            if (currentToken.getType() == Token.Type.RPS)
            {
                output = String.format("%s%s", output, currentToken.getData());
            }
            else
            {
                throw new Error
                (   String.format
                    (   "Error de sintaxis. Se tiene: %s ~~~ Se esperaba: %s",
                        currentToken.getData(), (char) Token.Type.RPS
                    )
                );
            }
        }
        else if (currentToken.getType() == Token.Type.VAR
              || currentToken.getType() == Token.Type.TERML)
        {
            output = String.format("%s%s", output, currentToken.getData());
        }
        else
        {
            throw new Error
            (   String.format
                (   "Error de sintaxis. Se tiene: %s ~~~ "
                  + "Se esperaba: '(' || 'VAR' || 'TERML'.",
                    currentToken.getData()
                )
            );
        }
    }
}
