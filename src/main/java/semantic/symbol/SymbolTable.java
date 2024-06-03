package semantic.symbol;

import codeGenerator.CodeGeneratorFacade;
import codeGenerator.Address;
import errorHandler.ErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Klass> klasses;
    private SymbolType lastType;
    private CodeGeneratorFacade cgf;

    public SymbolTable(CodeGeneratorFacade cgf) {
        this.cgf = cgf;
        this.klasses = new HashMap<>();
    }

    public void setLastType(SymbolType type) {
        lastType = type;
    }

    public void addClass(String className) {
        if (getKlasses().containsKey(className)) {
            ErrorHandler.printError("This class already defined");
        }
        getKlasses().put(className, new Klass());
    }

    public void addField(String fieldName, String className) {
        getKlasses().get(className).Fields.put(fieldName, new Symbol(lastType, cgf.getDateAddress()));
    }

    public void addMethod(String className, String methodName, int address) {
        if (getKlasses().get(className).Methodes.containsKey(methodName)) {
            ErrorHandler.printError("This method already defined");
        }
        getKlasses().get(className).Methodes.put(methodName, new Method(address, lastType));
    }

    public void addMethodParameter(String className, String methodName, String parameterName) {
        getKlasses().get(className).Methodes.get(methodName).addParameter(parameterName);
    }

    public void addMethodLocalVariable(String className, String methodName, String localVariableName) {
        if (getKlasses().get(className).Methodes.get(methodName).localVariable.containsKey(localVariableName)) {
            ErrorHandler.printError("This variable already defined");
        }
        getKlasses().get(className).Methodes.get(methodName).localVariable.put(localVariableName, new Symbol(lastType, cgf.getDateAddress()));
    }

    public void setSuperClass(String superClass, String className) {
        getKlasses().get(className).superClass = getKlasses().get(superClass);
    }

    public Address get(String keywordName) {
        return cgf.getAddressFromKeywordName(keywordName);
    }

    public Symbol get(String fieldName, String className) {
        return getKlasses().get(className).getField(fieldName);
    }

    public Symbol get(String className, String methodName, String variable) {
        Symbol res = getKlasses().get(className).Methodes.get(methodName).getVariable(variable);
        if (res == null) res = get(variable, className);
        return res;
    }

    public Symbol queryNextParam(String className, String methodName) {
        return getKlasses().get(className).Methodes.get(methodName).peekNextParameter();
    }

    public void advanceToNextParam(String className, String methodName) {
        getKlasses().get(className).Methodes.get(methodName).advanceToNextParameter();
    }

    public void startCall(String className, String methodName) {
        getKlasses().get(className).Methodes.get(methodName).reset();
    }

    public int getMethodCallerAddress(String className, String methodName) {
        return getKlasses().get(className).Methodes.get(methodName).callerAddress;
    }

    public int getMethodReturnAddress(String className, String methodName) {
        return getKlasses().get(className).Methodes.get(methodName).returnAddress;
    }

    public SymbolType getMethodReturnType(String className, String methodName) {
        return getKlasses().get(className).Methodes.get(methodName).returnType;
    }

    public int getMethodAddress(String className, String methodName) {
        return getKlasses().get(className).Methodes.get(methodName).codeAddress;
    }

    // Getter and Setter for klasses
    public Map<String, Klass> getKlasses() {
        return klasses;
    }

    public void setKlasses(Map<String, Klass> klasses) {
        this.klasses = klasses;
    }

    class Klass {
        public Map<String, Symbol> Fields;
        public Map<String, Method> Methodes;
        public Klass superClass;

        public Klass() {
            Fields = new HashMap<>();
            Methodes = new HashMap<>();
        }

        public Symbol getField(String fieldName) {
            if (Fields.containsKey(fieldName)) {
                return Fields.get(fieldName);
            }
            return superClass.getField(fieldName);
        }
    }

    class Method {
        public int codeAddress;
        public Map<String, Symbol> parameters;
        public Map<String, Symbol> localVariable;
        private ArrayList<String> orderdParameters;
        public int callerAddress;
        public int returnAddress;
        public SymbolType returnType;
        private int index;

        public Method(int codeAddress, SymbolType returnType) {
            this.codeAddress = codeAddress;
            this.returnType = returnType;
            this.orderdParameters = new ArrayList<>();
            this.returnAddress = cgf.getDateAddress();
            this.callerAddress = cgf.getDateAddress();
            this.parameters = new HashMap<>();
            this.localVariable = new HashMap<>();
        }

        public Symbol getVariable(String variableName) {
            if (parameters.containsKey(variableName)) return parameters.get(variableName);
            if (localVariable.containsKey(variableName)) return localVariable.get(variableName);
            return null;
        }

        public void addParameter(String parameterName) {
            parameters.put(parameterName, new Symbol(lastType, cgf.getDateAddress()));
            orderdParameters.add(parameterName);
        }

        public void reset() {
            index = 0;
        }

        public Symbol peekNextParameter() {
            return parameters.get(orderdParameters.get(index));
        }

        public void advanceToNextParameter() {
            index++;
        }
    }
}
