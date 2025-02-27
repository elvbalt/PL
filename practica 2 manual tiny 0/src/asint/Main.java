package asint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {
   public static void main(String[] args) throws FileNotFoundException, IOException {
     try{ 
    	 Reader input = new InputStreamReader(new FileInputStream(args[0]));
	     AnalizadorSintacticoTiny0 asint = new AnalizadorSintacticoTiny0(input);
	     asint.analiza();
	     System.out.println("OK");
	}
	catch (RuntimeException ex){
		System.out.println(ex.getMessage());
	}
 }
}   
   
