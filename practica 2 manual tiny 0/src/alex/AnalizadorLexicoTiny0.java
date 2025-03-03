package alex;

import java.io.FileInputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;

import errors.GestionErroresTiny0;

import java.io.InputStreamReader;
import java.io.IOException;

public class AnalizadorLexicoTiny0 {
    
   public static class ECaracterInesperado extends RuntimeException {
       public ECaracterInesperado(String msg) {
           super(msg);
       }
   }; 

   private Reader input;
   private StringBuffer lex;
   private int sigCar;
   private int filaInicio;
   private int columnaInicio;
   private int filaActual;
   private int columnaActual;
   private static String NL = System.getProperty("line.separator");
   private GestionErroresTiny0 errores;
   
   
   private static enum Estado {
    INICIO, REC_POR, REC_DIV, REC_PAP, REC_PCIERR, REC_COMA, REC_IGUAL,
    REC_MAS, REC_MENOS, REC_ID, REC_ENT, REC_0, REC_IDEC, REC_DEC, REC_COM, REC_EOF,
    
    
    REC_IEXCP,REC_MAS_EXP,REC_MENOS_EXP,REC_EL_CERO, REC_EXP,
    
    REC_ICOM,REC_INOT,REC_NOT,REC_II, REC_MENOR,REC_MENOR_IGUAL, REC_MAYOR, REC_MAYOR_IGUAL,
    REC_IFIN_DEC, REC_FIN_DEC, REC_AR, REC_PUNT_COMA ,
    REC_LLAVE_AP,REC_LLAVE_CIERRE
    
   }
   
   private static HashMap<String,ClaseLexica>privateWord;
   private void initPrivateWord() {
	   privateWord = new HashMap<String, ClaseLexica>();
	   privateWord.put("bool",ClaseLexica.BOOL);
	   privateWord.put("int",ClaseLexica.INT);
	   privateWord.put("real",ClaseLexica.REAL);
	   privateWord.put("or",ClaseLexica.OR);
	   privateWord.put("not",ClaseLexica.NOT);
	   privateWord.put("and",ClaseLexica.AND);
	   privateWord.put("true",ClaseLexica.TRUE);
	   privateWord.put("false",ClaseLexica.FALSE);
   }
   private Estado estado;

   public AnalizadorLexicoTiny0(Reader input, GestionErroresTiny0 errores) throws IOException {
    this.input = input;
    this.errores = errores;
    lex = new StringBuffer();
    sigCar = input.read();
    filaActual=1;
    columnaActual=1;
    initPrivateWord();
   }
   
   public UnidadLexica sigToken() throws IOException {
     estado = Estado.INICIO;
     filaInicio = filaActual;
     columnaInicio = columnaActual;
     lex.delete(0,lex.length());
     while(true) {
         switch(estado) {
           case INICIO: 
              if(hayLetra() || hayBarraBajo())  transita(Estado.REC_ID);
              else if (hayDigitoPos()) transita(Estado.REC_ENT);
              else if (hayCero()) transita(Estado.REC_0);
              else if (haySuma()) transita(Estado.REC_MAS);
              else if (hayResta()) transita(Estado.REC_MENOS);
              else if (hayMul()) transita(Estado.REC_POR);
              else if (hayDiv()) transita(Estado.REC_DIV);
              else if (hayPAp()) transita(Estado.REC_PAP);
              else if (hayPCierre()) transita(Estado.REC_PCIERR);
              else if (hayIgual()) transita(Estado.REC_IGUAL);
              //else if (hayComa()) transita(Estado.REC_COMA);
              else if (hayAlmohadilla()) transitaIgnorando(Estado.REC_ICOM);
              else if (haySep()) transitaIgnorando(Estado.INICIO);
              else if (hayEOF()) transita(Estado.REC_EOF);
              else if (hayExclamacion())transita(Estado.REC_INOT);
              else if (hayMenor())transita(Estado.REC_MENOR);
              else if (hayMayor())transita(Estado.REC_MAYOR);
              else if (hayArroba())transita(Estado.REC_AR);
              else if (hayAmpersand())transita(Estado.REC_IFIN_DEC);
              else if (hayllaveAp())transita(Estado.REC_LLAVE_AP);
              else if (hayllaveCierre())transita(Estado.REC_LLAVE_CIERRE);
              else if (hayPuntoComa())transita(Estado.REC_PUNT_COMA);
              else error();
              break;
           case REC_ID: 
              if (hayLetra() || hayDigito() || hayBarraBajo()) transita(Estado.REC_ID);
              else return unidadId();               
              break;
           case REC_ENT:
               if (hayDigito()) transita(Estado.REC_ENT);
               else if (hayPunto()) transita(Estado.REC_IDEC);
               else if(hayExp())transita(Estado.REC_IEXCP);
               else return unidadEnt();
               break;
           case REC_0:
               if (hayPunto()) transita(Estado.REC_IDEC);
               else if(hayExp())transita(Estado.REC_IEXCP);
               else return unidadEnt();
               break;
           case REC_MAS:
               if (hayDigitoPos()) transita(Estado.REC_ENT);
               else if(hayCero()) transita(Estado.REC_0);
               else return unidadMas();
               break;
           case REC_MENOS: 
               if (hayDigitoPos()) transita(Estado.REC_ENT);
               else if(hayCero()) transita(Estado.REC_0);
               else return unidadMenos();
               break;
           case REC_POR: return unidadPor();
           case REC_DIV: return unidadDiv();              
           case REC_PAP: return unidadPAp();
           case REC_PCIERR: return unidadPCierre();
           case REC_IGUAL:
        	   if(!hayIgual()) 
        		   return unidadIgual();
        	   transita(Estado.REC_II);
        	   break;
           case REC_COMA: return unidadComa();
           case  REC_ICOM :
        	   if (hayAlmohadilla()) transitaIgnorando(Estado.REC_COM);
        	   else error();
        	   break; 
           case REC_COM: 
               if (hayNL()) transitaIgnorando(Estado.INICIO);
               else if (hayEOF()) transita(Estado.REC_EOF);
               else transitaIgnorando(Estado.REC_COM);
               break;
           case REC_EOF: return unidadEof();
           case REC_IDEC:
               if (hayDigitoPos()||hayCero() ) transita(Estado.REC_DEC);
               else error();
               break;
           case REC_DEC: 
               if (hayDigitoPos()) transita(Estado.REC_DEC);
               else if (hayCero()) transita(Estado.REC_IDEC);
               else if(hayExp())transita(Estado.REC_IEXCP);
               else return unidadReal();
               break;

           case  REC_INOT :
        	   if (hayIgual())transita(Estado.REC_NOT);
        	   else error();
        	   break; 
           case  REC_NOT :
        	   return unidadDiferente();
           case REC_II:
        	   return unidadIgualIgual(); 
           case  REC_MENOR :
        	   if(!hayIgual())return unidadMenor();
        	   transita(Estado.REC_MENOR_IGUAL);
        	   break; 
           case REC_MENOR_IGUAL :
        	   return unidadMenorIgual(); 
           case  REC_MAYOR :
        	   if(!hayIgual())return unidadMayor();
        	   transita(Estado.REC_MAYOR_IGUAL);
        	   break; 
           case  REC_MAYOR_IGUAL :
        	   return unidadMayorIgual(); 
           case REC_IFIN_DEC :
        	   if(hayAmpersand()) transita(Estado.REC_FIN_DEC);
        	   else error();
        	   break; 
           case  REC_FIN_DEC :
        	   return unidadFin_Dec(); 
           case  REC_AR :
        	   return unidadArroba(); 
           case  REC_PUNT_COMA: 
        	   return unidadPuntoComa();
           case REC_LLAVE_AP:
        	   return unidadLlaveAp();
           case REC_LLAVE_CIERRE:
        	   return unidadLlaveCierre();
           case REC_IEXCP:
        	   if(haySuma())transita(Estado.REC_MAS_EXP);
        	   else if(hayResta())transita(Estado.REC_MENOS_EXP);
        	   else if(hayCero())transita(Estado.REC_EL_CERO);
        	   else if(hayDigito())transita(Estado.REC_EXP);
        	   else error(); 
        	   break;
           case REC_MAS_EXP:
        	   if(hayDigito())transita(Estado.REC_EXP);
        	   else if(hayCero())transita(Estado.REC_EL_CERO);
        	   else error();
        	   break;
           case REC_MENOS_EXP:
        	   if(hayDigito())transita(Estado.REC_EXP);
        	   else if(hayCero())transita(Estado.REC_EL_CERO);
        	   else error();
        	   break;
           case REC_EL_CERO:
        	   return unidadExp();
           case REC_EXP:
        	   if(!hayDigito())
        		   return unidadExp();
        	   transita(Estado.REC_EXP);
        	   break;
         }
     }    
   }
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
   private boolean hayBarraBajo() {return sigCar == '_';}
   private boolean hayDigitoPos() {return sigCar >= '1' && sigCar <= '9';}
   private boolean hayCero() {return sigCar == '0';}
   private boolean hayDigito() {return hayDigitoPos() || hayCero();}
   private boolean haySuma() {return sigCar == '+';}
   private boolean hayResta() {return sigCar == '-';}
   private boolean hayMul() {return sigCar == '*';}
   private boolean hayDiv() {return sigCar == '/';}
   private boolean hayPAp() {return sigCar == '(';}
   private boolean hayPCierre() {return sigCar == ')';}
   private boolean hayIgual() {return sigCar == '=';}
   private boolean hayComa() {return sigCar == ',';}
   private boolean hayPunto() {return sigCar == '.';}
   private boolean hayAlmohadilla() {return sigCar == '#';}
   private boolean haySep() {return sigCar == ' ' || sigCar == '\t' || sigCar=='\n';}
   private boolean hayNL() {return sigCar == '\r' || sigCar == '\b' || sigCar == '\n';}
   private boolean hayEOF() {return sigCar == -1;}
   
   // implementado 
   
   private boolean hayExclamacion() {return sigCar == '!';}
   private boolean hayMenor() {return sigCar == '<';}
   private boolean hayMayor() {return sigCar == '>';}
   private boolean hayArroba() {return sigCar == '@';}
   private boolean hayAmpersand() {return sigCar == '&';}
   private boolean hayllaveAp() {return sigCar == '{';}
   private boolean hayllaveCierre() {return sigCar == '}';}
   private boolean hayPuntoComa() {return sigCar == ';';}
   private boolean hayExp() {return  sigCar== 'e' || sigCar == 'E';}
   
   
   
   private UnidadLexica unidadId() {
	   String entrada = lex.toString();
	   if(privateWord.containsKey(entrada.toLowerCase()))
		   return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,privateWord.get(entrada.toLowerCase()));
	   else 
		   return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.IDEN,lex.toString());  

   }  
   private UnidadLexica unidadEnt() {
     return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.ENT,lex.toString());     
   }    
   private UnidadLexica unidadReal() {
     return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.DOUBLE,lex.toString());     
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
   private UnidadLexica unidadIgual() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.IGUAL);     
   }    
   private UnidadLexica unidadComa() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.COMA);     
   }    
   private UnidadLexica unidadEof() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EOF);     
   }    
   private void error() {
	   errores.errorLexico(filaActual,columnaActual,(char)sigCar);
   }

   
   private UnidadLexica unidadPuntoComa() {
	   return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,ClaseLexica.PUNTOCOMA );
   }
   
   private UnidadLexica unidadMenor() {
	   return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,ClaseLexica.MENOR );	   
   }
   private UnidadLexica unidadMenorIgual() {
	   return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,ClaseLexica.MENOR_IGUAL );
   }
   private UnidadLexica unidadMayor() {
	   return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,ClaseLexica.MAYOR);	   
   }
   private UnidadLexica unidadMayorIgual() {
	   return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,ClaseLexica.MAYOR_IGUAL );	   
   }
   private UnidadLexica unidadArroba() {
	   return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,ClaseLexica.ARROBA );	   
   }
   private UnidadLexica unidadDiferente() {
	   return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,ClaseLexica.DIF );	   
   }
   private UnidadLexica unidadFin_Dec() {
	   return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,ClaseLexica.FIN_DEC );	   
   }
   private UnidadLexica unidadLlaveAp() {
	   return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,ClaseLexica.LAP );	   
   }
   private UnidadLexica unidadLlaveCierre() {
	   return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,ClaseLexica.LCIERRE );	   
   }
   
   private UnidadLexica unidadIgualIgual() {
	   return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.IGUAL_IGUAL);
   }
   private UnidadLexica unidadExp() {
	     return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.EXP,lex.toString());     
   }

   public static void main(String arg[]) throws IOException {
     Reader input = new InputStreamReader(new FileInputStream("input.txt"));
     AnalizadorLexicoTiny0 al = new AnalizadorLexicoTiny0(input, new GestionErroresTiny0());
     UnidadLexica unidad;
     do {
       unidad = al.sigToken();
       System.out.println(unidad);
     }
     while (unidad.clase() != ClaseLexica.EOF);
    } 
}