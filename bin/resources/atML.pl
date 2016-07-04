
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
clause1(refSem(h,h1),true).
clause1(refSem(e,h1),true).
clause1(refSem(e,h2),true). 
clause1(refSem(e,h3),true).
clause1(refSem(f,h3),true).
clause1(refSem(f,h4),true).
clause1(hasAtt(c1,c),true).
clause1(hasAtt(c2,a),true).
clause1(hasAtt(c1,h),true).
clause1(hasAtt(c2,e),true).
clause1(hasAtt(c3,c),true).
clause1(hasAtt(c3,a),true).

   