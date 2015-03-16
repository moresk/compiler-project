package scanner;

import java.util.ArrayList;

/**
 *
 * @author Mario
 */
public class Parser
{
    private ArrayList<Token> tokens;

    private int next = 0;
    private Token currentToken;

    private String output;

    public void parse(ArrayList<Token> tokens)
    {
        this.tokens = tokens;
        currentToken = tokens.get(next++);
        prod();
    }

    private void prod()
    {
        prim();

        currentToken = tokens.get(next++);
        if (currentToken.getType() == Token.Type.DEF)
        {
            currentToken = tokens.get(next++);
            expr();
            output = String.format("%s%s", output, Token.Type.DEF);
        }
        else
        {
            throw new Error("Error de sintaxis. Se esperaba: ::=");
        }

        if (currentToken.getType() != Token.Type.END)
        {
            throw new Error
            (   String.format
                (   "Error de sintaxis. Se esperaba: %s",
                    (char) Token.Type.END
                )
            );
        }
    }

    private void expr()
    {
        term();

        if (currentToken.getType() == Token.Type.OR)
        {
            output = String.format("%s%s", output, Token.Type.OR);
        }
        else
        {
            throw new Error
            (   String.format
                (   "Error de sintaxis. Se esperaba: %s",
                    (char) Token.Type.OR
                )
            );
        }
    }

    private void term()
    {
        fact();

        if (currentToken.getType() == Token.Type.AND)
        {
            output = String.format("%s%s", output, Token.Type.AND);
        }
        else
        {
            throw new Error
            (   String.format
                (   "Error de sintaxis. So tiene: %s ~~~ Se esperaba: %s",
                    currentToken.getData(), (char) Token.Type.AND
                )
            );
        }
    }

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
                    (   "Error de sintaxis. Se esperaba: %s",
                        (char) Token.Type.RBC
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
                    (   "Error de sintaxis. Se esperaba: %s",
                        (char) Token.Type.RBK
                    )
                );
            }
        }
        else
        {
            prim();
        }
    }

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
                    (   "Error de sintaxis. Se esperaba: %s",
                        (char) Token.Type.RPS
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
            (   "Error de sintaxis. Se esperaba: '(' || 'VAR' || 'TERML'."
            );
        }
    }

    public String getOutput()
    {
        return output;
    }
}
