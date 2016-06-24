% To write the rules into a file
:-dynamic(listAMLPredicates/1).
:-dynamic(amlPredicates/1).

myWrite(FileName):-listAMLPredicates(P),Z=..[P,X,Y],retract(Z),Z=..[P,X,Y],X\==Y,Z1=..[P,Y,X],retract(Z1),writeln(FileName, Z), writeln(FileName), fail.
myWrite(_).


listAMLPredicates(P):-amlPredicates(L),member(P,L).


%writeSRCL(FileName):-sameRoleClassLib(X,Y),X\==Y,Z=..['sameRoleClassLib',X ,Y], writeln(FileName, Z), writeln(FileName), fail.
%writeSRCL(_).   