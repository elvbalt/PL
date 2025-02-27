package alex;

public enum ClaseLexica {
 IDEN, 
 ENT, 
 DOUBLE, 
 EXP,
 PAP("("), 
 PCIERRE(")"), 
 IGUAL("="), 
 COMA(","), 
 MAS("+"), 
 MENOS("-"), 
 POR("*"), 
 DIV("/"), 
 EOF("EOF"),
 PUNTOCOMA(";"),
 MENOR("<"),
 MAYOR(">"),
 MENOR_IGUAL("<="),
 MAYOR_IGUAL(">="),
 DIF("!="),
 ARROBA("@"),
 FIN_DEC("&&"),
 LAP("{"),
 LCIERRE("}"),
 IGUAL_IGUAL("=="),
 BOOL("<bool>"),
 INT("<int>"),
 REAL("<real>"),
 OR("<or>"),
 NOT("<not>"),
 AND("<and>"),
 TRUE("<true>"),
 FALSE("<false>"), 
 ;
	
private String image;
public String getImage() {
     return image;
 }
 private ClaseLexica() {
     image = toString();
 }
 private ClaseLexica(String image) {
    this.image = image;  
 }

}
