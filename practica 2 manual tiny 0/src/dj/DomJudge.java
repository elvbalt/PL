package dj;
import asint.AnalizadorSintacticoTiny0;
import asint.AnalizadorSintacticoTiny0DJ;
import errors.GestionErroresTiny0.ErrorLexico;
import errors.GestionErroresTiny0.ErrorSintactico;
import java.io.InputStreamReader;
public class DomJudge{
   public static void main(String[] args) throws Exception {
     try{  
      AnalizadorSintacticoTiny0 asint = new AnalizadorSintacticoTiny0DJ(new InputStreamReader(System.in));
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