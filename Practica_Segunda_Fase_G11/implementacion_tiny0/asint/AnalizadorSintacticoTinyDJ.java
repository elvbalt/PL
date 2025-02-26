package asint;

import java.io.IOException;
import java.io.Reader;

import practica1.UnidadLexica;

public class AnalizadorSintacticoTinyDJ extends AnalizadorSintacticoTiny {
       public AnalizadorSintacticoTinyDJ(Reader input) throws IOException {
          super(input); 
       }
     protected final void traza_emparejamiento(UnidadLexica unidad) {
         switch(unidad.clase()) {
		   case IDEN: case ENT: case REAL: System.out.println(unidad.lexema()); break;
                   default: System.out.println(unidad.clase().getSimbolo());
	 }
     } 
}
