package cup.alex;

import cup.asint.ClaseLexica;
import java.io.IOException;
import java.util.Hashtable;


public class ALexOperations {
	Hashtable<String,Integer> pal_reserv;
	
	private AnalizadorLexicoTiny lex;
	
	public ALexOperations(AnalizadorLexicoTiny alex) {
	    this.lex = alex;   
		rellenar_tabla();
	}
	    public void rellenar_tabla(){
			pal_reserv = new Hashtable<String,Integer>();
			pal_reserv.put("int", ClaseLexica.PR_ENT);
			pal_reserv.put("real", ClaseLexica.PR_REAL);
			pal_reserv.put("bool", ClaseLexica.PR_BOOL);
			pal_reserv.put("true", ClaseLexica.TRUE);
			pal_reserv.put("false", ClaseLexica.FALSE);
			pal_reserv.put("and", ClaseLexica.AND);
			pal_reserv.put("or", ClaseLexica.OR);
			pal_reserv.put("not", ClaseLexica.NOT);
			
			pal_reserv.put("string", ClaseLexica.PR_STRING);
			pal_reserv.put("null", ClaseLexica.NULL);
			pal_reserv.put("proc", ClaseLexica.PROC);
			pal_reserv.put("if", ClaseLexica.IF);
			pal_reserv.put("else", ClaseLexica.ELSE);
			pal_reserv.put("while", ClaseLexica.WHILE);
			pal_reserv.put("struct", ClaseLexica.STRUCT);
			pal_reserv.put("new", ClaseLexica.NEW);
			pal_reserv.put("delete", ClaseLexica.DELETE);
			pal_reserv.put("read", ClaseLexica.READ);
			pal_reserv.put("write", ClaseLexica.WRITE);
			pal_reserv.put("nl", ClaseLexica.NL);
			pal_reserv.put("type", ClaseLexica.TYPE);
			pal_reserv.put("call", ClaseLexica.CALL);
		}
  	public UnidadLexica unidadId() {
		if(pal_reserv.containsKey(lex.lexema().toLowerCase())){
		return new UnidadLexica(lex.fila(),lex.columna(),pal_reserv.get(lex.lexema().toLowerCase()),"<"+lex.lexema().toLowerCase()+">");
		}
		else{
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.IDEN,lex.lexema()); 
		}
	}  
	public UnidadLexica unidadEnt() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.ENT,lex.lexema());     
	}    
	public UnidadLexica unidadReal() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.REAL,lex.lexema());     
	}
	public UnidadLexica unidadCadena() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.CADENA,lex.lexema());     
	}     
	public UnidadLexica unidadMas() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.MAS,"+");     
	}    
	public UnidadLexica unidadMenos() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.MENOS,"-");     
	}    
	public UnidadLexica unidadPor() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.POR,"*");     
	}    
	public UnidadLexica unidadDiv() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.DIV,"/");     
	}    
	public UnidadLexica unidadPAp() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.PAP,"(");     
	}    
	public UnidadLexica unidadPCierre() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.PCIERRE,")");     
	}
	public UnidadLexica unidadCAp() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.CAP,"[");     
	}    
	public UnidadLexica unidadCCierre() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.CCIERRE,"]");     
	}
	public UnidadLexica unidadLAp() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.LAP,"{");     
	}    
	public UnidadLexica unidadLCierre() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.LCIERRE,"}");     
	}
	public UnidadLexica unidadAsig() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.ASIG,"=");     
	}   
	public UnidadLexica unidadIgual() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.IGUAL,"==");     
	}  
	public UnidadLexica unidadMayor() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.MAYOR,">");     
	} 
	public UnidadLexica unidadMayorIgual() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.MAYORIG,">=");     
	} 
	public UnidadLexica unidadMenor() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.MENOR,"<");     
	} 
	public UnidadLexica unidadMenorIgual() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.MENORIG,"<=");     
	}
	public UnidadLexica unidadDistinto() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.DISTINTO,"!=");     
	} 
	public UnidadLexica unidadComa() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.COMA,",");     
	}
	public UnidadLexica unidadPunto() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.PUNTO,".");     
	}
	public UnidadLexica unidadPuntoComa() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.PUNCOMA,";");     
	}  
	public UnidadLexica unidadPotencia() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.POTENCIA,"^");     
	}  
	public UnidadLexica unidadMod() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.MOD,"%");     
	}  
	public UnidadLexica unidadAmpersand() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.AMPERSAND,"&");     
	}       
	public UnidadLexica unidadCierreDeclaracion() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.CIERREDECLARACION,"&&");     
	}  
	public UnidadLexica unidadEvalua() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.EVALUA,"@");     
	}  
	public UnidadLexica unidadEof() {
		return new UnidadLexica(lex.fila(),lex.columna(),ClaseLexica.EOF,"<EOF>");     
	}

}
