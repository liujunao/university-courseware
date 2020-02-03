#include "def.h"
int errornum = 0;
char *strcat0(char *s1, char *s2)
{
    static char result[10];
    strcpy(result, s1);
    strcat(result, s2);
    return result;
}

char *newAlias()
{
    static int no = 1;
    char s[10];
    itoa(no++, s, 10);
    return strcat0("v", s);
}

char *newLabel()
{
    static int no = 1;
    char s[10];
    itoa(no++, s, 10);
    return strcat0("label", s);
}

char *newTemp()
{
    static int no = 1;
    char s[10];
    itoa(no++, s, 10);
    return strcat0("temp", s);
}

//生成一条TAC代码的结点组成的双向循环链表，返回头指针
struct codenode *genIR(int op, struct opn opn1, struct opn opn2, struct opn result)
{
    struct codenode *h = (struct codenode *)malloc(sizeof(struct codenode));
    h->op = op;
    h->opn1 = opn1;
    h->opn2 = opn2;
    h->result = result;
    h->next = h->prior = h;
    return h;
}

//生成一条标号语句，返回头指针
struct codenode *genLabel(char *label)
{
    struct codenode *h = (struct codenode *)malloc(sizeof(struct codenode));
    h->op = LABEL;
    strcpy(h->result.id, label);
    h->next = h->prior = h;
    return h;
}

//生成GOTO语句，返回头指针
struct codenode *genGoto(char *label)
{
    struct codenode *h = (struct codenode *)malloc(sizeof(struct codenode));
    h->op = GOTO;
    strcpy(h->result.id, label);
    h->next = h->prior = h;
    return h;
}

//合并多个中间代码的双向循环链表，首尾相连
struct codenode *merge(int num, ...)
{
    struct codenode *h1, *h2, *p, *t1, *t2;
    va_list ap;
    va_start(ap, num);
    h1 = va_arg(ap, struct codenode *);
    while (--num > 0)
    {
        h2 = va_arg(ap, struct codenode *);
        if (h1 == NULL)
            h1 = h2;
        else if (h2)
        {
            t1 = h1->prior;
            t2 = h2->prior;
            t1->next = h2;
            t2->next = h1;
            h1->prior = t2;
            h2->prior = t1;
        }
    }
    va_end(ap);
    return h1;
}

//输出中间代码
void prnIR(struct codenode *head)
{
    char opnstr1[32], opnstr2[32], resultstr[32];
    struct codenode *h = head;
    do
    {
        if (h->opn1.kind == INT)
            sprintf(opnstr1, "#%d", h->opn1.const_int);
        if (h->opn1.kind == FLOAT)
            sprintf(opnstr1, "#%f", h->opn1.const_float);
        if (h->opn1.kind == CHAR)
            sprintf(opnstr1, "#%c", h->opn1.const_char);
        if (h->opn1.kind == ID)
            sprintf(opnstr1, "%s", h->opn1.id);

        if (h->opn2.kind == INT)
            sprintf(opnstr2, "#%d", h->opn2.const_int);
        if (h->opn2.kind == FLOAT)
            sprintf(opnstr2, "#%f", h->opn2.const_float);
        if (h->opn2.kind == CHAR)
            sprintf(opnstr2, "#%c", h->opn2.const_char);
        if (h->opn2.kind == ID)
            sprintf(opnstr2, "%s", h->opn2.id);

        sprintf(resultstr, "%s", h->result.id);
        switch (h->op)
        {
        case ASSIGNOP:
            printf("  %s := %s\n", resultstr, opnstr1);
            break;
        case Array_CALL:
        case RELOP:
        case OR:
        case NOT:
        case PLUS:
        case MINUS:
        case STAR:
        case DIV:
            printf("  %s := %s %c %s\n", resultstr, opnstr1, h->op == PLUS ? '+' : h->op == MINUS ? '-' : h->op == STAR ? '*' : '\\', opnstr2);
            break;
        case INC_OPP:
        case DEC_OPP:
            printf("  %s := %s %c %s\n", resultstr, opnstr1,
                   h->op == INC_OP ? '+' : '-', opnstr2);
            printf("  %s := %s\n", opnstr1, resultstr);
            break;
        case INC_OP:
        case DEC_OP:
            printf("  %s := %s\n", resultstr, opnstr1);
            printf("  %s := %s %c %s\n", opnstr1, opnstr1, h->op == INC_OPP ? '+' : '-', opnstr2);
            break;
        case INC:
        case DEC:
            printf("  %s := %s %c %s\n", resultstr, opnstr1, h->op == INC ? '+' : '-', opnstr2);
            printf("  %s := %s\n", opnstr1, resultstr);
            break;
        case UMINUS:
        case FUNC_CALL:
        case ARGS:
        case FUNCTION:
            printf("\nFUNCTION %s :\n", h->result.id);
            break;
        case PARAM:
            printf("  PARAM %s\n", h->result.id);
            break;
        case LABEL:
            printf("LABEL %s :\n", h->result.id);
            break;
        case GOTO:
            printf("  GOTO %s\n", h->result.id);
            break;
        case JLE:
            printf("  IF %s <= %s GOTO %s\n", opnstr1, opnstr2, resultstr);
            break;
        case JLT:
            printf("  IF %s < %s GOTO %s\n", opnstr1, opnstr2, resultstr);
            break;
        case JGE:
            printf("  IF %s >= %s GOTO %s\n", opnstr1, opnstr2, resultstr);
            break;
        case JGT:
            printf("  IF %s > %s GOTO %s\n", opnstr1, opnstr2, resultstr);
            break;
        case EQ:
            printf("  IF %s == %s GOTO %s\n", opnstr1, opnstr2, resultstr);
            break;
        case NEQ:
            printf("  IF %s != %s GOTO %s\n", opnstr1, opnstr2, resultstr);
            break;
        case ARG:
            printf("  ARG %s\n", h->result.id);
            break;
        case CALL:
            printf("  %s := CALL %s\n", resultstr, opnstr1);
            break;

        case RETURN:
            if (h->result.kind)
                printf("  RETURN %s\n", resultstr);
            else
                printf("  RETURN\n");
            break;
        }
        h = h->next;
    } while (h != head);
}

void semantic_error(int line, char *msg1, char *msg2)
{
    errornum = 1; //这里可以只收集错误信息，最后在一次显示
    printf("在%d行,%s %s\n", line, msg1, msg2);
}

//显示符号表
void prn_symbol()
{
    int i = 0;
    printf("%6s %6s %6s  %6s %4s %6s\n", "变量名", "别 名", "层 号", "类  型", "标记", "偏移量");
    for (i = 0; i < symbolTable.index; i++)
        printf("%6s %6s %6d  %6s %4c %6d\n", symbolTable.symbols[i].name,
               symbolTable.symbols[i].alias, symbolTable.symbols[i].level,
               symbolTable.symbols[i].type == INT ? "int" : (symbolTable.symbols[i].type == FLOAT ? "float" : "char"),
               symbolTable.symbols[i].flag, symbolTable.symbols[i].offset);
}

//查找符号表
int searchSymbolTable(char *name)
{
    int i;
    for (i = symbolTable.index - 1; i >= 0; i--)
        if (!strcmp(symbolTable.symbols[i].name, name))
            return i;
    return -1;
}

//填充符号表
int fillSymbolTable(char *name, char *alias, int level, int type, char flag, int offset)
{
    //首先根据name查符号表，不能重复定义 重复定义返回-1
    int i;
    /*符号查重，考虑外部变量声明前有函数定义，
    其形参名还在符号表中，这时的外部变量与前函数的形参重名是允许的*/
    for (i = symbolTable.index - 1; symbolTable.symbols[i].level == level || (level == 0 && i >= 0); i--)
    {
        if (level == 0 && symbolTable.symbols[i].level == 1)
            continue; //外部变量和形参不必比较重名
        if (!strcmp(symbolTable.symbols[i].name, name))
            return -1;
    }
    //填写符号表内容
    strcpy(symbolTable.symbols[symbolTable.index].name, name);
    strcpy(symbolTable.symbols[symbolTable.index].alias, alias);
    symbolTable.symbols[symbolTable.index].level = level;
    symbolTable.symbols[symbolTable.index].type = type;
    symbolTable.symbols[symbolTable.index].flag = flag;
    symbolTable.symbols[symbolTable.index].offset = offset;
    return symbolTable.index++; //返回的是符号在符号表中的位置序号，中间代码生成时可用序号取到符号别名
}

//填写临时变量到符号表，返回临时变量在符号表中的位置
int fill_Temp(char *name, int level, int type, char flag, int offset)
{
    strcpy(symbolTable.symbols[symbolTable.index].name, "");
    strcpy(symbolTable.symbols[symbolTable.index].alias, name);
    symbolTable.symbols[symbolTable.index].level = level;
    symbolTable.symbols[symbolTable.index].type = type;
    symbolTable.symbols[symbolTable.index].flag = flag;
    symbolTable.symbols[symbolTable.index].offset = offset;
    return symbolTable.index++; //返回的是临时变量在符号表中的位置序号
}

int LEV = 0;   //层号
int func_size; //1个函数的活动记录大小

//处理变量列表
void ext_var_list(struct node *T)
{
    int rtn, num = 1;
    switch (T->kind)
    {
    case EXT_DEC_LIST:
        T->ptr[0]->type = T->type;                //将类型属性向下传递变量结点
        T->ptr[0]->offset = T->offset;            //外部变量的偏移量向下传递
        T->ptr[1]->type = T->type;                //将类型属性向下传递变量结点
        T->ptr[1]->offset = T->offset + T->width; //外部变量的偏移量向下传递
        T->ptr[1]->width = T->width;
        ext_var_list(T->ptr[0]);
        ext_var_list(T->ptr[1]);
        T->num = T->ptr[1]->num + 1;
        break;
    case Array_LIST:
        T->ptr[0]->type = T->type;                //将类型属性向下传递变量结点
        T->ptr[0]->offset = T->offset;            //外部变量的偏移量向下传递
        T->ptr[1]->type = T->type;                //将类型属性向下传递变量结点
        T->ptr[1]->offset = T->offset + T->width; //外部变量的偏移量向下传递
        if (T->ptr[1]->ptr[1])
        {
            T->ptr[1]->width = (T->type == INT ? 4 : (T->type == FLOAT ? 8 : 2)) * (T->ptr[1]->ptr[0]->ptr[0]->type_int);
        } //将一个变量的宽度向下传递
        else
        {
            T->ptr[1]->width = (T->type == INT ? 4 : (T->type == FLOAT ? 8 : 2)) * (T->ptr[1]->ptr[0]->type_int);
        }
        ext_var_list(T->ptr[0]);
        ext_var_list(T->ptr[1]);
        T->num = T->ptr[1]->num + T->ptr[0]->ptr[0]->type_int;
        break;
    case ARRAYDEC:
        rtn = fillSymbolTable(T->type_id, newAlias(), LEV, T->type, 'A', T->offset); //最后一个变量名
        if (rtn == -1)
            semantic_error(T->pos, T->type_id, "变量重复定义");
        else
            T->place = rtn;
        T->num = T->ptr[0]->type_int;
        symbolTable.symbols[rtn].arraynum = T->num;
        break;
    case ID:
        rtn = fillSymbolTable(T->type_id, newAlias(), LEV, T->type, 'V', T->offset); //最后一个变量名
        if (rtn == -1)
            semantic_error(T->pos, T->type_id, "变量重复定义");
        else
            T->place = rtn;
        T->num = 1;
        break;
    }
}

//检测参数列表
int match_param(int i, struct node *T)
{
    int j, num = symbolTable.symbols[i].paramnum;
    int type1, type2;
    if (num == 0 && T == NULL)
        return 1;
    for (j = 1; j <= num; j++)
    {
        if (!T)
        {
            semantic_error(T->pos, "", "函数调用参数太少");
            return 0;
        }
        type1 = symbolTable.symbols[i + j].type; //形参类型
        type2 = T->ptr[0]->type;
        if (type1 != type2)
        {
            semantic_error(T->pos, "", "参数类型不匹配");
            return 0;
        }
        T = T->ptr[1];
    }
    if (T->ptr[1])
    { //num个参数已经匹配完，还有实参表达式
        semantic_error(T->pos, "", "函数调用参数太多");
        return 0;
    }
    return 1;
}

//布尔表达式，参考文献[2]p84的思想
void boolExp(struct node *T)
{
    struct opn opn1, opn2, result;
    int op;
    int rtn;
    if (T)
    {
        switch (T->kind)
        {
        case INT:
            if (T->type_int != 0)
                T->code = genGoto(T->Etrue);
            else
                T->code = genGoto(T->Efalse);
            T->width = 0;
            break;
        case FLOAT:
            if (T->type_float != 0.0)
                T->code = genGoto(T->Etrue);
            else
                T->code = genGoto(T->Efalse);
            T->width = 0;
            break;
        case CHAR:
            if (T->type_char != 0)
                T->code = genGoto(T->Etrue);
            else
                T->code = genGoto(T->Efalse);
            T->width = 0;
            break;
        case ID: //查符号表，获得符号表中的位置，类型送type
            rtn = searchSymbolTable(T->type_id);
            if (rtn == -1)
                semantic_error(T->pos, T->type_id, "变量未定义");
            if (symbolTable.symbols[rtn].flag == 'F')
                semantic_error(T->pos, T->type_id, "是函数名，类型不匹配");
            else
            {
                opn1.kind = ID;
                strcpy(opn1.id, symbolTable.symbols[rtn].alias);
                opn1.offset = symbolTable.symbols[rtn].offset;
                opn2.kind = INT;
                opn2.const_int = 0;
                result.kind = ID;
                strcpy(result.id, T->Etrue);
                T->code = genIR(NEQ, opn1, opn2, result);
                T->code = merge(2, T->code, genGoto(T->Efalse));
            }
            T->width = 0;
            break;
        case Array_CALL:
            rtn = searchSymbolTable(T->type_id);
            if (rtn == -1)
            {
                semantic_error(T->pos, T->type_id, "数组未定义");
                break;
            }
            if (symbolTable.symbols[rtn].flag != 'A')
            {
                semantic_error(T->pos, T->type_id, "不是一个数组");
                break;
            }
            if (T->ptr[0]->type_int >= symbolTable.symbols[rtn].arraynum)
            {
                semantic_error(T->pos, T->type_id, "超出定义数组的长度");
                break;
            }
            else
            {
                opn1.kind = ID;
                sprintf(opn1.id, "%s[%d]", symbolTable.symbols[rtn].alias, T->ptr[0]->type_int);
                opn1.offset = symbolTable.symbols[rtn].offset;
                opn2.kind = INT;
                opn2.const_int = 0;
                result.kind = ID;
                strcpy(result.id, T->Etrue);
                T->code = genIR(NEQ, opn1, opn2, result);
                T->code = merge(2, T->code, genGoto(T->Efalse));
            }
            break;
        case RELOP: //处理关系运算表达式,2个操作数都按基本表达式处理
            T->ptr[0]->offset = T->ptr[1]->offset = T->offset;
            Exp(T->ptr[0]);
            T->width = T->ptr[0]->width;
            Exp(T->ptr[1]);
            if (T->width < T->ptr[1]->width)
                T->width = T->ptr[1]->width;
            if (symbolTable.symbols[T->ptr[0]->place].flag == 'A')
            {
                opn1.kind = ID;
                sprintf(opn1.id, "%s[%d]", symbolTable.symbols[T->ptr[1]->place].alias, T->ptr[0]->ptr[0]->type_int);
            }
            else
            {
                opn1.kind = ID;
                strcpy(opn1.id, symbolTable.symbols[T->ptr[0]->place].alias);
            }
            opn1.type = T->ptr[0]->type;
            opn1.offset = symbolTable.symbols[T->ptr[0]->place].offset;
            if (symbolTable.symbols[T->ptr[0]->place].flag == 'A')
            {
                opn2.kind = ID;
                sprintf(opn2.id, "%s[%d]", symbolTable.symbols[T->ptr[1]->place].alias, T->ptr[1]->ptr[0]->type_int);
            }
            else
            {
                opn2.kind = ID;
                strcpy(opn2.id, symbolTable.symbols[T->ptr[1]->place].alias);
            }
            opn2.offset = symbolTable.symbols[T->ptr[1]->place].offset;
            result.kind = ID;
            strcpy(result.id, T->Etrue);
            if (strcmp(T->type_id, "<") == 0)
                op = JLT;
            else if (strcmp(T->type_id, "<=") == 0)
                op = JLE;
            else if (strcmp(T->type_id, ">") == 0)
                op = JGT;
            else if (strcmp(T->type_id, ">=") == 0)
                op = JGE;
            else if (strcmp(T->type_id, "==") == 0)
                op = EQ;
            else if (strcmp(T->type_id, "!=") == 0)
                op = NEQ;
            T->code = genIR(op, opn1, opn2, result);
            T->code = merge(4, T->ptr[0]->code, T->ptr[1]->code, T->code, genGoto(T->Efalse));
            break;
        case AND:
        case OR:
            if (T->kind == AND)
            {
                strcpy(T->ptr[0]->Etrue, newLabel());
                strcpy(T->ptr[0]->Efalse, T->Efalse);
            }
            else
            {
                strcpy(T->ptr[0]->Etrue, T->Etrue);
                strcpy(T->ptr[0]->Efalse, newLabel());
            }
            strcpy(T->ptr[1]->Etrue, T->Etrue);
            strcpy(T->ptr[1]->Efalse, T->Efalse);
            T->ptr[0]->offset = T->ptr[1]->offset = T->offset;
            boolExp(T->ptr[0]);
            T->width = T->ptr[0]->width;
            boolExp(T->ptr[1]);
            if (T->width < T->ptr[1]->width)
                T->width = T->ptr[1]->width;
            if (T->kind == AND)
                T->code = merge(3, T->ptr[0]->code, genLabel(T->ptr[0]->Etrue), T->ptr[1]->code);
            else
                T->code = merge(3, T->ptr[0]->code, genLabel(T->ptr[0]->Efalse), T->ptr[1]->code);
            break;
        case NOT:
            strcpy(T->ptr[0]->Etrue, T->Efalse);
            strcpy(T->ptr[0]->Efalse, T->Etrue);
            boolExp(T->ptr[0]);
            T->code = T->ptr[0]->code;
            break;
        }
    }
}

//处理基本表达式，参考文献[2]p82的思想
void Exp(struct node *T)
{
    int rtn, num, width;
    struct node *T0;
    struct opn opn1, opn2, result;
    if (T)
    {
        switch (T->kind)
        {
        case ID: //查符号表，获得符号表中的位置，类型送type
            rtn = searchSymbolTable(T->type_id);
            if (rtn == -1)
                semantic_error(T->pos, T->type_id, "变量未定义");
            if (symbolTable.symbols[rtn].flag == 'F')
                semantic_error(T->pos, T->type_id, "是函数名，类型不匹配");
            if (symbolTable.symbols[rtn].flag == 'A')
                semantic_error(T->pos, T->type_id, "是数组名，类型不匹配");
            else
            {
                T->place = rtn; //结点保存变量在符号表中的位置
                T->code = NULL; //标识符不需要生成TAC
                T->type = symbolTable.symbols[rtn].type;
                T->offset = symbolTable.symbols[rtn].offset;
                T->width = 0; //未再使用新单元
            }
            break;
        case INT:
            T->place = fill_Temp(newTemp(), LEV, T->type, 'T', T->offset); //为整常量生成一个临时变量
            T->type = INT;
            opn1.kind = INT;
            opn1.const_int = T->type_int;
            result.kind = ID;
            strcpy(result.id, symbolTable.symbols[T->place].alias);
            result.offset = symbolTable.symbols[T->place].offset;
            T->code = genIR(ASSIGNOP, opn1, opn2, result);
            T->width = 4;
            break;
        case FLOAT:
            T->place = fill_Temp(newTemp(), LEV, T->type, 'T', T->offset); //为浮点常量生成一个临时变量
            T->type = FLOAT;
            opn1.kind = FLOAT;
            opn1.const_float = T->type_float;
            result.kind = ID;
            strcpy(result.id, symbolTable.symbols[T->place].alias);
            result.offset = symbolTable.symbols[T->place].offset;
            T->code = genIR(ASSIGNOP, opn1, opn2, result);
            T->width = 4;
            break;
        case CHAR:
            T->place = fill_Temp(newTemp(), LEV, T->type, 'T', T->offset); //为浮点常量生成一个临时变量
            T->type = CHAR;
            opn1.kind = CHAR;
            opn1.const_char = T->type_char;
            result.kind = ID;
            strcpy(result.id, symbolTable.symbols[T->place].alias);
            result.offset = symbolTable.symbols[T->place].offset;
            T->code = genIR(ASSIGNOP, opn1, opn2, result);
            T->width = 2;
            break;
        case ASSIGNOP:
            if (T->ptr[0]->kind != ID && T->ptr[0]->kind != Array_CALL)
            {
                semantic_error(T->pos, "", "赋值语句需要左值");
                break;
            }
            else
            {
                Exp(T->ptr[0]); //处理左值，例中仅为变量
                T->ptr[1]->offset = T->offset;
                Exp(T->ptr[1]);
                if (T->ptr[0]->type != T->ptr[1]->type)
                {
                    semantic_error(T->pos, "", "赋值语句不匹配");
                }
                T->type = T->ptr[0]->type;
                T->width = T->ptr[1]->width;
                T->code = merge(2, T->ptr[0]->code, T->ptr[1]->code);
                if (symbolTable.symbols[T->ptr[1]->place].flag == 'A')
                {
                    opn1.kind = ID;
                    sprintf(opn1.id, "%s[%d]", symbolTable.symbols[T->ptr[1]->place].alias, T->ptr[1]->ptr[0]->type_int);
                }
                else
                {
                    opn1.kind = ID;
                    strcpy(opn1.id, symbolTable.symbols[T->ptr[1]->place].alias);
                } //右值一定是个变量或临时变量
                opn1.offset = symbolTable.symbols[T->ptr[1]->place].offset;
                if (symbolTable.symbols[T->ptr[0]->place].flag == 'A')
                {
                    result.kind = ID;
                    sprintf(result.id, "%s[%d]", symbolTable.symbols[T->ptr[0]->place].alias, T->ptr[0]->ptr[0]->type_int);
                }
                else
                {
                    result.kind = ID;
                    strcpy(result.id, symbolTable.symbols[T->ptr[0]->place].alias);
                }
                result.offset = symbolTable.symbols[T->ptr[0]->place].offset;
                T->code = merge(2, T->code, genIR(ASSIGNOP, opn1, opn2, result));
            }
            break;
        case AND:
        case OR:
        case RELOP:
            T->type = INT;
            T->ptr[0]->offset = T->ptr[1]->offset = T->offset;
            Exp(T->ptr[0]);
            Exp(T->ptr[1]);
            boolExp(T);
            break;
        case PLUS:
        case MINUS:
        case STAR:
        case DIV:
            T->ptr[0]->offset = T->offset;
            Exp(T->ptr[0]);
            T->ptr[1]->offset = T->offset + T->ptr[0]->width;
            Exp(T->ptr[1]);
            //类型属性计算
            if (T->ptr[0]->type == CHAR || T->ptr[1]->type == CHAR)
            {
                semantic_error(T->pos, T->type_id, "表达式类型不符合");
            }
            if (T->ptr[0]->type == INT && T->ptr[1]->type == FLOAT)
            {
                semantic_error(T->pos, T->type_id, "表达式类型矛盾");
            }
            else if (T->ptr[0]->type == FLOAT && T->ptr[1]->type == INT)
            {
                semantic_error(T->pos, T->type_id, "表达式类型矛盾");
            }
            if (T->ptr[0]->type == FLOAT || T->ptr[1]->type == FLOAT)
                T->type = FLOAT, T->width = T->ptr[0]->width + T->ptr[1]->width + 4;
            else
                T->type = INT, T->width = T->ptr[0]->width + T->ptr[1]->width + 2;
            T->place = fill_Temp(newTemp(), LEV, T->type, 'T', T->offset + T->ptr[0]->width + T->ptr[1]->width);
            if (symbolTable.symbols[T->ptr[0]->place].flag == 'A')
            {
                opn1.kind = ID;
                sprintf(opn1.id, "%s[%d]", symbolTable.symbols[T->ptr[1]->place].alias, T->ptr[0]->ptr[0]->type_int);
            }
            else
            {
                opn1.kind = ID;
                strcpy(opn1.id, symbolTable.symbols[T->ptr[0]->place].alias);
            }
            opn1.type = T->ptr[0]->type;
            opn1.offset = symbolTable.symbols[T->ptr[0]->place].offset;
            if (symbolTable.symbols[T->ptr[0]->place].flag == 'A')
            {
                opn2.kind = ID;
                sprintf(opn2.id, "%s[%d]", symbolTable.symbols[T->ptr[1]->place].alias, T->ptr[1]->ptr[0]->type_int);
            }
            else
            {
                opn2.kind = ID;
                strcpy(opn2.id, symbolTable.symbols[T->ptr[1]->place].alias);
            }
            opn2.type = T->ptr[1]->type;
            opn2.offset = symbolTable.symbols[T->ptr[1]->place].offset;
            result.kind = ID;
            strcpy(result.id, symbolTable.symbols[T->place].alias);
            result.type = T->type;
            result.offset = symbolTable.symbols[T->place].offset;
            T->code = merge(3, T->ptr[0]->code, T->ptr[1]->code, genIR(T->kind, opn1, opn2, result));
            T->width = T->ptr[0]->width + T->ptr[1]->width + (T->type == INT ? 4 : (T->type == CHAR ? 2 : 8));
            break;
        case INC:
        case DEC:
            if (T->ptr[0]->kind != ID && T->ptr[0]->kind != Array_CALL)
            {
                semantic_error(T->pos, "", "赋值语句需要左值");
                break;
            }
            else
            {
                T->ptr[0]->offset = T->offset;
                Exp(T->ptr[0]);
                T->ptr[1]->offset = T->offset + T->ptr[0]->width;
                Exp(T->ptr[1]);
                //类型属性计算
                if (T->ptr[0]->type == CHAR || T->ptr[1]->type == CHAR)
                {
                    semantic_error(T->pos, T->type_id, "表达式类型不符合");
                }
                if (T->ptr[0]->type == INT && T->ptr[1]->type == FLOAT)
                {
                    semantic_error(T->pos, T->type_id, "表达式类型矛盾");
                }
                else if (T->ptr[0]->type == FLOAT && T->ptr[1]->type == INT)
                {
                    semantic_error(T->pos, T->type_id, "表达式类型矛盾");
                }
                if (T->ptr[0]->type == FLOAT || T->ptr[1]->type == FLOAT)
                    T->type = FLOAT, T->width = T->ptr[0]->width + T->ptr[1]->width + 4;
                else
                    T->type = INT, T->width = T->ptr[0]->width + T->ptr[1]->width + 2;
                T->place = fill_Temp(newTemp(), LEV, T->type, 'T', T->offset + T->ptr[0]->width + T->ptr[1]->width);
                if (symbolTable.symbols[T->ptr[0]->place].flag == 'A')
                {
                    opn1.kind = ID;
                    sprintf(opn1.id, "%s[%d]", symbolTable.symbols[T->ptr[0]->place].alias, T->ptr[0]->ptr[0]->type_int);
                }
                else
                {
                    opn1.kind = ID;
                    strcpy(opn1.id, symbolTable.symbols[T->ptr[0]->place].alias);
                }
                opn1.type = T->ptr[0]->type;
                opn1.offset = symbolTable.symbols[T->ptr[0]->place].offset;
                if (symbolTable.symbols[T->ptr[1]->place].flag == 'A')
                {
                    opn2.kind = ID;
                    sprintf(opn2.id, "%s[%d]", symbolTable.symbols[T->ptr[1]->place].alias, T->ptr[1]->ptr[0]->type_int);
                }
                else
                {
                    opn2.kind = ID;
                    strcpy(opn2.id, symbolTable.symbols[T->ptr[1]->place].alias);
                }
                opn2.type = T->ptr[1]->type;
                opn2.offset = symbolTable.symbols[T->ptr[1]->place].offset;
                result.kind = ID;
                strcpy(result.id, symbolTable.symbols[T->place].alias);
                result.type = T->type;
                result.offset = symbolTable.symbols[T->place].offset;
                T->code = merge(3, T->ptr[0]->code, T->ptr[1]->code, genIR(T->kind, opn1, opn2, result));
                T->width = T->ptr[0]->width + T->ptr[1]->width + (T->type == INT ? 4 : (T->type == CHAR ? 2 : 8));
            }
            break;
        case INC_OP:
        case DEC_OP:
            if (T->ptr[0]->kind != ID && T->ptr[0]->kind != Array_CALL)
            {
                semantic_error(T->pos, "", "自增运算需要右值");
                break;
            }
            else
            {
                T->ptr[0]->offset = T->offset;
                Exp(T->ptr[0]);
                //类型属性计算
                if (T->ptr[0]->type == CHAR)
                {
                    semantic_error(T->pos, "", "CHAR类型不能自增");
                    break;
                }
                else if (T->ptr[0]->type == FLOAT)
                {
                    semantic_error(T->pos, "", "FLOAT类型不能自增");
                    break;
                }
                else
                    T->type = INT, T->width = T->ptr[0]->width + 2;
                T->place = fill_Temp(newTemp(), LEV, T->type, 'T', T->offset + T->ptr[0]->width);
                if (symbolTable.symbols[T->ptr[0]->place].flag == 'A')
                {
                    opn1.kind = ID;
                    sprintf(opn1.id, "%s[%d]", symbolTable.symbols[T->ptr[0]->place].alias, T->ptr[0]->ptr[0]->type_int);
                }
                else
                {
                    opn1.kind = ID;
                    strcpy(opn1.id, symbolTable.symbols[T->ptr[0]->place].alias);
                }
                opn1.type = T->ptr[0]->type;
                opn1.offset = symbolTable.symbols[T->ptr[0]->place].offset;
                opn2.kind = INT;
                opn2.const_int = 1;
                result.kind = ID;
                strcpy(result.id, symbolTable.symbols[T->place].alias);
                result.type = T->type;
                result.offset = symbolTable.symbols[T->place].offset;
                T->code = merge(2, T->ptr[0]->code, genIR(T->kind, opn1, opn2, result));
                T->width = T->ptr[0]->width + (T->type == INT ? 4 : (T->type == CHAR ? 2 : 8));
            }
            break;
        case INC_OPP:
        case DEC_OPP:
            if (T->ptr[0]->kind != ID && T->ptr[0]->kind != Array_CALL)
            {
                semantic_error(T->pos, "", "自增运算需要左值");
                break;
            }
            else
            {
                T->ptr[0]->offset = T->offset;
                Exp(T->ptr[0]);
                //类型属性计算
                if (T->ptr[0]->type == CHAR)
                {
                    semantic_error(T->pos, "", "CHAR类型不能自增");
                    break;
                }
                else if (T->ptr[0]->type == FLOAT)
                {
                    semantic_error(T->pos, "", "FLOAT类型不能自增");
                    break;
                }
                else
                    T->type = INT, T->width = T->ptr[0]->width + 2;
                T->place = fill_Temp(newTemp(), LEV, T->type, 'T', T->offset + T->ptr[0]->width);
                if (symbolTable.symbols[T->ptr[0]->place].flag == 'A')
                {
                    opn1.kind = ID;
                    sprintf(opn1.id, "%s[%d]", symbolTable.symbols[T->ptr[0]->place].alias, T->ptr[0]->ptr[0]->type_int);
                }
                else
                {
                    opn1.kind = ID;
                    strcpy(opn1.id, symbolTable.symbols[T->ptr[0]->place].alias);
                }
                opn1.type = T->ptr[0]->type;
                opn1.offset = symbolTable.symbols[T->ptr[0]->place].offset;
                opn2.kind = INT;
                opn2.const_int = 1;
                result.kind = ID;
                strcpy(result.id, symbolTable.symbols[T->place].alias);
                result.type = T->type;
                result.offset = symbolTable.symbols[T->place].offset;
                T->code = merge(2, T->ptr[0]->code, genIR(T->kind, opn1, opn2, result));
                T->width = T->ptr[0]->width + (T->type == INT ? 4 : (T->type == CHAR ? 2 : 8));
            }
            break;
        case NOT:
            T->type = INT;
            T->ptr[0]->offset = T->offset;
            Exp(T->ptr[0]);
            boolExp(T);
            break;
        case UMINUS:
            T->ptr[0]->offset = T->offset;
            Exp(T->ptr[0]);
            //类型属性计算
            if (T->ptr[0]->type == CHAR)
            {
                semantic_error(T->pos, "", "类型不符合");
                break;
            }
            else if (T->ptr[0]->type == FLOAT)
                T->type = FLOAT, T->width = T->ptr[0]->width + 4;
            else
                T->type = INT, T->width = T->ptr[0]->width + 2;
            T->place = fill_Temp(newTemp(), LEV, T->type, 'T', T->offset + T->ptr[0]->width);
            opn1.kind = ID;
            strcpy(opn1.id, symbolTable.symbols[T->ptr[0]->place].alias);
            opn1.type = T->ptr[0]->type;
            opn1.offset = symbolTable.symbols[T->ptr[0]->place].offset;
            result.kind = ID;
            strcpy(result.id, symbolTable.symbols[T->place].alias);
            result.type = T->type;
            result.offset = symbolTable.symbols[T->place].offset;
            T->code = merge(2, T->ptr[0]->code, genIR(T->kind, opn1, opn2, result));
            T->width = T->ptr[0]->width + (T->type == INT ? 4 : (T->type == CHAR ? 2 : 8));
            break;
        case Array_CALL:
            rtn = searchSymbolTable(T->type_id);
            if (rtn == -1)
            {
                semantic_error(T->pos, T->type_id, "数组未定义");
                break;
            }
            if (symbolTable.symbols[rtn].flag != 'A')
            {
                semantic_error(T->pos, T->type_id, "不是一个数组");
                break;
            }
            if (T->ptr[0]->type_int >= symbolTable.symbols[rtn].arraynum)
            {
                semantic_error(T->pos, T->type_id, "超出定义数组的长度");
                break;
            }
            else
            {
                T->place = rtn; //结点保存变量在符号表中的位置
                //标识符不需要生成TAC
                T->type = symbolTable.symbols[rtn].type;
                T->offset = symbolTable.symbols[rtn].offset;
                T->width = 0; //未再使用新单元
                T->code = NULL;
            }
            break;
        case FUNC_CALL: //根据T->type_id查出函数的定义，如果语言中增加了实验教材的read，write需要单独处理一下
            rtn = searchSymbolTable(T->type_id);
            if (rtn == -1)
            {
                semantic_error(T->pos, T->type_id, "函数未定义");
                break;
            }
            if (symbolTable.symbols[rtn].flag != 'F')
            {
                semantic_error(T->pos, T->type_id, "不是一个函数");
                break;
            }
            T->type = symbolTable.symbols[rtn].type;
            width = T->type == INT ? 4 : (T->type == CHAR ? 2 : 8); //存放函数返回值的单数字节数
            if (T->ptr[0])
            {
                T->ptr[0]->offset = T->offset;
                Exp(T->ptr[0]);                      //处理所有实参表达式求值，及类型
                T->width = T->ptr[0]->width + width; //累加上计算实参使用临时变量的单元数
                T->code = T->ptr[0]->code;
            }
            else
            {
                T->width = width;
                T->code = NULL;
            }
            match_param(rtn, T->ptr[0]); //处理所以参数的匹配
                //处理参数列表的中间代码
            T0 = T->ptr[0];
            while (T0)
            {
                result.kind = ID;
                strcpy(result.id, symbolTable.symbols[T0->ptr[0]->place].alias);
                result.offset = symbolTable.symbols[T0->ptr[0]->place].offset;
                T->code = merge(2, T->code, genIR(ARG, opn1, opn2, result));
                T0 = T0->ptr[1];
            }
            T->place = fill_Temp(newTemp(), LEV, T->type, 'T', T->offset + T->width - width);
            opn1.kind = ID;
            strcpy(opn1.id, T->type_id); //保存函数名
            opn1.offset = rtn;           //这里offset用以保存函数定义入口,在目标代码生成时，能获取相应信息
            result.kind = ID;
            strcpy(result.id, symbolTable.symbols[T->place].alias);
            result.offset = symbolTable.symbols[T->place].offset;
            T->code = merge(2, T->code, genIR(CALL, opn1, opn2, result)); //生成函数调用中间代码
            break;
        case ARGS: //此处仅处理各实参表达式的求值的代码序列，不生成ARG的实参系列
            T->ptr[0]->offset = T->offset;
            Exp(T->ptr[0]);
            T->width = T->ptr[0]->width;
            T->code = T->ptr[0]->code;
            if (T->ptr[1])
            {
                T->ptr[1]->offset = T->offset + T->ptr[0]->width;
                Exp(T->ptr[1]);
                T->width += T->ptr[1]->width;
                T->code = merge(2, T->code, T->ptr[1]->code);
            }
            break;
        }
    }
}

//对抽象语法树的先根遍历,按display的控制结构修改完成符号表管理和语义检查和TAC生成（语句部分）
void semantic_Analysis(struct node *T)
{
    int rtn, num, width;
    struct node *T0;
    struct opn opn1, opn2, result;
    if (T)
    {
        switch (T->kind)
        {
        case EXT_DEF_LIST:
            if (!T->ptr[0])
                break;
            T->ptr[0]->offset = T->offset;
            semantic_Analysis(T->ptr[0]); //访问外部定义列表中的第一个
            T->code = T->ptr[0]->code;
            if (T->ptr[1])
            {
                T->ptr[1]->offset = T->ptr[0]->offset + T->ptr[0]->width;
                semantic_Analysis(T->ptr[1]); //访问该外部定义列表中的其它外部定义
                T->code = merge(2, T->code, T->ptr[1]->code);
            }
            break;
        case Array_LIST:
            if (!T->ptr[0])
                break;
            T->ptr[0]->offset = T->offset;
            semantic_Analysis(T->ptr[0]); //访问外部定义列表中的第一个
            T->code = T->ptr[0]->code;
            if (T->ptr[1])
            {
                T->ptr[1]->offset = T->ptr[0]->offset + T->ptr[0]->width;
                semantic_Analysis(T->ptr[1]); //访问该外部定义列表中的其它外部定义
                T->code = merge(2, T->code, T->ptr[1]->code);
            }
            break;
        case EXT_VAR_DEF: //处理外部说明,将第一个孩子(TYPE结点)中的类型送到第二个孩子的类型域
            T->type = T->ptr[1]->type = !strcmp(T->ptr[0]->type_id, "int") ? INT : (!strcmp(T->ptr[0]->type_id, "float") ? FLOAT : CHAR);
            T->ptr[1]->offset = T->offset;                                                //这个外部变量的偏移量向下传递
            T->ptr[1]->width = T->type == INT ? 4 : (T->type == FLOAT ? 8 : 2);           //将一个变量的宽度向下传递
            ext_var_list(T->ptr[1]);                                                      //处理外部变量说明中的标识符序列
            T->width = (T->type == INT ? 4 : (T->type == CHAR ? 2 : 8)) * T->ptr[1]->num; //计算这个外部变量说明的宽度
            T->code = NULL;                                                               //这里假定外部变量不支持初始化
            break;
        case FUNC_DEF:                                                                                                          //填写函数定义信息到符号表
            T->ptr[1]->type = !strcmp(T->ptr[0]->type_id, "int") ? INT : (!strcmp(T->ptr[0]->type_id, "float") ? FLOAT : CHAR); //获取函数返回类型送到含函数名、参数的结点
            T->width = 0;                                                                                                       //函数的宽度设置为0，不会对外部变量的地址分配产生影响
            T->offset = DX;                                                                                                     //设置局部变量在活动记录中的偏移量初值
            semantic_Analysis(T->ptr[1]);                                                                                       //处理函数名和参数结点部分，这里不考虑用寄存器传递参数
            T->offset += T->ptr[1]->width;                                                                                      //用形参单元宽度修改函数局部变量的起始偏移量
            T->ptr[2]->offset = T->offset;
            strcpy(T->ptr[2]->Snext, newLabel()); //函数体语句执行结束后的位置属性
            semantic_Analysis(T->ptr[2]);         //处理函数体结点
            //计算活动记录大小,这里offset属性存放的是活动记录大小，不是偏移
            symbolTable.symbols[T->ptr[1]->place].offset = T->offset + T->ptr[2]->width;
            T->code = merge(3, T->ptr[1]->code, T->ptr[2]->code, genLabel(T->ptr[2]->Snext)); //函数体的代码作为函数的代码
            break;
        case FUNC_DEC:                                                           //根据返回类型，函数名填写符号表
            rtn = fillSymbolTable(T->type_id, newAlias(), LEV, T->type, 'F', 0); //函数不在数据区中分配单元，偏移量为0
            if (rtn == -1)
            {
                semantic_error(T->pos, T->type_id, "函数重复定义");
                break;
            }
            else
                T->place = rtn;
            result.kind = ID;
            strcpy(result.id, T->type_id);
            result.offset = rtn;
            T->code = genIR(FUNCTION, opn1, opn2, result); //生成中间代码：FUNCTION 函数名
            T->offset = DX;                                //设置形式参数在活动记录中的偏移量初值
            if (T->ptr[0])
            { //判断是否有参数
                T->ptr[0]->offset = T->offset;
                semantic_Analysis(T->ptr[0]); //处理函数参数列表
                T->width = T->ptr[0]->width;
                symbolTable.symbols[rtn].paramnum = T->ptr[0]->num;
                T->code = merge(2, T->code, T->ptr[0]->code); //连接函数名和参数代码序列
            }
            else
                symbolTable.symbols[rtn].paramnum = 0, T->width = 0;
            break;
        case ARRAYDEF:
            T->type = T->ptr[1]->type = !strcmp(T->ptr[0]->type_id, "int") ? INT : (!strcmp(T->ptr[0]->type_id, "float") ? FLOAT : CHAR);
            T->ptr[1]->offset = T->offset; //这个外部变量的偏移量向下传递
            if (T->ptr[1]->ptr[1])
            {
                T->ptr[1]->width = (T->type == INT ? 4 : (T->type == FLOAT ? 8 : 2)) * (T->ptr[1]->ptr[0]->ptr[0]->type_int);
            } //将一个变量的宽度向下传递
            else
            {
                T->ptr[1]->width = (T->type == INT ? 4 : (T->type == FLOAT ? 8 : 2)) * (T->ptr[1]->ptr[0]->type_int);
            }
            ext_var_list(T->ptr[1]);                                                      //处理外部变量说明中的标识符序列
            T->width = (T->type == INT ? 4 : (T->type == CHAR ? 2 : 8)) * T->ptr[1]->num; //计算这个外部变量说明的宽度
            T->code = NULL;                                                               //这里假定外部变量不支持初始化
            break;
        case ARRAYDEC:
            break;
        case ARRAYDEA:
            break;
        case PARAM_LIST: //处理函数形式参数列表
            T->ptr[0]->offset = T->offset;
            semantic_Analysis(T->ptr[0]);
            if (T->ptr[1])
            {
                T->ptr[1]->offset = T->offset + T->ptr[0]->width;
                semantic_Analysis(T->ptr[1]);
                T->num = T->ptr[0]->num + T->ptr[1]->num;             //统计参数个数
                T->width = T->ptr[0]->width + T->ptr[1]->width;       //累加参数单元宽度
                T->code = merge(2, T->ptr[0]->code, T->ptr[1]->code); //连接参数代码
            }
            else
            {
                T->num = T->ptr[0]->num;
                T->width = T->ptr[0]->width;
                T->code = T->ptr[0]->code;
            }
            break;
        case PARAM_DEC:
            rtn = fillSymbolTable(T->ptr[1]->type_id, newAlias(), 1, T->ptr[0]->type, 'P', T->offset);
            if (rtn == -1)
                semantic_error(T->ptr[1]->pos, T->ptr[1]->type_id, "参数名重复定义");
            else
                T->ptr[1]->place = rtn;
            T->num = 1;                                                                //参数个数计算的初始值
            T->width = T->ptr[0]->type == INT ? 4 : (T->ptr[0]->type == CHAR ? 2 : 8); //参数宽度
            result.kind = ID;
            strcpy(result.id, symbolTable.symbols[rtn].alias);
            result.offset = T->offset;
            T->code = genIR(PARAM, opn1, opn2, result); //生成：FUNCTION 函数名
            break;
        case COMP_STM:
            LEV++;
            //设置层号加1，并且保存该层局部变量在符号表中的起始位置在symbol_scope_TX
            symbol_scope_TX.TX[symbol_scope_TX.top++] = symbolTable.index;
            T->width = 0;
            T->code = NULL;
            if (T->ptr[0])
            {
                T->ptr[0]->offset = T->offset;
                semantic_Analysis(T->ptr[0]); //处理该层的局部变量DEF_LIST
                T->width += T->ptr[0]->width;
                T->code = T->ptr[0]->code;
            }
            if (T->ptr[1])
            {
                T->ptr[1]->offset = T->offset + T->width;
                strcpy(T->ptr[1]->Snext, T->Snext); //S.next属性向下传递
                semantic_Analysis(T->ptr[1]);       //处理复合语句的语句序列
                T->width += T->ptr[1]->width;
                T->code = merge(2, T->code, T->ptr[1]->code);
            }
            prn_symbol();                                                  //c在退出一个符合语句前显示的符号表
            LEV--;                                                         //出复合语句，层号减1
            symbolTable.index = symbol_scope_TX.TX[--symbol_scope_TX.top]; //删除该作用域中的符号
            break;
        case DEF_LIST:
            T->code = NULL;
            if (T->ptr[0])
            {
                T->ptr[0]->offset = T->offset;
                semantic_Analysis(T->ptr[0]); //处理一个局部变量定义
                T->code = T->ptr[0]->code;
                T->width = T->ptr[0]->width;
            }
            if (T->ptr[1])
            {
                T->ptr[1]->offset = T->offset + T->ptr[0]->width;
                semantic_Analysis(T->ptr[1]); //处理剩下的局部变量定义
                T->code = merge(2, T->code, T->ptr[1]->code);
                T->width += T->ptr[1]->width;
            }
            break;
        case VAR_DEF: //处理一个局部变量定义,将第一个孩子(TYPE结点)中的类型送到第二个孩子的类型域
                      //类似于上面的外部变量EXT_VAR_DEF，换了一种处理方法
            T->code = NULL;
            T->ptr[1]->type = !strcmp(T->ptr[0]->type_id, "int") ? INT : (!strcmp(T->ptr[0]->type_id, "float") ? FLOAT : CHAR); //确定变量序列各变量类型
            T0 = T->ptr[1];                                                                                                     //T0为变量名列表子树根指针，对ID、ASSIGNOP类结点在登记到符号表，作为局部变量
            num = 0;
            T0->offset = T->offset;
            T->width = 0;
            width = T->ptr[1]->type == INT ? 4 : (T->ptr[1]->type == CHAR ? 2 : 8); //一个变量宽度
            while (T0)
            { //处理所以DEC_LIST结点
                num++;
                T0->ptr[0]->type = T0->type; //类型属性向下传递
                if (T0->ptr[1])
                    T0->ptr[1]->type = T0->type;
                T0->ptr[0]->offset = T0->offset; //类型属性向下传递
                if (T0->ptr[1])
                    T0->ptr[1]->offset = T0->offset + width;
                if (T0->ptr[0]->kind == ID)
                {
                    rtn = fillSymbolTable(T0->ptr[0]->type_id, newAlias(), LEV, T0->ptr[0]->type, 'V', T->offset + T->width); //此处偏移量未计算，暂时为0
                    if (rtn == -1)
                        semantic_error(T0->ptr[0]->pos, T0->ptr[0]->type_id, "变量重复定义");
                    else
                        T0->ptr[0]->place = rtn;
                    T->width += width;
                }
                else if (T0->ptr[0]->kind == ASSIGNOP)
                {
                    rtn = fillSymbolTable(T0->ptr[0]->ptr[0]->type_id, newAlias(), LEV, T0->ptr[0]->type, 'V', T->offset + T->width); //此处偏移量未计算，暂时为0
                    if (rtn == -1)
                        semantic_error(T0->ptr[0]->ptr[0]->pos, T0->ptr[0]->ptr[0]->type_id, "变量重复定义");
                    else
                    {
                        T0->ptr[0]->place = rtn;
                        T0->ptr[0]->ptr[1]->offset = T->offset + T->width + width;
                        Exp(T0->ptr[0]->ptr[1]);
                        opn1.kind = ID;
                        strcpy(opn1.id, symbolTable.symbols[T0->ptr[0]->ptr[1]->place].alias);
                        result.kind = ID;
                        strcpy(result.id, symbolTable.symbols[T0->ptr[0]->place].alias);
                        T->code = merge(3, T->code, T0->ptr[0]->ptr[1]->code, genIR(ASSIGNOP, opn1, opn2, result));
                    }
                    T->width += width + T0->ptr[0]->ptr[1]->width;
                }
                else if (T0->ptr[0]->kind == ARRAYDEC)
                {
                    rtn = fillSymbolTable(T0->ptr[0]->type_id, newAlias(), LEV, T0->ptr[0]->type, 'A', T->offset + T->width); //此处偏移量未计算，暂时为0
                    if (rtn == -1)
                        semantic_error(T0->ptr[0]->pos, T0->ptr[0]->type_id, "变量重复定义");
                    else
                        T0->ptr[0]->place = rtn;
                    T->width = T->width + width * (T0->ptr[0]->ptr[0]->type_int);
                    symbolTable.symbols[rtn].arraynum = T0->ptr[0]->ptr[0]->type_int;
                }
                T0 = T0->ptr[1];
            }
            break;
        case STM_LIST:
            if (!T->ptr[0])
            {
                T->code = NULL;
                T->width = 0;
                break;
            }              //空语句序列
            if (T->ptr[1]) //2条以上语句连接，生成新标号作为第一条语句结束后到达的位置
                strcpy(T->ptr[0]->Snext, newLabel());
            else //语句序列仅有一条语句，S.next属性向下传递
                strcpy(T->ptr[0]->Snext, T->Snext);
            T->ptr[0]->offset = T->offset;
            semantic_Analysis(T->ptr[0]);
            T->code = T->ptr[0]->code;
            T->width = T->ptr[0]->width;
            if (T->ptr[1])
            { //2条以上语句连接,S.next属性向下传递
                strcpy(T->ptr[1]->Snext, T->Snext);
                T->ptr[1]->offset = T->offset; //顺序结构共享单元方式
                semantic_Analysis(T->ptr[1]);
                //序列中第1条为表达式语句，返回语句，复合语句时，第2条前不需要标号
                if (T->ptr[0]->kind == RETURN || T->ptr[0]->kind == EXP_STMT || T->ptr[0]->kind == COMP_STM)
                    T->code = merge(2, T->code, T->ptr[1]->code);
                else
                    T->code = merge(3, T->code, genLabel(T->ptr[0]->Snext), T->ptr[1]->code);
                if (T->ptr[1]->width > T->width)
                    T->width = T->ptr[1]->width; //顺序结构共享单元方式
            }
            break;
        case IF_THEN:
            strcpy(T->ptr[0]->Etrue, newLabel()); //设置条件语句真假转移位置
            strcpy(T->ptr[0]->Efalse, T->Snext);
            T->ptr[0]->offset = T->ptr[1]->offset = T->offset;
            boolExp(T->ptr[0]);
            T->width = T->ptr[0]->width;
            strcpy(T->ptr[1]->Snext, T->Snext);
            semantic_Analysis(T->ptr[1]); //if子句
            if (T->width < T->ptr[1]->width)
                T->width = T->ptr[1]->width;
            T->code = merge(3, T->ptr[0]->code, genLabel(T->ptr[0]->Etrue), T->ptr[1]->code);
            break; //控制语句都还没有处理offset和width属性
        case IF_THEN_ELSE:
            strcpy(T->ptr[0]->Etrue, newLabel()); //设置条件语句真假转移位置
            strcpy(T->ptr[0]->Efalse, newLabel());
            T->ptr[0]->offset = T->ptr[1]->offset = T->ptr[2]->offset = T->offset;
            boolExp(T->ptr[0]); //条件，要单独按短路代码处理
            T->width = T->ptr[0]->width;
            strcpy(T->ptr[1]->Snext, T->Snext);
            semantic_Analysis(T->ptr[1]); //if子句
            if (T->width < T->ptr[1]->width)
                T->width = T->ptr[1]->width;
            strcpy(T->ptr[2]->Snext, T->Snext);
            semantic_Analysis(T->ptr[2]); //else子句
            if (T->width < T->ptr[2]->width)
                T->width = T->ptr[2]->width;
            T->code = merge(6, T->ptr[0]->code, genLabel(T->ptr[0]->Etrue), T->ptr[1]->code,
                            genGoto(T->Snext), genLabel(T->ptr[0]->Efalse), T->ptr[2]->code);
            break;
        case WHILE:
            strcpy(T->ptr[0]->Etrue, newLabel()); //子结点继承属性的计算
            strcpy(T->ptr[0]->Efalse, T->Snext);
            T->ptr[0]->offset = T->ptr[1]->offset = T->offset;
            boolExp(T->ptr[0]); //循环条件，要单独按短路代码处理
            T->width = T->ptr[0]->width;
            strcpy(T->ptr[1]->Snext, newLabel());
            semantic_Analysis(T->ptr[1]); //循环体
            if (T->width < T->ptr[1]->width)
                T->width = T->ptr[1]->width;
            T->code = merge(5, genLabel(T->ptr[1]->Snext), T->ptr[0]->code,
                            genLabel(T->ptr[0]->Etrue), T->ptr[1]->code, genGoto(T->ptr[1]->Snext));
            break;
        case EXP_STMT:
            T->ptr[0]->offset = T->offset;
            semantic_Analysis(T->ptr[0]);
            T->code = T->ptr[0]->code;
            T->width = T->ptr[0]->width;
            break;
        case RETURN:
            if (T->ptr[0])
            {
                T->ptr[0]->offset = T->offset;
                Exp(T->ptr[0]);
                num = symbolTable.index;
                do
                    num--;
                while (symbolTable.symbols[num].flag != 'F');
                if (T->ptr[0]->type != symbolTable.symbols[num].type)
                {
                    semantic_error(T->pos, "返回值类型错误", "");
                    T->width = 0;
                    T->code = NULL;
                    break;
                }
                T->width = T->ptr[0]->width;
                result.kind = ID;
                strcpy(result.id, symbolTable.symbols[T->ptr[0]->place].alias);
                result.offset = symbolTable.symbols[T->ptr[0]->place].offset;
                T->code = merge(2, T->ptr[0]->code, genIR(RETURN, opn1, opn2, result));
            }
            else
            {
                T->width = 0;
                result.kind = 0;
                T->code = genIR(RETURN, opn1, opn2, result);
            }
            break;
        case ID:
        case INT:
        case FLOAT:
        case CHAR:
        case ASSIGNOP:
        case AND:
        case OR:
        case RELOP:
        case PLUS:
        case MINUS:
        case STAR:
        case DIV:
        case NOT:
        case INC:
        case DEC:
        case INC_OP:
        case DEC_OP:
        case INC_OPP:
        case DEC_OPP:
        case UMINUS:
        case FUNC_CALL:
        case Array_CALL:
            Exp(T); //处理基本表达式
            break;
        }
    }
}

void objectCode(struct codenode *head)
{
    char opnstr1[32], opnstr2[32], resultstr[32];
    struct codenode *h = head, *p;
    int i;
    FILE *fp;
    fp = fopen("object.s", "w");
    fprintf(fp, ".data\n");
    fprintf(fp, "_Prompt: .asciiz \"Enter an integer:  \"\n");
    fprintf(fp, "_ret: .asciiz \"\\n\"\n");
    fprintf(fp, ".globl main\n");
    fprintf(fp, ".text\n");
    fprintf(fp, "read:\n");
    fprintf(fp, "  li $v0,4\n");
    fprintf(fp, "  la $a0,_Prompt\n");
    fprintf(fp, "  syscall\n");
    fprintf(fp, "  li $v0,5\n");
    fprintf(fp, "  syscall\n");
    fprintf(fp, "  jr $ra\n");
    fprintf(fp, "write:\n");
    fprintf(fp, "  li $v0,1\n");
    fprintf(fp, "  syscall\n");
    fprintf(fp, "  li $v0,4\n");
    fprintf(fp, "  la $a0,_ret\n");
    fprintf(fp, "  syscall\n");
    fprintf(fp, "  move $v0,$0\n");
    fprintf(fp, "  jr $ra\n");
    do
    {
        switch (h->op)
        {
        case ASSIGNOP:
            if (h->opn1.kind == INT)
                fprintf(fp, "  li $t3, %d\n", h->opn1.const_int);
            else
            {
                fprintf(fp, "  lw $t1, %d($sp)\n", h->opn1.offset);
                fprintf(fp, "  move $t3, $t1\n");
            }
            fprintf(fp, "  sw $t3, %d($sp)\n", h->result.offset);
            break;
        case PLUS:
        case MINUS:
        case STAR:
        case DIV:
            fprintf(fp, "  lw $t1, %d($sp)\n", h->opn1.offset);
            fprintf(fp, "  lw $t2, %d($sp)\n", h->opn2.offset);
            if (h->op == PLUS)
                fprintf(fp, "  add $t3,$t1,$t2\n");
            else if (h->op == MINUS)
                fprintf(fp, "  sub $t3,$t1,$t2\n");
            else if (h->op == STAR)
                fprintf(fp, "  mul $t3,$t1,$t2\n");
            else
            {
                fprintf(fp, "  div $t1, $t2\n");
                fprintf(fp, "  mflo $t3\n");
            }
            fprintf(fp, "  sw $t3, %d($sp)\n", h->result.offset);
            break;
        case FUNCTION:
            fprintf(fp, "\n%s:\n", h->result.id);
            if (!strcmp(h->result.id, "main")) //特殊处理main
                fprintf(fp, "  addi $sp, $sp, -%d\n", symbolTable.symbols[h->result.offset].offset);
            break;
        case PARAM: //直接跳到后面一条
            break;
        case LABEL:
            fprintf(fp, "%s:\n", h->result.id);
            break;
        case GOTO:
            fprintf(fp, "  j %s\n", h->result.id);
            break;
        case JLE:
        case JLT:
        case JGE:
        case JGT:
        case EQ:
        case NEQ:
            fprintf(fp, "  lw $t1, %d($sp)\n", h->opn1.offset);
            fprintf(fp, "  lw $t2, %d($sp)\n", h->opn2.offset);
            if (h->op == JLE)
                fprintf(fp, "  ble $t1,$t2,%s\n", h->result.id);
            else if (h->op == JLT)
                fprintf(fp, "  blt $t1,$t2,%s\n", h->result.id);
            else if (h->op == JGE)
                fprintf(fp, "  bge $t1,$t2,%s\n", h->result.id);
            else if (h->op == JGT)
                fprintf(fp, "  bgt $t1,$t2,%s\n", h->result.id);
            else if (h->op == EQ)
                fprintf(fp, "  beq $t1,$t2,%s\n", h->result.id);
            else
                fprintf(fp, "  bne $t1,$t2,%s\n", h->result.id);
            break;
        case ARG: //直接跳到后面一条,回头反查
            break;
        case CALL:
            if (!strcmp(h->opn1.id, "read"))
            { //特殊处理read
                fprintf(fp, "  addi $sp, $sp, -4\n");
                fprintf(fp, "  sw $ra,0($sp)\n"); //保留返回地址
                fprintf(fp, "  jal read\n");      //保留返回地址
                fprintf(fp, "  lw $ra,0($sp)\n"); //恢复返回地址
                fprintf(fp, "  addi $sp, $sp, 4\n");
                fprintf(fp, "  sw $v0, %d($sp)\n", h->result.offset);
                break;
            }
            if (!strcmp(h->opn1.id, "write"))
            { //特殊处理write
                fprintf(fp, "  lw $a0, %d($sp)\n", h->prior->result.offset);
                fprintf(fp, "  addi $sp, $sp, -4\n");
                fprintf(fp, "  sw $ra,0($sp)\n");
                fprintf(fp, "  jal write\n");
                fprintf(fp, "  lw $ra,0($sp)\n");
                fprintf(fp, "  addi $sp, $sp, 4\n");
                break;
            }

            for (p = h, i = 0; i < symbolTable.symbols[h->opn1.offset].paramnum; i++) //定位到第一个实参的结点
                p = p->prior;
            //开活动记录空间
            fprintf(fp, "  move $t0,$sp\n"); //保存当前函数的sp到$t0中，为了取实参表达式的值
            fprintf(fp, "  addi $sp, $sp, -%d\n", symbolTable.symbols[h->opn1.offset].offset);
            fprintf(fp, "  sw $ra,0($sp)\n"); //保留返回地址
            i = h->opn1.offset + 1;           //第一个形参变量在符号表的位置序号
            while (symbolTable.symbols[i].flag == 'P')
            {
                fprintf(fp, "  lw $t1, %d($t0)\n", p->result.offset); //取实参值
                fprintf(fp, "  move $t3,$t1\n");
                fprintf(fp, "  sw $t3,%d($sp)\n", symbolTable.symbols[i].offset); //送到被调用函数的形参单元
                p = p->next;
                i++;
            }
            fprintf(fp, "  jal %s\n", h->opn1.id);                                          //恢复返回地址
            fprintf(fp, "  lw $ra,0($sp)\n");                                               //恢复返回地址
            fprintf(fp, "  addi $sp,$sp,%d\n", symbolTable.symbols[h->opn1.offset].offset); //释放活动记录空间
            fprintf(fp, "  sw $v0,%d($sp)\n", h->result.offset);                            //取返回值
            break;
        case RETURN:
            fprintf(fp, "  lw $v0,%d($sp)\n", h->result.offset); //返回值送到$v0
            fprintf(fp, "  jr $ra\n");
            break;
        }
        h = h->next;
    } while (h != head);
    fclose(fp);
}


void semantic_Analysis0(struct node *T)
{
    symbolTable.index = 0;
    fillSymbolTable("read", "", 0, INT, 'F', 4);
    symbolTable.symbols[0].paramnum = 0; //read的形参个数
    fillSymbolTable("x", "", 1, INT, 'P', 12);
    fillSymbolTable("write", "", 0, INT, 'F', 4);
    symbolTable.symbols[2].paramnum = 1;
    symbol_scope_TX.TX[0] = 0; //外部变量在符号表中的起始序号为0
    symbol_scope_TX.top = 1;
    T->offset = 0; //外部变量在数据区的偏移量
    semantic_Analysis(T);

    objectCode(T->code);
    if (errornum == 0)
    {
        prnIR(T->code);
    }
}
