package scanner;

import java.util.ArrayList;

/**
 * Analizador lexico.
 *
 * Obtiene una lista de tokens en base a una cadena de texto.
 * La cadena de texto debe estar formada, en forma cuerente,
 * por los siguientes simbolos: .;&|{}[]+-'::=0-0a-z
 *
 * @author Mario
 * @version 1.0
 * @since 11/03/15
 */
public class Lexer
{
    /**
     * Transforma una cadena de texto en tokens.
     *
     * @param input Cadena de texto.
     * @return Lista de tokens.
     */
    public static ArrayList<Token> getTokens(String input)
    {
        // Almacenamos la lista de tokens temporalmente.
        ArrayList<Token> tokens = new ArrayList<>();

        // Recorremos la cadena de texto, letra por letra.
        for (int i = 0; i < input.length(); i++)
        {
            // Si el caracter no coincide con ningun token:
            if( input.charAt(i) != Token.Type.EOF
             && input.charAt(i) != Token.Type.END
             && input.charAt(i) != Token.Type.AND
             && input.charAt(i) != Token.Type.OR
             && input.charAt(i) != Token.Type.RBC
             && input.charAt(i) != Token.Type.LBC
             && input.charAt(i) != Token.Type.RBK
             && input.charAt(i) != Token.Type.LBK
             && input.charAt(i) != Token.Type.PLUS
             && input.charAt(i) != Token.Type.MIN
             && input.charAt(i) != Token.Type.APOS)
            {
                // Verificamos que se trate de una definicion.
                if (input.charAt(i) == ':'
                 && input.charAt(i + 1) == ':'
                 && input.charAt(i + 2) == '=')
                {
                    // En caso afirmativo: Agregamos el token a la lista.
                    tokens.add(new Token(Token.Type.DEF, "::="));

                    // El indice es aumentado ya que
                    // dos caracteres mas fueron "analizados" (':' '=').
                    i += 2;
                }
                // Si no coincide en ninguno de los casos:
                else
                {
                    // Informamos sobre el error y en que columna esta.
                    throw new Error
                    (   String.format("Error lexico en columna: %d", i)
                    );
                }
            }
            // Si el caracter coincide con algun token:
            else
            {
                // Lo agregamos a la lista de tokens.
                tokens.add
                (   new Token
                    (   input.charAt(i),
                        String.valueOf(input.charAt(i))
                    )
                );
            }
        }

        return tokens; // Almacenamos la lista permanentemente.
    }
}
