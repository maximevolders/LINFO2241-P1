#!/usr/bin/env python3

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

mean = np.zeros((4,7))
std = np.zeros((4,7))
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
mean[0][0] = data2.mean()['time']
std[0][0] = data2.std()['time']

mean[0][1] = data5.mean()['time']
std[0][1] = data5.std()['time']

mean[0][2] = data10.mean()['time']
std[0][2] = data10.std()['time']

mean[0][3] = data25.mean()['time']
std[0][3] = data25.std()['time']

mean[0][4] = data50.mean()['time']
std[0][4] = data50.std()['time']

mean[0][5] = data75.mean()['time']
std[0][5] = data75.std()['time']

mean[0][6] = data100.mean()['time']
std[0][6] = data100.std()['time']


#Import data
data2 = pd.read_csv("SO_RANDOM_3/mesures_2_clients.csv")
data5 = pd.read_csv("SO_RANDOM_3/mesures_5_clients.csv")
data10 = pd.read_csv("SO_RANDOM_3/mesures_10_clients.csv")
data25 = pd.read_csv("SO_RANDOM_3/mesures_25_clients.csv")
data50 = pd.read_csv("SO_RANDOM_3/mesures_50_clients.csv")
data75 = pd.read_csv("SO_RANDOM_3/mesures_75_clients.csv")
data100 = pd.read_csv("SO_RANDOM_3/mesures_100_clients.csv")

#Calcul de la moyenne et de l'écart type
mean[1][0] = data2.mean()['time']
std[1][0] = data2.std()['time']

mean[1][1] = data5.mean()['time']
std[1][1] = data5.std()['time']

mean[1][2] = data10.mean()['time']
std[1][2] = data10.std()['time']

mean[1][3] = data25.mean()['time']
std[1][3] = data25.std()['time']

mean[1][4] = data50.mean()['time']
std[1][4] = data50.std()['time']

mean[1][5] = data75.mean()['time']
std[1][5] = data75.std()['time']

mean[1][6] = data100.mean()['time']
std[1][6] = data100.std()['time']


#Import data

data2 = pd.read_csv("SO_RANDOM_4/mesures_2_clients.csv")
data5 = pd.read_csv("SO_RANDOM_4/mesures_5_clients.csv")
data10 = pd.read_csv("SO_RANDOM_4/mesures_10_clients.csv")
data25 = pd.read_csv("SO_RANDOM_4/mesures_25_clients.csv")
data50 = pd.read_csv("SO_RANDOM_4/mesures_50_clients.csv")
data75 = pd.read_csv("SO_RANDOM_4/mesures_75_clients.csv")
data100 = pd.read_csv("SO_RANDOM_4/mesures_100_clients.csv")

#Calcul de la moyenne et de l'écart type
mean[2][0] = data2.mean()['time']
std[2][0] = data2.std()['time']

mean[2][1] = data5.mean()['time']
std[2][1] = data5.std()['time']

mean[2][2] = data10.mean()['time']
std[2][2] = data10.std()['time']

mean[2][3] = data25.mean()['time']
std[2][3] = data25.std()['time']

mean[2][4] = data50.mean()['time']
std[2][4] = data50.std()['time']

mean[2][5] = data75.mean()['time']
std[2][5] = data75.std()['time']

mean[2][6] = data100.mean()['time']
std[2][6] = data100.std()['time']


#Import data
data2 = pd.read_csv("SO_LIST/mesures_2_clients.csv")
data5 = pd.read_csv("SO_LIST/mesures_5_clients.csv")
data10 = pd.read_csv("SO_LIST/mesures_10_clients.csv")
data25 = pd.read_csv("SO_LIST/mesures_25_clients.csv")
data50 = pd.read_csv("SO_LIST/mesures_50_clients.csv")
data75 = pd.read_csv("SO_LIST/mesures_75_clients.csv")
data100 = pd.read_csv("SO_LIST/mesures_100_clients.csv")

#Calcul de la moyenne et de l'écart type
mean[3][0] = data2.mean()['time']
std[3][0] = data2.std()['time']

mean[3][1] = data5.mean()['time']
std[3][1] = data5.std()['time']

mean[3][2] = data10.mean()['time']
std[3][2] = data10.std()['time']

mean[3][3] = data25.mean()['time']
std[3][3] = data25.std()['time']

mean[3][4] = data50.mean()['time']
std[3][4] = data50.std()['time']

mean[3][5] = data75.mean()['time']
std[3][5] = data75.std()['time']

mean[3][6] = data100.mean()['time']
std[3][6] = data100.std()['time']



#Création du graph
fig = plt.figure()

#Plot de la moyenne et de l'erreur
plt.plot(clients, mean[0]/1000, color="tab:blue", linewidth=1.0, linestyle="-")
#plt.errorbar(clients, mean[1]/60000, yerr=std[0]/60000, fmt='-.o', color="tab:blue")

plt.plot(clients, mean[1]/1000, color="tab:red", linewidth=1.0, linestyle="-")
#plt.errorbar(clients, mean[0]/60000, yerr=std[1]/60000, fmt='-o', color="tab:blue")

plt.plot(clients, mean[2]/1000, color="tab:green", linewidth=1.0, linestyle="-")
#plt.errorbar(clients, mean[2]/60000, yerr=std[2]/60000, fmt='-.o', color="tab:green")

plt.plot(clients, mean[3]/1000, color="gold", linewidth=1.0, linestyle="-")
#plt.errorbar(clients, mean[3]/60000, yerr=std[3]/60000, fmt='-o', color="tab:green")

#Modification des limites des axes du graphiques
#plt.xlim(0,8.5)
#plt.ylim(-2,20)
#plt.yscale('log')

#Légende des axes, titre du graphique, grille, légende du graphique
plt.xlabel('# clients')
plt.ylabel('Time [sec]')
plt.title("Time of execution depending on\nthe password difficulty")
plt.grid(True)
plt.legend(['2 characters','3 characters','4 characters','Password from list'], loc = 'upper left')

#Enregistrement de la figure
plt.savefig("pwd_diff.png")
#plt.savefig("Passwords_time.pdf")

plt.show()
#plt.close()