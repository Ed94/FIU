murderer(X):- hair(X,brown).

attire(holman,ring ).
attire(pope  ,watch).

attire(woodley,pincenez):- attire(raymond, tattered_cuffs).
attire(raymond,pincenez):- attire(woodley, tattered_cuffs).

attire(X,tattered_cuffs):- room(X,16).

hair(X, black):- room(X, 14).
hair(X, grey ):- room(X, 12).

hair(X, brown):- attire(X, pincenez      ).
hair(X, red  ):- attire(X, tattered_cuffs).

room(holman , 12).
room(raymond, 10).
room(woodley, 16).

room(X,14):- attire(X,watch).






