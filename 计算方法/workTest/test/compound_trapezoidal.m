%% compound_trapezoidal: 复合梯形公式
function F = compound_trapezoidal(f,a,b,h)
	% f是被积函数，a，b是积分区间，h是步长
	N=(b-a)/h; % 节点个数
	f_sum=0;
	for n=1:N-1
		x_n=a+n*h;
		f_sum=f_sum+f(x_n);
	end
	F=h/2*(f(a)+2*f_sum+f(b));
end