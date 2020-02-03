%% compound_simpson: 复合辛普森公�?

function F = compound_simpson(f,a,b,h)
	% f是被积函数，a，b是积分区间，h是步�?
    %
	N=(b-a)/h; % 节点个数
	f_sum_half=0;
	f_sum=0;
	x_n=a; % 保证x_n_half的起始计�?
	for n=1:N-1
        x_n_half=x_n+h/2;
        x_n=a+n*h;
		f_sum_half=f_sum_half+f(x_n_half);
		f_sum=f_sum+f(x_n);
	end
	F=h/6*(f(a)+4*f_sum_half+2*f_sum+f(b)); % 注意课本上x_n_half的求和是从n=0�?���?
end
