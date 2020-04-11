// Generated from Expr.g4 by ANTLR 4.8

        package antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExprLexer extends Lexer {
    static {
        RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17,
            T__17 = 18, T__18 = 19, T__19 = 20, T__20 = 21, T__21 = 22, T__22 = 23, T__23 = 24,
            T__24 = 25, T__25 = 26, T__26 = 27, T__27 = 28, T__28 = 29, T__29 = 30, T__30 = 31,
            T__31 = 32, T__32 = 33, T__33 = 34, T__34 = 35, T__35 = 36, T__36 = 37, T__37 = 38,
            T__38 = 39, T__39 = 40, T__40 = 41, T__41 = 42, T__42 = 43, VARIABLE = 44, IntConstant = 45,
            BoolConstant = 46, ConstantValue = 47, ID = 48, COMMENT = 49, WS = 50, SEMICOLON = 51,
            COMMA = 52;
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    private static String[] makeRuleNames() {
        return new String[]{
                "T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
                "T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16",
                "T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24",
                "T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32",
                "T__33", "T__34", "T__35", "T__36", "T__37", "T__38", "T__39", "T__40",
                "T__41", "T__42", "VARIABLE", "IntConstant", "BoolConstant", "ConstantValue",
                "ID", "COMMENT", "WS", "SEMICOLON", "COMMA"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'='", "'['", "']'", "'loop_require'", "'('", "')'", "'loop_init'",
                "'{'", "'}'", "'loop'", "'loop_invariant'", "'loop_variant'", "'loop_ensure'",
                "'fun'", "'fun_require'", "'return'", "'fun_ensure'", "'if_require'",
                "'if_ensure'", "'if'", "'else'", "'.'", "'add'", "'remove'", "'all'",
                "'some'", "'*'", "'/'", "'%'", "'+'", "'-'", "'each'", "'<'", "'<='",
                "'>'", "'>='", "'=='", "'!='", "'!'", "'&&'", "'||'", "'=>'", "'<=>'",
                null, null, null, null, null, null, null, "';'", "','"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, "VARIABLE", "IntConstant",
                "BoolConstant", "ConstantValue", "ID", "COMMENT", "WS", "SEMICOLON",
                "COMMA"
        };
    }

    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

	@Override
	@Deprecated
	public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }


    public ExprLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    public String getGrammarFileName() {
        return "Expr.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\66\u017a\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t" +
                    " \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t" +
                    "+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64" +
                    "\t\64\4\65\t\65\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3" +
                    "\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b" +
                    "\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f" +
                    "\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3" +
                    "\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3" +
                    "\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3" +
                    "\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3" +
                    "\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3" +
                    "\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3" +
                    "\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3" +
                    "\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3" +
                    "\33\3\33\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3" +
                    "!\3!\3!\3!\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3(\3(" +
                    "\3)\3)\3)\3*\3*\3*\3+\3+\3+\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-\5-\u0148" +
                    "\n-\3.\6.\u014b\n.\r.\16.\u014c\3/\3/\3/\3/\3/\3/\3/\3/\3/\5/\u0158\n" +
                    "/\3\60\3\60\5\60\u015c\n\60\3\61\3\61\7\61\u0160\n\61\f\61\16\61\u0163" +
                    "\13\61\3\62\3\62\3\62\3\62\7\62\u0169\n\62\f\62\16\62\u016c\13\62\3\62" +
                    "\3\62\3\63\6\63\u0171\n\63\r\63\16\63\u0172\3\63\3\63\3\64\3\64\3\65\3" +
                    "\65\2\2\66\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33" +
                    "\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67" +
                    "\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65" +
                    "i\66\3\2\7\3\2\62;\3\2c|\6\2\62;C\\aac|\4\2\f\f\17\17\5\2\13\f\17\17\"" +
                    "\"\2\u0180\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2" +
                    "\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27" +
                    "\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2" +
                    "\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2" +
                    "\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2" +
                    "\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2" +
                    "\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S" +
                    "\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2" +
                    "\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\3k\3\2\2\2" +
                    "\5m\3\2\2\2\7o\3\2\2\2\tq\3\2\2\2\13~\3\2\2\2\r\u0080\3\2\2\2\17\u0082" +
                    "\3\2\2\2\21\u008c\3\2\2\2\23\u008e\3\2\2\2\25\u0090\3\2\2\2\27\u0095\3" +
                    "\2\2\2\31\u00a4\3\2\2\2\33\u00b1\3\2\2\2\35\u00bd\3\2\2\2\37\u00c1\3\2" +
                    "\2\2!\u00cd\3\2\2\2#\u00d4\3\2\2\2%\u00df\3\2\2\2\'\u00ea\3\2\2\2)\u00f4" +
                    "\3\2\2\2+\u00f7\3\2\2\2-\u00fc\3\2\2\2/\u00fe\3\2\2\2\61\u0102\3\2\2\2" +
                    "\63\u0109\3\2\2\2\65\u010d\3\2\2\2\67\u0112\3\2\2\29\u0114\3\2\2\2;\u0116" +
                    "\3\2\2\2=\u0118\3\2\2\2?\u011a\3\2\2\2A\u011c\3\2\2\2C\u0121\3\2\2\2E" +
                    "\u0123\3\2\2\2G\u0126\3\2\2\2I\u0128\3\2\2\2K\u012b\3\2\2\2M\u012e\3\2" +
                    "\2\2O\u0131\3\2\2\2Q\u0133\3\2\2\2S\u0136\3\2\2\2U\u0139\3\2\2\2W\u013c" +
                    "\3\2\2\2Y\u0147\3\2\2\2[\u014a\3\2\2\2]\u0157\3\2\2\2_\u015b\3\2\2\2a" +
                    "\u015d\3\2\2\2c\u0164\3\2\2\2e\u0170\3\2\2\2g\u0176\3\2\2\2i\u0178\3\2" +
                    "\2\2kl\7?\2\2l\4\3\2\2\2mn\7]\2\2n\6\3\2\2\2op\7_\2\2p\b\3\2\2\2qr\7n" +
                    "\2\2rs\7q\2\2st\7q\2\2tu\7r\2\2uv\7a\2\2vw\7t\2\2wx\7g\2\2xy\7s\2\2yz" +
                    "\7w\2\2z{\7k\2\2{|\7t\2\2|}\7g\2\2}\n\3\2\2\2~\177\7*\2\2\177\f\3\2\2" +
                    "\2\u0080\u0081\7+\2\2\u0081\16\3\2\2\2\u0082\u0083\7n\2\2\u0083\u0084" +
                    "\7q\2\2\u0084\u0085\7q\2\2\u0085\u0086\7r\2\2\u0086\u0087\7a\2\2\u0087" +
                    "\u0088\7k\2\2\u0088\u0089\7p\2\2\u0089\u008a\7k\2\2\u008a\u008b\7v\2\2" +
                    "\u008b\20\3\2\2\2\u008c\u008d\7}\2\2\u008d\22\3\2\2\2\u008e\u008f\7\177" +
                    "\2\2\u008f\24\3\2\2\2\u0090\u0091\7n\2\2\u0091\u0092\7q\2\2\u0092\u0093" +
                    "\7q\2\2\u0093\u0094\7r\2\2\u0094\26\3\2\2\2\u0095\u0096\7n\2\2\u0096\u0097" +
                    "\7q\2\2\u0097\u0098\7q\2\2\u0098\u0099\7r\2\2\u0099\u009a\7a\2\2\u009a" +
                    "\u009b\7k\2\2\u009b\u009c\7p\2\2\u009c\u009d\7x\2\2\u009d\u009e\7c\2\2" +
                    "\u009e\u009f\7t\2\2\u009f\u00a0\7k\2\2\u00a0\u00a1\7c\2\2\u00a1\u00a2" +
                    "\7p\2\2\u00a2\u00a3\7v\2\2\u00a3\30\3\2\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6" +
                    "\7q\2\2\u00a6\u00a7\7q\2\2\u00a7\u00a8\7r\2\2\u00a8\u00a9\7a\2\2\u00a9" +
                    "\u00aa\7x\2\2\u00aa\u00ab\7c\2\2\u00ab\u00ac\7t\2\2\u00ac\u00ad\7k\2\2" +
                    "\u00ad\u00ae\7c\2\2\u00ae\u00af\7p\2\2\u00af\u00b0\7v\2\2\u00b0\32\3\2" +
                    "\2\2\u00b1\u00b2\7n\2\2\u00b2\u00b3\7q\2\2\u00b3\u00b4\7q\2\2\u00b4\u00b5" +
                    "\7r\2\2\u00b5\u00b6\7a\2\2\u00b6\u00b7\7g\2\2\u00b7\u00b8\7p\2\2\u00b8" +
                    "\u00b9\7u\2\2\u00b9\u00ba\7w\2\2\u00ba\u00bb\7t\2\2\u00bb\u00bc\7g\2\2" +
                    "\u00bc\34\3\2\2\2\u00bd\u00be\7h\2\2\u00be\u00bf\7w\2\2\u00bf\u00c0\7" +
                    "p\2\2\u00c0\36\3\2\2\2\u00c1\u00c2\7h\2\2\u00c2\u00c3\7w\2\2\u00c3\u00c4" +
                    "\7p\2\2\u00c4\u00c5\7a\2\2\u00c5\u00c6\7t\2\2\u00c6\u00c7\7g\2\2\u00c7" +
                    "\u00c8\7s\2\2\u00c8\u00c9\7w\2\2\u00c9\u00ca\7k\2\2\u00ca\u00cb\7t\2\2" +
                    "\u00cb\u00cc\7g\2\2\u00cc \3\2\2\2\u00cd\u00ce\7t\2\2\u00ce\u00cf\7g\2" +
                    "\2\u00cf\u00d0\7v\2\2\u00d0\u00d1\7w\2\2\u00d1\u00d2\7t\2\2\u00d2\u00d3" +
                    "\7p\2\2\u00d3\"\3\2\2\2\u00d4\u00d5\7h\2\2\u00d5\u00d6\7w\2\2\u00d6\u00d7" +
                    "\7p\2\2\u00d7\u00d8\7a\2\2\u00d8\u00d9\7g\2\2\u00d9\u00da\7p\2\2\u00da" +
                    "\u00db\7u\2\2\u00db\u00dc\7w\2\2\u00dc\u00dd\7t\2\2\u00dd\u00de\7g\2\2" +
                    "\u00de$\3\2\2\2\u00df\u00e0\7k\2\2\u00e0\u00e1\7h\2\2\u00e1\u00e2\7a\2" +
                    "\2\u00e2\u00e3\7t\2\2\u00e3\u00e4\7g\2\2\u00e4\u00e5\7s\2\2\u00e5\u00e6" +
                    "\7w\2\2\u00e6\u00e7\7k\2\2\u00e7\u00e8\7t\2\2\u00e8\u00e9\7g\2\2\u00e9" +
                    "&\3\2\2\2\u00ea\u00eb\7k\2\2\u00eb\u00ec\7h\2\2\u00ec\u00ed\7a\2\2\u00ed" +
                    "\u00ee\7g\2\2\u00ee\u00ef\7p\2\2\u00ef\u00f0\7u\2\2\u00f0\u00f1\7w\2\2" +
                    "\u00f1\u00f2\7t\2\2\u00f2\u00f3\7g\2\2\u00f3(\3\2\2\2\u00f4\u00f5\7k\2" +
                    "\2\u00f5\u00f6\7h\2\2\u00f6*\3\2\2\2\u00f7\u00f8\7g\2\2\u00f8\u00f9\7" +
                    "n\2\2\u00f9\u00fa\7u\2\2\u00fa\u00fb\7g\2\2\u00fb,\3\2\2\2\u00fc\u00fd" +
                    "\7\60\2\2\u00fd.\3\2\2\2\u00fe\u00ff\7c\2\2\u00ff\u0100\7f\2\2\u0100\u0101" +
                    "\7f\2\2\u0101\60\3\2\2\2\u0102\u0103\7t\2\2\u0103\u0104\7g\2\2\u0104\u0105" +
                    "\7o\2\2\u0105\u0106\7q\2\2\u0106\u0107\7x\2\2\u0107\u0108\7g\2\2\u0108" +
                    "\62\3\2\2\2\u0109\u010a\7c\2\2\u010a\u010b\7n\2\2\u010b\u010c\7n\2\2\u010c" +
                    "\64\3\2\2\2\u010d\u010e\7u\2\2\u010e\u010f\7q\2\2\u010f\u0110\7o\2\2\u0110" +
                    "\u0111\7g\2\2\u0111\66\3\2\2\2\u0112\u0113\7,\2\2\u01138\3\2\2\2\u0114" +
                    "\u0115\7\61\2\2\u0115:\3\2\2\2\u0116\u0117\7\'\2\2\u0117<\3\2\2\2\u0118" +
                    "\u0119\7-\2\2\u0119>\3\2\2\2\u011a\u011b\7/\2\2\u011b@\3\2\2\2\u011c\u011d" +
                    "\7g\2\2\u011d\u011e\7c\2\2\u011e\u011f\7e\2\2\u011f\u0120\7j\2\2\u0120" +
                    "B\3\2\2\2\u0121\u0122\7>\2\2\u0122D\3\2\2\2\u0123\u0124\7>\2\2\u0124\u0125" +
                    "\7?\2\2\u0125F\3\2\2\2\u0126\u0127\7@\2\2\u0127H\3\2\2\2\u0128\u0129\7" +
                    "@\2\2\u0129\u012a\7?\2\2\u012aJ\3\2\2\2\u012b\u012c\7?\2\2\u012c\u012d" +
                    "\7?\2\2\u012dL\3\2\2\2\u012e\u012f\7#\2\2\u012f\u0130\7?\2\2\u0130N\3" +
                    "\2\2\2\u0131\u0132\7#\2\2\u0132P\3\2\2\2\u0133\u0134\7(\2\2\u0134\u0135" +
                    "\7(\2\2\u0135R\3\2\2\2\u0136\u0137\7~\2\2\u0137\u0138\7~\2\2\u0138T\3" +
                    "\2\2\2\u0139\u013a\7?\2\2\u013a\u013b\7@\2\2\u013bV\3\2\2\2\u013c\u013d" +
                    "\7>\2\2\u013d\u013e\7?\2\2\u013e\u013f\7@\2\2\u013fX\3\2\2\2\u0140\u0141" +
                    "\7k\2\2\u0141\u0142\7p\2\2\u0142\u0148\7v\2\2\u0143\u0144\7d\2\2\u0144" +
                    "\u0145\7q\2\2\u0145\u0146\7q\2\2\u0146\u0148\7n\2\2\u0147\u0140\3\2\2" +
                    "\2\u0147\u0143\3\2\2\2\u0148Z\3\2\2\2\u0149\u014b\t\2\2\2\u014a\u0149" +
                    "\3\2\2\2\u014b\u014c\3\2\2\2\u014c\u014a\3\2\2\2\u014c\u014d\3\2\2\2\u014d" +
                    "\\\3\2\2\2\u014e\u014f\7v\2\2\u014f\u0150\7t\2\2\u0150\u0151\7w\2\2\u0151" +
                    "\u0158\7g\2\2\u0152\u0153\7h\2\2\u0153\u0154\7c\2\2\u0154\u0155\7n\2\2" +
                    "\u0155\u0156\7u\2\2\u0156\u0158\7g\2\2\u0157\u014e\3\2\2\2\u0157\u0152" +
                    "\3\2\2\2\u0158^\3\2\2\2\u0159\u015c\5[.\2\u015a\u015c\5]/\2\u015b\u0159" +
                    "\3\2\2\2\u015b\u015a\3\2\2\2\u015c`\3\2\2\2\u015d\u0161\t\3\2\2\u015e" +
                    "\u0160\t\4\2\2\u015f\u015e\3\2\2\2\u0160\u0163\3\2\2\2\u0161\u015f\3\2" +
                    "\2\2\u0161\u0162\3\2\2\2\u0162b\3\2\2\2\u0163\u0161\3\2\2\2\u0164\u0165" +
                    "\7\61\2\2\u0165\u0166\7\61\2\2\u0166\u016a\3\2\2\2\u0167\u0169\n\5\2\2" +
                    "\u0168\u0167\3\2\2\2\u0169\u016c\3\2\2\2\u016a\u0168\3\2\2\2\u016a\u016b" +
                    "\3\2\2\2\u016b\u016d\3\2\2\2\u016c\u016a\3\2\2\2\u016d\u016e\b\62\2\2" +
                    "\u016ed\3\2\2\2\u016f\u0171\t\6\2\2\u0170\u016f\3\2\2\2\u0171\u0172\3" +
                    "\2\2\2\u0172\u0170\3\2\2\2\u0172\u0173\3\2\2\2\u0173\u0174\3\2\2\2\u0174" +
                    "\u0175\b\63\2\2\u0175f\3\2\2\2\u0176\u0177\7=\2\2\u0177h\3\2\2\2\u0178" +
                    "\u0179\7.\2\2\u0179j\3\2\2\2\n\2\u0147\u014c\u0157\u015b\u0161\u016a\u0172" +
                    "\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}