import os,time
import datetime




def main():
    
    # load database
    DB = open('data.txt', 'r')
    ipsMap = {}
    for i in DB:
        l = []
        l = i.split("=>")
        ipsMap[l[0]] = l[1]
        print l[0],l[1]

    # get initialize state for routers
    os.system('nmap -sP 192.168.43.0/24|grep "192"|cut -d " " -f 6 >ipsSaved.txt')
    
    # check the router state
    while(True):
        attendance=open('attendance.txt','a')
        leaving=open('leaving.txt','a')
        time.sleep(10)
        
        # get new state of router
        os.system('nmap -sP 192.168.43.0/24|grep "192"|cut -d " " -f 6|sort >ipsNew.txt')
        
        # check for attendance
        cpFlag = 0
        if 0 == os.system('grep -v -x -f ipsSaved.txt ipsNew.txt > newConnectors.txt'):
            # record the attendance of all employees            
            newConnectors=open('newConnectors.txt','r')
            
            for i in newConnectors:
                attendance.write(str(ipsMap[i[:-1]][:-1]) + "\t" + str(datetime.datetime.now())+"\n")                     
                #print str(ipsMap[i[:-2]]) + str(datetime.datetime.now())
            cpFlag = 1  
            print "connectors\n"

        # check for leaving
        if 0 == os.system('grep -v -x -f ipsNew.txt ipsSaved.txt > DisConnectors.txt'):
            
            # record the attendance of all employees            
            DisConnectors=open('DisConnectors.txt','r')
            
            for i in DisConnectors:
                leaving.write(str(ipsMap[i[:-1]][:-1]) + "\t" + str(datetime.datetime.now())+"\n")                     
                #print str(ipsMap[i]) + "\t" + str(datetime.datetime.now())
            print "disconnectors\n"
            cpFlag = 1
        
        # update current state
        if cpFlag == 1:
            os.system('cp ipsNew.txt ipsSaved.txt')
        

        print "checking..."                    



if __name__=="__main__":
    main()
