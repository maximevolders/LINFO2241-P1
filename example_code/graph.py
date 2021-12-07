#!/usr/bin/env python3

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

numberOfClients = int(sys.argv[1]))

#Import data POSIX
data1 = pd.read_csv("mesures1posix.csv")

#Calcul de la moyenne par thread, l'écart type par thread et du nombre de threads POSIX
mean1 = data1.groupby(["thread"]).mean()["time"]
std1 = data1.groupby(["thread"]).std()["time"]
threads1 = data1["thread"].unique()

#Import data attente active
data2 = pd.read_csv("mesures1AttAct.csv")

#Calcul de la moyenne par thread, l'écart type par thread et du nombre de threads attente active
mean2 = data2.groupby(["thread"]).mean()["time"]
std2 = data2.groupby(["thread"]).std()["time"]
threads2 = data2["thread"].unique()

#Création du graph
fig1 = plt.figure()

#Plot de la moyenne et de l'erreur concernant les verrous POSIX
plt.plot(threads1, mean1, color="tab:blue", linewidth=1.0, linestyle="-")
plt.errorbar(threads1, mean1, yerr=std1, fmt='-o', color="tab:blue")

#Plot de la moyenne et de l'erreur concernant les verrous par attente active
plt.plot(threads2, mean2, color="tab:red", linewidth=1.0, linestyle="-")
plt.errorbar(threads2, mean2, yerr=std2, fmt='-o', color="tab:red")

#Modification des limites des axes du graphiques
plt.xlim(0,8.5)
plt.ylim(0,2)

#Légende des axes, titre du graphique, grille, légende du graphique
plt.xlabel('# threads')
plt.ylabel('Temps [s]')
plt.title("Comparaison du temps d'exécution du problème des philosophes\n en fonction du nombre de threads")
plt.grid(True)
plt.legend(['POSIX','Attente active'], loc = 'upper left')

#Enregistrement de la figure
plt.savefig("Philosophes.png")
plt.savefig("Philosophes.pdf")

plt.show()
plt.close()