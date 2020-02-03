bison -d parser.y
flex lex.l
gcc -o parser2 ast.c def.c lex.yy.c parser.tab.c
parser2.exe test2.c > text2.txt