package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

import Log.Log;
import codeGenerator.CodeGeneratorFacade;
import errorHandler.ErrorHandler;
import scanner.lexicalAnalyzer;
import scanner.token.Token;

public class Parser {
    private ArrayList<Rule> rules;
    private Stack<Integer> parsStack;
    private ParseTable parseTable;
    private lexicalAnalyzer lexicalAnalyzer;
    private CodeGeneratorFacade cgf;

    public Parser(CodeGeneratorFacade cgf) {
        parsStack = new Stack<Integer>();
        parsStack.push(0);
        try {
            parseTable = new ParseTable(Files.readAllLines(Paths.get("src/main/resources/parseTable")).get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rules = new ArrayList<Rule>();
        try {
            for (String stringRule : Files.readAllLines(Paths.get("src/main/resources/Rules"))) {
                rules.add(new Rule(stringRule));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.cgf = cgf;
    }


    public void startParse(java.util.Scanner sc) {
        lexicalAnalyzer = new lexicalAnalyzer(sc);
        Token lookAhead = lexicalAnalyzer.getNextToken();
        boolean finish = false;
        Action currentAction;
        while (!finish) {
            try {
                Log.print(/*"lookahead : "+*/ lookAhead.toString() + "\t" + parsStack.peek());
//                Log.print("state : "+ parsStack.peek());
                currentAction = parseTable.getActionTable(parsStack.peek(), lookAhead);
                Log.print(currentAction.toString());

                Act act = currentAction.action;

                if(act instanceof Shift) {
                    parsStack.push(act.number);
                    lookAhead = lexicalAnalyzer.getNextToken();
                } else if (act instanceof Reduce) {
                    Rule rule = rules.get(act.number);
                    for (int i = 0; i < rule.RHS.size(); i++) {
                        parsStack.pop();
                    }

                    Log.print(/*"state : " +*/ parsStack.peek() + "\t" + rule.LHS);
                    parsStack.push(parseTable.getGotoTable(parsStack.peek(), rule.LHS));
                    Log.print(/*"new State : " + */parsStack.peek() + "");
                    try {
                        cgf.semanticFunction(rule.semanticAction, lookAhead);
                    } catch (Exception e) {
                        Log.print("Code Genetator Error");
                    }
                } else if (act instanceof Accept){
                        finish = true;
                }
                Log.print("");
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        if (!ErrorHandler.hasError) cgf.printMemory();
    }
}
