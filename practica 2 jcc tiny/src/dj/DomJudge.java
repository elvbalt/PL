package dj;
import asint.AnalizadorSintacticoTiny;
import asint.AnalizadorSintacticoTinyDJ;
import asint.ParseException;
import asint.TokenMgrError;

import java.io.FileReader;
import java.io.InputStreamReader;
public class DomJudge{
   public static void main(String[] args) throws Exception {
     try{  
      AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(new InputStreamReader(System.in));
      //Reader input = new InputStreamReader(new FileInputStream("sample3.in"));
	  //AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(input);
      asint.programa();
     }
     catch(ParseException e) {
        System.out.println("ERROR_SINTACTICO"); 
     }
     catch(TokenMgrError e) {
        System.out.println("ERROR_LEXICO"); 
     }
   }
}