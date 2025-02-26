package cc.asint;

import java.io.Reader;
import java.util.Hashtable;



public class AnalizadorSintacticoTinyDJ extends AnalizadorSintacticoTiny {
    private Hashtable<Integer,String> pal_reserv;

    public AnalizadorSintacticoTinyDJ(Reader r) {
        super(r);
        rellenar_tabla();
        disable_tracing();
    }

    public void rellenar_tabla(){
		pal_reserv = new Hashtable<Integer,String>();
		pal_reserv.put(0,"<EOF>");
		pal_reserv.put(8,"<int>");
		pal_reserv.put(9,"<real>");
		pal_reserv.put(10,"<bool>");
		pal_reserv.put(11,"<true>");
		pal_reserv.put(12,"<false>");
		pal_reserv.put(13,"<and>");
		pal_reserv.put(14,"<or>");
		pal_reserv.put(15,"<not>");
		pal_reserv.put(16,"<null>");
		pal_reserv.put(17,"<proc>");
		pal_reserv.put(18,"<if>");
		pal_reserv.put(19,"<else>");
		pal_reserv.put(20,"<while>");
		pal_reserv.put(21,"<struct>");
		pal_reserv.put(22,"<string>");
		pal_reserv.put(23,"<new>");
		pal_reserv.put(24,"<delete>");
		pal_reserv.put(25,"<read>");
		pal_reserv.put(26,"<write>");
		pal_reserv.put(27,"<nl>");
		pal_reserv.put(28,"<type>");
		pal_reserv.put(29,"<call>");
	}
    //modificar este metodo
    final protected void trace_token(Token t, String where) {
    	   if(pal_reserv.containsKey(t.kind)){
               System.out.println(pal_reserv.get(t.kind));
           }
           else{
               System.out.println(t.image);
           }
    		
    }
    
}
