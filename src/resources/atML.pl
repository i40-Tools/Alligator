
:-dynamic(type/2).
:-dynamic(refSem/2).
:-dynamic(hasAtt/2).
:-dynamic(sameAs/2).


clause1(sameAs(X,Y),(refSem(X,Z),refSem(Y,Z))).

clause1(sameAs(X,Y),(sameAs(X,Z),sameAs(Z,Y))).

clause1(hasAtt(X,Y),(hasAtt(X,Z),sameAs(Z,Y))).

clause1(refSem(a,b),true).
clause1(refSem(c,b),true).
clause1(refSem(c,d),true).
clause1(refSem(h,d),true).
clause1(hasAtt(c1,c),true).
clause1(hasAtt(c2,a),true).

 