%
% File: exampleAML.pl
%

:- module('AML_rules', []).

amlPredicate(sameAttribute/2).
amlPredicate(sameClassification/2).
amlPredicate(sameEClassVersion/2).
amlPredicate(sameEClassIRDI/2).
amlPredicate(sameEClassSpec/2).
amlPredicate(sameRoleClass/2).
amlPredicate(sameAttributeRoleClass/2).
amlPredicate(sameRoleClassLib/2).
amlPredicate(sameCAEXFile/2).

%H,B) :- rule_module:clause(H,B).


sameAttribute(X,Y) :- hasRefSemantic(X,Z), hasRefSemantic(Y,Z).
sameAttribute(X,Y) :- sameAttribute(X,Z), sameAttribute(Z,Y).


% Attributes are the same if the have the same refSemantic 
%sameAttribute(X,Y) :- hasRefSemantic(X,Z),hasRefSemantic(Y,Z).
%sameAttribute(X,Y) :- sameAttribute(X,Z),sameAttribute(Z,Y).

% same eClass Classification 
sameClassification(X,Y) :- classificationClass(X,Z),classificationClass(Y,Z).
sameClassification(X,Y) :- sameClassification(X,Z),sameClassification(Z,Y).

% Same eClass Version
sameEClassVersion(X,Y) :- eClassVersion(X,Z),eClassVersion(Y,Z).
sameEClassVersion(X,Y) :- sameEClassVersion(X,Z),sameEClassVersion(Z,Y).

% Same eClass IRDI
sameEClassIRDI(X,Y) :- eClassIRDI(X,Z),eClassIRDI(Y,Z).
sameEClassIRDI(X,Y) :- sameEClassIRDI(X,Z),sameEClassIRDI(Z,Y).
 
% Same eClass Specification is the combination of eClass IRDI, eClass Classification and eClass Version
sameEClassSpec(X,Y) :- sameClassification(X,Y),sameEClassVersion(X,Y),sameEClassIRDI(X,Y).
sameEClassSpec(X,Y) :- sameEClassSpec(X,Z),sameEClassSpec(Z,Y).

% Same Role Class if the eClass Specification is the same
sameRoleClass(Z,T) :- sameAttributeRoleClass(Z,T),sameEClassSpec(X,Y),roleClassRefSem(Z,X),roleClassRefSem(T,Y).
sameRoleClass(X,Y) :- sameRoleClass(X,Z),sameRoleClass(Z,Y).

% Testing
sameAttributeRoleClass(Z,T) :- sameAttribute(X,Y),hasAttribute(Z,X),hasAttribute(T,Y).

% Same Role Class Lib if the Role Classes are the same 
sameRoleClassLib(Z,T) :- sameRoleClass(X,Y),hasRoleClass(Z,X),hasRoleClass(T,Y).
sameRoleClassLib(X,Y) :- sameRoleClassLib(X,Z),sameRoleClassLib(Z,Y). 

% Same Role Class Lib if the Role Classes are the same 
sameCAEXFile(Z,T) :- sameRoleClassLib(X,Y),hasRoleClassLib(Z,X),hasRoleClassLib(T,Y).
sameCAEXFile(X,Y) :- sameCAEXFile(X,Z),sameCAEXFile(Z,Y). 



