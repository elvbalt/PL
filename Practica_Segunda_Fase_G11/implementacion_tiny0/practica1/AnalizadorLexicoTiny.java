package practica1;

import java.io.Reader;
import java.util.Hashtable;

import errors.GestionErroresTiny;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AnalizadorLexicoTiny {
	 
	   
   private Reader input;
   private StringBuffer lex;
   private int sigCar;
   private int filaInicio;
   private int columnaInicio;
   private int filaActual;
   private int columnaActual;
   private static String NL = System.getProperty("line.separator");
   private Hashtable<String,ClaseLexica> pal_reserv;
   //para el error
   private GestionErroresTiny errores;
   //conjunto de estados de nuestro diagrama
   
   private static enum Estado {
    INICIO, REC_POR, REC_DIV, REC_PAP, REC_PCIERR, REC_LAP, REC_LCIERR,
    REC_PUNCOMA, REC_MAS, REC_MENOS, REC_ID, REC_ENT, REC_COM, REC_EVALUA, 
    REC_IGUAL, REC_ASIG, REC_MENOR, REC_MENIG, REC_MAYOR,REC_MAYIG, REC_EXCLAM, REC_DISTINTO,
    REC_AMPER, REC_CIERREDECL, REC_ALM1, REC_0,
    REC_EXP, REC_IDEC, REC_DEC, REC_0DEC, REC_MAS2, REC_MENOS2, REC_ENT2, REC_02,
    REC_EOF
   }

   private Estado estado;

   public AnalizadorLexicoTiny(Reader input, GestionErroresTiny gestionErroresEval) throws IOException {
    this.input = input;
    lex = new StringBuffer();
    sigCar = input.read();
    filaActual=1;
    columnaActual=1;
    rellenar_tabla();
    this.errores =gestionErroresEval;
   }

   public void rellenar_tabla(){
     pal_reserv = new Hashtable<String,ClaseLexica>();
     pal_reserv.put("int", ClaseLexica.PR_ENT);
     pal_reserv.put("real", ClaseLexica.PR_REAL);
     pal_reserv.put("bool", ClaseLexica.PR_BOOL);
     pal_reserv.put("true", ClaseLexica.TRUE);
     pal_reserv.put("false", ClaseLexica.FALSE);
     pal_reserv.put("and", ClaseLexica.AND);
     pal_reserv.put("or", ClaseLexica.OR);
     pal_reserv.put("not", ClaseLexica.NOT);
   }
   //operacion para leer el siguiente componente lexico
   public UnidadLexica sigToken() throws IOException {
     estado = Estado.INICIO;
     filaInicio = filaActual;
     columnaInicio = columnaActual;
     lex.delete(0,lex.length());
     while(true) {
         switch(estado) {
           case INICIO: 
              if(hayLetra() || hayBarraBaja())  transita(Estado.REC_ID);
              else if (hayDigitoPos()) transita(Estado.REC_ENT);
              else if (hayCero()) transita(Estado.REC_0);
              else if (haySuma()) transita(Estado.REC_MAS);
              else if (hayResta()) transita(Estado.REC_MENOS);
              else if (hayMul()) transita(Estado.REC_POR);
              else if (hayDiv()) transita(Estado.REC_DIV);
              else if (hayPAp()) transita(Estado.REC_PAP);
              else if (hayPCierre()) transita(Estado.REC_PCIERR);
              else if (hayLAp()) transita(Estado.REC_LAP);
              else if (hayLCierre()) transita(Estado.REC_LCIERR);
              else if (hayIgual()) transita(Estado.REC_ASIG);
              else if (hayMenor()) transita(Estado.REC_MENOR);
              else if (hayMayor()) transita(Estado.REC_MAYOR);
              else if (hayExclamacion()) transita(Estado.REC_EXCLAM);
              else if (hayAmpersand()) transita(Estado.REC_AMPER);
              else if (hayArroba()) transita(Estado.REC_EVALUA);
              else if (hayPuntoComa()) transita(Estado.REC_PUNCOMA);
              else if (hayAlmohadilla()) transitaIgnorando(Estado.REC_ALM1);
              else if (haySep()) transitaIgnorando(Estado.INICIO);
              else if (hayEOF()) transita(Estado.REC_EOF);
              else error();
              break;
           case REC_ID: 
              if (hayLetra() || hayDigito() || hayBarraBaja()) transita(Estado.REC_ID);
              else return unidadId();               
              break;
           case REC_ENT:
               if (hayDigito()) transita(Estado.REC_ENT);
               else if (hayExponente()) transita(Estado.REC_EXP);
               else if (hayPunto()) transita(Estado.REC_IDEC);
               else return unidadEnt();
               break;
           case REC_MAS:
               if (hayDigitoPos()) transita(Estado.REC_ENT);
               else if (hayCero()) transita(Estado.REC_0);
               else return unidadMas();
               break;
           case REC_MENOS: 
               if (hayDigito()) transita(Estado.REC_ENT);
               else if (hayCero()) transita(Estado.REC_0);
               else return unidadMenos();
               break;
           case REC_0:
               if (hayExponente()) transita(Estado.REC_EXP); 
               else if (hayPunto()) transita(Estado.REC_IDEC);
               else return unidadEnt();
               break;
           case REC_POR: return unidadPor();
           case REC_DIV: return unidadDiv();              
           case REC_PAP: return unidadPAp();
           case REC_PCIERR: return unidadPCierre();
           case REC_LAP: return unidadLAp();
           case REC_LCIERR: return unidadLCierre();
           case REC_IGUAL: return unidadIgual();
           case REC_MENIG: return unidadMenorIgual();
           case REC_MAYIG: return unidadMayorIgual();
           case REC_DISTINTO: return unidadDistinto();
           case REC_PUNCOMA: return unidadPuntoComa();
           case REC_CIERREDECL: return unidadCierreDeclaracion();
           case REC_EVALUA: return unidadEvalua();
           case REC_ASIG:
        	   if(hayIgual()) transita(Estado.REC_IGUAL);
        	   else return unidadAsig();
        	   break;
           case REC_MENOR:
        	   if(hayIgual()) transita(Estado.REC_MENIG);
        	   else return unidadMenor();
        	   break;
           case REC_MAYOR:
        	   if(hayIgual()) transita(Estado.REC_MAYIG);
        	   else return unidadMayor();
        	   break;
           case REC_EXCLAM:
               if (hayIgual()) transita(Estado.REC_DISTINTO);
               else error();
               break;
           case REC_AMPER:
               if (hayAmpersand()) transita(Estado.REC_CIERREDECL);
               else error();
               break;
           case REC_ALM1:
               if (hayAlmohadilla()) transitaIgnorando(Estado.REC_COM);
               else error();
               break;
           case REC_COM: 
               if (hayNL()) transitaIgnorando(Estado.INICIO);
               else if (hayEOF()) transita(Estado.REC_EOF);
               else transitaIgnorando(Estado.REC_COM);
               break;
           case REC_EOF: return unidadEof();
           //PARTE DECIMAL
           case REC_IDEC:
               if (hayDigito()) transita(Estado.REC_DEC);
               else error();
               break;
           case REC_DEC: 
               if (hayDigitoPos()) transita(Estado.REC_DEC);
               else if(hayExponente()) transita(Estado.REC_EXP);
               else if (hayCero()) transita(Estado.REC_0DEC);
               else return unidadReal();
               break;
           case REC_0DEC: 
               if (hayDigitoPos()) transita(Estado.REC_DEC);
               else if (hayCero()) transita(Estado.REC_0DEC);
               else error(); //return unidadReal() 
               break;
           //PARTE EXPONENTE
           case REC_EXP:
               if (hayDigitoPos()) transita(Estado.REC_ENT2);
               else if(hayCero()) transita(Estado.REC_02);
               else if(haySuma()) transita(Estado.REC_MAS2);
               else if(hayResta()) transita(Estado.REC_MENOS2);
               else error();
               break;
           case REC_MAS2:
               if (hayDigitoPos()) transita(Estado.REC_ENT2);
               else if (hayCero()) transita(Estado.REC_02);
               else error();
               break;
           case REC_MENOS2: 
               if (hayDigitoPos()) transita(Estado.REC_ENT2);
               else if (hayCero()) transita(Estado.REC_02);
               else error();
               break;
           case REC_ENT2: 
               if (hayDigito()) transita(Estado.REC_ENT2);
               else return unidadReal();
               break;
           case REC_02: return unidadReal();
         }
     }    
   }
   //cambio de estado 
   private void transita(Estado sig) throws IOException {
     lex.append((char)sigCar);
     sigCar();         
     estado = sig;
   }
   private void transitaIgnorando(Estado sig) throws IOException {
     sigCar();         
     filaInicio = filaActual;
     columnaInicio = columnaActual;
     estado = sig;
   }
   private void sigCar() throws IOException {
     sigCar = input.read();
     if (sigCar == NL.charAt(0)) saltaFinDeLinea();
     if (sigCar == '\n') {
        filaActual++;
        columnaActual=0;
     }
     else {
       columnaActual++;  
     }
   }
   private void saltaFinDeLinea() throws IOException {
      for (int i=1; i < NL.length(); i++) {
          sigCar = input.read();
          if (sigCar != NL.charAt(i)) error();
      }
      sigCar = '\n';
   }
   
   private boolean hayLetra() {return sigCar >= 'a' && sigCar <= 'z' ||
                                      sigCar >= 'A' && sigCar <= 'Z';}
   private boolean hayDigitoPos() {return sigCar >= '1' && sigCar <= '9';}
   private boolean hayCero() {return sigCar == '0';}
   private boolean hayDigito() {return hayDigitoPos() || hayCero();}
   private boolean hayExponente() {return sigCar == 'e' || sigCar == 'E';}
   private boolean haySuma() {return sigCar == '+';}
   private boolean hayResta() {return sigCar == '-';}
   private boolean hayMul() {return sigCar == '*';}
   private boolean hayDiv() {return sigCar == '/';}
   private boolean hayPAp() {return sigCar == '(';}
   private boolean hayPCierre() {return sigCar == ')';}
   private boolean hayLAp() {return sigCar == '{';}
   private boolean hayLCierre() {return sigCar == '}';}
   private boolean hayIgual() {return sigCar == '=';}
   private boolean hayMayor() {return sigCar == '>';}
   private boolean hayMenor() {return sigCar == '<';}
   private boolean hayExclamacion() {return sigCar == '!';}
   private boolean hayPuntoComa() {return sigCar == ';';}
   private boolean hayPunto() {return sigCar == '.';}
   private boolean hayBarraBaja() {return sigCar == '_';}
   private boolean hayAmpersand() {return sigCar == '&';}
   private boolean hayArroba() {return sigCar == '@';}
   private boolean hayAlmohadilla() {return sigCar == '#';}
   private boolean haySep() {return sigCar == ' ' || sigCar == '\t' || sigCar=='\n';}
   private boolean hayNL() {return sigCar == '\r' || sigCar == '\b' || sigCar == '\n';}
   private boolean hayEOF() {return sigCar == -1;}
   private UnidadLexica unidadId() {
     if(pal_reserv.containsKey(lex.toString().toLowerCase())){
       return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,pal_reserv.get(lex.toString().toLowerCase()));
     }
     else{
       return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.IDEN,lex.toString()); 
     }
   }  
   private UnidadLexica unidadEnt() {
     return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.ENT,lex.toString());     
   }    
   private UnidadLexica unidadReal() {
     return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.REAL,lex.toString());     
   }    
   private UnidadLexica unidadMas() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAS);     
   }    
   private UnidadLexica unidadMenos() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOS);     
   }    
   private UnidadLexica unidadPor() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.POR);     
   }    
   private UnidadLexica unidadDiv() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.DIV);     
   }    
   private UnidadLexica unidadPAp() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PAP);     
   }    
   private UnidadLexica unidadPCierre() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PCIERRE);     
   }
   private UnidadLexica unidadLAp() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LAP);     
   }    
   private UnidadLexica unidadLCierre() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LCIERRE);     
   }
   private UnidadLexica unidadAsig() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.ASIG);     
   }   
   private UnidadLexica unidadIgual() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.IGUAL);     
   }  
   private UnidadLexica unidadMayor() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAYOR);     
   } 
   private UnidadLexica unidadMayorIgual() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAYORIG);     
   } 
   private UnidadLexica unidadMenor() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOR);     
   } 
   private UnidadLexica unidadMenorIgual() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENORIG);     
   } 
   private UnidadLexica unidadDistinto() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.DISTINTO);     
   }
   private UnidadLexica unidadPuntoComa() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PUNCOMA);     
   }      
   private UnidadLexica unidadCierreDeclaracion() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.CIERREDECLARACION);     
   }  
   private UnidadLexica unidadEvalua() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EVALUA);     
   }  
   private UnidadLexica unidadEof() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EOF);     
   }
   private void error()
   {
	   errores.errorLexico(filaActual,columnaActual,(char)sigCar);
   }
   public static void main(String arg[]) throws IOException {
	     Reader input = new InputStreamReader(new FileInputStream("input.txt"));
	     AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input, new GestionErroresTiny());
	     UnidadLexica unidad;
	     do {
	       unidad = al.sigToken();
	       System.out.println(unidad);
	     }
	     while (unidad.clase() != ClaseLexica.EOF);
	    } 
}
