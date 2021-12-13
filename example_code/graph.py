#!/usr/bin/env python3

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

mean = np.zeros((5,7))
std = np.zeros((5,7))
clients = [2,5,10,25,50,75,100]

#Import data
data2 = pd.read_csv("SO_RANDOM_2/mesures_2_clients.csv")
data5 = pd.read_csv("SO_RANDOM_2/mesures_5_clients.csv")
data10 = pd.read_csv("SO_RANDOM_2/mesures_10_clients.csv")
data25 = pd.read_csv("SO_RANDOM_2/mesures_25_clients.csv")
data50 = pd.read_csv("SO_RANDOM_2/mesures_50_clients.csv")
data75 = pd.read_csv("SO_RANDOM_2/mesures_75_clients.csv")
data100 = pd.read_csv("SO_RANDOM_2/mesures_100_clients.csv")

#Calcul de la moyenne et de l'écart type
mean[0][0] = data2.mean()[1]
std[0][0] = data2.std()[1]

mean[0][1] = data5.mean()[1]
std[0][1] = data5.std()[1]

mean[0][2] = data10.mean()[1]
std[0][2] = data10.std()[1]

mean[0][3] = data25.mean()[1]
std[0][3] = data25.std()[1]

mean[0][4] = data50.mean()[1]
std[0][4] = data50.std()[1]

mean[0][5] = data75.mean()[1]
std[0][5] = data75.std()[1]

mean[0][6] = data100.mean()[1]
std[0][6] = data100.std()[1]


#Import data
data2 = pd.read_csv("SO_RANDOM_3/mesures_2_clients.csv")
data5 = pd.read_csv("SO_RANDOM_3/mesures_5_clients.csv")
data10 = pd.read_csv("SO_RANDOM_3/mesures_10_clients.csv")
data25 = pd.read_csv("SO_RANDOM_3/mesures_25_clients.csv")
data50 = pd.read_csv("SO_RANDOM_3/mesures_50_clients.csv")
data75 = pd.read_csv("SO_RANDOM_3/mesures_75_clients.csv")
data100 = pd.read_csv("SO_RANDOM_3/mesures_100_clients.csv")

#Calcul de la moyenne et de l'écart type
mean[1][0] = data2.mean()[1]
std[1][0] = data2.std()[1]

mean[1][1] = data5.mean()[1]
std[1][1] = data5.std()[1]

mean[1][2] = data10.mean()[1]
std[1][2] = data10.std()[1]

mean[1][3] = data25.mean()[1]
std[1][3] = data25.std()[1]

mean[1][4] = data50.mean()[1]
std[1][4] = data50.std()[1]

mean[1][5] = data75.mean()[1]
std[1][5] = data75.std()[1]

mean[1][6] = data100.mean()[1]
std[1][6] = data100.std()[1]


#Import data
data2 = pd.read_csv("SO_RANDOM_4/mesures_2_clients.csv")
data5 = pd.read_csv("SO_RANDOM_4/mesures_5_clients.csv")
data10 = pd.read_csv("SO_RANDOM_4/mesures_10_clients.csv")
data25 = pd.read_csv("SO_RANDOM_4/mesures_25_clients.csv")
data50 = pd.read_csv("SO_RANDOM_4/mesures_50_clients.csv")
data75 = pd.read_csv("SO_RANDOM_4/mesures_75_clients.csv")
data100 = pd.read_csv("SO_RANDOM_4/mesures_100_clients.csv")

#Calcul de la moyenne et de l'écart type
mean[2][0] = data2.mean()[1]
std[2][0] = data2.std()[1]

mean[2][1] = data5.mean()[1]
std[2][1] = data5.std()[1]

mean[2][2] = data10.mean()[1]
std[2][2] = data10.std()[1]

mean[2][3] = data25.mean()[1]
std[2][3] = data25.std()[1]

mean[2][4] = data50.mean()[1]
std[2][4] = data50.std()[1]

mean[2][5] = data75.mean()[1]
std[2][5] = data75.std()[1]

mean[2][6] = data100.mean()[1]
std[2][6] = data100.std()[1]


#Import data
data2 = pd.read_csv("SO_LIST/mesures_2_clients.csv")
data5 = pd.read_csv("SO_LIST/mesures_5_clients.csv")
data10 = pd.read_csv("SO_LIST/mesures_10_clients.csv")
data25 = pd.read_csv("SO_LIST/mesures_25_clients.csv")
data50 = pd.read_csv("SO_LIST/mesures_50_clients.csv")
data75 = pd.read_csv("SO_LIST/mesures_75_clients.csv")
data100 = pd.read_csv("SO_LIST/mesures_100_clients.csv")

#Calcul de la moyenne et de l'écart type
mean[3][0] = data2.mean()[1]
std[3][0] = data2.std()[1]

mean[3][1] = data5.mean()[1]
std[3][1] = data5.std()[1]

mean[3][2] = data10.mean()[1]
std[3][2] = data10.std()[1]

mean[3][3] = data25.mean()[1]
std[3][3] = data25.std()[1]

mean[3][4] = data50.mean()[1]
std[3][4] = data50.std()[1]

mean[3][5] = data75.mean()[1]
std[3][5] = data75.std()[1]

mean[3][6] = data100.mean()[1]
std[3][6] = data100.std()[1]




#Création du graph
fig = plt.figure()

#Plot de la moyenne et de l'erreur concernant les verrous POSIX
plt.plot(clients, mean[0], color="tab:blue", linewidth=1.0, linestyle="-")
plt.errorbar(clients, mean[0], yerr=std[0], fmt='-o', color="tab:blue")

plt.plot(clients, mean[1], color="tab:red", linewidth=1.0, linestyle="-")
plt.errorbar(clients, mean[1], yerr=std[1], fmt='-o', color="tab:red")

plt.plot(clients, mean[2], color="tab:green", linewidth=1.0, linestyle="-")
plt.errorbar(clients, mean[2], yerr=std[2], fmt='-o', color="tab:green")

plt.plot(clients, mean[3], color="gold", linewidth=1.0, linestyle="-")
plt.errorbar(clients, mean[3], yerr=std[3], fmt='-o', color="gold")

#Modification des limites des axes du graphiques
#plt.xlim(0,8.5)
#plt.ylim(0,2)

#Légende des axes, titre du graphique, grille, légende du graphique
plt.xlabel('# clients')
plt.ylabel('Temps [s]')
plt.title("Comparaison du temps d'exécution")
plt.grid(True)
plt.legend(['SORAND2','SORAND3','SORAND4','SOLIST'], loc = 'upper left')

#Enregistrement de la figure
plt.savefig("Passwords_time.png")
#plt.savefig("Passwords_time.pdf")

plt.show()
#plt.close()