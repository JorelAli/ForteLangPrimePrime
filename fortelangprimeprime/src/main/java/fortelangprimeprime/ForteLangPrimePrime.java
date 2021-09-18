package fortelangprimeprime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.yuanheng.cookcc.CookCCOption;
import org.yuanheng.cookcc.CookCCToken;
import org.yuanheng.cookcc.Lex;
import org.yuanheng.cookcc.Lexs;
import org.yuanheng.cookcc.Rule;
import org.yuanheng.cookcc.TokenGroup;
import org.yuanheng.cookcc.TokenType;

@CookCCOption(lexerTable = "compressed", parserTable = "compressed")
public class ForteLangPrimePrime extends Parser {

	@CookCCToken
	static enum Token
	{
	    @TokenGroup
	    VARIABLE, INTEGER, WHILE, IF, PRINT, ASSIGN, SEMICOLON,
	    @TokenGroup
	    IFX,
	    @TokenGroup
	    ELSE,

	    @TokenGroup (type = TokenType.LEFT)
	    GE, LE, EQ, NE, LT, GT,
	    @TokenGroup (type = TokenType.LEFT)
	    ADD, SUB,
	    @TokenGroup (type = TokenType.LEFT)
	    MUL, DIV,
	    @TokenGroup (type = TokenType.LEFT)
	    UMINUS
	}
	
	@Lex (pattern = "[ \\t\\r\\n]+")
	protected void ignoreWhiteSpace() {
	}
	
	@Lexs (patterns = {
		@Lex (pattern = "[:]", token = "COLON"),
		@Lex (pattern = "[;]", token = "SEMICOLON"),
		@Lex (pattern = "[=]", token = "ASSIGN"),
		@Lex (pattern = "[+]", token = "ADD"),
		@Lex (pattern = "\\-", token = "SUB"),
		@Lex (pattern = "[*]", token = "MUL"),
		@Lex (pattern = "[/]", token = "DIV"),
		@Lex (pattern = "[<]", token = "LT"),
		@Lex (pattern = "[>]", token = "GT"),
		@Lex (pattern = ">=", token = "GE"),
		@Lex (pattern = "<=", token = "LE"),
		@Lex (pattern = "!=", token = "NE"),
		@Lex (pattern = "==", token = "EQ")
	})
	protected Object parseOp() {
		return null;
	}
	
	@Lex (pattern="[0-9]+", token="INTEGER")
	protected Integer parseInt() {
		return Integer.parseInt(super.yyText());
	}

	@Lex (pattern = "[a-zA-Z]+", token = "variable")
	protected String parseVariable() {
		return super.yyText();
	}
	
//	@Lex (pattern = "[a-zA-Z]+", token = "type")
//	protected String parseType() {
//		return super.yyText();
//	}

//	@Lexs (patterns = {
//	    @Lex (pattern = "while", token = "WHILE"),
//	    @Lex (pattern = "if", token = "IF"),
//	    @Lex (pattern = "else", token = "ELSE"),
//	    @Lex (pattern = "print", token = "PRINT")
//	})
//	protected Object parseKeyword ()
//	{
//	    return null;
//	}
	
	@Lex (pattern = "<<EOF>>")
	protected int parseEOF() {
		return 0;
	}
	
	@Rule (lhs = "expr", rhs = "INTEGER")
	protected Integer parseExpr(Node node) {
		return parseInt();
	}
	
	@Rule (lhs = "function", rhs = "variable COLON variable EQ expr", args = "1 3 5")
	protected Node parseFunction(String variable, String type, Integer expr) {
		return new FunctionNode(variable, type, expr);
	}
	
	@Rule (lhs = "program", rhs = "function")
	protected int parseProgram() {
		return 0;
	}
	


	private void parse(Reader in) {
		setInput (in);
		try {
			yyParse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// NODES
	
	static class Node
	{
	}

	static class FunctionNode extends Node
	{
		String name;
		int value;

		public FunctionNode (String variable, String type, Integer expr)
		{
			System.out.println(variable);
			System.out.println(type);
			System.out.println(expr);
		}
	}
	
	public static void main(String[] args) {
		ForteLangPrimePrime wc = new ForteLangPrimePrime ();
		if (args.length == 0)
			wc.parse (new BufferedReader(new InputStreamReader(System.in)));
		//else
		//	wc.parse(new FileInputStream(new File(args[0])));
	}
	
}
