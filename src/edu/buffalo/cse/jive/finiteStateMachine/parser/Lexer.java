package edu.buffalo.cse.jive.finiteStateMachine.parser;

import java.util.Scanner;

public class Lexer {

	private char ch = ' ';
	private String ident = "";
	private Buffer buffer;
	private int nextToken;
	private char nextChar;
	private int intValue;

	public Lexer(String input) {
		buffer = new Buffer(new Scanner(input));
		ch = ' ';
		ident = "";
	}

	public int lex() throws Exception {
		while (Character.isWhitespace(ch))
			ch = buffer.getChar();

		if (ch == '/') { // maybe comment
			ch = buffer.getChar();
			if (ch == '/') { // it is a comment
				while (ch != '\n')
					ch = buffer.getChar();
				return lex();
			} else {
				nextChar = '/';
				nextToken = Token.DIV_OP;
				return nextToken;
			}
		}
		if (Character.isLetter(ch)) {
			ident = ident();
			switch (ident) {
			case "F":
				nextToken = Token.F_OP;
				break;
			case "G":
				nextToken = Token.G_OP;
				break;
			case "U":
				nextToken = Token.U_OP;
				break;
			case "X":
				nextToken = Token.X_OP;
				break;
			case "end":
				nextToken = Token.KEY_END;
				break;
			default:
				nextToken = Token.ID;
			}
		} else if (Character.isDigit(ch)) {
			num();
			nextToken = Token.INT_LIT; // intValue would be set
		} else {
			nextChar = ch;
			switch (ch) {
			case '\'':
				nextToken = Token.PRIME_OP;
				ch = buffer.getChar();
				break;
			case ';':
				nextToken = Token.SEMICOLON;
				ch = buffer.getChar();
				break;
			case ',':
				nextToken = Token.COMMA;
				ch = buffer.getChar();
				break;
			case '+':
				nextToken = Token.ADD_OP;
				ch = buffer.getChar();
				break;
			case '-':
				ch = buffer.getChar();
				if (Character.isDigit(ch)) {
					num();
					setNextToken(Token.N_INT_LIT);
				} else if (ch == '>') {
					nextToken = Token.IMPLY_OP;
					ch = buffer.getChar();
				} else
					nextToken = Token.SUB_OP;
				break;
			case '*':
				nextToken = Token.MULT_OP;
				ch = buffer.getChar();
				break;
			case '=':
				ch = buffer.getChar();
				if (ch == '=') {
					nextToken = Token.EQ_OP;
					ch = buffer.getChar();
				} else
					nextToken = Token.EQ_OP;
				break;
			case '&':
				ch = buffer.getChar();
				if (ch == '&') {
					nextToken = Token.AND_OP;
					ch = buffer.getChar();
				} else
					nextToken = Token.AND_OP;
				break;
			case '|':
				ch = buffer.getChar();
				if (ch == '|') {
					nextToken = Token.OR_OP;
					ch = buffer.getChar();
				} else
					nextToken = Token.OR_OP;
				break;
			case '<':
				ch = buffer.getChar();
				if (ch == '=') {
					nextToken = Token.LESSEQ_OP;
					ch = buffer.getChar();
				} else
					nextToken = Token.LESSER_OP;
				break;
			case '>':
				ch = buffer.getChar();
				if (ch == '=') {
					nextToken = Token.GREATEREQ_OP;
					ch = buffer.getChar();
				} else
					nextToken = Token.GREATER_OP;
				break;
			case '!':
				ch = buffer.getChar();
				if (ch == '=') {
					nextToken = Token.NOT_EQ;
					ch = buffer.getChar();
				} else
					nextToken = Token.NOT_OP;
				break;
			case '(':
				nextToken = Token.LEFT_PAREN;
				ch = buffer.getChar();
				break;
			case ')':
				nextToken = Token.RIGHT_PAREN;
				ch = buffer.getChar();
				break;
			case '{':
				nextToken = Token.LEFT_BRACE;
				ch = buffer.getChar();
				break;
			case '}':
				nextToken = Token.RIGHT_BRACE;
				ch = buffer.getChar();
				break;
			case '[':
				nextToken = Token.LEFT_BOX;
				ch = buffer.getChar();
				break;
			case ']':
				nextToken = Token.RIGHT_BOX;
				ch = buffer.getChar();
				break;
			case '\"':
				ident();
				setNextToken(Token.STRING_LIT);
				break;
			default:
				error("Illegal character " + ch);
				break;
			}
		}
		return nextToken;
	} // lex

//	private String ident() {
//		// ch is declared in class Lexer
//		String ident = "";
//		do {
//			ident = ident + ch;
//			ch = buffer.getChar();
//		} while (Character.isLetter(ch) || Character.isDigit(ch));
//		return ident;
//	}

	private String ident() throws Exception {
		ident = "";
		do {
			ident = ident + ch;
			ch = buffer.getChar();
		} while (Character.isLetter(ch) || Character.isDigit(ch) || ch == '\"' || ch == '_' || ch == '.' || ch == ':');
		return ident;
	}

	private String num() throws Exception {
		ident = "";
		do {
			ident = ident + ch;
			ch = buffer.getChar();
		} while (Character.isDigit(ch) || ch == '.');
		return ident;
	}

//	private int getNumToken() {
//		int num = 0;
//		do {
//			num = num * 10 + Character.digit(ch, 10);
//			ch = buffer.getChar();
//		} while (Character.isDigit(ch));
//		intValue = num;
//		return Token.INT_LIT;
//	}

	public int number() {
		return intValue;
	} // number

	public String identifier() {
		return ident;
	} // letter

	public void error(String msg) {
		System.err.println(msg);
		System.exit(1);
	} // error

	public char getCh() {
		return ch;
	}

	public void setCh(char ch) {
		this.ch = ch;
	}

	public Buffer getBuffer() {
		return buffer;
	}

	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

	public int getNextToken() {
		return nextToken;
	}

	public void setNextToken(int nextToken) {
		this.nextToken = nextToken;
	}

	public char getNextChar() {
		return nextChar;
	}

	public void setNextChar(char nextChar) {
		this.nextChar = nextChar;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getIdent() {
		return ident;
	}
}