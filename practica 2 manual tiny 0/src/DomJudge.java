
import asint.AnalizadorSintacticoTiny0;
import asint.AnalizadorSintacticoTiny0DJ;
import errors.GestionErroresTiny0.ErrorLexico;
import errors.GestionErroresTiny0.ErrorSintactico;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
public class DomJudge{
   public static void main(String[] args) throws Exception {
     try{  
      AnalizadorSintacticoTiny0 asint = new AnalizadorSintacticoTiny0DJ(new InputStreamReader(System.in));
 
	  //AnalizadorSintacticoTiny0 asint = new AnalizadorSintacticoTiny0DJ(new InputStreamReader(new FileInputStream("prueba3.txt")));
      asint.analiza();
     }
     catch(ErrorSintactico e) {
        System.out.println("ERROR_SINTACTICO"); 
     }
     catch(ErrorLexico e) {
        System.out.println("ERROR_LEXICO"); 
     }
   }
}