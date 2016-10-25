:- consult('evalAML').                     % top-level eval/0 and eval/5 predicate

:- consult(generate_model_seminaive).      % semi naive evaluation meta-interpreter
:- consult(generate_model_via_prolog).     % partial evaluation of semi naive interpreter
                                           % yielding executable prolog clauses
 
:- consult('AML/__load_AML.pl').             % AML rules and facts

% ________________________________________________________________
% General utilities
%  
% Get a clause contianed in (not imported into) the module Module: 
get_clause(Module,Head,Body) :-
	current_predicate(Module:F/N), 
	functor(Head,F,N),
    \+ predicate_property(Module:Head, imported_from(_)), % not imported
	clause(Module:Head,Body).
