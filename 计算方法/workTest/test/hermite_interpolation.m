%% hermite_interpolation: Hermite插值算法
function Y = hermite_interpolation(X_inter,Y_inter,Y_inter_de,X)
	% Y_inter_de代表插值节点的导数值
	n=length(X_inter); % 插值节点个数
	m=length(X); % 待求节点个数
	% 下面这个循环求每个待求点的函数值
	for X_num=1:m 
		x=X(X_num);
		H=0.0; % H是hermite插值函数
		% 下面这个循环求hermite插值函数H
		for j=1:n
			L_2=1.0; % L_2是Lagrange基函数的平方
			b=0; % b是求解H的中间量
			% 下面这个循环求Lagrange基函数L_j(x)的平方
			for i=1:n
				if i~=j
					L_2=L_2*((x-X_inter(i))/(X_inter(j)-X_inter(i)))^2;
					b=1/(X_inter(j)-X_inter(i))+b;
				end
			end
			H=H+L_2*((X_inter(j)-x)*(2*b*Y_inter(j)-Y_inter_de(j))+Y_inter(j));
		end
		Y(X_num)=H;
	end
end
