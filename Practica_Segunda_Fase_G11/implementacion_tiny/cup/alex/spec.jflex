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



numeroEntero = [\+\-]?{parteEntera}
numeroReal = {numeroEntero} (\.{parteDecimal} | (e|E){numeroEntero} | \.{parteDecimal}(e|E){numeroEntero})
cadena = (\"([^\"\r\b\n])*\")
letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = ({digitoPositivo}{digito}*|0)
parteDecimal = ({digito}*{digitoPositivo}|0)
separador = [ \t\r\b\n]
comentario = ##[^\n]* 
int = (i|I)(n|N)(t|T)
real = (r|R)(e|E)(a|A)(l|L)
bool = (b|B)(o|O)(o|O)(l|L)
true = (t|T)(r|R)(u|U)(e|E)
string = (s|S)(t|T)(r|R)(i|I)(n|N)(g|G)
false = (f|F)(a|A)(l|L)(s|S)(e|E)
and = (a|A)(n|N)(d|D)
or = (o|O)(r|R)
not = (n|N)(o|O)(t|T)
null = (n|N)(u|U)(l|L)(l|L)
proc =(p|P)(r|R)(o|O)(c|C) 
if = (i|I)(f|F)
else = (e|E)(l|L)(s|S)(e|E)
while = (w|W)(h|H)(i|I)(l|L)(e|E) 
struct = (s|S)(t|T)(r|R)(u|U)(c|C)(t|T)
new = (n|N)(e|E)(w|W)
delete = (d|D)(e|E)(l|L)(e|E)(t|T)(e|E)
read = (r|R)(e|E)(a|A)(d|D)
write = (w|W)(r|R)(i|I)(t|T)(e|E) 
nl = (n|N)(l|L)
type = (t|T)(y|Y)(p|P)(e|E)
call = (c|C)(a|A)(l|L)(l|L)
identificador = ({letra}|\_)({letra}|{digito} |\_)* 
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
operadorModulo = \%
operadorPotencia = \^
operadorAsignacion = \=
operadorIgual = \==
operadorDistinto = \!=
operadorMayor = \>
operadorMenor = \<
operadorMayorIgual = \>=
operadorMenorIgual = \<=
parentesisApertura = \(				
parentesisCierre = \)
corcheteApertura = \[
corcheteCierre = \]
llaveApertura = \{
llaveCierre = \}
coma  = \,
punto = \.
puntoComa = \;
ampersand = \&
cierreDeclaracion = \&&
evalua = \@

%%
{separador}               {}
{comentario}              {}
{identificador}           {return ops.unidadId();}
{numeroEntero}            {return ops.unidadEnt();}
{numeroReal}              {return ops.unidadReal();}
{cadena}                  {return ops.unidadCadena();}
{operadorSuma}            {return ops.unidadMas();}
{operadorResta}           {return ops.unidadMenos();}
{operadorMultiplicacion}  {return ops.unidadPor();}
{operadorDivision}        {return ops.unidadDiv();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{corcheteApertura}        {return ops.unidadCAp();}
{corcheteCierre}          {return ops.unidadCCierre();}
{llaveApertura}        	  {return ops.unidadLAp();}
{llaveCierre}        	    {return ops.unidadLCierre();} 
{operadorIgual}           {return ops.unidadIgual();} 
{operadorAsignacion}      {return ops.unidadAsig();}
{operadorMayor}           {return ops.unidadMayor();}
{operadorMayorIgual}      {return ops.unidadMayorIgual();}
{operadorMenor}           {return ops.unidadMenor();}
{operadorMenorIgual}      {return ops.unidadMenorIgual();}
{operadorDistinto}        {return ops.unidadDistinto();}
{puntoComa}				        {return ops.unidadPuntoComa();}
{operadorPotencia}        {return ops.unidadPotencia();}
{operadorModulo}	        {return ops.unidadMod();}
{ampersand}			          {return ops.unidadAmpersand();}
{cierreDeclaracion}	      {return ops.unidadCierreDeclaracion();}
{evalua}				          {return ops.unidadEvalua();}
{coma}                    {return ops.unidadComa();}
{punto}                   {return ops.unidadPunto();}
[^]                       {errores.errorLexico(fila(),columna(),lexema());}  