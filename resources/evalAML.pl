 eval:- 
	declare_amlPredsDynamicInResultModule,
 	% tdb,
 	buil_full_model,
 	writePredicates('d:/Deutch/development/Rules4AMLIntegration/resources/output.txt').

declare_amlPredsDynamicInResultModule :- 
	amlPredicate(Name/Arity),
	   dynamic(generated_model:Name/Arity),
	fail.
declare_amlPredsDynamicInResultModule .
	
amlPredicate(refSemantic/2).
amlPredicate(sameEClassSpec/2).
amlPredicate(hasRoleClassLib/2).
amlPredicate(hasRoleClass/2).
amlPredicate(roleClassRefSem/2).
amlPredicate(classificationClass/2).
amlPredicate(sameClassification/2).
amlPredicate(eClassVersion/2).
amlPredicate(eClassIRDI/2).
amlPredicate(sameEClassVersion/2).
amlPredicate(sameEClassIRDI/2).
amlPredicate(sameAttribute/2).
amlPredicate(sameRoleClass/2).
amlPredicate(sameRoleClassLib/2).
amlPredicate(sameCAEXFile/2).
amlPredicate(hasAttribute/2).
amlPredicate(sameAttributeRoleClass/2).
