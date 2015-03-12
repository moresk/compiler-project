package scanner;

/**
 * Contiene toda la informacion referente al token.
 *
 * @author Mario
 * @version 1.0
 * @since 11/03/15
 */
public class Token
{
    /**
     * Tipos de tokens.
     *
     * @author Mario
     * @version 1.0
     * @since 11/03/15
     */
    public class Type
    {
        public static final int EOF = '.';
        public static final int END = ';';
        public static final int AND = '&';
        public static final int OR = '|';
        public static final int RBC = '{';
        public static final int LBC = '}';
        public static final int RBK = '[';
        public static final int LBK = ']';
        public static final int PLUS = '+';
        public static final int MIN = '-';
        public static final int APOS = '\'';
        public static final int DEF = 1000;
        public static final int NUM = 1001;
        public static final int VAR = 1002;
    }

    private int type;
    private String data;

    Token(int type, String data)
    {
        this.type = type;
        this.data = data;
    }

    public int getType()
    {
        return this.type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getData()
    {
        return this.data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return
            String.format
            (   "Type: %s ~~~ Data: %s",
                this.type, this.data
            );
    }
}
