package scanner;

import java.io.*;

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
            (   getInputFrom("input")
            )
        );

        System.out.println(parser.getOutput());
    }

    /**
     * Obtiene la cadena de caracteres de un archivo.
     *
     * @param file Nombre del archivo.
     * @return Cadena de caracteres.
     */
    private static String getInputFrom(String file)
    {
        String text = "";
        FileReader rfile = null;
        String line = "";

        try
        {
            rfile = new FileReader(file);
            BufferedReader buff = new BufferedReader(rfile);
            while ((line = buff.readLine()) != null)
            {
                text = String.format("%s%s", text, line);
            }
        }
        catch (FileNotFoundException ex)
        {
            throw new RuntimeException("Archivo no encontrado!");
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Error de entrada/saladia!");
        }
        finally
        {
            if (rfile != null)
            {
                try
                {
                    rfile.close();
                }
                catch (IOException ex)
                {
                    throw new RuntimeException("Error al cerrar el archivo!");
                }
            }
        }

        return text;
    }
}
