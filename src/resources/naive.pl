%
% ARCHIVO: naive.pl
% EVALUACION NAIVE, Bottom-Up
% Maria Esther Vidal y Sandra Zabala
%


:-dynamic(belongs/2).


tdb:- tdb(0).


tdb(I):- I1 is I+1, clause1(H,B), valid(B,I),
         (\+(belongs(H,I1)) -> assert(H), 
                               assert(belongs(H,I1)), 
			       fail).

tdb(I):- I1 is I+1, belongs(H,I1), \+(belongs(H,I)), tdb(I1).

tdb(_). 

valid(true,_).

valid(A,I):- belongs(A,I).

valid((A,B),I):- valid(A,I), valid(B,I).









