bison -d parser.y
flex lex.l
gcc -o parser4 ast.c def.c lex.yy.c parser.tab.c -g
parser4.exe test4.c