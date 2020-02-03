#include <stdio.h>

#define MAX_SIZE    (100)
#define UNUSED_LIST_HEAD    (MAX_SIZE - 1)

typedef struct nODE
{
  int data;
  int next;
} NODE;

static NODE pool[MAX_SIZE];

//***********************************
static void init ()
{
  int i;

  for (i = 0; i < UNUSED_LIST_HEAD; i++)
  {
    pool[i].data = i;
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
static void release (int i)
{
  int tmp = pool[UNUSED_LIST_HEAD].next;

  pool[UNUSED_LIST_HEAD].next = i;
  pool[i].next = tmp;
}

//***********************************
static int remov (int list, int k)
{
  int i = pool[list].next, j, l;

  for (j = 1; j < k; j++)
  {
    l = i;
      i = pool[i].next;
  }
  pool[l].next = pool[i].next;
  pool[list].next = pool[i].next;
  release (i);

  return (i);
}

//***********************************
static int create (int n)
{
  int list = alloc ();
  int i, j, k;

  if (list < 0)
  {
    printf ("alloc () failed\n");
    return (-1);
  }
  i = list;
  for (j = 0; j < n; j++)
  {
    pool[i].next = alloc ();
    i = pool[i].next;
      if (i < 0)
      {
        printf ("alloc (%d) failed\n", j);
        return (-1);
      }
  }

  pool[i].next = pool[list].next;
  return (0);
}

//***********************************
static int survivor (int n, int k)
{
  int i, j;


  for (i = 1, j = 2; j <= n; j++)
  {
    i = (k + i) % (j);
    if (i == 0)
        i = j;
    //printf ("%d\n", i);
  }

  return (i);
}

//***********************************
int main ()
{
    int i, j, l;
    int n = 13, k = 5;

    init ();

    l = create (n);
    if (l < 0)
        return (-1);

    for (i = 0; i < n - 1; i++)
    {
      j = remov (l, k);
        printf ("%d removed\n", pool[j].data);
    }

    printf ("the survivor is %d %d\n", pool[pool[l].next].data, survivor (n, k));
    return (0);
}
