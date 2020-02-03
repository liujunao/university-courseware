#include <stdio.h>

#define MAX_SIZE    (100)
#define UNUSED_LIST_HEAD    (MAX_SIZE - 1)

typedef struct nODE
{
    int coeff;
    int order;
    int next;
} NODE;

static NODE pool[MAX_SIZE];

//***********************************
static void init ()
{
    int i;

    for (i = 0; i < UNUSED_LIST_HEAD; i++)
    {
        pool[i].next = (i + 1) % UNUSED_LIST_HEAD;
    }
}

//***********************************
static int alloc ()
{
    int tmp;

    if (pool[UNUSED_LIST_HEAD].next < 0)
        return (-1);

    tmp = pool[UNUSED_LIST_HEAD].next;
    pool[UNUSED_LIST_HEAD].next = pool[tmp].next;

    return (tmp);
}

//***********************************
static int set_poly (int list, int k)
{
    int i = list, j, l;

    for (j = 1; j <= k; j++)
    {
        l = alloc ();
        if (l < 0)
            return (-1);
        pool[i].next = l;
        i = l;
        pool[i].coeff = 1;
        pool[i].order = j;
    }
    pool[i].next = -1;
    list = pool[list].next;
    while (list >= 0)
    {
        //printf ("%d %d\n", pool[list].coeff, pool[list].order);
        list = pool[list].next;
    }

    return (0);
}

//***********************************
static int multiply (int l1, int l2)
{
    int list = alloc ();
    int j, k, kk, m;

    if (list < 0)
    {
        printf ("alloc () failed\n");
        return (-1);
    }
    m = list;
    pool[m].next = -1;
    kk = -1;
    j = pool[l1].next; k = pool[l2].next;

    while (j >= 0)
    {
        while (k >= 0)
        {
            while (kk >= 0)
            {
                m = kk;
                if (pool[m].order == (pool[j].order + pool[k].order)) //found
                    break;
                kk = pool[m].next;
            }
            if (kk < 0)  //new
            {
                kk = alloc ();
                pool[m].next = kk;
                m = kk;
                if (m < 0)
                {
                    printf ("alloc (%d) failed\n", j*k);
                    return (-1);
                }

                pool[m].order = pool[j].order + pool[k].order;
                pool[m].next = -1;
                pool[m].coeff = 0;
            }
            pool[m].coeff += pool[j].coeff * pool[k].coeff;
            m = pool[k].next;
            k = m;
        }

        kk = pool[list].next;
        m = pool[j].next;
        j = m;
        k = pool[l2].next;
    }
    return (list);
}

//***********************************
int main ()
{
    int l, l1, l2;
    int n = 10;

    init ();

    l1= alloc ();
    set_poly (l1, n);
    l2 = alloc ();
    set_poly (l2, n);
    if (l1 < 0 || l2 < 0)
        return (-1);

    l = multiply (l1, l2);

    n = pool[l].next;
    while (n >= 0)
    {
        printf ("%dx^%d + ", pool[n].coeff, pool[n].order);
        l = pool[n].next;
        n = l;
    }
    printf (" \n");
    return (0);
}
