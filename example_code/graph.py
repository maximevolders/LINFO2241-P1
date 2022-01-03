#!/usr/bin/env python3

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

mean = np.zeros((4,4))
clients = [2,5,10,25,50,75,100]
'''
#Import data
data2 = pd.read_csv("SO_LIST_MU/mesures_2_clients.csv")
data5 = pd.read_csv("SO_LIST_MU/mesures_5_clients.csv")
data10 = pd.read_csv("SO_LIST_MU/mesures_10_clients.csv")
data25 = pd.read_csv("SO_LIST_MU/mesures_25_clients.csv")
data50 = pd.read_csv("SO_LIST_MU/mesures_50_clients.csv")
data75 = pd.read_csv("SO_LIST_MU/mesures_75_clients.csv")
data100 = pd.read_csv("SO_LIST_MU/mesures_100_clients.csv")

#Calcul de la moyenne et de l'écart type
mean[0][0] = data2.mean()['time']

mean[0][1] = data5.mean()['time']

mean[0][2] = data10.mean()['time']

mean[0][3] = data25.mean()['time']

mean[0][4] = data50.mean()['time']

mean[0][5] = data75.mean()['time']

mean[0][6] = data100.mean()['time']


#Import data
data2 = pd.read_csv("SO_RANDOM_3_MU/mesures_2_clients.csv")
data5 = pd.read_csv("SO_RANDOM_3_MU/mesures_5_clients.csv")
data10 = pd.read_csv("SO_RANDOM_3_MU/mesures_10_clients.csv")
data25 = pd.read_csv("SO_RANDOM_3_MU/mesures_25_clients.csv")
data50 = pd.read_csv("SO_RANDOM_3_MU/mesures_50_clients.csv")
data75 = pd.read_csv("SO_RANDOM_3_MU/mesures_75_clients.csv")
data100 = pd.read_csv("SO_RANDOM_3_MU/mesures_100_clients.csv")

#Calcul de la moyenne et de l'écart type
mean[1][0] = data2.mean()['time']

mean[1][1] = data5.mean()['time']

mean[1][2] = data10.mean()['time']

mean[1][3] = data25.mean()['time']

mean[1][4] = data50.mean()['time']

mean[1][5] = data75.mean()['time']

mean[1][6] = data100.mean()['time']


#Import data
'''
data2 = pd.read_csv("MU_LIST/mesures_mu_-0.5.csv")
data5 = pd.read_csv("MU_LIST/mesures_mu_-1.csv")
data10 = pd.read_csv("MU_LIST/mesures_mu_-2.csv")
data25 = pd.read_csv("MU_LIST/mesures_mu_-5.csv")

#Calcul de la moyenne et de l'écart type
mean[2][3] = data2.mean()['time']

mean[2][2] = data5.mean()['time']

mean[2][1] = data10.mean()['time']

mean[2][0] = data25.mean()['time']


#Import data
data2 = pd.read_csv("MU_RANDOM_3/mesures_mu_-0.5.csv")
data5 = pd.read_csv("MU_RANDOM_3/mesures_mu_-1.csv")
data10 = pd.read_csv("MU_RANDOM_3/mesures_mu_-2.csv")
data25 = pd.read_csv("MU_RANDOM_3/mesures_mu_-5.csv")

#Calcul de la moyenne et de l'écart type
mean[3][3] = data2.mean()['time']

mean[3][2] = data5.mean()['time']

mean[3][1] = data10.mean()['time']

mean[3][0] = data25.mean()['time']



#Création du graph
fig = plt.figure()
'''
#Plot de la moyenne et de l'erreur
plt.plot(clients, mean[0]/1000, color="tab:blue", linewidth=1.0, linestyle="-")

plt.plot(clients, mean[1]/1000, color="tab:red", linewidth=1.0, linestyle="-")
'''
plt.plot([0.5,1,2,5], mean[2]/1000, color="tab:green", linewidth=1.0, linestyle="-")

plt.plot([0.5,1,2,5], mean[3]/1000, color="gold", linewidth=1.0, linestyle="-")

#Modification des limites des axes du graphiques
#plt.xlim(0,8.5)
#plt.ylim(-2,20)
#plt.yscale('log')

#Légende des axes, titre du graphique, grille, légende du graphique
plt.xlabel('\u03BC')
plt.ylabel('Time [sec]')
plt.title("Time of execution depending on\nthe value of \u03BC")
plt.grid(True)
plt.legend(['Password from list', '3 characters'], loc = 'lower left')

#Enregistrement de la figure
plt.savefig("mu_diff.png")
#plt.savefig("Passwords_time.pdf")

plt.show()
#plt.close()