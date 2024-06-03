package codeGenerator;

import scanner.token.Token;
import semantic.symbol.SymbolTable;

import java.util.HashMap;
import java.util.Map;

public class CodeGeneratorFacade {
    private CodeGenerator cg;
    private Memory mem;
    private Map<String, Address> keyWords;

    public CodeGeneratorFacade() {
        this.cg = new CodeGenerator();
        this.mem = cg.getMemory();
        cg.setSymbolTable(new SymbolTable(this));
        keyWords = new HashMap<>();
        keyWords.put("true", new Address(1, varType.Bool, TypeAddress.Imidiate));
        keyWords.put("false", new Address(0, varType.Bool, TypeAddress.Imidiate));
    }

    public void semanticFunction(int semanticAction, Token lookAhead) {
        cg.semanticFunction(semanticAction, lookAhead);
    }

    public void printMemory() {
        cg.printMemory();
    }

    public int getDateAddress() {
        return mem.getDateAddress();
    }

    public Address getAddressFromKeywordName(String keywordName) {
        return keyWords.get(keywordName);
    }
}
