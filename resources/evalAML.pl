 eval:- assert(amlPredicates(['sameCAEXFile','sameRoleClassLib','sameRoleClass','sameEClassSpec',
                              'sameClassification','sameEClassVersion'])), 
 consult("d:/Deutch/development/Rules4AMLIntegration/resources/semi1.pl"),
 consult("d:/Deutch/development/Rules4AMLIntegration/resources/exampleAML.pl"),
 tdb.
 
 writePredicates:-
 open('d:/Deutch/development/Rules4AMLIntegration/resources/output.txt',write,FileName),
 consult("d:/Deutch/development/Rules4AMLIntegration/resources/writeRules.pl"),
 myWrite(FileName),close(FileName).