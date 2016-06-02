%
% File: exampleAML.pl
%

:-dynamic(refSemantic/2).
:-dynamic(sameAs/2).


clause1(sameAs(X,Y),(refSemantic(X,Z),refSemantic(Y,Z))).

clause1(sameAs(X,Y),(sameAs(X,Z),sameAs(Z,Y))).

clause1(refSemantic(attribute_1,"0173-1#02-BAE069#007"),true).
clause1(refSemantic(attribute_3,"0173-1#02-BAE069#007"),true).
