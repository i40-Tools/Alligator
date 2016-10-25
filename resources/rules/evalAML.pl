 readFile(X,N):- 
	     open(X,read,Str),
         read(Str,N), 
         close(Str).






 eval :-    
    readFile('resources/files/output.txt',Z),
   
	 eval( semi,
	 	  'AML_rules',
	 	  'AML_facts_role_class',
	 	  'generated_model',
	      Z
%         'd:/Deutch/development/Rules4AMLIntegration/resources/output.txt'
	 ).
 
 lrm :-
  readFile('resources/files/edb.txt',X),
 
	listResultModule('AML_rules',X,generated_model ).
 
 % _____________________________________________________________________________
 
 
 eval(Interpreter,RuleModule,FactModule,ResultModule,File):- 
	declare_amlPredsDynamicInResultModule(RuleModule,FactModule,ResultModule),
	cleanResultModule(RuleModule,FactModule,ResultModule),
 	format( ' *** Storing derived facts into result module ~a ...~n', [ResultModule]),
 	( Interpreter == semi
 	-> build_model_semi(RuleModule,FactModule,ResultModule)
 	;  (  Interpreter == prolog
 	   -> buil_model_prolog
 	   ;  throw( wrong_intepreter_parameter(Interpreter) )
 	   )
 	),
 	format( ' *** completed.~n'),
% 	format( ' *** Contents of result module ~a:~n~n', [ResultModule]),
% 	listResultModule(RuleModule,FactModule,ResultModule), 
 	format( ' *** Writing results to file ~a ...~n', [File]),
 	writePredicates(RuleModule,FactModule,ResultModule,File),
 	format( ' *** completed.~n').

% _____________________________________________________________________________


declare_amlPredsDynamicInResultModule(RuleModule,FactModule,ResultModule) :- 
	( RuleModule:amlPredicate(Name/Arity)
	; FactModule:amlPredicate(Name/Arity)
	),
	   dynamic(ResultModule:Name/Arity),
	fail.
declare_amlPredsDynamicInResultModule(_,_,_) .


cleanResultModule(RuleModule,FactModule,ResultModule) :- 
	( RuleModule:amlPredicate(Name/Arity)
	; FactModule:amlPredicate(Name/Arity)
	),
		functor(Head,Name,Arity),
		retractall(ResultModule:Head),
	fail.
cleanResultModule(_,_,_) .


listResultModule(RuleModule,FactModule,ResultModule) :- 
	( RuleModule:amlPredicate(Name/Arity)
	; FactModule:amlPredicate(Name/Arity)
	),
		listing(ResultModule:Name/Arity),
	fail.
listResultModule(_,_,_) .


% _____________________________________________________________________________


writePredicates(RuleModule,FactModule,ResultModule,FilePath):-
 	setup_call_cleanup(
 		open(FilePath,write,Stream),
 		with_output_to(Stream, 
 		               myWrite(RuleModule,FactModule,ResultModule)
 		),
 		close(Stream)
 	). 
 	
myWrite(RuleModule,FactModule,ResultModule):-
	% Determine which predicates to export to the file:
	( RuleModule:amlPredicate(Name/Arity)
	; FactModule:amlPredicate(Name/Arity)
	),
	  functor(Fact,Name,Arity),  
	  % Write all instances of Fact from the ResultModule to the File: 
	  nl,
	  call(ResultModule:Fact),
	    format('~w.~n',[Fact]), 
	fail.
myWrite(_,_,_).
