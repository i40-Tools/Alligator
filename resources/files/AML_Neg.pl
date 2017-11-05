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
:-dynamic(sameInterfaceClass/2).
:-dynamic(type/2).
:-dynamic(sameEClassificationRoleClass/2).
:-dynamic(sameRoleClassLib/2).
:-dynamic(sameSystemUnitClass/2).
:-dynamic(sibling/2).
:-dynamic(concatString/2).
:-dynamic(identifier/2).
:-dynamic(sameIdentifier/2).
:-dynamic(sameId/2).
:-dynamic(refBaseClassPath/2).
:-dynamic(hasInternalElement/2).
:-dynamic(hasInternalLink/2).
:-dynamic(hasRefPartnerSideA/2).
:-dynamic(hasRefPartnerSideB/2).
:-dynamic(sameExternalReference/2).
:-dynamic(sameId/2).
:-dynamic(diffAttribute/2).
:-dynamic(diffIdentifier/2).
:-dynamic(diffIdentifier2/2).
:-dynamic(diffInterfaceClass/2).
:-dynamic(diffRoleClass/2).
:-dynamic(diffRoleClassLib/2). 
:-dynamic(diffeClassClassificationAtt/2).
:-dynamic(diffeClassIRDI/2).
:-dynamic(diffEClassVersion/2).
:-dynamic(diffRoleClassLib/2).


% Negative Rules----------------------------------------------------------

% Rule:1 Attributes not are not the same if they dont have same name
clause1(diffAttribute(X,Y),( 
type(X,'attribute'),
type(Y,'attribute'),
hasAttributeName(X,Z),hasAttributeName(Y,R),
not(Z=R))).

% Rule:2 InterfaceClass Lib Attributes are not the same if they dont have same name
clause1(diffAttribute(X,Y),( 
type(X,'interfaceClassLib'),
type(Y,'interfaceClassLib'),
hasAttributeName(X,Z),hasAttributeName(Y,R),
not(Z=R))).

% Rule:3 InterfaceClass Attributes are not the same if they dont have same name
clause1(diffAttribute(X,Y),( 
type(X,'interfaceClass'),
type(Y,'interfaceClass'),
hasAttributeName(X,Z),hasAttributeName(Y,R),
not(Z=R))).

% Rule:4 InternalElement Attributes  are not the same if they dont have same name
clause1(diffAttribute(X,Y),( 
type(X,'internalElement'),
type(Y,'internalElement'),
hasAttributeName(X,Z),hasAttributeName(Y,R),
not(Z=R))).

% Rule:5 RoleClassLIb Attributes are not the same if they dont have same name
clause1(diffAttribute(X,Y),( 
type(X,'roleClassLib'),
type(Y,'roleClassLib'),
hasAttributeName(X,Z),hasAttributeName(Y,R),
not(Z=R))).

% Rule:6 RoleClass Attributes are not the same if they dont have same name
clause1(diffAttribute(X,Y),( 
type(X,'roleClass'),
type(Y,'roleClass'),
hasAttributeName(X,Z),hasAttributeName(Y,R),
not(Z=R))).

% Rule:7 SystemUnitClassLIb Attributes are not the same if they dont have same name
clause1(diffAttribute(X,Y),( 
type(X,'systemUnitClassLib'),
type(Y,'systemUnitClassLib'),
hasAttributeName(X,Z),hasAttributeName(Y,R),
not(Z=R))).

% Rule:8 SystemUnitClass Attributes are not the same if they dont have same name
clause1(diffAttribute(X,Y),( 
type(X,'systemUnitClass'),
type(Y,'systemUnitClass'),
hasAttributeName(X,Z),hasAttributeName(Y,R),
not(Z=R))).

% Rule:9 IH Attributes are not the same if they dont have same name
clause1(diffAttribute(X,Y),( 
type(X,'instanceHierarchy'),
type(Y,'instanceHierarchy'),
hasAttributeName(X,Z),hasAttributeName(Y,R),
not(Z=R))).

% Rule:10 ER Attributes are not the same if they dont have same name
clause1(diffAttribute(X,Y),( 
type(X,'externalReference'),
type(Y,'externalReference'),
hasAttributeName(X,Z),hasAttributeName(Y,R),
not(Z=R))).

% Rule:11 Attributes are not the same if they dont have same Refsemantic
clause1(diffAttribute(X,Y),( hasRefSemantic(X,T),hasRefSemantic(Y,Z),
hasCorrespondingAttributePath(T,W),
hasCorrespondingAttributePath(Z,R),not(W=R)
)).

% Rule:15 Two AML CAEX files are not the same if they dont have the same path 
clause1(diffAttribute(X,Y),(refBaseClassPath(X,Z),refBaseClassPath(Y,R),
not(Z=R))).


% Rule:16 IE are not the same if they dont have same Ilink
clause1(diffIdentifier2(X,Y),(hasInternalLink(X,T),hasInternalLink(Y,Z),
hasRefPartnerSideA(T,W),hasRefPartnerSideA(Z,P),
hasRefPartnerSideB(T,B),hasRefPartnerSideB(Z,D),
not(W=P),not(B=D)
)).



% Attributes related to eClass
clause1(diffeClassClassificationAtt(X,Y),(hasAttributeName(X,'eClassClassificationClass'),
                                     hasAttributeName(Y,'eClassClassificationClass'),
                                     hasAttributeValue(X,W),
                                     hasAttributeValue(Y,R),
                                     not(W=R)                               
                                     )).

clause1(diffEClassVersion(X,Y),(hasAttributeName(X,'eClassVersion'),
                               hasAttributeName(Y,'eClassVersion'),
                               hasAttributeValue(X,W),
                               hasAttributeValue(Y,R),
                               not(W=R)                               
                               )).                                     



clause1(diffeClassIRDI(X,Y),(hasAttributeName(X,'eClassIRDI'),
                              hasAttributeName(Y,'eClassIRDI'),
                              hasAttributeValue(X,W),
                              hasAttributeValue(Y,R),
                               not(W=R)
                               )).                                     

% Rule:17 RoleClass are not the same if they dont have same eclass,version and irdi 
clause1(diffRoleClass(Z,T),(  
                             type(Z,'roleClass'),
                             type(T,'roleClass'),
                             diffeClassClassificationAtt(X,Y),
                             diffeClassIRDI(B,C),
                             diffEClassVersion(D,E),
                             hasAttribute(Z,X),
                             hasAttribute(Z,B),
                             hasAttribute(Z,D),
                             hasAttribute(T,Y),
                             hasAttribute(T,C),
                             hasAttribute(T,E) 
                             )).

% Rule:18 IfClass are not the same if they dont have same eclass,version and irdi 
clause1(diffRoleClass(Z,T),(  
                             type(Z,'interfaceClass'),
                             type(T,'interfaceClass'),
                             diffeClassClassificationAtt(X,Y),
                             diffeClassIRDI(B,C),
                             diffEClassVersion(D,E),
                             hasAttribute(Z,X),
                             hasAttribute(Z,B),
                             hasAttribute(Z,D),
                             hasAttribute(T,Y),
                             hasAttribute(T,C),
                             hasAttribute(T,E) 
                             )).
                             
% Rule:19 SUClass are not the same if they dont have same eclass,version and irdi 
clause1(diffRoleClass(Z,T),(  
                             type(Z,'systemUnitClass'),
                             type(T,'systemUnitClass'),   
                             diffeClassClassificationAtt(X,Y),
                             diffeClassIRDI(B,C),
                             diffEClassVersion(D,E),
                             hasAttribute(Z,X),
                             hasAttribute(Z,B),
                             hasAttribute(Z,D),
                             hasAttribute(T,Y),
                             hasAttribute(T,C),
                             hasAttribute(T,E) 
                             )).

% Rule:20 ID are not the same if they dont have same ID
clause1(diffIdentifier(X,Y),(
identifier(X,Z),identifier(Y,T),not(Z=T))).

% Rule:21 Attributes not are not the same if they dont have same name
clause1(diffIdentifier(X,Y),(
type(X,'attribute'),
type(Y,'attribute'),
identifier(X,Z),identifier(Y,T),not(Z=T))).

% Rule:22 InterfaceClass Lib Attributes are not the same if they dont have same name
clause1(diffIdentifier(X,Y),(
type(X,'interfaceClassLib'),
type(Y,'interfaceClassLib'),
identifier(X,Z),identifier(Y,T),not(Z=T))).

% Rule:23 InterfaceClass Attributes are not the same if they dont have same name
clause1(diffIdentifier(X,Y),
(type(X,'interfaceClass'),
type(Y,'interfaceClass'),
identifier(X,Z),identifier(Y,T),not(Z=T))).

% Rule:24 InternalElement Attributes  are not the same if they dont have same name
clause1(diffIdentifier(X,Y),(
type(X,'internalElement'),
type(Y,'internalElement'),
identifier(X,Z),identifier(Y,T),not(Z=T))).

% Rule:25 RoleClassLIb Attributes are not the same if they dont have same name
clause1(diffIdentifier(X,Y),(
type(X,'roleClassLib'),
type(Y,'roleClassLib'),
identifier(X,Z),identifier(Y,T),not(Z=T))).

% Rule:26 RoleClass Attributes are not the same if they dont have same name
clause1(diffIdentifier(X,Y),(
type(X,'roleClass'),
type(Y,'roleClass'),
identifier(X,Z),identifier(Y,T),not(Z=T))).

% Rule:27 SystemUnitClassLIb Attributes are not the same if they dont have same name
clause1(diffIdentifier(X,Y),(
type(X,'systemUnitClassLib'),
type(Y,'systemUnitClassLib'),
identifier(X,Z),identifier(Y,T),not(Z=T))).

% Rule:28 SystemUnitClass Attributes are not the same if they dont have same name
clause1(diffIdentifier(X,Y),(
type(X,'systemUnitClass'),
type(Y,'systemUnitClass'),
identifier(X,Z),identifier(Y,T),not(Z=T))).

% Rule:29 IH Attributes are not the same if they dont have same name
clause1(diffIdentifier(X,Y),(
type(X,'instanceHierarchy'),
type(Y,'instanceHierarchy'),
identifier(X,Z),identifier(Y,T),not(Z=T))).

% Rule:30 ER Attributes are not the same if they dont have same name
clause1(diffIdentifier(X,Y),(
type(X,'externalReference'),
type(Y,'externalReference'),
identifier(X,Z),identifier(Y,T),not(Z=T))).








