package asint;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.FileNotFoundException;

import alex.AnalizadorLexicoTiny;

public class Main {
   public static void main(String[] args) throws Exception {
	 try {
		 Reader input = new InputStreamReader(new FileInputStream(args[0]));
		 AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		 AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(alex);
		 //asint.parse();
		 System.out.println("OK");
	 }	
	 catch (FileNotFoundException | RuntimeException ex) {
		 System.out.println(ex.getMessage());
	 }
   }
}   
   
