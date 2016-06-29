%
% File: exampleAML.pl
%

:- module(rule_module, []).

%:-dynamic(refSemantic/2).
%:-dynamic(sameAs/2).
%:-dynamic(sameEClassSpec/2).
%:-dynamic(hasRoleClassLib/2).
%:-dynamic(hasRoleClass/2).
%:-dynamic(roleClassRefSem/2).
%:-dynamic(classificationClass/2).
%:-dynamic(sameClassification/2).
%:-dynamic(eClassVersion/2).
%:-dynamic(eClassIRDI/2).
%:-dynamic(sameEClassVersion/2).
%:-dynamic(sameEClassIRDI/2).
%:-dynamic(sameAttribute/2).
%:-dynamic(sameRoleClass/2).
%:-dynamic(sameRoleClassLib/2).
%:-dynamic(sameCAEXFile/2).
%:-dynamic(hasAttribute/2).
%:-dynamic(sameAttributeRoleClass/2).


%amlPredicate('sameCAEXFile').
%amlPredicate('sameRoleClassLib').
%amlPredicate('sameRoleClass').
%amlPredicate('sameEClassSpec').
%amlPredicate('sameClassification').
%amlPredicate('sameEClassVersion').



%H,B) :- rule_module:clause(H,B).


sameAttribute(X,Y) :- refSemantic(X,Z), refSemantic(Y,Z).
sameAttribute(X,Y) :- sameAttribute(X,Z),sameAttribute(Z,Y).

 
% Attributes are the same if the have the same refSemantic 
%sameAttribute(X,Y) :- refSemantic(X,Z),refSemantic(Y,Z).
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




% CAEX FILE - RoleClassLIb
hasRoleClassLib(cAEXFile_1,roleClassLib_1).
hasRoleClassLib(cAEXFile_2,roleClassLib_2).

% The one that is different
hasRoleClassLib(cAEXFile_3,roleClassLib_3).
 
% RoleClassLIb - Role Class
hasRoleClass(roleClassLib_1,roleClass_1).
hasRoleClass(roleClassLib_2,roleClass_2).

hasRoleClass(roleClassLib_3,roleClass_3).
 
% Role Class - eClass Specification
roleClassRefSem(roleClass_1,eclassspecification_1).
roleClassRefSem(roleClass_2,eclassspecification_2).
 
roleClassRefSem(roleClass_3,eclassspecification_3).
  
% eClass specification for role Class 1
classificationClass(eclassspecification_1,"27022501").
eClassVersion(eclassspecification_1,"9.0").
eClassIRDI(eclassspecification_1,"0173-1#BASIC_1_1#01-ABW077#009").

% eClass specification for role Class 2
classificationClass(eclassspecification_2,"27022501").
eClassVersion(eclassspecification_2,"9.0").
eClassIRDI(eclassspecification_2,"0173-1#BASIC_1_1#01-ABW077#009").

% eClass specification for role Class 2
classificationClass(eclassspecification_3,"37022501").
eClassVersion(eclassspecification_3,"9.1").
eClassIRDI(eclassspecification_3,"0173-1#BASIC_1_1#01-ABW077#008").

hasAttribute(roleClass_1,attribute_1).
hasAttribute(roleClass_2,attribute_3).  

% Attributes   
refSemantic(attribute_1,"0173-1#02-BAE069#007").
refSemantic(attribute_2,"0173-1#02-BAE122#006").
refSemantic(attribute_3,"0173-1#02-BAE069#007").  