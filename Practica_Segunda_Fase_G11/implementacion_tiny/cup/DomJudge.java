package cup;

import cup.alex.AnalizadorLexicoTiny;

import cup.asint.AnalizadorSintacticoTiny;
import cup.asint.AnalizadorSintacticoTinyDJ;
import cup.errors.GestionErroresTiny.ErrorLexico;
import cup.errors.GestionErroresTiny.ErrorSintactico;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class DomJudge {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(System.in);
     AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
     AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(alex);
	 //asint.setScanner(alex);
     try {    
        asint.debug_parse();
     }
     catch(ErrorLexico e) {
        System.out.println("ERROR_LEXICO"); 
     }
     catch(ErrorSintactico e) {
        System.out.println("ERROR_SINTACTICO"); 
     }
 }

}
