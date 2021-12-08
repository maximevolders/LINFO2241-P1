import sys

f = open('mesures.csv','w')
f.write('clientID,time\n')
#numberOfClients = int(sys.argv[1])
numberOfClients = 5
for i in range(1, numberOfClients+1):
    fileName = "statsClient_" + str(i) + ".csv"
    fc = open(fileName,'r')
    f.write(fc.read())
    fc.close()
f.close()