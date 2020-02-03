#include <stdio.h>

#define MAX(x, y)   ( (x) > (y) ? (x) : (y))

//define a tree using cursors
typedef struct tREE
{
  char data;
  int left;
  int right;
} TREE;

//test case: modify to test different paths
static TREE tr[] =
{
  {'a', 1, 2},
  {'b', -1, 3},
  {'c', 6, 5},
  {'d', 4, -1},
  {'e', -1, -1},
  {'f', -1, -1},
  {'g', 7, -1},
  {'h', -1, -1},
};

//***********************************
static int count_height (int root)
{
  int l, r;

  if (root == -1)
      return (-1);

  printf ("%c\n", tr[root].data);
  l = count_height (tr[root].left);
  r = count_height (tr[root].right);

  return (1 + MAX (l, r));
}

//***********************************
int main ()
{
  printf ("height is %d\n", count_height (0));

  return (0);
}
