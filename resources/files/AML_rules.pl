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
% Finds substring
containsOnly(X,Y) :- forall(sub_atom(X,_,1,_,C), sub_atom(Y,_,1,_,C)).

% Rule:1 Attributes are the same if the have the same refSemantic 
clause1(sameAttribute(X,Y),( hasRefSemantic(X,T),hasRefSemantic(Y,Z),sameRefSemantic(T,Z))).
clause1(sameRefSemantic(X,Y),(hasCorrespondingAttributePath(X,Z),hasCorrespondingAttributePath(Y,Z))).
clause1(sameRefSemantic(X,Y),(sameRefSemantic(X,Z),sameRefSemantic(Z,Y))).

% Rule:2 Generic Attributes are the same if the have the same Name 
clause1(sameAttribute(X,Y),( hasAttributeName(X,Z),hasAttributeName(Y,Z))).


% Rule:3 Generic Attributes are the same if they have the same values
clause1(sameAttribute(X,Y),(  hasAttributeValue(X,Z),
                              hasAttributeValue(Y,Z))
                              ).                                     


% Rule:4 Two AML CAEX files are the same if they have the same path 
clause1(sameExternalReference(X,Y),(refBaseClassPath(X,T),refBaseClassPath(Y,T))).


% Rule:5 Internal Elements are the same if the have the same InternalLink
clause1(sameIdentifier(X,Y),(hasInternalLink(X,T),hasInternalLink(Y,Z),
hasRefPartnerSideA(T,A),hasRefPartnerSideA(Z,A),
hasRefPartnerSideB(T,B),hasRefPartnerSideB(Z,B)
)).

% Rule:6 Generic Elements are the same if the have the same identifier 
clause1(sameId(X,Y),(identifier(X,T),identifier(Y,T))).


% Rule:7 Generic Class are same if they have same eclass,iridi and eclassversion 
%sameRoleClass is just a variable to store result.Not specific to roleClass but all classes.
clause1(sameRoleClass(Z,T),(  
                             eClassClassificationAtt(X,Y),
                             eClassVersionAtt(B,C),
                             eClassIRDIAtt(D,E),
                             hasAttribute(Z,X),
                             hasAttribute(Z,B),
                             hasAttribute(Z,D),
                             hasAttribute(T,Y),
                             hasAttribute(T,C),
                             hasAttribute(T,E)
                             )).


% Rule:8 Generic Class are same if attribute set is same 
clause1(sameInterfaceClass(Z,T),( 
                             type(Z,interfaceClass),
                             type(T,interfaceClass),   
                             hasAttribute(Z,X),
                             hasAttribute(T,Y),
                             sameAttribute(X,Y),
                             sameInterfaceClass(Z,T)
                           
                             )).

 
clause1(sibling(X,Y),(hasAttribute(Z,X),hasAttribute(Z,Y))).



% Attributes related to eClass
clause1(eClassClassificationAtt(X,Y),(hasAttributeName(X,'eClassClassificationClass'),
                                     hasAttributeName(Y,'eClassClassificationClass'),
                                     hasAttributeValue(X,Z),
                                     hasAttributeValue(Y,Z))
                                     ).                                    

clause1(eClassVersionAtt(X,Y),(hasAttributeName(X,'eClassVersion'),
                               hasAttributeName(Y,'eClassVersion'),
                               hasAttributeValue(X,Z),
                               hasAttributeValue(Y,Z),
                               sibling(X,T1),
                               sibling(Y,T2),
                               eClassIRDIAtt(T1,T2)
                               )).                                     




clause1(eClassIRDIAtt(X,Y),(  hasAttributeName(X,'eClassIRDI'),
                              hasAttributeName(Y,'eClassIRDI'),
                              hasAttributeValue(X,Z),
                              hasAttributeValue(Y,Z))
                              ).                                     
