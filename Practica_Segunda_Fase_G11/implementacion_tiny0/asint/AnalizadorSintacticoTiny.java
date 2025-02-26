package asint;

import java.io.IOException;
import java.io.Reader;
import java.util.EnumSet;
import java.util.Set;

import errors.GestionErroresTiny;
import practica1.AnalizadorLexicoTiny;
import practica1.ClaseLexica;
import practica1.UnidadLexica;

public class AnalizadorSintacticoTiny {
   private UnidadLexica anticipo;       // token adelantado
   private AnalizadorLexicoTiny alex;   // analizador léxico 
   private GestionErroresTiny errores;  // gestor de errores
   private Set<ClaseLexica> esperados;  // clases léxicas esperadas
   
   public AnalizadorSintacticoTiny(Reader input) throws IOException {
        // se crea el gestor de errores
      errores = new GestionErroresTiny();
        // se crea el analizador léxico
      alex = new AnalizadorLexicoTiny(input,errores);
      
      //alex.fijaGestionErrores(errores);
      
      // se crea el conjunto de clases léxicas esperadas 
      // (estará incializadoa vacío)
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
	   	empareja(ClaseLexica.LAP);
		
	   	declaraciones();
		
		instrucciones();
		
		empareja(ClaseLexica.LCIERRE);
	}


private void declaraciones() {
    switch(anticipo.clase()) {
         case PR_ENT: case PR_BOOL: case PR_REAL:
             lista_declaraciones();
             //empareja(ClaseLexica.CIERREDECLARACION);
             cierredec();
             break;
         default: 
            esperados(ClaseLexica.PR_ENT,ClaseLexica.PR_BOOL,ClaseLexica.PR_REAL);
            break;
        }
   }
   
   private void lista_declaraciones() {
       declaracion();
       rec_declaración();
   }
   
   private void declaracion() {
	    switch(anticipo.clase()) {
         case PR_ENT: case PR_BOOL: case PR_REAL:
             tipo();
             empareja(ClaseLexica.IDEN);
             break;
         default: 
            esperados(ClaseLexica.PR_ENT,ClaseLexica.PR_BOOL,ClaseLexica.PR_REAL);
            error();
        }  
   }    
   
    private void cierredec() {
        switch(anticipo.clase()) {
        case CIERREDECLARACION:
           empareja(ClaseLexica.CIERREDECLARACION);
           break;
        default: 
        esperados(ClaseLexica.CIERREDECLARACION);
        error();
        }  
    }    

   private void tipo() {
	   switch(anticipo.clase()) {
       case PR_ENT: empareja(ClaseLexica.PR_ENT); break;  
       case PR_BOOL: empareja(ClaseLexica.PR_BOOL); break;
       case PR_REAL: empareja(ClaseLexica.PR_REAL); break;
       default:    
            esperados(ClaseLexica.PR_ENT,ClaseLexica.PR_BOOL,ClaseLexica.PR_REAL);             
            error();
   }
	
}

	private void rec_declaración() {
	       switch(anticipo.clase()) {
	           case PUNCOMA: 
	               empareja(ClaseLexica.PUNCOMA);
	               declaracion();
	               rec_declaración();
	               break;
	           default:
	              esperados(ClaseLexica.PUNCOMA);
	              break;
	       }
	   }
	private void instrucciones() {
	      switch(anticipo.clase()) {
          case EVALUA: 
        	  lista_instrucciones();
              break;
            default:
                esperados(ClaseLexica.EVALUA);
                break;
      }
	}
 
 
	private void lista_instrucciones() {
		instruccion();
		rec_instrucciones();
	}

	private void rec_instrucciones() {
       switch(anticipo.clase()) {
           case PUNCOMA: 
               empareja(ClaseLexica.PUNCOMA);
               instruccion();
               rec_instrucciones();
               break;
           default:
              esperados(ClaseLexica.PUNCOMA);
              break;
       }
   }  

    private void instruccion() {
        switch(anticipo.clase()) {
            case EVALUA: 
                empareja(ClaseLexica.EVALUA);
                E0();
                break;
            default:
                esperados(ClaseLexica.EVALUA);
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
           case ASIG: 
              empareja(ClaseLexica.ASIG);
               E0();
               break;
           default: 
              esperados(ClaseLexica.ASIG);
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
           case IGUAL: case DISTINTO: case MENOR: case MENORIG: case MAYOR: case MAYORIG:  
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
	         case IGUAL: empareja(ClaseLexica.IGUAL); break; 
	         case DISTINTO: empareja(ClaseLexica.DISTINTO); break; 
	         case MENOR: empareja(ClaseLexica.MENOR); break; 
	         case MENORIG: empareja(ClaseLexica.MENORIG); break; 
	         case MAYOR: empareja(ClaseLexica.MAYOR); break; 
	         case MAYORIG: empareja(ClaseLexica.MAYORIG); break;  
	         default:    
	             esperados(ClaseLexica.IGUAL,ClaseLexica.DISTINTO,ClaseLexica.MENOR,ClaseLexica.MENORIG,ClaseLexica.MAYOR,ClaseLexica.MAYORIG);             
	             error();
	     }  
	   }

   //EXPRESION NIVEL 2
   private void E2() {
      E3();
      RE2();
      REPR();
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

	private void REPR(){
	   		switch(anticipo.clase()) {
	          case MAS: empareja(ClaseLexica.MAS);
          		E3();
          		REPR();
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
            case TRUE:case FALSE:case ENT: case REAL:case IDEN: case PAP:
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
         case TRUE:case FALSE:case ENT: case REAL:case IDEN: 
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
		     case IDEN: empareja(ClaseLexica.IDEN); break;
		     default:    
		         esperados(ClaseLexica.TRUE,ClaseLexica.FALSE,ClaseLexica.ENT,ClaseLexica.REAL,ClaseLexica.IDEN);             
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
