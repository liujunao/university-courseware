//***********************************
//    This is the source code for
//    lab assignment 2: majority
//    functions to test
//        major_i
//        major_r
//    respectively in recursive and
//    iterative forms
//    test cases: best/worst cases of majority existing or nonexisting
//**********************************
#include <stdio.h>

#ifndef SUCCESS
#define SUCCESS    (0)
#endif

#ifndef FAILURE
#define FAILURE    (-1)
#endif

#ifndef BOOL
typedef unsigned char BOOL;
#endif

#define NUM_OF_ENTRIES(a)    ((sizeof(a))/sizeof(a[0]))

//***********************************
static int major_i (int a[], int n);
static int major_r (int a[], int n);

//***********************************
static int major_i (int a[], int n)
{
  int count = 0, i, j;
  int candidate;

  for (i= 0; i < n; i++)
  {
    j = a[i];//C4droid bug
      if (count == 0)
          candidate = j;

    if (candidate == j)
        count++;
    else
      count--;
  }

  if (count > 0)
      return (candidate);

  return (FAILURE);
}

//***********************************
int major_r (int a[], int n)
{
  int i, j;
  int tmp;

  if (0 == n)//recursion exit
      return (FAILURE);

  //each cell has two adjacent elements. if equal, could be a candidate, placed in the front
  for (i = 0, j = 0; i < n/2; i++)
      if (a[i*2] == a[i*2 + 1])
      {
        tmp = a[j];
          a[j++] = a[i*2];//j: count of candidates
          a[i*2] = tmp;//array preserved
      }

  tmp = major_r (a, j);//recursion
  if (tmp < 0)//no candidate found
      if (n & 1)//odd number
          return (a[n - 1]);//the last one
     else
          return (FAILURE);

   return (tmp);
}

//***********************************
static BOOL verify (int a[], int n, int x)
{
  int i, count = 0;

  //count the occurences
  for (i = 0; i < n; i++)
      if (a[i] == x)
          count++;

  return (count > n/2);
}

//***********************************
static int aa[]= {1,1,2,1,2,2,2,2,1};

//***********************************
int main ()
{
  int x = major_i (aa, NUM_OF_ENTRIES (aa));

  printf ("iterative\n");
  if (verify (aa, NUM_OF_ENTRIES (aa), x))
      printf ("majority element is %d\n", x);
  else
      printf ("no majority exists\n");

    printf ("recursive\n");
    x = major_r (aa, NUM_OF_ENTRIES (aa));

  if (verify (aa,NUM_OF_ENTRIES (aa) , x))
      printf ("majority element is %d\n", x);
  else
      printf ("no majority exists\n");

  return (SUCCESS);
}
