bison -d parser.y
flex lex.l
gcc -o parser1 ast.c lex.yy.c parser.tab.c
parser1.exe test1.c > text1.txt