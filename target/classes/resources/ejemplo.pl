<<<<<<< HEAD
%
% ARCHIVO: ejemplo.pl
% Maria Esther Vidal y Sandra Zabala
% Dic. 1994
%

:-dynamic(arc/2).
:-dynamic(path/2).


clause1(path(X,Y),arc(X,Y)).

clause1(path(X,Y),(arc(X,Z),path(Z,Y))).

clause1(arc(a,b),true).
clause1(arc(h,a),true).
clause1(arc(a,c),true).
clause1(arc(c,c),true).
clause1(arc(c,d),true).
clause1(arc(b,f),true).
clause1(arc(f,g),true).

=======
%
% ARCHIVO: ejemplo.pl
% Maria Esther Vidal y Sandra Zabala
% Dic. 1994
%

:-dynamic(arc/2).
:-dynamic(path/2).


clause1(path(X,Y),arc(X,Y)).

clause1(path(X,Y),(arc(X,Z),path(Z,Y))).

clause1(arc(a,b),true).
clause1(arc(h,a),true).
clause1(arc(a,c),true).
clause1(arc(c,c),true).
clause1(arc(c,d),true).
clause1(arc(b,f),true).
clause1(arc(f,g),true).

>>>>>>> 1e7055be8a216c6eb52108b2bbe671c1e738835b
