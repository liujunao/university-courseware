#include <stdio.h>

#define NUM_OF_ENTRIES(a)    (sizeof (a))/(sizeof (a[0]))

//define a tree using cursors
typedef struct tREE
{
  int data;
  int left;
  int right;
} TREE;

//test case: modify to test different paths
static TREE tr[] =
{
  {10, 1, 2},
  {5, -1, 3},
  {15, 6, 5},
  {8, 4, -1},
  {6, -1, -1},
  {22, -1, -1},
  {12, 7, -1},
  {11, -1, -1},
  {80, -1, -1},
  {2, -1, -1},
  {88, -1, -1},
  {18, -1, -1},
};

//**********************************
static int find_min (int root)
{
  if (tr[root].left < 0)
      return (root);

  return (find_min (tr[root].left));
}


//**********************************
static int insert (int root, int i)
{
  if (root < 0)
  {
      return (i);
  }

  if (tr[i].data < tr[root].data)
      tr[root].left =  insert (tr[root].left, i) ;
  else if (tr[i].data > tr[root].data)
      tr[root].right = insert (tr[root].right, i) ;

  return (root);
}

static void traverse (int root)
{
  if (root == -1)
      return;

  traverse (tr[root].left);
  printf ("%d\n", tr[root].data);
  traverse (tr[root].right);

  return;
}

int main()
{
  int i;
  find_min(tr[0].data);
  insert(tr[0].data,i);
  printf ("after insertions\n");

  for (i = 0; i < NUM_OF_ENTRIES (tr); i++)
      printf ("{%d, %d, %d}\n", tr[i]. data, tr[i].left, tr[i].right);

  traverse(root);
}
