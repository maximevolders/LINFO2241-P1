import sys


numberOfClients = int(sys.argv[1])
nameOfFile = str(sys.argv[2])

f = open(nameOfFile,'w')
f.write('clientID,time\n')

for i in range(1, numberOfClients+1):
    fileName = "statsClient_" + str(i) + ".csv"
    fc = open(fileName,'r')
    f.write(fc.read())
    fc.close()
f.close()