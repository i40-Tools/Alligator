%
% TO BE UPDATED, currently inconsistent with the rest!!!
%
% TODO:
%  - Adapt to separate modules for rules, facts and results.
%  - Implement automated generation of prolog rules that do 
%    not loop on cyclic data. Read the semi-naive rules and
%    expand them appropriatel, then assert the expanded version
%    and just call prolog on the asserted rules.  


% Test call with fixed input and ouptut modules:
buil_model_prolog :-
	buil_model_prolog(rule_module, generated_model).

% Build for all aml-predicates:
buil_model_prolog(RuleModule, ResultModule) :-
	amlPredicate(Name/Arity),
	  functor(Call,Name,Arity),
	  build_model_prolog(RuleModule, ResultModule,Call),
	fail.
buil_model_prolog(_RuleModule, _ResultModule) :- 
	writeln('Finished building model').

% Build just for predicate with head H:
build_model_prolog(RuleModule, ResultModule,H) :- 
	retractall(ResultModule:H),
%	% Rename the predicate
%	H =.. [Functor|Arguments],
%	atom_concat(Functor,'_generated',NewFunctor),
%	Hnew =.. [NewFunctor|Arguments],
	% Compute results and assert them with the new name:
	call(RuleModule:H), 
		assert_unique(ResultModule:H),
	fail.




:- dynamic generated_model:p/2.

assert_unique(Module:Head) :- 
    ( not(call(Module:Head)) 
      -> assert(Module:Head)
      ;  true
    ).
% 


/*
:- dynamic(visited_same_attribute/1) .

same_attribute(X,Y) :- retractall(visited_same_attribute(X)),
                       sameAttribute(X,Y).

sameAttribute(X,Y) :- sameAttribute_simple(X,Y),
                      assert(visited_same_attribute(X)).
sameAttribute(X,Y) :- sameAttribute_simple(X,Z),
                      not(visited_same_attribute(Z)),
                      assert(visited_same_attribute(Z)),
                      sameAttribute(Z,Y).

sameAttribute_simple(X,Y) :- 
	hasRefSemantic(X,Z), 
	hasRefSemantic(Y,Z).
	
generate :-
	rule_module:(
		( retractall(result(X,Y)), 
		  same_attribute(X,Y), 
		  assert(result(X,Y)),
		  fail
		) 
		; listing(result/2)
	).
	
	
p(X,Y) :- a(X),b(Y).

a(1).
a(2).
b(a).
b(b).

*/		