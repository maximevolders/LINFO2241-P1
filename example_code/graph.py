#!/usr/bin/env python3

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

#Import data POSIX
data = pd.read_csv("mesures.csv")

#Calcul de la moyenne par thread, l'écart type par thread et du nombre de threads POSIX
mean = data1.groupby(["thread"]).mean()["time"]
std = data1.groupby(["thread"]).std()["time"]
threads = data1["thread"].unique()

#Création du graph
fig = plt.figure()

#Plot de la moyenne et de l'erreur concernant les verrous POSIX
plt.plot(threads, mean, color="tab:blue", linewidth=1.0, linestyle="-")
plt.errorbar(threads, mean, yerr=std, fmt='-o', color="tab:blue")

#Modification des limites des axes du graphiques
plt.xlim(0,8.5)
plt.ylim(0,2)

#Légende des axes, titre du graphique, grille, légende du graphique
plt.xlabel('# clients')
plt.ylabel('Temps [s]')
plt.title("Comparaison du temps d'exécution du problème des philosophes\n en fonction du nombre de threads")
plt.grid(True)
plt.legend(['POSIX','Attente active'], loc = 'upper left')

#Enregistrement de la figure
plt.savefig("Philosophes.png")
plt.savefig("Philosophes.pdf")

plt.show()
plt.close()