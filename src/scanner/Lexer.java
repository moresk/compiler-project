package scanner;

import java.util.ArrayList;

/**
 * Analizador lexico.
 *
 * Obtiene una lista de tokens en base a una cadena de texto.
 * La cadena de texto debe estar formada, en forma coherente,
 * por los siguientes simbolos: . ; & | { } [ ] '' <> ::=
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
        // Eliminamos los espacios en blanco
        input = input.replaceAll("\\s", "");

        // Almacena la lista de tokens temporalmente.
        ArrayList<Token> tokens = new ArrayList<>();

        // Recorre la cadena de texto, letra por letra.
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
             && input.charAt(i) != Token.Type.RPS
             && input.charAt(i) != Token.Type.LPS)
            {
                // Verifica que se trate de una definicion.
                if (input.charAt(i) == ':'
                 && (i + 3) <= input.length() // Asegura 3 caracteres.
                 && input.charAt(i + 1) == ':'
                 && input.charAt(i + 2) == '=')
                {
                    // Agregamos el token a la lista.
                    tokens.add(new Token(Token.Type.DEF, "::="));

                    // El indice es aumentado dos veces ya que
                    // dos caracteres mas fueron "analizados" (':' '=').
                    i += 2;
                }
                // Si no: Verifica que sea un terminal:
                else if (input.charAt(i) == '\''
                      && input.indexOf('\'', i + 1) != -1)
                {
                    // Obtiene el index del proximo apostrofe.
                    int ni = input.indexOf('\'', i + 1);

                    // Agrega el token a la lista, con los apostrofes y
                    // los caracteres dentro de estos como lexema.
                    tokens.add
                    (   new Token
                        (   Token.Type.TERML,
                            input.substring
                            (   i,
                                ni + 1
                            )
                        )
                    );

                    // El indice es incrementado hasta el ultimo simbolo \'
                    // ya que no es necesario analizar lo que hay dentro.
                    i = ni;
                }
                // Si no: Verifica que sea una variable:
                else if (input.charAt(i) == '<'
                      && input.indexOf('>', i + 1) != -1
                      && (i + 1) < input.length() // Aseguramos 2 caracteres.
                    // El primer caracter debe de ser letra.
                      && Character.isLetter(input.charAt(i + 1)) == true)
                {
                    // Obtiene el index del proximo >.
                    int ni = input.indexOf('>', i + 1);

                    // Obtiene los simbolos <> y los caracteres dentro.
                    String svar = input.substring(i, ni + 1);

                    // Verifica que lo que hay dentro sean letras o numeros.
                    for (int j = 1; j < svar.length() - 1; j++)
                    {
                        // Si no:
                        if (Character.isDigit(svar.charAt(j)) != true
                         && Character.isLetter(svar.charAt(j)) != true)
                        {
                            // Informamos sobre el error y en que columna esta.
                            throw new Error
                            (   String.format("Error lexico en columna: %d", i)
                            );
                        }
                    }

                    // Agrega el token a la lista.
                    tokens.add
                    (   new Token
                        (   Token.Type.VAR,
                            svar
                        )
                    );

                    // El indice es incrementado hasta el ultimo simbolo >
                    // ya que lo que hay dentro de los simbolos fue analizado.
                    i = ni;
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
                // Lo agrega a la lista de tokens.
                tokens.add
                (   new Token
                    (   input.charAt(i),
                        String.valueOf(input.charAt(i))
                    )
                );
            }
        }

        return tokens; // Almacena la lista permanentemente.
    }
}
