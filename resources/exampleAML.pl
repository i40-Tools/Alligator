%
% File: exampleAML.pl
%

:-dynamic(refSemantic/2).
:-dynamic(sameAs/2).
:-dynamic(sameEClassSpec/2).
:-dynamic(hasRoleClassLib/2).
:-dynamic(hasRoleClass/2).
:-dynamic(roleClassRefSem/2).
:-dynamic(classificationClass/2).
:-dynamic(eClassVersion/2).
:-dynamic(eClassIRDI/2).


clause1(sameAs(X,Y),(refSemantic(X,Z),refSemantic(Y,Z))).

clause1(sameAs(X,Y),(sameAs(X,Z),sameAs(Z,Y))).

clause1(sameEClassSpec(X,Y),(classificationClass(X,Z),eClassVersion(X,Z),eClassIRDI(X,Z),classificationClass(Y,Z),eClassVersion(Y,Z),eClassIRDI(Y,Z))).


% CAEX FILE - RoleClassLIb
clause1(hasRoleClassLib(cAEXFile_1,roleClassLib_1),true).
clause1(hasRoleClassLib(cAEXFile_2,roleClassLib_2),true).


% RoleClassLIb - Role Class
clause1(hasRoleClass(roleClassLib_1,roleClass_1),true).
clause1(hasRoleClass(roleClassLib_2,roleClass_2),true).

% Role Class - eClass Specification
clause1(roleClassRefSem(roleClass_1,eClassSpecification_1),true).
clause1(roleClassRefSem(roleClass_2,eClassSpecification_2),true).

% eClass specification for role Class 1
clause1(classificationClass(eclassspecification_1,"27022501"),true).
clause1(eClassVersion(eclassspecification_1,"9.0"),true).
clause1(eClassIRDI(eclassspecification_1,"0173-1#BASIC_1_1#01-ABW077#009"),true).

% eClass specification for role Class 2
clause1(classificationClass(eclassspecification_2,"27022501"),true).
clause1(eClassVersion(eclassspecification_2,"9.0"),true).
clause1(eClassIRDI(eclassspecification_2,"0173-1#BASIC_1_1#01-ABW077#009"),true).

% Attributes
clause1(refSemantic(attribute_1,"0173-1#02-BAE069#007"),true).
clause1(refSemantic(attribute_2,"0173-1#02-BAE122#006"),true).
clause1(refSemantic(attribute_3,"0173-1#02-BAE069#007"),true).