%
% semi.pl
% EVALUADOR SEMI-NAIVE, Bottom-Up
% Maria Esther Vidal y Sandra Zabala

% Adaptation to Prolog modules: 
% Günter Kniesel, 7.7.2016

:- module( semi_naive, [
			build_model_semi/3  % (RuleModule,FactModule,ResultModule)
			] 
).




	
:-dynamic(belongs/2). 

build_model_semi(RuleModule,FactModule,ResultModule):-
	% Generate belongs/2 facts via bottom-up evaluation:
	once(tdb(RuleModule,FactModule)),
	% Copy the facts without the iteration number to the result module
	belongs(H,_),
		ResultModule:assert(H),
	fail.
build_model_semi(_,_,_) :-
	true.



tdb(RuleModule,FactModule):- 
	retractall(belongs(_,_)),      % Cleanup before starting!
	tdb(RuleModule,FactModule,0).  

tdb(RuleModule,FactModule,I):- 
	% Get rule or fact from the respective module:
	( get_clause(FactModule,H,B)  % Be smart, start with the facts ;-)  
	; get_clause(RuleModule,H,B)  % ... then continue with the rules 
	), 
	   % Solve the body, possibly further instantiating H: 
	   valid(B,I),
	   % Delta step: Store H for the next iteration only
	   % if H was not derived before: 
	   ( \+ belongs(H,_)
	   -> ( I1 is I+1,  
            assert(belongs(H,I1))
          )
       ;  true  % do nothing for duplicates
       ), 
	fail.  % backtrack, iterating over all rules and facts.

% When all rules and facts have been consumed by the previous clause
% start the next iteration, provided at least one new fact has been
% created in the current iteration:
tdb(RuleModule,FactModule,I):-
	I1 is I+1, 
	once(belongs(_,I1)),  % Use once/1 to avoid unintended backtracking
	tdb(RuleModule,FactModule,I1).

tdb(_,_,_).


% I don't understand the following rules and would apreciate 
% if Maria would have time to explain me what they should do -- Guenter:

valid(B,I):- valid_db(B,I), valid_delta(B,I).

valid_db(true,_).
valid_db((A,B),I):- valid_db(A,I), valid_db(B,I).
valid_db(A,I):-     belongs(A,I1), I >= I1.

valid_delta(true,0).
valid_delta((A,B),I):- valid_delta(A,I) ; valid_delta(B,I).
valid_delta(A,I):-     belongs(A,I).










