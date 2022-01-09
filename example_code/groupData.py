import sys


numberOfClients = int(sys.argv[1])
nameOfFile = str(sys.argv[2])

f = open(nameOfFile,'w')
f.write('clientID,time\n')

not_todo = [20, 32, 52, 54]

for i in range(1, numberOfClients+1):
    if i not in not_todo:
        fileName = "statsClient_" + str(i) + ".csv"
        fc = open(fileName,'r')
        f.write(fc.read())
        fc.close()
f.close()