%
% File: exampleAML.pl
%

:-dynamic(refSemantic/2).
:-dynamic(sameEClassSpec/2).
:-dynamic(hasRoleClassLib/2).
:-dynamic(hasRoleClass/2).
:-dynamic(roleClassRefSem/2).
:-dynamic(classificationClass/2).
:-dynamic(sameClassification/2).
:-dynamic(eClassVersion/2).
:-dynamic(eClassIRDI/2).
:-dynamic(sameEClassVersion/2).
:-dynamic(sameEClassIRDI/2).
:-dynamic(sameAttribute/2).
:-dynamic(sameRoleClass/2).
:-dynamic(sameRoleClassLib/2). 
:-dynamic(sameCAEXFile/2).
:-dynamic(hasAttribute/2).
:-dynamic(sameAttributeRoleClass/2).
:-dynamic(hasCorrespondingAttributePath/2).
:-dynamic(sameRefSemantic/2).
:-dynamic(hasRefSemantic/2).
:-dynamic(hasAttributeName/2).
:-dynamic(hasAttributeValue/2).
:-dynamic(type/2).
:-dynamic(classificationClassAtt/1).
:-dynamic(versionClassAtt/1).


% Attributes are the same if the have the same refSemantic 
clause1(sameAttribute(X,Y),(sameRefSemantic(X,Z),sameRefSemantic(Y,Z))).
clause1(sameAttribute(X,Y),(sameAttribute(X,Z),sameAttribute(Z,Y))).

clause1(sameRefSemantic(X,Y),(hasCorrespondingAttributePath(X,Z),hasCorrespondingAttributePath(Y,Z))).
clause1(sameRefSemantic(X,Y),(sameRefSemantic(X,Z),sameRefSemantic(Z,Y))).

% same eClass Classification 
clause1(sameClassification(X,Y),(classificationClass(X,Z),classificationClass(Y,Z))).
clause1(sameClassification(X,Y),(sameClassification(X,Z),sameClassification(Z,Y))).

% Same eClass Version
clause1(sameEClassVersion(X,Y),(eClassVersion(X,Z),eClassVersion(Y,Z))).
clause1(sameEClassVersion(X,Y),(sameEClassVersion(X,Z),sameEClassVersion(Z,Y))).

% Same eClass IRDI
clause1(sameEClassIRDI(X,Y),(eClassIRDI(X,Z),eClassIRDI(Y,Z))).
clause1(sameEClassIRDI(X,Y),(sameEClassIRDI(X,Z),sameEClassIRDI(Z,Y))).
 
% Same eClass Specification is the combination of eClass IRDI, eClass Classification and eClass Version
clause1(sameEClassSpec(X,Y),(sameClassification(X,Y),sameEClassVersion(X,Y),sameEClassIRDI(X,Y))).
clause1(sameEClassSpec(X,Y),(sameEClassSpec(X,Z),sameEClassSpec(Z,Y))).

% Same Role Class if the eClass Specification is the same
clause1(sameRoleClass(Z,T),(sameAttributeRoleClass(Z,T),sameEClassSpec(X,Y),roleClassRefSem(Z,X),roleClassRefSem(T,Y))).
clause1(sameRoleClass(X,Y),(sameRoleClass(X,Z),sameRoleClass(Z,Y))).

% Testing
clause1(sameAttributeRoleClass(Z,T),(sameAttribute(X,Y),hasAttribute(Z,X),hasAttribute(T,Y))).

% Same Role Class Lib if the Role Classes are the same 
clause1(sameRoleClassLib(Z,T),(sameRoleClass(X,Y),hasRoleClass(Z,X),hasRoleClass(T,Y))).
clause1(sameRoleClassLib(X,Y),(sameRoleClassLib(X,Z),sameRoleClassLib(Z,Y))). 

% Same Role Class Lib if the Role Classes are the same 
clause1(sameCAEXFile(Z,T),(sameRoleClassLib(X,Y),hasRoleClassLib(Z,X),hasRoleClassLib(T,Y))).
clause1(sameCAEXFile(X,Y),(sameCAEXFile(X,Z),sameCAEXFile(Z,Y))). 

clause1(classificationClassAtt(X),(hasAttributeName(X,'eClassClassificationClass'),type(X,attribute))).

clause1(versionClassAtt(X),(hasAttributeName(X,'eClassVersion'),type(X,attribute))).

% CAEX FILE - RoleClassLIb
%clause1(hasRoleClassLib(cAEXFile_1,roleClassLib_1),true).
%clause1(hasRoleClassLib(cAEXFile_2,roleClassLib_2),true).

% The one that is different
%clause1(hasRoleClassLib(cAEXFile_3,roleClassLib_3),true).
 
% RoleClassLIb - Role Class
%clause1(hasRoleClass(roleClassLib_1,roleClass_1),true).
%clause1(hasRoleClass(roleClassLib_2,roleClass_2),true).

%clause1(hasRoleClass(roleClassLib_3,roleClass_3),true).
 
% Role Class - eClass Specification
clause1(roleClassRefSem(roleClass_1,eclassspecification_1),true).
%clause1(roleClassRefSem(roleClass_2,eclassspecification_2),true).
 
%clause1(roleClassRefSem(roleClass_3,eclassspecification_3),true).
  
% eClass specification for role Class 1
%clause1(classificationClass(eclassspecification_1,"27022501"),true). 
%clause1(eClassVersion(eclassspecification_1,"9.0"),true).
%clause1(eClassIRDI(eclassspecification_1,"0173-1#BASIC_1_1#01-ABW077#009"),true).

% eClass specification for role Class 2
clause1(classificationClass(eclassspecification_2,"27022501"),true).
clause1(eClassVersion(eclassspecification_2,"9.0"),true).
clause1(eClassIRDI(eclassspecification_2,"0173-1#BASIC_1_1#01-ABW077#009"),true).

% eClass specification for role Class 2
clause1(classificationClass(eclassspecification_3,"37022501"),true).
clause1(eClassVersion(eclassspecification_3,"9.1"),true).
clause1(eClassIRDI(eclassspecification_3,"0173-1#BASIC_1_1#01-ABW077#008"),true).

clause1(hasAttribute(roleClass_1,attribute_1),true).

clause1(hasAttribute(roleClass_1,attribute_1),true).
clause1(hasAttribute(roleClass_2,attribute_3),true). 

% Attributes   
clause1(hasRefSemantic(attribute_1,refSemantic1),true).
clause1(hasRefSemantic(attribute_2,refSemantic2),true).

clause1(hasCorrespondingAttributePath(refSemantic1,'ECLASS:0173-1#02-BAE122#007'),true).
clause1(hasCorrespondingAttributePath(refSemantic2,'ECLASS:0173-1#02-BAE122#007'),true).

clause1(hasAttributeValue(attribute2,'27022501'),true).
clause1(hasAttributeName(attribute2,'eClassClassificationClass'),true).
clause1(type(attribute2,attribute),true).

clause1(hasAttributeName(attribute1,'eClassVersion'),true).
clause1(hasAttributeValue(attribute1,'9.0'),true).
clause1(type(attribute1,attribute),true).
