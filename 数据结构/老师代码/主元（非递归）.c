//***********************************
//    This is the source code for
//    lab assignment 2
//    functions to test
//        power_2i
//        power_2r
//    both in recursive and
//    iterative forms
//    test cases: best/worst cases
//***********************************
#include <stdio.h>
#include <time.h>

//***** declare local functions below
static int power_2r (int x, int n);
static int power_2i (int x, int n);

//***********************************
static int power_2r (int x,int n)
{
  if (0 == n)
    return (1);

  if (1 == n)
    return (x);

  if (0 == n % 2)
    {
    return (power_2r (x * x, n / 2));
  }
  return (power_2r (x * x, n / 2) * x);
}

//********************************
static int power_2i (int x, int n)
{
  int res = 1;

  for (; n > 0; n = n >> 1)
  {
    if (n & 1)
        res *= x;

      x *= x;
  }

  return (res);
}

//***********************************
int main ()
{
  clock_t start;
  clock_t end;
  int sum;
  int n, i, j;

  printf ("recursions\n");
  for  (n = 32; n <= 1 << 16; n = n << 1)
  {
    //calculate in power_2
    sum = 0;
    start = clock();
    for (i = 0; i < 5000; i++)
        for (j= 0; j < 1000; j++)
            power_2r (1, n - 1);

    end = clock ();
    sum += (end - start) ;

    printf ("power_2 () calculated x^%d using time : %2.3f \n", n - 1, (double)sum / (CLOCKS_PER_SEC));
  }

  printf ("iterations\n");
  for  (n = 32; n <= 1 << 16; n = n << 1)
  {
    //calculate in power_2
    sum = 0;
    start = clock();
    for (i = 0; i < 5000; i++)
        for (j= 0; j < 1000; j++)
            power_2i (1, n - 1);

    end = clock ();
    sum += (end - start) ;

    printf ("power_2r () calculated x^%d using time : %2.3f \n", n - 1, (double)sum / (CLOCKS_PER_SEC));
  }
  return (0);
}
