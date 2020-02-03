import csv
import networkx as nx
import matplotlib.pyplot as plt
import random
import numpy as np

# 分别统计点横纵值
node_1 = []
node_0 = []
# 统计数量
lines = 0
# 读取文件
csv_reader = csv.reader(open("./data.csv"))
for csv in csv_reader:
    node_1.append((csv[0].split())[1])
    node_0.append((csv[0].split())[0])
    lines += 1

# 随机取测试点100个
top = 100
r_list = (random.sample(range(1, len(node_0)), top))
r_list.sort()

G = nx.Graph()
# 建立一个 lines * lindes 矩阵
net_node_list = [[0 for i in range(lines)] for j in range(lines)]
for i in r_list:
    G.add_edge(node_1[i], node_0[i])
    net_node_list[int(node_0[i])][int(node_1[i])] = 1

# 度中心性计算
degree = [0 for i in range(top)]
p_degree = []
for i in range(top):
    for j in range(i, top):
        if net_node_list[int(node_0[i])][int(node_1[i])] == 1:
            degree[i] += 1
            degree[j] += 1

node_index = 0
max_degree = max(degree)

for i in degree:
    p_degree.append(i/max_degree)
for j in range(top):
    if degree[j] == max_degree:
        node_index = j

nodes = []
for i in r_list:
    nodes.append(node_0[i])

# closeness中心性计算
close_cen = nx.closeness_centrality(G)
close_cen_value = list(close_cen.values())
print(close_cen_value)
# betweenness中心性计算
# bet_cen = nx.betweenness_centrality(G)
# bet_cen_value = list(bet_cen.values())
# print(bet_cen_value)

fig, ax = plt.subplots()
index = np.arange(len(nodes))

plt.title("degree_centrality")
plt.xticks(rotation=45)

rects1 = plt.bar(len(close_cen_value), close_cen_value,
                 color='g', align="center")

# rects1 = plt.bar(len(bet_cen_value), bet_cen_value,
#                  color='g', align="center")
ax.annotate('max_node_numb = (%.1d)' % (node_index), xy=(node_index, 1), xytext=(node_index+50, 0.8),
            xycoords='data', arrowprops=dict(facecolor='black', shrink=0.05)
            )
plt.show()

# 绘制
nx.draw(G, pos=nx.random_layout(G), node_size=25, node_shape='o',
        node_color='r', width=0.3, style='solid', font_size=5)

plt.show()
