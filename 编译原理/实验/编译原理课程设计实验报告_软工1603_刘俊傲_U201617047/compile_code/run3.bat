bison -d parser.y
flex lex.l
gcc -o parser3 ast.c def.c lex.yy.c parser.tab.c
parser3.exe test3.c > text3.txt