package cc.asint;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
public class CCMain{
   public static void main(String[] args) throws Exception {
	   try {
			Reader input = new InputStreamReader(new FileInputStream(args[0]));
			AnalizadorSintacticoTiny cc = new AnalizadorSintacticoTiny(input);
			cc.disable_tracing();
			cc.programa();
			System.out.println("OK");
		}
		catch (FileNotFoundException | ParseException | RuntimeException ex){
			System.out.println(ex.getMessage());
		}
   }
}