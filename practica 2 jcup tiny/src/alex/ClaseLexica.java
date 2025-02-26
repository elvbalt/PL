package alex;

public enum ClaseLexica {
 IDEN, 
 ENT, 
 REAL, 
 PAP("("), 
 PCIERRE(")"), 
 IGUAL("="), 
 COMA(","), 
 MAS("+"), 
 MENOS("-"), 
 POR("*"), 
 DIV("/"), 
 EVALUA("<evalua>"), 
 DONDE("<donde>"),
 //nuevo Alvaro 
 STRING("<string>"),
 NULL("<null>"),
 PROC("<proc>"),
 TRUE("<true>"),
 FALSE("<false>"),
 IF("<if>"),
 ELSE("<else>"),
 WHILE("<while>"),
 STRUCT("<struct>"),
 NEW("<new>"),
 DELETE("<delete>"),
 READ("<read>"),
 WRITE("<write>"),
 NL("<nl>"),
 TYPE("<type>"),
 CALL("<call>"),
 MODULO("%"),
 REFERENCIA("&"),
 PUNTO("."),
 MENOR("<"),
 MAYOR(">"),
 MENORIGUAL("<="),
 MAYORIGUAL(">="),
 IGUALIGUAL("=="),
 DIFERENTE("!="),
 PUNTERO("^"),
 CAP("["),
 CCIERRE("]"),
 ARROBA("@"),
 PUNTOCOMA(";"),
 FINSECCIONDECLAR("&&"),
 LLAVEAP("{"),
 LLAVECIERRE("}"),
 CADENA,
 EXP,
 DOUBLE("<real>"),
 BOOL("<bool>"),
 INT("<int>"),
 OR("<or>"),
 NOT("<not>"),
 AND("<and>"),
 
 //aqui acaba lo nuevo
 EOF("EOF");
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
