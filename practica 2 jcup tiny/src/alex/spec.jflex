package alex;

import errors.GestionErroresTiny;

%%
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode
%public
%cup

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }

%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = ({digitoPositivo}{digito}*|0)
parteDecimal = ({digito}*{digitoPositivo}|0)
separador = [ \t\r\b\n]
comentario = #[^\n]* 
evalua = [eE][vV][aA][lL][uU][aA]
donde = [dD][oO][nN][dD][eE]
identificador = {letra}({letra}|{digito})*
numeroEntero = [\+\-]?{parteEntera}
numeroReal = [\+\-]?{parteEntera}\.{parteDecimal}
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
parentesisApertura = \(
parentesisCierre = \)
igual = \=
coma  = \,

string = [sS][tT][rR][iI][nN][gG]
null = [nN][uU][lL][lL]
proc = [pP][rR][oO][cC]
true = [tT][rR][uU][eE]
false = [fF][aA][lL][sS][eE]
if = [iI][fF]
else = [eE][lL][sS][eE]
while = [wW][hH][iI][lL][eE]
struct = [sS][tT][rR][uU][cC][tT]
new = [nN][eE][wW]
delete = [dD][eE][lL][eE][tT][eE]
read = [rR][eE][aA][dD]
write = [wW][rR][iI][tT][eE]
nl = [nN][lL]
type = [tT][yY][pP][eE]
call = [cC][aA][lL][lL]
and = [aA][nN][dD]
not = [nN][oO][tT]
or = [oO][rR]
int = [iI][nN][tT]
real = [rR][eE][aA][lL]
bool = [bB][oO][oO][lL]
modulo = \%
referencia = \&
punto = \.
menor = \<
mayor = \>
menorIgual = \<=
mayorIgual = \>=
igualIgual = \==
diferente = \!=
puntero = \^
corcheteApertura = \[
corcheteCierre = \]
arroba = \@
puntocoma = \;
finSeccionDeclar = \&&
llaveAp = \{
llaveCierre = \}
literalCadena = \"([^\n])*\"
literalExponencial = ({numeroReal}|{numeroEntero})[eE]{numeroEntero}

%%
{string}			      {return ops.unidadString();}
{null} 					  {return ops.unidadNull();}
{proc} 					  {return ops.unidadProc();}
{true} 					  {return ops.unidadTrue();}
{false} 				  {return ops.unidadFalse();}
{if} 					  {return ops.unidadIf();}
{else} 					  {return ops.unidadElse();}
{while} 				  {return ops.unidadWhile();}
{struct} 				  {return ops.unidadStruct();}
{new} 					  {return ops.unidadNew();}
{delete} 				  {return ops.unidadDelete();}
{read} 					  {return ops.unidadRead();}
{write} 				  {return ops.unidadWrite();}
{nl} 					  {return ops.unidadNl();}
{type} 					  {return ops.unidadType();}
{call} 					  {return ops.unidadCall();}
{bool} 					  {return ops.unidadBool();}
{int} 					  {return ops.unidadInt();}
{modulo}				  {return ops.unidadModulo();}
{referencia}              {return ops.unidadReferencia();}
{punto}   				  {return ops.unidadPunto();}
{menor}    				  {return ops.unidadMenor();}
{mayor}					  {return ops.unidadMayor();}
{menorIgual}			  {return ops.unidadMenorIgual();}
{mayorIgual}			  {return ops.unidadMayorIgual();}
{igualIgual}			  {return ops.unidadIgualIgual();}
{diferente}			  	  {return ops.unidadDiferente();}
{puntero}				  {return ops.unidadPuntero();}
{corcheteApertura}		  {return ops.unidadCAp();}
{corcheteCierre}		  {return ops.unidadCCierre();}
{arroba} 				  {return ops.unidadArroba();}
{puntocoma} 			  {return ops.unidadPuntocoma();}
{finSeccionDeclar} 		  {return ops.unidadFinSecDec();}
{llaveAp} 				  {return ops.unidadLlaveAp();}
{llaveCierre} 			  {return ops.unidadLlaveCierre();}
{literalCadena}			  {return ops.unidadCadena();}
{and}			  		  {return ops.unidadAnd();}
{or}			  		  {return ops.unidadOr();}
{not}			  		  {return ops.unidadNot();}


{separador}               {}
{comentario}              {}
{identificador}           {return ops.unidadId();}
{numeroEntero}            {return ops.unidadEnt();}
{numeroReal}              {return ops.unidadReal();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{igual}                   {return ops.unidadIgual();} 
{coma}                    {return ops.unidadComa();}
[^]                       {errores.errorLexico(fila(),columna(),lexema());}  