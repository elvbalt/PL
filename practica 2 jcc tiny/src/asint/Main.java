package asint;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
public class Main{
   public static void main(String[] args) throws Exception {
      /*AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(new FileReader(args[0]));
      asint.disable_tracing();
      asint.programa();*/
	   try {
			Reader input = new InputStreamReader(new FileInputStream(args[0]));
			//Reader input = new InputStreamReader(new FileInputStream("04.in"));
			AnalizadorSintacticoTiny cc = new AnalizadorSintacticoTiny(input);
			//cc.disable_tracing();
			cc.programa();
			System.out.println("OK");
		}
		catch (FileNotFoundException | ParseException | RuntimeException ex){
			System.out.println(ex.getMessage());
		}
   }
}