
function [x1,k] = 2_newton(f,f_de,varible,x0,eplison)
	
	dx = -subs(f_de,varible,x0)\subs(f,varible,x0);
    x1=x0+dx;
	k=1; 
	while norm(x1-x0)>=eplison
		x0=x1;
		dx=-subs(f_de,varible,x0)\subs(f,varible,x0);
		x1=x0+dx;
		k=k+1;
    end
end

