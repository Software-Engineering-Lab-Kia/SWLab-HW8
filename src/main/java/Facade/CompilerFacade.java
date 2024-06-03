package Facade;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import parser.Parser;
import codeGenerator.CodeGenerator;
import errorHandler.ErrorHandler;
import Log.Log;

public class CompilerFacade {
    private Parser parser;
    private CodeGenerator codeGenerator;
    private ErrorHandler errorHandler;
    private Log log;

    public CompilerFacade() {
        this.codeGenerator = new CodeGenerator();
        this.parser = new Parser(this.codeGenerator);
        this.errorHandler = new ErrorHandler();
        this.log = new Log();
    }

    public void compile(String codeFilePath) {
        try {
            Scanner scanner = new Scanner(new File(codeFilePath));
            parser.startParse(scanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
