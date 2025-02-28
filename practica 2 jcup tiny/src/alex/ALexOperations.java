package alex;

import asint.sym;

public class ALexOperations {
    
  private AnalizadorLexicoTiny alex;
  public ALexOperations(AnalizadorLexicoTiny alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadId() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.IDEN,alex.lexema()); 
  }  
  public UnidadLexica unidadEnt() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.ENT,alex.lexema()); 
  } 
  public UnidadLexica unidadReal() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.REAL,alex.lexema()); 
  } 
  public UnidadLexica unidadSuma() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.MAS,"+"); 
  } 
  public UnidadLexica unidadResta() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.MENOS,"-"); 
  } 
  public UnidadLexica unidadMul() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.POR,"*"); 
  } 
  public UnidadLexica unidadDiv() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.DIV,"/"); 
  } 
  public UnidadLexica unidadPAp() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.PAP,"("); 
  } 
  public UnidadLexica unidadPCierre() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.PCIERRE,")"); 
  } 
  public UnidadLexica unidadIgual() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.ASIGNACION,"="); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.COMA,","); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(), alex.columna(),sym.EOF,"<EOF>"); 
  }
  public UnidadLexica unidadModulo() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.MOD,"%"); 
  }
  public UnidadLexica unidadReferencia() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.AMPERSAND,"&");     
  }
  public UnidadLexica unidadPunto() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.PUNTO,".");     
  }
  public UnidadLexica unidadPuntocoma() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.PUNTOCOMA,";");     
  }
  public UnidadLexica unidadMenor() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.MENOR,"<");
  }
  public UnidadLexica unidadMayor() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.MAYOR,">");     
  }
  public UnidadLexica unidadArroba() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.EVALUA,"@");     
  }
  public UnidadLexica unidadCAp() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.CAP,"[");     
  }
  public UnidadLexica unidadCCierre() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.CCIERRE,"]");     
  }
  public UnidadLexica unidadPuntero() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.FARR,"^");     
  }
  public UnidadLexica unidadLlaveAp() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.LLAP,"{");     
  }
  public UnidadLexica unidadLlaveCierre() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.LLACIERRE,"}");
  }
  public UnidadLexica unidadDiferente() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.DISTINTO,"!=");     
  }
  public UnidadLexica unidadCadena() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.CADENA,alex.lexema());     
  }
  public UnidadLexica unidadFinSecDec() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.DOBLEAMPERSAND, "&&");     
  }
  public UnidadLexica unidadMenorIgual() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.MENORIGUAL, "<=");     	
  }
  public UnidadLexica unidadMayorIgual() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.MAYORIGUAL, ">=");     	
  }
  public UnidadLexica unidadIgualIgual() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.IGUAL, "==");     	
  }
  public UnidadLexica unidadIf() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.IF, "<if>");     	
  }
  public UnidadLexica unidadNl() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.NL, "<NL>");     	
  }
  public UnidadLexica unidadOr() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.OR, "<or>");     	
  }
  public UnidadLexica unidadAnd() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.AND, "<and>");     	
  }
  public UnidadLexica unidadExp() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.REAL, "<real>");     	
  }
  public UnidadLexica unidadInt() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.PR_INT, "<int>");     	
  }
  public UnidadLexica unidadBool() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.PR_BOOL, "<bool>");     	
}
  public UnidadLexica unidadNew() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.NEW, "<new>");     	
  }
  public UnidadLexica unidadNot() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.NOT, "<not>");     	
  }
  public UnidadLexica unidadCall() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.CALL, "<call>");     	
  }
  public UnidadLexica unidadElse() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.ELSE, "<else>");     	
  }
  public UnidadLexica unidadNull() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.NULL, "<null>");     	
  }
  public UnidadLexica unidadProc() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.PROC, "<proc>");     	
  }
  public UnidadLexica unidadRead() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.READ, "<read>");     	
  }
  public UnidadLexica unidadDouble() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.REAL, "<real>");     	
  }
  public UnidadLexica unidadTrue() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.TRUE, "<true>");     	
  }
  public UnidadLexica unidadType() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.TYPE, "<type>");     	
  }
  public UnidadLexica unidadFalse() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.FALSE, "<false>");     	
  }
  public UnidadLexica unidadWhile() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.WHILE, "<while>");     	
  }
  public UnidadLexica unidadWrite() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.WRITE, "<write>");     	
  }
  public UnidadLexica unidadDelete() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.DELETE, "<delete>");     	
  }
  public UnidadLexica unidadString() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.PR_STRING, "<string>");     	
  }
  public UnidadLexica unidadStruct() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.STRUCT, "<struct>");     	
}
}
