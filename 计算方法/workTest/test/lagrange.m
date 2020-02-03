%% lagrange: Lagrange插值函数
function Y = lagrange(X_inter,Y_inter,X)
	% X_inter是插值节点，Y_inter是插值节点的函数值，X是待求点，Y是待求点的函数值 
	n=length(X_inter); % 插值节点个数
	m=length(X); % 待求节点个数
	% 下面这个循环求每个待求点的函数值
	for X_num=1:m 
		x=X(X_num);
		P=0.0; % P是Lagrange插值函数
		% 下面这个循环求Lagrange插值函数P，即所有基函数L_j(x)*y_j的和
		for j=1:n
			L=1.0; % L是Lagrange基函数
			% 下面这个循环求Lagrange基函数L_j(x)
			for i=1:n
				if i~=j
					L=L*(x-X_inter(i))/(X_inter(j)-X_inter(i));
				end
			end
			P=P+L*Y_inter(j);
		end
		Y(X_num)=P;
	end
end