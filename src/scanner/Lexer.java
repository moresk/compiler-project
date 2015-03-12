package scanner;

import java.util.ArrayList;

/**
 * Analizador lexico.
 *
 * Obtiene una lista de tokens en base a una cadena de texto.
 * La cadena de texto debe estar formada, en forma coherente,
 * por los siguientes simbolos: .;&|{}[]+-'::=0-9a-z
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

        String completeVar = "";
        boolean lexingVar = false;

        String completeNum = "";
        boolean lexingNum = false;

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
                    // Si estabamos analizando una letra:
                    if (lexingVar == true)
                    {
                        // Quiere decir que terminamos de analizar la variable.
                        lexingVar = false;

                        // Agregamos la variable completa a la lista.
                        tokens.add
                        (   new Token
                            (   Token.Type.VAR,
                                completeVar
                            )
                        );

                        completeVar = ""; // Evitamos acomulaciones.
                    }
                    // O si estabamos analiznado un numero:
                    else if (lexingNum == true)
                    {
                        // Quiere decir que terminamos de analizar el numero.
                        lexingNum = false;

                        // Agregamos el numero completo a la lista.
                        tokens.add
                        (   new Token
                            (   Token.Type.NUM,
                                completeNum
                            )
                        );

                        completeNum = ""; // Evitamos acomulaciones.
                    }

                    // Agregamos el token a la lista.
                    tokens.add(new Token(Token.Type.DEF, "::="));

                    // El indice es aumentado dos veces ya que
                    // dos caracteres mas fueron "analizados" (':' '=').
                    i += 2;
                }
                // Si no: Verificamos que sea una letra:
                else if (Character.isLetter(input.charAt(i)) == true)
                {
                    // Si estabamos analiznado un numero:
                    if (lexingNum == true)
                    {
                        // Quiere decir que terminamos de analizar el numero.
                        lexingNum = false;

                        // Agregamos el numero completo a la lista.
                        tokens.add
                        (   new Token
                            (   Token.Type.NUM,
                                completeNum
                            )
                        );

                        completeNum = ""; // Evitamos acomulaciones.
                    }

                    // Indicamos que estamos analizando una variable.
                    lexingVar = true;

                    // Agregamos la letra a la variable.
                    completeVar =
                        String.format("%s%s", completeVar, input.charAt(i));
                }
                // Si no: Verificamos que sea un numero:
                else if (Character.isDigit(input.charAt(i)) == true)
                {
                    // Si estabamos analizando una letra:
                    if (lexingVar == true)
                    {
                        // Quiere decir que terminamos de analizar la variable.
                        lexingVar = false;

                        // Agregamos la variable completa a la lista.
                        tokens.add
                        (   new Token
                            (   Token.Type.VAR,
                                completeVar
                            )
                        );

                        completeVar = ""; // Evitamos acomulaciones.
                    }

                    // Indicamos que estamos analizando un numero.
                    lexingNum = true;

                    // Agregamos la letra como numero.
                    completeNum =
                        String.format("%s%s", completeNum, input.charAt(i));
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
                // Y estabamos analizando una letra:
                if (lexingVar == true)
                {
                    // Quiere decir que terminamos de analizar la variable.
                    lexingVar = false;

                    // Agregamos la variable completa a la lista.
                    tokens.add
                    (   new Token
                        (   Token.Type.VAR,
                            completeVar
                        )
                    );

                    completeVar = ""; // Limpiamos para evitar acomulaciones.
                }
                // O si estabamos analiznado un numero:
                else if (lexingNum == true)
                {
                    // Quiere decir que terminamos de analizar el numero.
                    lexingNum = false;

                    // Agregamos el numero completo a la lista.
                    tokens.add
                    (   new Token
                        (   Token.Type.NUM,
                            completeNum
                        )
                    );

                    completeNum = ""; // Evitamos acomulaciones.
                }

                // Agregamos a la lista de tokens.
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
