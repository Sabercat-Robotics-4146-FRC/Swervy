import math
import numpy as np
import matplotlib.pyplot as plt
v = 22.5
g = -9.81
h = 10
d = 20

part = math.sqrt(v*v*v*v + g * (2 * h * v*v - g * d*d));
ang = math.atan((v * v + part) / (-g * d));

print("Launch Angle(degrees): " + str(ang*180/math.pi))
t = np.linspace(0,50,20000)
vx = v*math.cos(ang)
vy = v*math.sin(ang)
x = vx*t
y = vy*t + g*(t**2)/2
for index in range(len(y)):
    if(y[index] < 0): yIndex = index;break;
plt.plot(x,y)
plt.plot([0,100],[h,h], '-r')
plt.plot([d,d],[0,100], '-r')
plt.ylim(0, max(y)+1)
plt.xlim(0,x[yIndex]+1)
plt.show()