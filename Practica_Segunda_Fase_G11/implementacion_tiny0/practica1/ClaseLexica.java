package practica1;
// todas las clases lexica creadas para este lenguaje tiny, con el nombre y su simbolo
public enum ClaseLexica {
	 IDEN,PR_ENT("<int>"), PR_BOOL("<bool>"), PR_REAL("<real>"), ENT, REAL, BOOL, CIERREDECLARACION("&&"), EVALUA ("@"), //id, int, real, bool, &&, @
	 PAP("("), PCIERRE(")"), LAP("{"), LCIERRE("}"), //parentesis, llaves
	 IGUAL("=="), ASIG("="), MAS("+"), MENOS("-"), POR("*"), DIV("/"), PUNCOMA(";"), //operaciones
	 MAYOR(">"), MAYORIG(">="), MENOR("<"), MENORIG("<="), DISTINTO("!="), 
	 TRUE("<true>"), FALSE("<false>"), AND("<and>"), OR("<or>"), NOT("<not>"), EOF("<EOF>"); 

private String simbolo;
public String getSimbolo() {
     return simbolo;
 }
 private ClaseLexica() {
	 simbolo = toString();
 }
 private ClaseLexica(String simbolo) {
    this.simbolo = simbolo;  
 }
}