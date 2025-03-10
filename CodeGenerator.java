import java.util.*;

// Classe de base pour tous les nœuds de l'AST
abstract class Node {
    abstract List<String> generateCode();
}

// Nœud pour une constante entière
class NumberNode extends Node {
    int value;

    public NumberNode(int value) {
        this.value = value;
    }

    @Override
    List<String> generateCode() {
        return List.of("mov eax, " + value);
    }
}

// Nœud pour une variable
class VariableNode extends Node {
    String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    List<String> generateCode() {
        return List.of("mov eax, " + name);
    }
}

// Nœud pour une opération binaire
class BinaryOpNode extends Node {
    String op;
    Node left, right;

    public BinaryOpNode(String op, Node left, Node right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    List<String> generateCode() {
        List<String> code = new ArrayList<>();
        code.addAll(left.generateCode());
        code.add("push eax");
        code.addAll(right.generateCode());
        code.add("pop ebx");

        switch (op) {
            case "+" -> code.add("add eax, ebx");
            case "-" -> code.add("sub eax, ebx");
            case "*" -> code.add("mul eax, ebx");
            case "/" -> {
                code.add("mov ecx, eax");
                code.add("div ebx");
                code.add("mov eax, ecx");
            }
            case "mod" -> {
                code.add("mov ecx, eax");
                code.add("div ebx");
                code.add("mul ecx, ebx");
                code.add("sub eax, ecx");
            }
        }
        return code;
    }
}

// Nœud pour une déclaration de variable
class LetNode extends Node {
    String name;
    Node value;

    public LetNode(String name, Node value) {
        this.name = name;
        this.value = value;
    }

    @Override
    List<String> generateCode() {
        List<String> code = new ArrayList<>();
        code.addAll(value.generateCode());
        code.add("mov " + name + ", eax");
        return code;
    }
}

// Nœud pour une boucle while
class WhileNode extends Node {
    Node condition;
    Node body;
    static int labelCount = 0;
    int label;

    public WhileNode(Node condition, Node body) {
        this.condition = condition;
        this.body = body;
        this.label = labelCount++;
    }

    @Override
    List<String> generateCode() {
        List<String> code = new ArrayList<>();
        code.add("debut_while_" + label + ":");
        code.addAll(condition.generateCode());
        code.add("jz sortie_while_" + label);
        code.addAll(body.generateCode());
        code.add("jmp debut_while_" + label);
        code.add("sortie_while_" + label + ":");
        return code;
    }
}

// Génération du exemple3me
public class CodeGenerator {
    public static void main(String[] args) {

        // Exemple 1: Calcul de prixTtc
        Node prixHt = new LetNode("prixHt", new NumberNode(200));
        Node prixTtc = new LetNode("prixTtc", new BinaryOpNode("*", new VariableNode("prixHt"), new NumberNode(119)));
        prixTtc = new LetNode("prixTtc", new BinaryOpNode("/", new VariableNode("prixTtc"), new NumberNode(100)));

        // Génération du code
        List<String> exemple1 = new ArrayList<>();
        exemple1.add("DATA SEGMENT");
        exemple1.add("\tprixHt DD");
        exemple1.add("\tprixTtc DD");
        exemple1.add("DATA ENDS");
        exemple1.add("CODE SEGMENT");
        exemple1.addAll(prixHt.generateCode());
        exemple1.addAll(prixTtc.generateCode());
        exemple1.add("out eax");
        exemple1.add("CODE ENDS");

        // Affichage du code généré
        exemple1.forEach(System.out::println);

        System.out.println("--------------------");

        // Exemple 2: PGCD avec boucle while
        Node a = new LetNode("a", new VariableNode("input"));
        Node b = new LetNode("b", new VariableNode("input"));
        Node condition = new BinaryOpNode(">", new VariableNode("b"), new NumberNode(0));
        Node aux = new LetNode("aux", new BinaryOpNode("mod", new VariableNode("a"), new VariableNode("b")));
        Node updateA = new LetNode("a", new VariableNode("b"));
        Node updateB = new LetNode("b", new VariableNode("aux"));
        Node loop = new WhileNode(condition, new LetNode("loop_body", new BinaryOpNode("+", aux, new BinaryOpNode("+", updateA, updateB))));

        // Génération du code
        List<String> exemple2 = new ArrayList<>();
        exemple2.add("DATA SEGMENT");
        exemple2.add("\ta DD");
        exemple2.add("\tb DD");
        exemple2.add("\taux DD");
        exemple2.add("DATA ENDS");
        exemple2.add("CODE SEGMENT");
        exemple2.addAll(a.generateCode());
        exemple2.addAll(b.generateCode());
        exemple2.addAll(loop.generateCode());
        exemple2.add("out eax");
        exemple2.add("CODE ENDS");

        // Affichage du code généré
        exemple2.forEach(System.out::println);

        System.out.println("--------------------");

        // Exemple 3: PGCD avec fonction lambda (récursive)
        Node x = new LetNode("x", new VariableNode("input"));
        Node y = new LetNode("y", new VariableNode("input"));
        Node pgcd = new LetNode("pgcd", new BinaryOpNode("lambda", new VariableNode("a"), new VariableNode("b")));
        pgcd = new LetNode("pgcd", new BinaryOpNode("if", new BinaryOpNode(">", new VariableNode("a"), new NumberNode(0)),
            new BinaryOpNode("pgcd", new VariableNode("b"), new BinaryOpNode("mod", new VariableNode("a"), new VariableNode("b"))), new VariableNode("a")));
        Node z = new LetNode("z", new BinaryOpNode("output", new VariableNode("pgcd")));

        List<String> exemple3 = new ArrayList<>();
        
        exemple3.add("DATA SEGMENT");
        exemple3.add("\tx DD");
        exemple3.add("\ty DD");
        exemple3.add("\tpgcd DD");
        exemple3.add("DATA ENDS");
        exemple3.add("CODE SEGMENT");
        exemple3.addAll(x.generateCode());
        exemple3.addAll(y.generateCode());
        exemple3.addAll(pgcd.generateCode());
        exemple3.addAll(z.generateCode());
        exemple3.add("out eax");
        exemple3.add("CODE ENDS");

        // Affichage du code généré
        exemple3.forEach(System.out::println);


       
    }
}
