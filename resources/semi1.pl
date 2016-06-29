%
% semi.pl
% EVALUADOR SEMI-NAIVE, Bottom-Up
% Maria Esther Vidal y Sandra Zabala
%

:-dynamic(belongs/2).

get_rule(H,B) :- 
	get_rule(rule_module,H,B).  % shortcut when you have just 1 rule module	

get_rule(Module,H,B) :-
	current_predicate(Module:H), 
	H=Functor/Arity, 
	functor(Head,Functor,Arity),
	clause(Module:Head,B).


tdb:- tdb(0),build_model.

tdb(I):- get_rule(H,B), valid(B,I), 
	 (\+(belongs(H,_)) ->  
                   I1 is I+1,  
                   assert(belongs(H,I1)), 
		   fail).

tdb(I):- I1 is I+1, belongs(_,I1), tdb(I1).

tdb(_).

valid(B,I):- valid_db(B,I), valid_delta(B,I).

valid_db(true,_).

valid_db((A,B),I):- valid_db(A,I), valid_db(B,I).

valid_db(A,I):- (belongs(A,I1), I >= I1 ; A).

valid_delta(true,0).

valid_delta((A,B),I):- valid_delta(A,I) ; valid_delta(B,I).

valid_delta(A,I):- (belongs(A,I);A).

build_model:-belongs(H,I),assert(H),retract(belongs(H,I)),fail.
build_model.


buil_full_model :-
	amlPredicate(Name/Arity),
	  functor(Call,Name,Arity),
	  build_model(Call),
	fail.
buil_full_model :- 
	writeln('Finished building model').

% Don't forget to declare H dynamic!!!
build_model(H) :- 
	retractall(generated_model:H),
%	% Rename the predicate
%	H =.. [Functor|Arguments],
%	atom_concat(Functor,'_generated',NewFunctor),
%	Hnew =.. [NewFunctor|Arguments],
	% Compute results and assert them with the new name:
	call(rule_module:H), 
		assert_unique(generated_model:H),
	fail.


	
p(X,Y) :- a(X),b(Y).

a(1).
a(2).
b(a).
b(b).

:- dynamic generated_model:p/2.

assert_unique(Module:Head) :- 
    ( not(call(Module:Head)) 
      -> assert(Module:Head)
      ;  true
    ).
% 










