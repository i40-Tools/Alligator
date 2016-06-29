

writePredicates(FilePath):-
 	setup_call_cleanup(
 		open(FilePath,write,Stream),
 		myWrite(Stream),
 		close(Stream)
 	). 
 	
myWrite(FileName):-
	amlPredicate(Name/Arity),
	  functor(Z,Name,Arity),   %Z=..[P,X,Y],
	  retract(Z),
	  Z=..[P,X,Y],
	  X\==Y,
	  Z1=..[P,Y,X],
	  retract(Z1),
	  writeln(FileName, Z), 
	  writeln(FileName), 
	fail.
myWrite(_).


%writeSRCL(FileName):-sameRoleClassLib(X,Y),X\==Y,Z=..['sameRoleClassLib',X ,Y], writeln(FileName, Z), writeln(FileName), fail.
%writeSRCL(_).   