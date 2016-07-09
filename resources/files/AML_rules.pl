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
:-dynamic(eClassClassificationAtt/2).
:-dynamic(eClassVersionAtt/2).
:-dynamic(eClassIRDIAtt/2).


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
%clause1(sameRoleClass(Z,T),(sameAttributeRoleClass(Z,T),sameEClassSpec(X,Y),roleClassRefSem(Z,X),roleClassRefSem(T,Y))).
%clause1(sameRoleClass(X,Y),(sameRoleClass(X,Z),sameRoleClass(Z,Y))).

% Testing
clause1(sameAttributeRoleClass(Z,T),(sameAttribute(X,Y),hasAttribute(Z,X),hasAttribute(T,Y))).

% Same Role Class Lib if the Role Classes are the same 
clause1(sameRoleClassLib(Z,T),(sameRoleClass(X,Y),hasRoleClass(Z,X),hasRoleClass(T,Y))).
clause1(sameRoleClassLib(X,Y),(sameRoleClassLib(X,Z),sameRoleClassLib(Z,Y))). 

% Same Role Class Lib if the Role Classes are the same 
clause1(sameCAEXFile(Z,T),(sameRoleClassLib(X,Y),hasRoleClassLib(Z,X),hasRoleClassLib(T,Y))).
clause1(sameCAEXFile(X,Y),(sameCAEXFile(X,Z),sameCAEXFile(Z,Y))). 

% Attributes related to eClass
clause1(eClassClassificationAtt(X,Y),(hasAttributeName(X,'eClassClassificationClass'),
                                     hasAttributeName(Y,'eClassClassificationClass'),
                                     hasAttributeValue(X,Z),
                                     hasAttributeValue(Y,Z))
                                     ).
                                     
clause1(eClassVersionAtt(X,Y),(hasAttributeName(X,'eClassVersion'),
                              hasAttributeName(Y,'eClassVersion'),
                              hasAttributeValue(X,Z),
                              hasAttributeValue(Y,Z))
                              ).                                     

clause1(eClassIRDIAtt(X,Y),(hasAttributeName(X,'eClassIRDI'),
                              hasAttributeName(Y,'eClassIRDI'),
                              hasAttributeValue(X,Z),
                              hasAttributeValue(Y,Z))
                              ).                                     

clause1(sameRoleClass(Z,T),( eClassClassificationAtt(X,Y),
                             eClassVersionAtt(B,C),
                             eClassIRDIAtt(D,E),
                             hasAttribute(Z,X),
                             hasAttribute(Z,B),
                             hasAttribute(Z,D),
                             hasAttribute(T,Y),
                             hasAttribute(T,C),
                             hasAttribute(T,E)
                             )).

