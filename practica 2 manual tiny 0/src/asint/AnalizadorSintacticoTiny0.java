package asint;

import alex.UnidadLexica;
import alex.AnalizadorLexicoTiny0;
import alex.ClaseLexica;
import errors.GestionErroresTiny0;
import java.io.IOException;
import java.io.Reader;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalizadorSintacticoTiny0 {
   private UnidadLexica anticipo;       // token adelantado
   private AnalizadorLexicoTiny0 alex;   // analizador léxico 
   private GestionErroresTiny0 errores;  // gestor de errores
   private Set<ClaseLexica> esperados;  // clases léxicas esperadas
   
   public AnalizadorSintacticoTiny0(Reader input) throws IOException {
        // se crea el gestor de errores
      errores = new GestionErroresTiny0();
        // se crea el analizador léxico
      alex = new AnalizadorLexicoTiny0(input,errores);
      esperados = EnumSet.noneOf(ClaseLexica.class);
        // Se lee el primer token adelantado
      sigToken();                      
   }
   public void analiza() {
      programa();
      empareja(ClaseLexica.EOF);
   }
   private void programa() {
       bloque();
   }
   
   private void bloque() {
	// TODO Auto-generated method stub
	empareja(ClaseLexica.LAP);
	
	declaraciones();
	
	instrucciones();
	
	empareja(ClaseLexica.LCIERRE);
	
   }
   
   private void declaraciones() {
	   switch(anticipo.clase()) {
       case INT: case BOOL: case REAL: case EXP: case DOUBLE: 
           lista_declaraciones();
           cierredec();
           break;
       //case LCIERRE:  
    	   // Si encontramos un `LCIERRE`, podemos terminar sin declaraciones
       default: 
          esperados(ClaseLexica.INT,ClaseLexica.BOOL,ClaseLexica.REAL, ClaseLexica.REAL, ClaseLexica.EXP, ClaseLexica.DOUBLE);
          break;
      }
   }
   private void lista_declaraciones() {
       declaracion();
       rec_declaracion();
   }
   
   private void declaracion() {
	    switch(anticipo.clase()) {
        case INT: case BOOL: case REAL: case EXP: case DOUBLE:
            tipo();
            empareja(ClaseLexica.IDEN);
            break;
        default: 
           esperados(ClaseLexica.INT,ClaseLexica.BOOL,ClaseLexica.REAL,ClaseLexica.EXP, ClaseLexica.DOUBLE);
           error();
       }  
   }
   private void cierredec() {
       switch(anticipo.clase()) {
       case FIN_DEC:
          empareja(ClaseLexica.FIN_DEC);
          break;
       default: 
       esperados(ClaseLexica.FIN_DEC);
       error();
       }  
   }
   private void tipo() {
	   switch(anticipo.clase()) {
       case INT: empareja(ClaseLexica.INT); break;  
       case BOOL: empareja(ClaseLexica.BOOL); break;
       case REAL: empareja(ClaseLexica.REAL); break;
       case EXP: empareja(ClaseLexica.EXP); break;
       case DOUBLE: empareja(ClaseLexica.DOUBLE); break;
       default:    
            esperados(ClaseLexica.INT,ClaseLexica.BOOL,ClaseLexica.REAL, ClaseLexica.EXP, ClaseLexica.DOUBLE);             
            error();
	   }
   }
   private void rec_declaracion() {
       switch(anticipo.clase()) {
           case PUNTOCOMA: 
               empareja(ClaseLexica.PUNTOCOMA);
               declaracion();
               rec_declaracion();
               break;
           default:
              esperados(ClaseLexica.PUNTOCOMA);
              break;
       }
   }
   private void instrucciones() {
	   switch(anticipo.clase()) {
       case ARROBA: //solo tenemos instrucciones tipo eval 
     	  lista_instrucciones();
       //case LCIERRE:  
    	   // Si encontramos un `LCIERRE`, podemos terminar sin instrucciones
           break;
         default:
             esperados(ClaseLexica.ARROBA);
             break;
	      }
   }
   private void lista_instrucciones() {
		instruccion();
		rec_instrucciones();
	}
   private void rec_instrucciones() {
       switch(anticipo.clase()) {
           case PUNTOCOMA: 
               empareja(ClaseLexica.PUNTOCOMA);
               instruccion();
               rec_instrucciones();
           //case LCIERRE:  
        	   // ??
               break;
           default:
              esperados(ClaseLexica.PUNTOCOMA);
              break;
       }
   } 
   private void instruccion() {
       switch(anticipo.clase()) {
           case ARROBA: 
               empareja(ClaseLexica.ARROBA);
               E0();
               break;
           default:
               esperados(ClaseLexica.ARROBA);
               error();
       }
   }
   
 //EXPRESIÓN NIVEL 0
   private void E0() {
       E1();
       RE0();
   }
   
   private void RE0() {
       switch(anticipo.clase()) {
           case IGUAL: 
              empareja(ClaseLexica.IGUAL);
               E0();
               break;
           default: 
              esperados(ClaseLexica.IGUAL);
              break;
       }    
    }
   
// EXPRESION NIVEL 1
   private void E1() {
       E2();
       RE1();
   }
   
   private void RE1() {
       switch(anticipo.clase()) {
           case IGUAL_IGUAL: case DIF: case MENOR: case MENOR_IGUAL: case MAYOR: case MAYOR_IGUAL:  
               OP1();
               E2();
               RE1();
               break;
           default: 
              esperados(ClaseLexica.POR,ClaseLexica.DIV);
              break;
       }    
    }
   private void OP1() {    
	     switch(anticipo.clase()) {
	         case IGUAL_IGUAL: empareja(ClaseLexica.IGUAL_IGUAL); break; 
	         case DIF: empareja(ClaseLexica.DIF); break; 
	         case MENOR: empareja(ClaseLexica.MENOR); break; 
	         case MENOR_IGUAL: empareja(ClaseLexica.MENOR_IGUAL); break; 
	         case MAYOR: empareja(ClaseLexica.MAYOR); break; 
	         case MAYOR_IGUAL: empareja(ClaseLexica.MAYOR_IGUAL); break;  
	         default:    
	             esperados(ClaseLexica.IGUAL,ClaseLexica.DIF,ClaseLexica.MENOR,ClaseLexica.MENOR_IGUAL,ClaseLexica.MAYOR,ClaseLexica.MAYOR_IGUAL);             
	             error();
	     }  
	   }

   //EXPRESION NIVEL 2
   private void E2() {
      E3();
      RE2();
      RE2_();
   }
   
   private void RE2(){
   		switch(anticipo.clase()) {
          case MENOS: empareja(ClaseLexica.MENOS); 
          		E3();
          		break;
          default:
              esperados(ClaseLexica.MENOS);
              break;
   		}   
   }

	private void RE2_(){
	   		switch(anticipo.clase()) {
	          case MAS: empareja(ClaseLexica.MAS);
          		E3();
          		RE2_();
          		break;
	          default:
	              esperados(ClaseLexica.MAS);
	              break;
	   		}   
	   }


//EXPRESION NIVEL 3
    private void E3() {
        E4();
        RE3();
    }  

    private void RE3() {
        switch(anticipo.clase()) {
            case AND: 
                empareja(ClaseLexica.AND); 
                E3();
                break;
            case OR: 
                empareja(ClaseLexica.OR); 
                E4();
                break; 
            default:
                esperados(ClaseLexica.AND,ClaseLexica.OR);
                break;
        }   
    }
   
 //EXPRESION NIVEL 4
    private void E4() {
        E5();
        RE4();
    }
    
    private void RE4() {
        switch(anticipo.clase()) {
            case POR: case DIV:
                OP4();
                E5();
                RE4();
                break;
            default: 
                esperados(ClaseLexica.POR,ClaseLexica.DIV);
                break;
        } 
    }
      
    private void OP4() {
        switch(anticipo.clase()) {
        case POR: empareja(ClaseLexica.POR); break; 
        case DIV: empareja(ClaseLexica.DIV); break; 
        default:    
            esperados(ClaseLexica.POR,ClaseLexica.DIV);             
            error();
        } 
            
    }
	//EXPRESION NIVEL 5
    private void E5() {
        switch(anticipo.clase()) {
		    case MENOS: empareja(ClaseLexica.MENOS); 
                E5();
                break;
		    case NOT: empareja(ClaseLexica.NOT); 
                E5();
                break;
            case TRUE:case FALSE:case ENT: case REAL: case EXP: case DOUBLE: case IDEN: case PAP:
                E6();
                break;
		    default:
		        esperados(ClaseLexica.MENOS,ClaseLexica.NOT);             
		        error();		
        }
      
    }

    //EXPRESION NIVEL 6
      private void E6() {
		switch(anticipo.clase()) {
         case TRUE:case FALSE:case ENT: case REAL: case EXP: case DOUBLE: case IDEN: 
	         EXPR();
	          break;
      	case PAP: 
      		empareja(ClaseLexica.PAP); 
      		E0(); 
      		empareja(ClaseLexica.PCIERRE); 
      		break;
      	 default:
             esperados(ClaseLexica.PAP);
             error();
		}
      }
      
      private void EXPR() {
  		switch(anticipo.clase()) {
  		     case TRUE: empareja(ClaseLexica.TRUE); break; 
  		     case FALSE: empareja(ClaseLexica.FALSE); break; 
  		     case ENT: empareja(ClaseLexica.ENT); break; 
  		     case REAL: empareja(ClaseLexica.REAL); break; 
  		     case EXP: empareja(ClaseLexica.EXP); break; 
  		     case DOUBLE: empareja(ClaseLexica.DOUBLE); break; 
  		     case IDEN: empareja(ClaseLexica.IDEN); break;
  		     default:    
  		         esperados(ClaseLexica.TRUE,ClaseLexica.FALSE,ClaseLexica.ENT,ClaseLexica.REAL, ClaseLexica.REAL, ClaseLexica.EXP, ClaseLexica.DOUBLE, ClaseLexica.IDEN);             
  		         error();
  		 }
  		}
        
       

   private void esperados(ClaseLexica ... esperadas) {
       for(ClaseLexica c: esperadas) {
           esperados.add(c);
       }
   }
   
   
   private void empareja(ClaseLexica claseEsperada) {
      if (anticipo.clase() == claseEsperada) {
          traza_emparejamiento(anticipo);
          sigToken();
      }    
      else {
          esperados(claseEsperada);
          error();
      }
   }
   private void sigToken() {
      try {
        anticipo = alex.sigToken();
        esperados.clear();
      }
      catch(IOException e) {
        errores.errorFatal(e);
      }
   }
   
    private void error() {
        errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), esperados);
    }
    
    protected void traza_emparejamiento(UnidadLexica anticipo) {}  
}
